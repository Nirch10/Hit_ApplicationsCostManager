package costmanagerapp.Tests;

import com.mysql.jdbc.AssertionFailedException;
import costmanagerapp.lib.IRetailDAO;
import costmanagerapp.lib.MySqlRetailDAO;
import costmanagerapp.lib.RetailType;
import costmanagerapp.lib.UsersPlatformException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;

public class RetailDAOTest {

    static IRetailDAO tester;

    @BeforeClass
    public static void testSetup() {
        try {
            tester = new MySqlRetailDAO();
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
        if (tester.getRetail(1) == null) throw new AssertionError();
    }

    @Test
    public void testGetAllRetails() throws UsersPlatformException {
        Collection<RetailType> cl = tester.getRetails();
        if(cl.size() == 0) throw new AssertionError("empty list");
        cl.forEach(c -> System.out.println(c.getGuid() +", "+ c.getType()));
    }

    @Test
    public void testInsertRetail(){
        try {
            tester.insertRetail(new RetailType(2,"Books"));
        } catch (UsersPlatformException e) {
            throw new AssertionError();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDeleteRetail(){
        try {
            tester.deleteRetail(1);
        } catch (UsersPlatformException e) {
            throw new AssertionError();
        }
    }
    @Test
    public void testUpdateRetail(){
        try {
            tester.setRetail(77953,"Test");
        } catch (SQLException e) {
            throw new AssertionError();
        }
    }



}
