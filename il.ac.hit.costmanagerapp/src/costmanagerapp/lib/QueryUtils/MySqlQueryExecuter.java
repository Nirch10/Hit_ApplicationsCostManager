//package costmanagerapp.lib.QueryUtils;
//
//import java.sql.*;
//import java.util.Collection;
//
//public class MySqlQueryExecuter implements IQueryExecuter<T> {
//
//    @Override
//    public Collection ExecuteGetQuery(AbstractDbConnector connector, String getQuery) throws SQLException {
//        Connection connection = DriverManager.getConnection(connector.connectionString, connector.userName, connector.password);
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement.executeQuery(getQuery);
//        return rs;
//    }
//
//    @Override
//    public Boolean TryExecuteInsertQuery(AbstractDbConnector connector, String insertQuery) throws SQLException {
//        return executeQuery(connector, insertQuery);
//    }
//
//    @Override
//    public Boolean TryExecuteUpdateQuery(AbstractDbConnector connector, String updateQuery)  throws SQLException {
//        return executeQuery(connector, updateQuery);
//    }
//
//    @Override
//    public Boolean TryExecuteDeleteQuery(AbstractDbConnector connector, String deleteQuery) throws SQLException {
//        return executeQuery(connector, deleteQuery);
//    }
//
//    private Boolean executeQuery(AbstractDbConnector connector, String query) throws SQLException {
//        Connection connection = DriverManager.getConnection(connector.connectionString, connector.userName, connector.password);
//        Statement statement = connection.createStatement();
//        boolean result = statement.execute(query);
//        return result;
//    }
//}
