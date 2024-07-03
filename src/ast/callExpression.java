package ast;
import com.sun.source.tree.AssertTree;
import token.*;
import java.util.ArrayList;

public class callExpression implements astt.Expression {
    TokenInit token;
    astt.Expression function;
    ArrayList<astt.Expression> params;

    public callExpression(TokenInit token, astt.Expression function, ArrayList<astt.Expression> params) {
        this.token = token;
        this.function = function;
        this.params = params;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String p = "[";
        System.out.println(p);
        for (int i = 0; i < params.size() - 1; i++) {
            p += params.get(i).String() + ", ";
        }
        if(params.size() > 1) {
            p += params.get(params.size() - 1).String();
        }
        p += "]";
        String out = String.format("{token: %s, function: %s, params: %s}", token.tokString, function.String(), p);
        return out;
    }

    @Override
    public void expressionNode() {

    }
}  

