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
import org.hibernate.Hibernate;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class HnetMySqlTransactionDAO implements ITransactionDAO {
    private String transactionsTable = "transactions";
    private String dateOfTransactionColumn = "DateOfTransaction";
    private String guidColumn = "Guid";
    private String priceColumn = "Price";
    private IQueryExecuter<Transaction> executor;
    private AbstractDbConnector dbConnector;
    private Transaction TransactionClass;


    private final String configFilePath = "C:\\code\\Hit_ApplicationsCostManager\\il.ac.hit.costmanagerapp\\out\\production\\il.ac.hit.costmanagerapp\\costmanagerapp\\lib\\Models\\hibernate.cfg.xml";
    private String userGuidColumn = "UserGuid";
    private String retailGuidColumn = "RetailGuid";

    public HnetMySqlTransactionDAO() {this(new HnetMySqlQueryExecuter() ,null);}

    public HnetMySqlTransactionDAO(@NotNull IQueryExecuter iQueryExecuter,
                                   AbstractDbConnector abstractDbConnector){
        executor = iQueryExecuter;
        TransactionClass = new Transaction();
        if (abstractDbConnector != null)
            dbConnector = abstractDbConnector;
        else
            dbConnector = new HnetMySqlDbConnector(new File(configFilePath));
    }

    private Collection<Transaction> getTransaction(String query) throws UsersPlatformException {
        executor.openConnection(dbConnector);
        Collection<Transaction> results  = executor.tryExecuteGetQuery(dbConnector, query, TransactionClass.getClass());

        if(results == null)
            throw new UsersPlatformException("No transaction found");
        if(results.size() <= 0)
            throw new UsersPlatformException("Not valid ID");
        return results;
    }

    @Override
    public Transaction getTransaction(int transactionId) throws UsersPlatformException {
        executor.openConnection(dbConnector);
        String stringQuery = ("SELECT * FROM " + transactionsTable + " WHERE "+ guidColumn +"=" + transactionId);
        Collection<Transaction> results = getTransaction(stringQuery);
        executor.closeConnection();
        return results.stream().findFirst().get();
    }

    @Override
    public Collection<Transaction> getTransactionByUser(int userId) throws UsersPlatformException {
        String stringQuery = ("SELECT * FROM " + transactionsTable + " WHERE "+ userGuidColumn+"=" + userId);
        return getListOfTranactions(stringQuery);

    }

    @Override
    public Collection<Transaction> getTransactionByRetail(int retailId) throws Exception {
        String stringQuery = ("SELECT * FROM " + transactionsTable + " WHERE "+ retailGuidColumn+"=" + retailId);
        return getListOfTranactions(stringQuery);
    }

    private Collection<Transaction> getListOfTranactions(String query) throws UsersPlatformException {
        executor.openConnection(dbConnector);
        Collection<Transaction> results =executor.tryExecuteGetQuery(dbConnector, query, TransactionClass.getClass());
        results.forEach(r ->{
            Hibernate.initialize(r.getUser());
            Hibernate.initialize(r.getRetail());
        });
        executor.closeConnection();
        return results;
    }

    @Override
    public Collection<Transaction> getTransactionByDateRange(Date from, Date to) throws UsersPlatformException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String stringQuery = ("SELECT * FROM " + transactionsTable + " WHERE "+ dateOfTransactionColumn +" BETWEEN '" +
                formatter.format(from)+ "' and '" + formatter.format(to)+ "' ORDER BY " + dateOfTransactionColumn + " desc");
        Collection<Transaction> results = getListOfTranactions(stringQuery);
        return results;
    }

    @Override
    public Collection<Transaction> getTransactionByPriceRange(double fromPrice, double toPrice) throws UsersPlatformException {
        String stringQuery = ("SELECT * FROM " + transactionsTable + " WHERE "+ priceColumn +" BETWEEN '" +
                fromPrice + "' and '" + toPrice + "' ORDER BY " + dateOfTransactionColumn + " desc");
        return getListOfTranactions(stringQuery);
    }

    @Override
    public void updateTransaction(int guid, boolean isIncome, double price, String description, int retailGuid, LocalDate dateOT) throws UsersPlatformException {

    }

    @Override
    public void insertTransaction(Transaction transaction) throws SQLException, UsersPlatformException {
        executor.openConnection(dbConnector);
        if (executor.TryExecuteInsertQuery(dbConnector, transaction) == false)
        {
            executor.closeConnection();
            throw new UsersPlatformException("Could not update Retail");
        }
        executor.closeConnection();
    }

    @Override
    public void deleteTransaction(int guid) throws UsersPlatformException, SQLException {
        Transaction rt = getTransaction(guid);
        executor.openConnection(dbConnector);
        if (executor.TryExecuteDeleteQuery(dbConnector,rt) == false)
        {
            executor.closeConnection();
            throw new UsersPlatformException("Could not update Retail");
        }
        executor.closeConnection();
    }

    @Override
    public void deleteUserTransactions(int userGuid) throws UsersPlatformException {
        Collection<Transaction> rt = getTransactionByUser(userGuid);
        AtomicBoolean foundError = new AtomicBoolean(false);
        executor.openConnection(dbConnector);
        rt.forEach(r ->{
                r.setDateOfTransaction(new Date(0,0,0));
                r.setUser(new User(0, "","",""));
            try {
                executor.TryExecuteUpdateQuery(dbConnector, r);
            } catch (SQLException e) {
                foundError.set(true);
            }
        });
        if(foundError.get())throw new UsersPlatformException("Error updating transaction row ");
        executor.closeConnection();
    }

    @Override
    public void deleteRetailTransactions(int retailGuid) throws Exception {
        Collection<Transaction> rt = getTransactionByRetail(retailGuid);
        executor.openConnection(dbConnector);
        rt.forEach(r ->{
            try {
                Transaction t1 = new Transaction(r.getGuid(), r.isIncome(), r.getPrice(), new RetailType(0, "None"),
                        new User(r.getUser().getUserName(), r.getUser().getEmail(), r.getUser().getPassword()),
                        r.getDateOfTransaction(), r.getDescription());
                executor.TryExecuteDeleteQuery(dbConnector, r);
                insertTransaction(t1);
//                executor.TryExecuteUpdateQuery(dbConnector,t1);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UsersPlatformException e) {
                e.printStackTrace();
            }
        });
        executor.closeConnection();
    }
}
