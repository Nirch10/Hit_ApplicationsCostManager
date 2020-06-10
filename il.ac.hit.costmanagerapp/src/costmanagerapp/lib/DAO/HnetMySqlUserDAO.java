package costmanagerapp.lib.DAO;

import com.sun.istack.internal.NotNull;
import costmanagerapp.lib.Models.User;
import costmanagerapp.lib.QueryUtils.AbstractDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlQueryExecuter;
import costmanagerapp.lib.QueryUtils.IQueryExecuter;
import costmanagerapp.lib.UsersPlatformException;
import java.io.File;
import java.sql.SQLException;
import java.util.Collection;

public class HnetMySqlUserDAO implements IUsersDAO {
    private String tableName = "user";
    private String guidColumn = "Guid";
    private IQueryExecuter<User> executor;
    private AbstractDbConnector dbConnector;
    private ITransactionDAO transactionDAO;
    private User userType;
    private final String filePath =
            "C:\\code\\Hit_ApplicationsCostManager\\il.ac.hit.costmanagerapp\\out\\production\\il.ac.hit.costmanagerapp\\costmanagerapp\\lib\\Models\\hibernate.cfg.xml";

    public HnetMySqlUserDAO(){this(new HnetMySqlQueryExecuter<>(), new HnetMySqlTransactionDAO(),  null);}
    public HnetMySqlUserDAO(@NotNull IQueryExecuter<User> queryExecutor,
                            @NotNull ITransactionDAO inputTransactionDAO, AbstractDbConnector connector){
        executor = queryExecutor;
        transactionDAO = inputTransactionDAO;
        userType =  new User();
        if(connector != null)
            dbConnector = connector;
        else
            dbConnector = new HnetMySqlDbConnector(new File(filePath));
    }

    @Override
    public User getUser(int userGuid) throws UsersPlatformException {
        executor.openConnection(dbConnector);
        Collection<User> rs = executor.tryExecuteGetQuery(dbConnector, "SELECT * FROM " + tableName +
                " WHERE "+guidColumn+" =" + userGuid, userType.getClass());
        executor.closeConnection();
        if (rs == null) throw new UsersPlatformException("Query result was null");
        if (rs.size() <= 0) throw new UsersPlatformException("User {" + userGuid + "}does not exist");
        return rs.stream().findFirst().get();
    }
    @Override
    public Collection<User> getAllUsers() throws UsersPlatformException {
        Collection<User> users;
        executor.openConnection(dbConnector);
        users = executor.tryExecuteGetQuery(dbConnector, "SELECT * FROM " + tableName, userType.getClass());
        executor.closeConnection();
        if (users == null) throw new UsersPlatformException("Query result was null");
        return users;
    }
    @Override
    public void insertUser(User user) throws UsersPlatformException, SQLException {
        executor.openConnection(dbConnector);
        if (executor.TryExecuteInsertQuery(dbConnector, user) == false)
        {
            executor.closeConnection();
            throw new UsersPlatformException("Could not insert new user");
        }
        executor.closeConnection();
    }
    @Override
    public void deleteUser(int userGuid) throws UsersPlatformException, SQLException {
        User user = getUser(userGuid);
        if(user == null)
            throw new UsersPlatformException("User {" + userGuid + "} not found");
        transactionDAO.deleteUserTransactions(userGuid);
        executor.openConnection(dbConnector);
        if (executor.TryExecuteDeleteQuery(dbConnector,user) == false)
        {
            executor.closeConnection();
            throw new UsersPlatformException("Could not delete user {" + userGuid + "}");
        }
        executor.closeConnection();
    }
    @Override
    public void setPassword(int userGuid, String newPassword) throws UsersPlatformException, SQLException {
        User user = getUser(userGuid);
        user.setPassword(newPassword);
        executor.openConnection(dbConnector);
        if(executor.TryExecuteUpdateQuery(dbConnector, user) == false)
        {
            executor.closeConnection();
            throw new UsersPlatformException("Could not update Retail");
        }
        executor.closeConnection();
    }
}
