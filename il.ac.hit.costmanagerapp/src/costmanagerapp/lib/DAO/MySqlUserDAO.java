package costmanagerapp.lib.DAO;

import costmanagerapp.lib.Models.User;
import costmanagerapp.lib.QueryUtils.AbstractDbConnector;
import costmanagerapp.lib.QueryUtils.IQueryExecuter;
import costmanagerapp.lib.QueryUtils.MySqlDbConnector;
import costmanagerapp.lib.QueryUtils.MySqlQueryExecuter;
import costmanagerapp.lib.UsersPlatformException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlUserDAO implements IUsersDAO {

    public String driver = "com.mysql.jdbc.Driver";
    private String userGuidColumn = "Guid";
    private String userNameColumn = "UserName";
    private String userPasswordColumn = "Password";
    private String userEmailColumn = "Email";
    private String userTableName = "user";
    private IQueryExecuter executor;
    private AbstractDbConnector dbConnector;
    private ITransactionDAO transactionDAO;


    public MySqlUserDAO() throws ClassNotFoundException {this(new MySqlQueryExecuter(),
            new MySqlDbConnector("jdbc:mysql://localhost:3306/costmanager", "costmanager", "123456"));
              }
    public MySqlUserDAO(IQueryExecuter queryExecutor, AbstractDbConnector connector) throws ClassNotFoundException {
        Class.forName(driver);
        executor = queryExecutor;
        dbConnector = connector;
    }
    private void initTransctionDAO() throws ClassNotFoundException {
        transactionDAO  =
                new MySqlTransactionDAO(this, new MySqlRetailDAO(), new MySqlQueryExecuter(), new MySqlDbConnector("jdbc:mysql://localhost:3306/costmanager", "costmanager", "123456"));

    }
    @Override
    public User getUser(int userId) throws UsersPlatformException {
        User user = null;
        try {
            ResultSet rs = executor.ExecuteGetQuery(dbConnector,"SELECT * FROM "+userTableName+" WHERE "+userGuidColumn+"=" + userId);
            if (!rs.next()) {
                throw new UsersPlatformException("No such user exists");
            }
            user = new User(rs.getInt(userGuidColumn) ,rs.getString(userNameColumn), rs.getString(userEmailColumn), rs.getString(userPasswordColumn));
        } catch (SQLException e) {
            throw new UsersPlatformException("Could not get user with guid {"+userId+"} from db,\n", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Collection<User> getAllUsers() throws UsersPlatformException {
        Collection<User> userCollection = new ArrayList<>();
        try {
            ResultSet rs = executor.ExecuteGetQuery(dbConnector, "SELECT * FROM "+userTableName);
            while (rs.next()) {
                userCollection.add(new User(rs.getInt(userGuidColumn) , rs.getString(userNameColumn),
                        rs.getString(userEmailColumn), rs.getString(userPasswordColumn)));

            }
            if (userCollection.size() == 0) {
                throw new UsersPlatformException("No user exists");
            }
        } catch (SQLException e) {
            throw new UsersPlatformException("Could not get users from db,\n", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return userCollection;

    }

    @Override
    public void insertUser(User user) throws UsersPlatformException {
        try {
            executor.TryExecuteInsertQuery(dbConnector,
                    "INSERT INTO "+userTableName+" ("+userGuidColumn+","+userNameColumn+","+userEmailColumn+","+userPasswordColumn+")" +  " VALUES("+ user.getGuid()+",\""+user.getUserName()
                    +"\",\""+ user.getEmail() + "\",\""+user.getPassword() + "\")");
        }
        catch (SQLException e) {
            throw new UsersPlatformException("Could not insert new user to db,\n", e);
        }
    }
    @Override
    public void deleteUser(int id) throws UsersPlatformException {
        try{
            initTransctionDAO();
           transactionDAO.deleteUserTransactions(id);
            executor.TryExecuteDeleteQuery(dbConnector, "DELETE FROM "+userTableName+" WHERE guid = " + id);
        }
        catch (SQLException e) {
            throw new UsersPlatformException("Could not delete user from db,\n", e);
        } catch (ClassNotFoundException e) {
            throw new UsersPlatformException("Could not delete user from db,\n", e);
        }
    }

    @Override
    public void setPassword(int id , String pw) throws UsersPlatformException {
        try{
            executor.TryExecuteUpdateQuery(dbConnector, "UPDATE "+userTableName+" SET" + userPasswordColumn + " = \""+
                    pw + "\" WHERE "+ userGuidColumn + " = "+ id);
        }
        catch (SQLException e) {
            throw new UsersPlatformException("Could not update user's password,\n", e);
        }
    }
}



