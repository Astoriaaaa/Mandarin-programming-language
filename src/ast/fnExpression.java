package ast;
import java.util.ArrayList;
import token.*;

public class fnExpression implements astt.Expression {
    TokenInit token;
    ArrayList <Identifier> params;
    blockStatements body;

    public fnExpression(TokenInit token, ArrayList <Identifier> params, blockStatements body) {
        this.token = token;
        this.params = params;
        this.body = body;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String p = "[";
        for (int i = 0; i < params.size() - 1; i++) {
            p += params.get(i).String() + ", ";
        }
        if(params.size() > 1) {
            p += params.get(params.size() - 1).String();
        }
       
        p += ']';
        String out = String.format("{token: %s, params: %s, body: %s}", this.token.tokString, p, this.body.String());
        return out;
    }
    @Override
    public void expressionNode() {

    }
}
