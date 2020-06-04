package costmanagerapp.lib.Models;

import com.sun.istack.internal.NotNull;

import java.util.Random;


public class RetailType {
    private int Guid;
    private String Name;

    public RetailType(int guid, @NotNull String name){
        //TODO::: Generate id automatically
        //Guid = new Random().nextInt(100000);
        Guid = guid;
        Name = name;
    }
    public RetailType(@NotNull String name){
        //TODO::: Generate id automatically
        Guid = new Random().nextInt(100000);
        Name = name;
    }

    public int getGuid() {
        return Guid;
    }
    public void setGuid(int guid){Guid = guid;}
    public String getType(){
        return Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name){Name = name;}
}
