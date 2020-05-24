package costmanagerapp.lib.DAO;

import costmanagerapp.lib.Models.User;
import costmanagerapp.lib.UsersPlatformException;

import java.util.Collection;
import java.util.List;

public interface IUsersDAO {
    User getUser(int userId) throws UsersPlatformException;
    Collection <User> getAllUsers() throws UsersPlatformException;
    void insertUser(User user) throws UsersPlatformException;
    void deleteUser(int id) throws UsersPlatformException;
    void setPassword(int id , String password) throws UsersPlatformException;;
}