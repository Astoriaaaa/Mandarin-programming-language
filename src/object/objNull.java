package object;

public class objNull implements  objInterface{

    String value;

    public objNull(String value) {
        this.value = value;
    }

    public String Type() {
        return "NULL";
    }

    public String Inspect() {
        return "null";
    }
    
}
