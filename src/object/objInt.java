package object;

public class objInt implements objInterface {
    int value;
    public objInt(int value) {
        this.value = value;
    }


    @Override
    public String Type() {
        return "INT";
    }

    @Override
    public String Inspect() {
        return Integer.toString(value);
    }
}
