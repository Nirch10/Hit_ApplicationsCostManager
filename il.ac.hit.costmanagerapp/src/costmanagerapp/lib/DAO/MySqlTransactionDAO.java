package costmanagerapp.lib.DAO;

import com.sun.istack.internal.NotNull;
import costmanagerapp.lib.Models.Transaction;
import costmanagerapp.lib.QueryUtils.*;
import costmanagerapp.lib.UsersPlatformException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlTransactionDAO implements ITransactionDAO {

    public String driver = "com.mysql.jdbc.Driver";
    public String connectionString = "jdbc:mysql://localhost:3306/costmanager";
    private String sqlUser = "costmanager";
    private String sqlPassword = "123456";
    private String transactionsTable = "transactions";
    private String guidColumn = "Guid";
    private String isIncomeColumn = "IsIncome";
    private String descriptionColumn = "Description";
    private String dateOfTransactionColumn = "DateOfTransaction";
    private String priceColumn = "Price";
    private String userGuidColumn = "UserGuid";
    private String retailGuidColumn = "RetailGuid";
    private IRetailDAO retailDAO;
    private IUsersDAO userDAO;
    private IQueryExecuter<Transaction> executor;
    private AbstractDbConnector dbConnector;

    public MySqlTransactionDAO() throws ClassNotFoundException {this(new MySqlUserDAO(), new HnetMySqlRetailsDAO(), new HnetMySqlQueryExecuter<>(),
            new MySqlDbConnector("jdbc:mysql://localhost:3306/costmanager", "costmanager", "123456"));}

    public MySqlTransactionDAO(IUsersDAO userDAO, IRetailDAO retailDAO, IQueryExecuter<Transaction> queryExecutor, AbstractDbConnector connector) throws ClassNotFoundException {
        Class.forName(driver);
        this.retailDAO = retailDAO;
        this.userDAO = userDAO;
        executor = queryExecutor;
        dbConnector = connector;
    }

    private Transaction getTransactionFromResultSet(ResultSet rs) throws SQLException, UsersPlatformException {
        Transaction transaction = new Transaction(rs.getInt(guidColumn), rs.getBoolean(isIncomeColumn), rs.getDouble(priceColumn),
                retailDAO.getRetail(rs.getInt(retailGuidColumn)),
                userDAO.getUser(rs.getInt(userGuidColumn)),rs.getDate(dateOfTransactionColumn),
                rs.getString(descriptionColumn));
        return transaction;
    }

    private ResultSet executeQueryGET(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionString, sqlUser, sqlPassword);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }
    private boolean executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionString, sqlUser, sqlPassword);
        Statement statement = connection.createStatement();
        boolean rs = statement.execute(query);
        return rs;
    }

    @Override
    public Transaction getTransaction(int transactionId) throws UsersPlatformException {
        Transaction transaction = null;
        try {
            String stringQuery=("SELECT * FROM " + transactionsTable + " WHERE "+userGuidColumn+"=" + transactionId);
            ResultSet rs = executeQueryGET(stringQuery);
            if (!rs.next()) {
                throw new UsersPlatformException("Transaction does not exist / empty");
            }
            transaction = getTransactionFromResultSet(rs);
            } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public Collection<Transaction> getTransactionByUser(int userId) throws UsersPlatformException {
        Collection<Transaction> transactions = new ArrayList<>();
        try {
            ResultSet rs = executeQueryGET("SELECT * FROM " + transactionsTable + " WHERE "+userGuidColumn+"=" + userId);
            while (rs.next())
                transactions.add(getTransactionFromResultSet(rs));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        return transactions;
    }
    @Override
    public Collection<Transaction> getTransactionByRetail(@NotNull int retailId) throws UsersPlatformException {
        Collection<Transaction> transactions = new ArrayList<>();
        try {
            ResultSet rs = executeQueryGET("SELECT * FROM " + transactionsTable + " WHERE "+retailGuidColumn+"=" + retailId);
            while (rs.next())
                transactions.add(getTransactionFromResultSet(rs));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        return transactions;
    }

    @Override
    public Collection<Transaction> getTransactionByDateRange(@NotNull LocalDate fromDate,@NotNull LocalDate toDate) throws UsersPlatformException {
        Collection<Transaction> transactions = new ArrayList<>();
        try {
            ResultSet rs = executeQueryGET("SELECT * FROM " + transactionsTable + " WHERE "+ dateOfTransactionColumn +" BETWEEN '" +
                    fromDate + "' and '" + toDate + "' ORDER BY " + dateOfTransactionColumn + " desc");
            while (rs.next())
                transactions.add(getTransactionFromResultSet(rs));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        return transactions;
    }

    @Override
    public Collection<Transaction> getTransactionByPriceRange(@NotNull double fromPrice,@NotNull double toPrice) throws UsersPlatformException {
        Collection<Transaction> transactions = new ArrayList<>();
        try {
            ResultSet rs = executeQueryGET("SELECT * FROM " + transactionsTable + " WHERE "+ priceColumn +" BETWEEN " +
                    fromPrice + " and " + toPrice + " ORDER BY " + priceColumn + " desc");
            while (rs.next())
                transactions.add(getTransactionFromResultSet(rs));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        return transactions;
    }

    @Override
    public void updateTransaction(@NotNull int guid, @NotNull boolean isIncome, @NotNull double price, @NotNull String description,
                                  @NotNull int retailGuid, @NotNull LocalDate dateOT) throws UsersPlatformException {
        try {
            executeQuery("UPDATE " + transactionsTable +
                    " SET "+ isIncomeColumn + "=" + isIncome +
                    ", " + priceColumn + "=" + price +
                    ", " + descriptionColumn + "='" + description +
                    "', " + retailGuidColumn + "=" + retailGuid +
                    ", " + dateOfTransactionColumn + "='" + dateOT +
                    "' WHERE " + guidColumn + "=" + guid);
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
    }
    @Override
    public void insertTransaction(Transaction transaction) throws SQLException, UsersPlatformException {
//        executor.TryExecuteInsertQuery(dbConnector, "INSERT INTO transactions ("+guidColumn+", "+isIncomeColumn+", " +
//                dateOfTransactionColumn + ", " + priceColumn + ", " +descriptionColumn +", " +
//                retailGuidColumn + ", " + userGuidColumn+") Values("+transaction.getGuid()+", "+transaction.getIsIncome() + ", \""+
//                transaction.getDateOfTransaction() + "\", " + transaction.getPrice() +", \""+ transaction.getDescription() +
//                "\","+transaction.getRetail().getGuid()+"," + transaction.getUser().getGuid() +")");
    }

    @Override
    public void deleteTransaction(int guid) throws UsersPlatformException, SQLException {
//        executor.TryExecuteDeleteQuery(dbConnector, "DELETE FROM transactions WHERE "+guidColumn+" = "+ guid);
    }

    @Override
    public void deleteUserTransactions(int userGuid) throws SQLException {
//        executor.TryExecuteDeleteQuery(dbConnector, "DELETE FROM transactions WHERE "+userGuidColumn+" = "+ userGuid);
    }
    @Override
    public void deleteRetailTransactions(int retailGuid) throws SQLException{
//        executor.TryExecuteUpdateQuery(dbConnector, "UPDATE "+ transactionsTable + " SET "+retailGuidColumn +
//                "= "+ 0 + "  WHERE "+retailGuidColumn+" = "+ retailGuid);
    }
}
