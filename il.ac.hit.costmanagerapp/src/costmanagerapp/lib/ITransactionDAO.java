package costmanagerapp.lib;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public interface ITransactionDAO {
//    TransactionType getTransaction(int guid) throws UsersPlatformException;

    Transaction getTransaction(int transaction_id) throws UsersPlatformException;

    Collection<Transaction> getUserTransactions(int userId) throws UsersPlatformException;
/*
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    java.util.Date yourDate = sdf.parse("1992-07-26");

    void updateTransaction(int guid, boolean isIncome, float price, String description,
                           String retailName, LocalDate sdf) throws SQLException;

    void insertTransaction(int guid, boolean isIncome, double price, String description,
                                  String retailName, LocalDate sdf) throws SQLException;

    void deleteTransaction(int guid) throws UsersPlatformException;*/
}