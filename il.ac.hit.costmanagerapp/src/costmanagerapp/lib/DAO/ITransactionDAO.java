package costmanagerapp.lib.DAO;

import com.sun.istack.internal.NotNull;
import costmanagerapp.lib.Models.Transaction;
import costmanagerapp.lib.UsersPlatformException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public interface ITransactionDAO {
//    TransactionType getTransaction(int guid) throws UsersPlatformException;

    Transaction getTransaction(int transaction_id) throws UsersPlatformException;

    Collection<Transaction> getTransactionByUser(int userId) throws UsersPlatformException;

    Collection<Transaction> getTransactionByRetail(int retailId) throws UsersPlatformException;

    Collection<Transaction> getTransactionByDateRange(LocalDate from, LocalDate to) throws UsersPlatformException;

    Collection<Transaction> getTransactionByPriceRange(double fromPrice, double toPrice) throws UsersPlatformException;

    void updateTransaction(@NotNull int guid, @NotNull boolean isIncome, @NotNull double price, @NotNull String description,
                           @NotNull int retailGuid, @NotNull LocalDate dateOT) throws UsersPlatformException;

    void insertTransaction(Transaction transaction) throws SQLException, UsersPlatformException;

    void deleteTransaction(int guid) throws UsersPlatformException, SQLException;

    void deleteUserTransactions(int userGuid) throws SQLException;
    void deleteRetailTransactions(int retailGuid) throws SQLException;
}