package costmanagerapp.Tests;

import costmanagerapp.lib.*;
import costmanagerapp.lib.DAO.*;
import costmanagerapp.lib.Models.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class TransactionDAOUnitTest {

    static ITransactionDAO tester;

    @BeforeClass
    public static void testSetup() {
            tester = new HnetMySqlTransactionDAO();
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
    public void testGetTransaction() throws UsersPlatformException, SQLException {
        Transaction cl = tester.getTransaction(1);
        if(cl == null) throw new AssertionError("empty list");
        System.out.println(cl.getGuid() + ", "+ cl.getPrice() +", " + cl.getDateOfTransaction());
    }


    @Test
    public void testGetTransactionByUser() throws UsersPlatformException {
        Collection<Transaction> cl = tester.getTransactionByUser(1);
        if(cl.size() == 0) throw new AssertionError("empty list");
        cl.forEach(c -> System.out.println(c.getGuid() +", "+ c.getPrice()));
    }

    @Test
    public void testUpdateTransaction() throws ClassNotFoundException  {
        LocalDate d = LocalDate.of(2020, 05, 10);
        String desc = "Lord Of The Rings";
        IRetailDAO retailDAO = new HnetMySqlRetailsDAO();
        IUsersDAO usersDAO = new HnetMySqlUserDAO();
        try {
            tester.updateTransaction(1234, true, 21, desc, 52790, d);
        } catch (UsersPlatformException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    @Test

    public void testGetTransactionByRetail() throws Exception {
        Collection<Transaction> cl = tester.getTransactionByRetail(52790);
        if(cl.size() == 0) throw new AssertionError("empty list");
        cl.forEach(c -> System.out.println(c.getGuid() +", "+ c.getUser().getUserName()));
    }

    @Test
    public void testGetTransactionByDateRange() throws UsersPlatformException {
//        LocalDate d1 = LocalDate.of(2020, 05, 10);
//        LocalDate d2 = LocalDate.of(2020, 05, 15);
        Date d1 = new Date(2020 -1900,00,01);
        Date d2 = new Date(2020 - 1900,05,31);
        Collection<Transaction> cl = tester.getTransactionByDateRange(d1, d2);
        if(cl.size() == 0) throw new AssertionError("empty list");
        cl.forEach(c -> System.out.println(c.getGuid() +", "+ c.getDateOfTransaction()));
    }

    @Test
    public void testGetTransactionByPriceRange() throws UsersPlatformException {
        Collection<Transaction> cl = tester.getTransactionByPriceRange(0, 21);
        if(cl.size() == 0) throw new AssertionError("empty list");
        cl.forEach(c -> System.out.println(c.getGuid() +", "+ c.getPrice()));
    }
    @Test
    public void testInsertTransaction() throws ClassNotFoundException {
        IRetailDAO retailDAO = new HnetMySqlRetailsDAO();
        IUsersDAO usersDAO = new HnetMySqlUserDAO();
        try {
            tester.insertTransaction(new Transaction(19, false, 54.522, retailDAO.getRetails().stream().findFirst().get(),
                    usersDAO.getAllUsers().stream().findFirst().get(), Date.from(Instant.now()), "Test etset"));
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
