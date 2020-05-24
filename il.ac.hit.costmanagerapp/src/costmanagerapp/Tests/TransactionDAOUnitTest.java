package costmanagerapp.Tests;

import costmanagerapp.lib.*;
import costmanagerapp.lib.DAO.*;
import costmanagerapp.lib.Models.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class TransactionDAOUnitTest {

    static ITransactionDAO tester;

    @BeforeClass
    public static void testSetup() {
        try {
            tester = new MySqlTransactionDAO();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void testCleanup() {
        // Do your cleanup here like close URL connection , releasing resources etc
    }

//    @Test
//    public void testGetUser() throws UsersPlatformException {
//        if (tester.getRetail(1) == null) throw new AssertionError();
//    }

    @Test
    public void testGetTransaction() throws UsersPlatformException {
        Transaction cl = tester.getTransaction(1);
        if(cl == null) throw new AssertionError("empty list");
        System.out.println(cl.getGuid() + ", "+ cl.getPrice() +", " + cl.getDateOfTransaction());
    }

    @Test
    public void testInsertTransaction() throws ClassNotFoundException {
        IRetailDAO retailDAO = new MySqlRetailDAO();
        IUsersDAO usersDAO = new MySqlUserDAO();
        try {
            tester.insertTransaction(new Transaction(11, false, 54.522, retailDAO.getRetails().stream().findFirst().get(),
                    usersDAO.getUser(1), LocalDate.now(), "Test etset"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AssertionError();
        } catch (UsersPlatformException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    @Test
    public void testDeleteTransaction(){
        try{
            tester.deleteTransaction(11);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
            throw new AssertionError();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }

}
