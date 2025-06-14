package object;

import java.util.ArrayList;

public class objArr implements objInterface{
   ArrayList<objInterface> array;

    public objArr(ArrayList<objInterface> array) {
        this.array = array;
    }

    public objInterface getIndex(int index) {
        if(index >= this.array.size()) {
            return null;
        }
        return array.get(index);
    }

    public String Type() {
        return "ARRAY";
    }

    public String Inspect() {
        String a = "[";
        for (int i = 0; i < array.size(); i++) {
            a += " " + this.array.get(i).Inspect();
            a += ",";
        }
        a = a.substring(0, a.length() - 2);
        a += "]";
        return a;
    }

}
