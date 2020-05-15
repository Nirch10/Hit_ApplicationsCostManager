package costmanagerapp.lib;

import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlTransactionDAO implements ITransactionDAO {

    public String driver = "com.mysql.jdbc.Driver";
    public String connectionString = "jdbc:mysql://localhost:3306/costmanager";
    private String sqlUser = "costmanager";
    private String sqlPassword = "123456";
    private String guidColumn = "Guid";
    private String isIncomeColumn = "IsIncome";
    private String descriptionColumn = "Description";
    private String dateOfTransactionColumn = "DateOfTransaction";
    private String priceColumn = "Price";
    private String userGuidColumn = "UserGuid";
    private String retailGuidColumn = "RetailGuid";

    public MySqlTransactionDAO() throws ClassNotFoundException {
        Class.forName(driver);
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
            ResultSet rs = executeQueryGET("SELECT * FROM transction WHERE transactionid =" + transactionId);
            if (!rs.next()) {
                throw new UsersPlatformException("Transaction does not exist / empty");
            }
            transaction = new Transaction(rs.getInt(guidColumn), rs.getBoolean(isIncomeColumn), rs.getDouble(descriptionColumn), new RetailType()
                    rs.getUser(), rs.getDateOfTransaction(), rs.getDescription());

        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public Collection<Transaction> getTransactions(int guid) throws UsersPlatformException {
        Collection<Transaction> transacrions = new ArrayList<>();
        try {
            ResultSet rs = executeQueryGET("SELECT * FROM transction WHERE Guid =" + gui);
            if (!rs.next()) {
                throw new UsersPlatformException("Transaction does not exist / empty");
            }
            else {
                do {
                    transacrions.add(new Transaction(rs.getGuid(guidColumn), rs.getIsIncome(), rs.getPrice(), rs.getRetail(),
                            rs.getUser(), rs.getDateOfTransaction(), rs.getDescription()));
                } while (rs.next());
            }
//            while (rs.next())
//                transacrions.add(new TransactionType(rs.getInt(guidColumn), rs.getString(typeColumn)));
        }
        catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return transacrions;
    }

    @Override
    // !!
    //TODO: go over the parameters and fit them as in the DB and according to their appearence in RetailType table
    // also for all the below there is might need to create transactionID so we can update transaction
    // !!
    public void updateTransactionAll(int guid, boolean isIncome, double price, String description,
                                  String retailName, LocalDate sdf) throws SQLException {
        execute("UPDATE transaction SET "+typeColumn+" = \"" + newName + "\" WHERE "+guidColumn+" = " + guid);
    }

    public void updateTransactionIsIncome(int guid, boolean isIncome) throws SQLException{

    }

    public void updateTransactionPrice(int guid, double price) throws SQLException{

    }

    public void updateTransactionDescription(int guid, String description) throws SQLException{

    }

    public void updateTransactionRetailName(int guid, String retailName) throws SQLException{

    }

    public void updateTransactionDate(int guid, LocalDate sdf) throws SQLException{

    }

    @Override
    public Collection<Transaction> getTransaction() throws UsersPlatformException {
        return null;
    }

    @Override
    public void updateTransaction(int guid, boolean isIncome, float price, String description, String retailName, LocalDate sdf) throws SQLException {

    }

    @Override
    // !!
    // TODO: make sure that we transfer the variables to the function that we have some default value that is fine by us, by our definition
    // !!
    public void insertTransaction(int guid, boolean isIncome, double price, String description,
                                     String retailName, LocalDate sdf) throws SQLException {
        execute("INSERT INTO transaction ("+guidColumn+", "+IsIncome+", "+price+", "+Description+", "+RetailName+", "+DateOfTransact+", "+ion+") " +
                "Values("+transaction.getGuid() + ", \""+transaction.getIsIncome() + ", \""+transaction.getPrice() +
                ", \""+transaction.getRetail() + ", \""+transaction.getDate() + ", \""+transaction.getDescription() + "\")");
    }


    @Override
    public void deleteTransaction(int guid) throws UsersPlatformException {
        try {
            execute("DELETE FROM transactions WHERE RetailGuid = "+ guid);
        } catch (SQLException e) {
            throw new UsersPlatformException();
        }
    }
}
