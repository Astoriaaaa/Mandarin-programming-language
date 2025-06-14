package ast;
import java.util.ArrayList;
import token.*;


public class arrayExpression implements  astt.Expression {
    TokenInit token;
    int length;
    ArrayList<astt.Expression> array = new ArrayList<>();

    public arrayExpression(TokenInit token, ArrayList<astt.Expression> array) {
        this.token = token;
        this.array = array;
        this.length = array.size();
    }

    public int getLen() {
        return this.length;
    }

    public astt.Expression getIndex(int index) {
        return array.get(index);
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String p = "[";
        for(int i = 0; i < array.size() - 1; i++) {
            p += array.get(i).String();
        }
        if(!array.isEmpty()) {
            p += array.get(array.size() - 1).String();
        }
        p += "]";
        String out = String.format("{token: %s, array: %s}", token.tokString, p);
        return out;
    }

    @Override
    public void expressionNode() {

    }

    
}