package costmanagerapp.lib.DAO;

import com.sun.istack.internal.NotNull;
import costmanagerapp.Config.HibernateMappingConfig;
import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.QueryUtils.AbstractDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlDbConnector;
import costmanagerapp.lib.QueryUtils.HnetMySqlQueryExecuter;
import costmanagerapp.lib.QueryUtils.IQueryExecuter;
import costmanagerapp.lib.UsersPlatformException;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;

public class HnetMySqlRetailsDAO implements IRetailDAO {
    private String guidColumn = "Guid";
    private String typeColumn = "Name";
    private String tableName = "retailtype";
    private IQueryExecuter<RetailType> executor;
    private AbstractDbConnector dbConnector;
    private ITransactionDAO transactionDAO;
    private RetailType RetailType;
    private final String filePath = "C:\\code\\Hit_ApplicationsCostManager\\il.ac.hit.costmanagerapp\\out\\production\\il.ac.hit.costmanagerapp\\costmanagerapp\\lib\\Models\\hibernate.cfg.xml";

    public HnetMySqlRetailsDAO(){this(new HnetMySqlQueryExecuter<>(), null);
    }

    public HnetMySqlRetailsDAO(@NotNull IQueryExecuter<RetailType> queryExecutor, AbstractDbConnector connector){
        executor = queryExecutor;
        RetailType =  new RetailType();
        if(connector != null)
            dbConnector = connector;
        else
            new HnetMySqlDbConnector(new File(filePath));
    }

    @Override
    public RetailType getRetail(@NotNull int guid) throws UsersPlatformException {
        Collection<RetailType> rs = executor.tryExecuteGetQuery(dbConnector, "SELECT * FROM " + tableName + " WHERE Guid =" + guid,
                RetailType.getClass());
        if (rs == null)
            throw new UsersPlatformException("Error handling query");
        if (rs.size() <= 0) {
            throw new UsersPlatformException("retail does not exist");
        }
        return rs.stream().findFirst().get();
    }

    @Override
    public Collection<RetailType> getRetails() throws UsersPlatformException {
        Collection<RetailType> retails = null;
        retails = executor.tryExecuteGetQuery(dbConnector, "SELECT * FROM " + tableName, RetailType.getClass());
        if (retails == null)
            throw new UsersPlatformException("Error handling query");
        return retails;
    }

    @Override
    public void setRetail(@NotNull int guid, String newName) throws UsersPlatformException, SQLException {
        if(executor.TryExecuteUpdateQuery(dbConnector, new RetailType(guid, newName)) == false)
                throw new UsersPlatformException("Could not update Retail");
    }

    @Override
    public void insertRetail(@NotNull RetailType retailType) throws UsersPlatformException, SQLException {
        if (executor.TryExecuteInsertQuery(dbConnector, retailType) == false)
            throw new UsersPlatformException("Could not update Retail");

    }

    @Override
    public void deleteRetail(@NotNull int guid) throws UsersPlatformException, SQLException {
        RetailType rt = getRetail(guid);
        if (executor.TryExecuteDeleteQuery(dbConnector,rt) == false)
            throw new UsersPlatformException("Could not update Retail");
    }
}
