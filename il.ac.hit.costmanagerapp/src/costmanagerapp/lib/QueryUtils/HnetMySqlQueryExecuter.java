package costmanagerapp.lib.QueryUtils;

import costmanagerapp.lib.Models.RetailType;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class HnetMySqlQueryExecuter<T> implements IQueryExecuter<T> {

   // private T classType;

    public HnetMySqlQueryExecuter(){
     //   classType = classTypeInput;
    }

    //TODO :: abstract problem - there might be huge problem with DbConnector
    @Override
    public Collection<T> ExecuteGetQuery(AbstractDbConnector connector, String getQuery) throws SQLException {
        Session session = connector.openConn();
        session.beginTransaction();


        List<T> res = (List<T>) session.createSQLQuery(getQuery).list();
        //List<T> res = (List<T>) session.createQuery(getQuery).list();
        return res;
    }

    @Override
    public Boolean TryExecuteInsertQuery(AbstractDbConnector connector, String insertQuery) throws SQLException {
        return null;
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
