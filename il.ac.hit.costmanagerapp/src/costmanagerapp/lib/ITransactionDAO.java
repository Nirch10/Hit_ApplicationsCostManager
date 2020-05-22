package costmanagerapp.lib;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public interface ITransactionDAO {
//    TransactionType getTransaction(int guid) throws UsersPlatformException;

    Transaction getTransaction(int transaction_id) throws UsersPlatformException;

    Collection<Transaction> getUserTransactions(int userId) throws UsersPlatformException;

    void updateTransaction(int guid, boolean isIncome, float price, String description,
                           String retailName, LocalDate sdf) throws SQLException;

    void insertTransaction(Transaction transaction) throws SQLException, UsersPlatformException;

    void deleteTransaction(int guid) throws UsersPlatformException;
}