package ast;
import java.util.ArrayList;



public class Program implements  astt.Node {
    ArrayList <astt.Statement> statements;

    public Program(ArrayList <astt.Statement> statements) {
        this.statements = statements;
    }

    @Override
    public String tokenLiteral() {
        if (!statements.isEmpty()) {
            return statements.get(0).tokenLiteral();
        }
        return "";
    }
    @Override
    public String String() {
        String out = "";
        while (!statements.isEmpty()) {
            out += "(" + statements.get(0).String() + ")";
            statements.remove(0);
        }
        return out;
    }
}

