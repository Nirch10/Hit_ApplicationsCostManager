package costmanagerapp.Tests;

import com.mysql.jdbc.AssertionFailedException;
import costmanagerapp.lib.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;

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
    public void Collection<Transaction> getUserTransactions(int userId) throws UsersPlatformException {

    }
}
