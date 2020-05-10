package costmanagerapp.lib;

import java.util.Collection;
import java.util.List;

public interface IUsersDAO {
    User GetUser(int userId) throws UsersPlatformException;
    Collection<User> GetAllUsers();

}
