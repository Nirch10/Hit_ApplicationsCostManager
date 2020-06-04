package costmanagerapp.lib.QueryUtils;

import org.hibernate.Session;

public class MySqlDbConnector extends AbstractDbConnector {

    public MySqlDbConnector(String connString, String uName, String uPassword){
        connectionString = connString;
        userName = uName;
        password = uPassword;
    }

    @Override
    Session openConn() {
        return null;
    }
}
