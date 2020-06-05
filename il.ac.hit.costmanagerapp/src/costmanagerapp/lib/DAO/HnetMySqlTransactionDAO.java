package costmanagerapp.lib.DAO;

import com.sun.istack.internal.NotNull;
import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.Models.Transaction;
import costmanagerapp.lib.Models.User;
import costmanagerapp.lib.QueryUtils.AbstractDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlQueryExecuter;
import costmanagerapp.lib.QueryUtils.IQueryExecuter;
import costmanagerapp.lib.UsersPlatformException;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public class HnetMySqlTransactionDAO implements ITransactionDAO {
    private String transactionsTable = "transactions";
    private String guidColumn = "Guid";

    private IRetailDAO retailDAO;
    private IUsersDAO userDAO;
    private IQueryExecuter<Transaction> executor;
    private AbstractDbConnector dbConnector;
    private Transaction TransactionClass;


    private final String configFilePath = "C:\\code\\Hit_ApplicationsCostManager\\il.ac.hit.costmanagerapp\\out\\production\\il.ac.hit.costmanagerapp\\costmanagerapp\\lib\\Models\\hibernate.cfg.xml";

    public HnetMySqlTransactionDAO() throws ClassNotFoundException {this(new HnetMySqlQueryExecuter(),
            new MySqlUserDAO(),new HnetMySqlRetailsDAO(),null);}

    public HnetMySqlTransactionDAO(@NotNull IQueryExecuter iQueryExecuter,IUsersDAO iUsersDAO, IRetailDAO iRetailDAO,
                                   AbstractDbConnector abstractDbConnector){
        retailDAO = iRetailDAO;
        userDAO = iUsersDAO;
        executor = iQueryExecuter;
        TransactionClass = new Transaction();
        if (abstractDbConnector != null)
            dbConnector = abstractDbConnector;
        else
            dbConnector = new HnetMySqlDbConnector(new File(configFilePath));
    }

    @Override
    public Transaction getTransaction(int transactionId) throws UsersPlatformException {
        String stringQuery = ("SELECT * FROM " + transactionsTable + " WHERE "+ guidColumn +"=" + transactionId);
        Collection<Transaction> results = executor.tryExecuteGetQuery(dbConnector, stringQuery, TransactionClass.getClass());
        if(results == null)
            throw new UsersPlatformException("No transaction found");
        if(results.size() <= 0)
            throw new UsersPlatformException("Not valid ID");
        return results.stream().findFirst().get();
    }

    @Override
    public Collection<Transaction> getTransactionByUser(int userId) throws UsersPlatformException {
        return null;
    }

    @Override
    public Collection<Transaction> getTransactionByRetail(int retailId) throws UsersPlatformException {
        return null;
    }

    @Override
    public Collection<Transaction> getTransactionByDateRange(LocalDate from, LocalDate to) throws UsersPlatformException {
        return null;
    }

    @Override
    public Collection<Transaction> getTransactionByPriceRange(double fromPrice, double toPrice) throws UsersPlatformException {
        return null;
    }

    @Override
    public void updateTransaction(int guid, boolean isIncome, double price, String description, int retailGuid, LocalDate dateOT) throws UsersPlatformException {

    }

    @Override
    public void insertTransaction(Transaction transaction) throws SQLException, UsersPlatformException {

    }

    @Override
    public void deleteTransaction(int guid) throws UsersPlatformException, SQLException {

    }

    @Override
    public void deleteUserTransactions(int userGuid) throws SQLException {

    }

    @Override
    public void deleteRetailTransactions(int retailGuid) throws SQLException {

    }
}
