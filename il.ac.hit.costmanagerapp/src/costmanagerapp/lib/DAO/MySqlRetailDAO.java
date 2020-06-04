package costmanagerapp.lib.DAO;

import costmanagerapp.Config.HibernateMappingConfig;
import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.Models.Transaction;
import costmanagerapp.lib.QueryUtils.*;
import costmanagerapp.lib.UsersPlatformException;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MySqlRetailDAO implements IRetailDAO {

    public String driver = "com.mysql.jdbc.Driver";
    public String connectionString = "jdbc:mysql://localhost:3306/costmanager";
    private String sqlUser = "costmanager";
    private String sqlPassword = "123456";
    private String guidColumn = "Guid";
    private String typeColumn = "Name";
    private String tableName = "retailtype";
    private HibernateMappingConfig hibernateMapping;
    private IQueryExecuter<RetailType> executor;
    private AbstractDbConnector dbConnector;
    private ITransactionDAO transactionDAO;

    public MySqlRetailDAO() throws ClassNotFoundException {this(new HnetMySqlQueryExecuter<RetailType>(),
            new HnetMySqlDbConnector(new File("C:\\code\\Hit_ApplicationsCostManager\\il.ac.hit.costmanagerapp\\out\\production\\il.ac.hit.costmanagerapp\\costmanagerapp\\lib\\Models\\hibernate.cfg.xml")));
    }
    public MySqlRetailDAO(IQueryExecuter<RetailType> queryExecutor, AbstractDbConnector connector) throws ClassNotFoundException {
        Class.forName(driver);
        executor = queryExecutor;
        dbConnector = connector;

    }

    private void initTransctionDAO() throws ClassNotFoundException {
        transactionDAO  =
                new MySqlTransactionDAO(new MySqlUserDAO(),
                        this, new HnetMySqlQueryExecuter<Transaction>(),
                        new MySqlDbConnector("jdbc:mysql://localhost:3306/costmanager", "costmanager", "123456"));

    }

    @Override
    public RetailType getRetail(int guid) throws UsersPlatformException {
        RetailType retailType = null;
        try {
            Collection<RetailType> rs = executor.ExecuteGetQuery(dbConnector,"SELECT * FROM "+tableName + " WHERE Guid =" + guid);
            if (rs.size() <= 0) {
                throw new UsersPlatformException("retail does not exist");
            }

            retailType = rs.stream().findFirst().get();

        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            throw new UsersPlatformException("problem with parsing", e);
        }
        return retailType;
    }

    @Override
    public Collection<RetailType> getRetails() throws UsersPlatformException {
        Collection<RetailType> retails = new ArrayList<>();
        try {
            Collection<RetailType> rs = executor.ExecuteGetQuery(dbConnector,"SELECT * FROM " + tableName);
            return rs;
//            for (RetailType r :
//                    rs) {
//                retails.add(new RetailType(r.getGuid(), r.getName()));
//            }
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
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
