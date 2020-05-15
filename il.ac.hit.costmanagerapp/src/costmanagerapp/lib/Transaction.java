package costmanagerapp.lib;

import com.sun.istack.internal.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class Transaction {
    private int Guid;
    private boolean IsIncome;
    private double Price;
    private RetailType Retail;
    private User User;
    private LocalDate DateOfTransaction;
    private String Description;


    public Transaction(@NotNull int guid, @NotNull boolean isIncome, @NotNull  double price, @NotNull  RetailType retailType, @NotNull  User user,
                       String description){
        //TODO::: Generate id automatically
        Guid = guid;
        IsIncome = isIncome;
        Price = price;
        Retail = retailType;
        User = user;
        DateOfTransaction = LocalDate.now();
        Description = description;
    }

    public int getGuid() {
        return Guid;
    }
    public boolean getIsIncome() {
        return IsIncome;
    }
    public double getPrice() {
        return Price;
    }
    public RetailType getRetail() {
        return Retail;
    }
    public User getUser() {
        return User;
    }
    public LocalDate getDateOfTransaction() {
        return DateOfTransaction;
    }
    public String getDescription() {
        return Description;
    }
}