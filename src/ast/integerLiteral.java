package ast;
import token.*;

public class integerLiteral implements astt.Expression {
    TokenInit token;
    int Value;

    public integerLiteral(TokenInit token, int Value) {
        this.token = token;
        this.Value = Value;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = "{token: " + token.tokLiteral + ", Value: " + Integer.toString(Value) + "}";
        return out;
    }
    @Override
    public void expressionNode() {

    }

    public int getValue() {
        return this.Value;
    }

}

