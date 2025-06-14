package object;

public class objNull implements  objInterface{


    public objNull(String value) {
        
    }

    @Override
    public String Type() {
        return "NULL";
    }

    @Override
    public String Inspect() {
        return "null";
    }
    
}
