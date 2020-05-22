package costmanagerapp.lib.DAO;

import costmanagerapp.lib.DAO.IRetailDAO;
import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.UsersPlatformException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlRetailDAO implements IRetailDAO {

    public String driver = "com.mysql.jdbc.Driver";
    public String connectionString = "jdbc:mysql://localhost:3306/costmanager";
    private String sqlUser = "costmanager";
    private String sqlPassword = "123456";
    private String guidColumn = "Guid";
    private String typeColumn = "Name";

    public MySqlRetailDAO() throws ClassNotFoundException {
        Class.forName(driver);
    }

    private ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionString, sqlUser, sqlPassword);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }
    private boolean execute(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionString, sqlUser, sqlPassword);
        Statement statement = connection.createStatement();
        boolean rs = statement.execute(query);
        return rs;
    }

    @Override
    public RetailType getRetail(int guid) throws UsersPlatformException {
        RetailType retailType = null;
        try {
            ResultSet rs = executeQuery("SELECT * FROM retailtype WHERE Guid =" + guid);
            if (!rs.next()) {
                throw new UsersPlatformException("retail does not exist");
            }
            retailType = new RetailType(rs.getInt(guidColumn), rs.getString(typeColumn));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return retailType;
    }

    @Override
    public Collection<RetailType> getRetails() throws UsersPlatformException {
        Collection<RetailType> retails = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * FROM retailtype");
            while (rs.next())
                retails.add(new RetailType(rs.getInt(guidColumn), rs.getString(typeColumn)));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        return retails;
    }

    @Override
    public void setRetail(int guid, String newName) throws SQLException {
        execute("UPDATE retailtype SET "+typeColumn+" = \"" + newName + "\" WHERE "+guidColumn+" = " + guid);
    }

    @Override
    public void insertRetail(RetailType retailType) throws SQLException {
        execute("INSERT INTO retailtype ("+guidColumn+", "+typeColumn+") Values("+retailType.getGuid()+", \""+retailType.getType() + "\")");
    }

    @Override
    public void deleteRetail(int guid) throws UsersPlatformException {
        try {
            execute("DELETE FROM transactions WHERE RetailGuid = "+ guid);
            execute("DELETE FROM retailtype WHERE Guid = " + guid);
        } catch (SQLException e) {
            throw new UsersPlatformException();
        }
    }
}
