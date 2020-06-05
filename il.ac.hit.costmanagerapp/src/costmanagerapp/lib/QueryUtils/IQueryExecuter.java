package costmanagerapp.lib.QueryUtils;

import java.sql.SQLException;
import java.util.Collection;

public interface IQueryExecuter<T> {
    Collection ExecuteGetQuery(AbstractDbConnector connector, String getQuery) throws SQLException;


    //TODO :: abstract problem - there might be huge problem with DbConnector
    Collection<T> ExecuteGetQuery(AbstractDbConnector connector, String getQuery, Class type) throws SQLException;

    Boolean TryExecuteInsertQuery(AbstractDbConnector connector, String insertQuery) throws SQLException;
    Boolean TryExecuteUpdateQuery(AbstractDbConnector connector, String updateQuery) throws SQLException;
    Boolean TryExecuteDeleteQuery(AbstractDbConnector connector, String deleteQuery) throws SQLException;
}
