package costmanagerapp.Tests;

import costmanagerapp.lib.DAO.HnetMySqlRetailsDAO;
import costmanagerapp.lib.DAO.IRetailDAO;
import costmanagerapp.lib.Models.RetailType;
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
        tester = new HnetMySqlRetailsDAO();
    }

    @AfterClass
    public static void testCleanup() {
        // Do your cleanup here like close URL connection , releasing resources etc
    }

    @Test
    public void testGetUser() throws UsersPlatformException {
        if (tester.getRetail(10) == null) throw new AssertionError();
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
            tester.insertRetail(new RetailType(15,"NirchFriday"));
        } catch (UsersPlatformException e) {
            throw new AssertionError();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDeleteRetail(){
        try {
            tester.deleteRetail(52790);
        } catch (UsersPlatformException e) {
            throw new AssertionError();
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
    }
    @Test
    public void testUpdateRetail(){
        try {
            tester.setRetail(93592,"Test42221");
        } catch (SQLException e) {
            throw new AssertionError();
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
    }
}
