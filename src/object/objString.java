package object;

public class objString implements objInterface{

    String value;
    public objString(String value) {
        this.value = value;
    }

    public String Type() {
        return "STRING";
    }

    public String Inspect() {
        return value;
    }
    
}
