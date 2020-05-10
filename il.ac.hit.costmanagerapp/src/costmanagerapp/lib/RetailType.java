package costmanagerapp.lib;

import com.sun.istack.internal.NotNull;

import java.util.Random;

public class RetailType {
    private int Guid;
    private String Name;

    public RetailType(int anInt, @NotNull String name){
        //TODO::: Generate id automatically
        Guid = new Random().nextInt(100000);
        Name = name;
    }

    public int getGuid() {
        return Guid;
    }
    public String getType(){
        return Name;
    }
}
