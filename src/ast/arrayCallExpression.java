package ast;
import token.*;

public class arrayCallExpression implements astt.Expression {
    TokenInit token;
    astt.Expression array;
    astt.Expression index;

    public arrayCallExpression(TokenInit token, astt.Expression array, astt.Expression index) {
        this.token = token;
        this.array = array;
        this.index = index;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, array: %s, index: %s}", token.tokString, array.String(), index.String());
        return out;
    }

    @Override
    public void expressionNode() {

    }
} 
