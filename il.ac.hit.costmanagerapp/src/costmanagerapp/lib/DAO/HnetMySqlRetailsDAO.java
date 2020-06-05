package costmanagerapp.lib.DAO;

import costmanagerapp.Config.HibernateMappingConfig;
import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.QueryUtils.AbstractDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlQueryExecuter;
import costmanagerapp.lib.QueryUtils.IQueryExecuter;
import costmanagerapp.lib.UsersPlatformException;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class HnetMySqlRetailsDAO implements IRetailDAO {
    private String guidColumn = "Guid";
    private String typeColumn = "Name";
    private String tableName = "retailtype";
    private HibernateMappingConfig hibernateMapping;
    private IQueryExecuter<RetailType> executor;
    private AbstractDbConnector dbConnector;
    private ITransactionDAO transactionDAO;
    private RetailType RetailType;

    public HnetMySqlRetailsDAO(){this(new HnetMySqlQueryExecuter<>(),
            new HnetMySqlDbConnector(new File("C:\\code\\Hit_ApplicationsCostManager\\il.ac.hit.costmanagerapp\\out\\production\\il.ac.hit.costmanagerapp\\costmanagerapp\\lib\\Models\\hibernate.cfg.xml")));

    }

    public HnetMySqlRetailsDAO(IQueryExecuter<RetailType> queryExecutor, AbstractDbConnector connector){
        executor = queryExecutor;
        dbConnector = connector;
        RetailType =  new RetailType();
    }

    @Override
    public RetailType getRetail(int guid) throws UsersPlatformException {
        RetailType retailType = null;
        try {
            Collection<RetailType> rs = executor.ExecuteGetQuery(dbConnector,"SELECT * FROM "+tableName + " WHERE Guid =" + guid,
                    RetailType.getClass());

            if (rs.size() <= 0) {
                throw new UsersPlatformException("retail does not exist");
            }
            retailType = rs.stream().findFirst().get();

        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
        return retailType;
    }

    @Override
    public Collection<RetailType> getRetails() throws UsersPlatformException {
        Collection<RetailType> retails = null;
        try {
            retails = executor.ExecuteGetQuery(dbConnector,"SELECT * FROM " + tableName,
                    RetailType.getClass());
        } catch (SQLException e) {
            throw new UsersPlatformException("problem getting row", e);
        }
        return retails;
    }

    @Override
    public void setRetail(int guid, String newName) throws SQLException {

    }

    @Override
    public void insertRetail(RetailType retailType) throws UsersPlatformException, SQLException {
        executor.TryExecuteInsertQuery(dbConnector,retailType);
    }

    @Override
    public void deleteRetail(int guid) throws UsersPlatformException, SQLException {
        RetailType rt = getRetail(guid);
        executor.TryExecuteDeleteQuery(dbConnector,rt);
    }
}
