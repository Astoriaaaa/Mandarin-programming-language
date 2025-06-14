package ast;
import java.util.ArrayList;
import token.*;

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
        for (int i = 0; i < params.size(); i++) {
            p += params.get(i).String() + ", ";
        }
        p = p.substring(0, p.length() - 2);
       
        p += ']';
        String out = String.format("{token: %s, function: %s, params: %s}", token.tokString, function.String(), p);
        return out;
    }

    @Override
    public void expressionNode() {

    }

    public astt.Expression getFunction() {
        return this.function;
    }

    public ArrayList<astt.Expression> getParams() {
        return this.params;
    }
}  

