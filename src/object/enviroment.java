package object;

import ast.*;
import java.util.HashMap;

public class enviroment {
    enviroment outer;
    HashMap<String, objInterface> contents;

    public enviroment(enviroment outer, HashMap<String, objInterface> contents) {
        this.outer = outer;
        this.contents = contents;
    }

    public enviroment getOuter() {
        return this.outer;
    }

    public objInterface get(Identifier ident) {
        String name = ident.getName();
        if (this.contents.containsKey(name)) {
            return this.contents.get(name);
        }
        return null;
    }

    public String set(String name, objInterface obj) {
        if (!this.contents.containsKey(name)) {
            this.contents.put(name, obj);
            return name;
        }
        return null;
    }
}
