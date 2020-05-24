package costmanagerapp.Tests;

import com.mysql.jdbc.AssertionFailedException;
import costmanagerapp.lib.DAO.IUsersDAO;
import costmanagerapp.lib.DAO.MySqlUserDAO;
import costmanagerapp.lib.Models.User;
import costmanagerapp.lib.UsersPlatformException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



import java.sql.SQLException;
import java.util.Collection;

public class UserDAOTest {
    static IUsersDAO tester;


    @BeforeClass
    public static void testSetup() {
        try {
            tester = new MySqlUserDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @AfterClass
    public static void testCleanup() {
        // Do your cleanup here like close URL connection , releasing resources etc
    }


    @Test
    public void testGetUser() throws UsersPlatformException {
        if (tester.getUser(1) == null) throw new AssertionError();
    }


    @Test
    public void testGetAllUsers() throws UsersPlatformException {
        Collection<User> users = tester.getAllUsers();
        if (users.size() == 0) throw new AssertionError("empty list");
        users.forEach(u -> System.out.println(u.getGuid() + ", " + u.getUserName()));
    }

    @Test
    public void testInsertUser() {
        try {
            tester.insertUser(new User("gila morgenshtein", "kusiteshmegilshesh@gmail.com", "6667788"));
        } catch (UsersPlatformException e) {
            throw new AssertionError();
        }
    }

    @Test
    public void testDeleteUser(){
        try {
            if (tester.getUser(96532) == null) throw new ValueException("the user isnt exists");
            tester.deleteUser(96532);

        } catch (UsersPlatformException e) {
            throw new AssertionError();
        }
    }

    @Test
    public void testSetPassword(){
        try {
            if (tester.getUser(1) == null) throw new ValueException("the user isnt exists");
            tester.setPassword(1,"6667");
            User u1 = tester.getUser(1);
            if (u1.getPassword() == "6667") throw new ValueException("Set Password isn't succeed");
        } catch (UsersPlatformException e) {
            throw new AssertionError();
        }
    }




}