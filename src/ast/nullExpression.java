package ast;
import token.*;

public class nullExpression implements astt.Expression {
    TokenInit token;
    String Value;

    public nullExpression(TokenInit token, String Value) {
        this.token = token;
        this.Value = Value;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, Value: %s}", token.tokLiteral, "null");
        return out;
    }
    @Override
    public void expressionNode() {

    }
}
