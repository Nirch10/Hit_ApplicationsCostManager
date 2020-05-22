package costmanagerapp.lib;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IQueryExecuter {
    ResultSet ExecuteGetQuery(AbstractDbConnector connector, String getQuery) throws SQLException;
    Boolean TryExecuteInsertQuery(AbstractDbConnector connector, String insertQuery) throws SQLException;
    Boolean TryExecuteUpdateQuery(AbstractDbConnector connector, String updateQuery) throws SQLException;
    Boolean TryExecuteDeleteQuery(AbstractDbConnector connector, String deleteQuery) throws SQLException;
}
