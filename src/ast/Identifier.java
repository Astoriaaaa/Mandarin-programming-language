package ast;
import token.TokenInit;

public class Identifier implements  astt.Expression{
    String name; 
    TokenInit token;
    public Identifier(TokenInit token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, Value: %s}", token.tokString, name);
        return out;
    }
    @Override
    public void expressionNode() {

    }
}

