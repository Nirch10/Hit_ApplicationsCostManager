package costmanagerapp.lib.Models;

import com.sun.istack.internal.NotNull;

import java.util.Random;

public class User {
    private int Guid;
    private String UserName;
    private String Email;
    private String Password;

    public User(int guid, @NotNull String userName, @NotNull String email, @NotNull String password){
        //TODO::: Generate id automatically
        //Guid = new Random().nextInt(100000);
        Guid = guid;
        UserName = userName;
        Password = password;
        Email = email;
    }

    public int getGuid() {
        return Guid;
    }
}
