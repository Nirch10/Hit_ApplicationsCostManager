package costmanagerapp.lib.DAO;

import costmanagerapp.lib.Models.User;
import costmanagerapp.lib.UsersPlatformException;

import java.util.Collection;

public interface IUsersDAO {
    User GetUser(int userId) throws UsersPlatformException;
    Collection<User> GetAllUsers();

}
