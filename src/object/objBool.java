package object;

public class objBool implements objInterface{
    String value;
    
    public objBool(String value) {
        this.value = value;
    }

    public String Type() {
        return "BOOLEAN";
    }

    public String Inspect() {
        return value;
    }
}
