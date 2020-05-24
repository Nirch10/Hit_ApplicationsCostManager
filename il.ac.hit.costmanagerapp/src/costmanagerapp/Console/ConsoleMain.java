package costmanagerapp.Console;

import costmanagerapp.lib.DAO.IUsersDAO;
import costmanagerapp.lib.DAO.MySqlUserDAO;
import costmanagerapp.lib.Models.User;
import costmanagerapp.lib.UsersPlatformException;

public class ConsoleMain {


    public static void main(String args[]){
        IUsersDAO dao = null;
        try {
            dao = new MySqlUserDAO();
            User u = dao.getUser(1);
            System.out.println(u);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
    }


}
