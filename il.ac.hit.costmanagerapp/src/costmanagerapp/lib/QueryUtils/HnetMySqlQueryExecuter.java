package costmanagerapp.lib.QueryUtils;

import costmanagerapp.lib.Models.RetailType;
import org.hibernate.Session;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class HnetMySqlQueryExecuter<T> implements IQueryExecuter<T> {

   // private T classType;

    public HnetMySqlQueryExecuter(){
     //   classType = classTypeInput;
    }

    @Override
    public Collection ExecuteGetQuery(AbstractDbConnector connector, String getQuery) throws SQLException {
        return null;
    }

    //TODO :: abstract problem - there might be huge problem with DbConnector
    @Override
    public Collection<T> ExecuteGetQuery(AbstractDbConnector connector, String getQuery, Class type) throws SQLException {
        Session session = connector.openConn();
        session.beginTransaction();
        List<T> res = (List<T>) session.createSQLQuery(getQuery).addEntity(type).list();
        session.close();
        return res;
    }

    @Override
    public Boolean TryExecuteInsertQuery(AbstractDbConnector connector, String insertQuery) throws SQLException {
        return false;
    }

    @Override
    public Boolean TryExecuteUpdateQuery(AbstractDbConnector connector, String updateQuery) throws SQLException {
        return null;
    }

    @Override
    public Boolean TryExecuteDeleteQuery(AbstractDbConnector connector, String deleteQuery) throws SQLException {
        return null;
    }
}
