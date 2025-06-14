package object;

public class objString implements objInterface{

    String value;
    public objString(String value) {
        this.value = value;
    }

    @Override
    public String Type() {
        return "STRING";
    }

    @Override
    public String Inspect() {
        return value;
    }
    
}
