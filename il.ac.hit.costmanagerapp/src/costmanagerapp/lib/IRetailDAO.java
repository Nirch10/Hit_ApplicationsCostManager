package costmanagerapp.lib;

import java.sql.SQLException;
import java.util.Collection;

public interface IRetailDAO {
    RetailType getRetail(int guid) throws UsersPlatformException;
    Collection<RetailType> getRetails() throws UsersPlatformException;
    void setRetail(int guid, String newName) throws SQLException;
    void insertRetail(RetailType retailType) throws UsersPlatformException, SQLException;
    void deleteRetail(int guid) throws UsersPlatformException;

}
