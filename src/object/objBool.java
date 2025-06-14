package object;

public class objBool implements objInterface{
    String value;
    
    public objBool(String value) {
        this.value = value;
    }

    @Override
    public String Type() {
        return "BOOLEAN";
    }

    @Override
    public String Inspect() {
        return value;
    }
}
