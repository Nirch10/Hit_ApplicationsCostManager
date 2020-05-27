package costmanagerapp.lib.DAO;

import costmanagerapp.Config.HibernateMappingConfig;
import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.QueryUtils.AbstractDbConnector;
import costmanagerapp.lib.QueryUtils.IQueryExecuter;
import costmanagerapp.lib.QueryUtils.MySqlDbConnector;
import costmanagerapp.lib.QueryUtils.MySqlQueryExecuter;
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
    private String tableName = "retailtype";
    private HibernateMappingConfig hibernateMapping;
    private IQueryExecuter executor;
    private AbstractDbConnector dbConnector;
    private ITransactionDAO transactionDAO;

    public MySqlRetailDAO() throws ClassNotFoundException {this(new MySqlQueryExecuter(),
            new MySqlDbConnector("jdbc:mysql://localhost:3306/costmanager", "costmanager", "123456"));
    }
    public MySqlRetailDAO(IQueryExecuter queryExecutor, AbstractDbConnector connector) throws ClassNotFoundException {
        Class.forName(driver);
        executor = queryExecutor;
        dbConnector = connector;
    }

    private void initTransctionDAO() throws ClassNotFoundException {
        transactionDAO  =
                new MySqlTransactionDAO(new MySqlUserDAO(), this, new MySqlQueryExecuter(), new MySqlDbConnector("jdbc:mysql://localhost:3306/costmanager", "costmanager", "123456"));

    }

    @Override
    public RetailType getRetail(int guid) throws UsersPlatformException {
        RetailType retailType = null;
        try {
            ResultSet rs = executor.ExecuteGetQuery(dbConnector,"SELECT * FROM "+tableName + " WHERE Guid =" + guid);
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
            ResultSet rs = executor.ExecuteGetQuery(dbConnector,"SELECT * FROM " + tableName);
            while (rs.next())
                retails.add(new RetailType(rs.getInt(guidColumn), rs.getString(typeColumn)));
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        return retails;
    }

    @Override
    public void setRetail(int guid, String newName) throws SQLException {
        executor.TryExecuteUpdateQuery(dbConnector, "UPDATE " + tableName + " SET " + typeColumn + " = \""
                + newName + "\" WHERE " + guidColumn + " = " + guid);
    }

    @Override
    public void insertRetail(RetailType retailType) throws SQLException {
        executor.TryExecuteInsertQuery(dbConnector,"INSERT INTO "+tableName+" ("+guidColumn+", "+typeColumn+") Values("+retailType.getGuid()+", \""+retailType.getType() + "\")");
    }

    @Override
    public void deleteRetail(int guid) throws UsersPlatformException {
        try {
            initTransctionDAO();
            transactionDAO.deleteRetailTransactions(guid);
            executor.TryExecuteDeleteQuery(dbConnector, "DELETE FROM "+tableName+" WHERE Guid = " + guid);
        } catch (SQLException | ClassNotFoundException e) {
            throw new UsersPlatformException();
        }
    }
}
