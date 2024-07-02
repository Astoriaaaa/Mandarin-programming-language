package ast;
import token.*;

public class stringLiteral implements astt.Expression {
    TokenInit token;
    String Value;

    public stringLiteral(TokenInit tok, String Value) {
        this.token = tok;
        this.Value = Value;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, Value: %s}", token.tokString, token.tokLiteral);
        return out;
    }
    @Override
    public void expressionNode() {

    }
}

