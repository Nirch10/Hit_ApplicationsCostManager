package costmanagerapp.lib;

import com.sun.istack.internal.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class Transction {
    private int Guid;
    private boolean IsIncome;
    private double Price;
    private RetailType Retail;
    private User User;
    private LocalDate DateOfTransaction;
    private String Description;

    public Transction(@NotNull boolean isIncome,@NotNull  double price,@NotNull  RetailType retailType,@NotNull  User user,
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
}
