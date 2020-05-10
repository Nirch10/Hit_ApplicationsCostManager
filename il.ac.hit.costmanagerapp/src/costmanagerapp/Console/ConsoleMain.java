package costmanagerapp.Console;

import costmanagerapp.lib.IUsersDAO;
import costmanagerapp.lib.MySqlUserDAO;
import costmanagerapp.lib.User;
import costmanagerapp.lib.UsersPlatformException;

public class ConsoleMain {


    public static void main(String args[]){
        IUsersDAO dao = null;
        try {
            dao = new MySqlUserDAO();
            User u = dao.GetUser(1);
            System.out.println(u);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
    }


}