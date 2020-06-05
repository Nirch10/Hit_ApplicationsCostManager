package costmanagerapp.lib.QueryUtils;

import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.UsersPlatformException;
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

    private Session setSession(AbstractDbConnector connector){
        Session session = connector.openConn();
        session.beginTransaction();
        return session;
    }

    //TODO :: abstract problem - there might be huge problem with DbConnector
    @Override
    public Collection<T> ExecuteGetQuery(AbstractDbConnector connector, String getQuery, Class type) {
        Session session= setSession(connector);
        List<T> res = (List<T>) session.createSQLQuery(getQuery).addEntity(type).list();
        session.close();
        return res;
    }

    @Override
    public Boolean TryExecuteInsertQuery(AbstractDbConnector connector, T insertObj) throws SQLException {
        try{
            Session session = setSession(connector);
            session.save(insertObj);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean TryExecuteUpdateQuery(AbstractDbConnector connector, String updateQuery) throws SQLException {
        return null;
    }

    @Override
    public Boolean TryExecuteDeleteQuery(AbstractDbConnector connector, T deleteObj) throws SQLException {
        try{Session session = setSession(connector);
        session.delete(deleteObj);
        session.flush();
        session.getTransaction().commit();
        session.close();
        return true;}
        catch (Exception e){
            return false;
        }
    }
}
