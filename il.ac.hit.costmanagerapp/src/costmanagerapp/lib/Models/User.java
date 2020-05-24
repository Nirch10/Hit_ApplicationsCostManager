package costmanagerapp.lib.Models;

import com.sun.istack.internal.NotNull;

import java.util.Random;

public class User {
    private int Guid;
    private String UserName;
    private String Email;
    private String Password;

    @Override
    public String toString() {
        return "User{" +
                "Guid=" + Guid +
                ", UserName='" + UserName + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    public User(@NotNull String userName, @NotNull String email, @NotNull String password){
        //TODO::: Generate id automatically
        Guid = new Random().nextInt(100000);
        UserName = userName;
        Password = password;
        Email = email;
    }

    public User(int guid ,@NotNull String userName, @NotNull String email, @NotNull String password){
        //TODO::: Generate id automatically
        Guid = guid;
        UserName = userName;
        Password = password;
        Email = email;
    }

    public int getGuid() {
        return Guid;
    }

    public void setGuid(int guid) {
        Guid = guid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}