package object;

import java.util.ArrayList;

import ast.*;

public class objFn implements objInterface {
    ArrayList<Identifier> params;
    blockStatements body;
    enviroment env;

    public objFn(ArrayList<Identifier> params, blockStatements body, enviroment env) {
        this.params = params;
        this.body = body;
        this.env = env;
    }

    @Override
    public String Inspect() {
        return "not required to inspect";
    }

    @Override
    public String Type() {
        return "FUNCTION_OBJ";
    }

    public ArrayList<Identifier> getParams() {
        return this.params;
    }

    public blockStatements getBody() {
        return this.body;
    }



}
