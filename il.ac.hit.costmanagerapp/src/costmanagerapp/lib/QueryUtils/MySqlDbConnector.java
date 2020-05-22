package costmanagerapp.lib.QueryUtils;

import costmanagerapp.lib.QueryUtils.AbstractDbConnector;

public class MySqlDbConnector extends AbstractDbConnector {

    public MySqlDbConnector(String connString, String uName, String uPassword){
        connectionString = connString;
        userName = uName;
        password = uPassword;
    }
}
