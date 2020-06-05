package costmanagerapp.lib.QueryUtils;

import java.sql.SQLException;
import java.util.Collection;

public interface IQueryExecuter<T> {
    Collection tryExecuteGetQuery(AbstractDbConnector connector, String getQuery) throws SQLException;


    //TODO :: abstract problem - there might be huge problem with DbConnector
    Collection<T> tryExecuteGetQuery(AbstractDbConnector connector, String getQuery, Class type) ;

    Boolean TryExecuteInsertQuery(AbstractDbConnector connector, T insertObj) throws SQLException;
    Boolean TryExecuteUpdateQuery(AbstractDbConnector connector, T updateObj) throws SQLException;
    Boolean TryExecuteDeleteQuery(AbstractDbConnector connector,T deleteObj) throws SQLException;
}
