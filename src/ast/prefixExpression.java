package ast;
import token.TokenInit;

public class prefixExpression implements astt.Expression {
    TokenInit token;
    String prefixOperator;
    astt.Expression rightExp;

    public prefixExpression(TokenInit token, String prefixOperator, astt.Expression rightExp) {
        this.token = token;
        this.prefixOperator = prefixOperator;
        this.rightExp = rightExp;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, prefixOperator: %s, rightExp: %s}", token.tokLiteral, prefixOperator, rightExp.String());
        return out;
    }
    @Override
    public void expressionNode() {

    }

    public String getOperator() {
        return this.prefixOperator;
    }

    public astt.Expression getRightExp() {
        return this.rightExp;
    }

    
}

