package costmanagerapp.lib.Models;

import com.sun.istack.internal.NotNull;

import java.time.LocalDate;
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
                       LocalDate date,String description){
        //TODO::: Generate id automatically
        Guid = guid;
        IsIncome = isIncome;
        Price = price;
        Retail = retailType;
        User = user;
        DateOfTransaction = date;
        Description = description;
    }

    public Transaction(@NotNull boolean isIncome, @NotNull  double price, @NotNull  RetailType retailType, @NotNull  User user,
                       String description){
        //TODO::: Generate id automatically
        Guid = new Random().nextInt(100000);
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
    public void setGuid(int guid){Guid = guid;}
    public boolean getIsIncome() {
        return IsIncome;
    }

    public boolean isIncome() {
        return IsIncome;
    }

    public void setIsIncome(boolean isIncome){IsIncome = isIncome;}
    public double getPrice() {
        return Price;
    }
    public void setPrice(double price){Price = price;}
    public RetailType getRetail() {
        return Retail;
    }
    public void setRetail(RetailType retail){Retail = retail;}
    public User getUser() {
        return User;
    }
    public void setUser(User user){User = user;}
    public LocalDate getDateOfTransaction() {
        return DateOfTransaction;
    }
    public void setDateOfTransaction(LocalDate dateOfTransaction){DateOfTransaction = dateOfTransaction;}
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description){Description = description;}
}