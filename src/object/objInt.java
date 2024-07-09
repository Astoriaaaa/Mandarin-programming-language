package object;

public class objInt implements objInterface {
    int value;
    public objInt(int value) {
        this.value = value;
    }


    public String Type() {
        return "INT";
    }

    public String Inspect() {
        return Integer.toString(value);
    }
}
