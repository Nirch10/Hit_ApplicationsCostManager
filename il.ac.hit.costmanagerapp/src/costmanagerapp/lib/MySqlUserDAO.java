package costmanagerapp.lib;

import java.sql.*;
import java.util.Collection;

public class MySqlUserDAO implements IUsersDAO {

    public String driver = "com.mysql.jdbc.Driver";
    public String connectionString = "jdbc:mysql://localhost:3306/costmanager";
    private String sqlUser = "costmanager";
    private String guidColumn = "Guid";

    public MySqlUserDAO() throws ClassNotFoundException {
        Class.forName(driver);

    }

    @Override
    public User GetUser(int userId) throws UsersPlatformException {
        User user = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(connectionString, sqlUser, "123456");
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM user WHERE guid =" + userId);
            if (!rs.next()) {
                throw new UsersPlatformException("user not exist");
            }
            user = new User(rs.getInt(guidColumn), rs.getString("userName"),
                    rs.getString("Password"), rs.getString("Email"));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Collection<User> GetAllUsers() {
        return null;
    }
}
