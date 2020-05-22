package costmanagerapp.lib;

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

    public MySqlTransactionDAO() throws ClassNotFoundException {this(new MySqlUserDAO(), new MySqlRetailDAO());}

    public MySqlTransactionDAO(IUsersDAO userDAO, IRetailDAO retailDAO) throws ClassNotFoundException {
        Class.forName(driver);
        this.retailDAO = retailDAO;
        this.userDAO = userDAO;
    }

    private Transaction getTransactionFromResultSet(ResultSet rs) throws SQLException, UsersPlatformException {
        Transaction transaction = new Transaction(rs.getInt(guidColumn), rs.getBoolean(isIncomeColumn), rs.getDouble(priceColumn),
                retailDAO.getRetail(rs.getInt(retailGuidColumn)),
                userDAO.GetUser(rs.getInt(userGuidColumn)),rs.getDate(dateOfTransactionColumn).toLocalDate(),
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
    public Collection<Transaction> getUserTransactions(int userId) throws UsersPlatformException {
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
/*
    @Override
    // !!
1    //TODO: go over the parameters and fit them as in the DB and according to their appearence in RetailType table
    // also for all the below there is might need to create transactionID so we can update transaction
    // !!
    public void updateTransactionAll(int guid, boolean isIncome, double price, String description,
                                  String retailName, LocalDate sdf) throws SQLException {
        execute("UPDATE transaction SET "+typeColumn+" = \"" + newName + "\" WHERE "+guidColumn+" = " + guid);
    }

    @Override
    // !!
    // TODO: make sure that we transfer the variables to the function that we have some default value that is fine by us, by our definition
    // !!
    public void insertTransaction(Transaction trans) throws SQLException {
        execute("INSERT INTO transaction ("+guidColumn+", "+IsIncome+", "+price+", "+Description+", "+RetailName+", "+DateOfTransact+", "+ion+") " +
                "Values("+transaction.getGuid() + ", \""+transaction.getIsIncome() + ", \""+transaction.getPrice() +
                ", \""+transaction.getRetail() + ", \""+transaction.getDate() + ", \""+transaction.getDescription() + "\")");
    }

    @Override
    public void deleteTransaction(int guid) throws UsersPlatformException {
        try {
            execute("DELETE FROM transactionsTable WHERE RetailGuid = "+ guid);
        } catch (SQLException e) {
            throw new UsersPlatformException();
        }
    }*/


/*TODO: - getTransactionByDateRange
    - getTransactionByRetail
    - getTransactionByPriceRange
 */
}
