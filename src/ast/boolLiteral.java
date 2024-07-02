package ast;

import token.TokenInit;

public class boolLiteral implements astt.Expression {
    TokenInit token;
    String Value;

    public boolLiteral(TokenInit token, String Value) {
        this.token = token;
        this.Value = Value;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, Value: %s}", token.tokString, Value);
        return out;
    }
    @Override
    public void expressionNode() {

    }

}

