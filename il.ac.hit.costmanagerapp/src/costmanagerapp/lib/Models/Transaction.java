package costmanagerapp.lib.Models;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;


@Entity
@Table(appliesTo = "transactions")
public class Transaction {
    @Id @GeneratedValue
    @Column(name = "Guid")
    private int Guid;
    @Column(name = "IsIncome")
    private boolean IsIncome;
    @Column(name = "Price")
    private double Price;
    @Fetch(FetchMode.JOIN)

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "RetailGuid", insertable=false, updatable=false)
    private RetailType Retail;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    //@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RetailGuid", insertable=false, updatable=false)
    private User User;
    @Column(name = "DateOfTransaction", columnDefinition = "DATE")
    private Date DateOfTransaction;
    @Column(name = "Description")
    private String Description;


    public Transaction(@NotNull int guid, @NotNull boolean isIncome, @NotNull  double price, @NotNull  RetailType retailType, @NotNull  User user,
                       Date date,String description){
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
        DateOfTransaction = Date.from(Instant.now());
        Description = description;
    }

    public Transaction() {

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
    public Date getDateOfTransaction() {
        return DateOfTransaction;
    }
    public void setDateOfTransaction(Date dateOfTransaction){DateOfTransaction = dateOfTransaction;}
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description){Description = description;}
}