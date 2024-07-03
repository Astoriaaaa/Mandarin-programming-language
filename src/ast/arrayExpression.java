package ast;
import com.sun.source.tree.AssertTree;
import java.util.ArrayList;
import java.util.HashMap;
import token.*;


public class arrayExpression implements  astt.Expression{
    TokenInit token;
    ArrayList<astt.Expression> array = new ArrayList<>();

    public arrayExpression(TokenInit token, ArrayList<astt.Expression> array) {
        this.token = token;
        this.array = array;
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
        if(array.size() > 0) {
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