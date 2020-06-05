package costmanagerapp.lib.QueryUtils;

import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.UsersPlatformException;
import org.hibernate.Session;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class HnetMySqlQueryExecuter<T> implements IQueryExecuter<T> {
    public HnetMySqlQueryExecuter(){}

    @Override
    public Collection tryExecuteGetQuery(AbstractDbConnector connector, String getQuery) {
        return null;
    }

    private Session setSession(AbstractDbConnector connector){
        Session session = connector.openConn();
        session.beginTransaction();
        return session;
    }

    //TODO :: abstract problem - there might be huge problem with DbConnector
    @Override
    public Collection<T> tryExecuteGetQuery(AbstractDbConnector connector, String getQuery, Class type) {
        try {
            Session session = setSession(connector);

            List<T> res = (List<T>) session.createSQLQuery(getQuery).addEntity(type).list();
            session.close();
            return res;
        } catch (Exception e) {
            return null;
        }
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
    public Boolean TryExecuteUpdateQuery(AbstractDbConnector connector, T updateObj) throws SQLException {
        try {
            Session session = setSession(connector);
            session.update(updateObj);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            return false;
        }
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
