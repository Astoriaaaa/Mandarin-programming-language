package ast;
import token.TokenInit;

public class infixExpression implements astt.Expression {
    TokenInit token;
    String prefixOperator;
    astt.Expression rightExp, leftExp;

    public infixExpression(TokenInit token, String prefixOperator, astt.Expression rightExp, astt.Expression leftExp) {
        this.token = token;
        this.prefixOperator = prefixOperator;
        this.rightExp = rightExp;
        this.leftExp = leftExp;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, infixOperator: %s, rightExp: %s, leftExp: %s}", token.tokLiteral, prefixOperator, rightExp.String(), leftExp.String());
        return out;
    }
    @Override
    public void expressionNode() {

    }
}
