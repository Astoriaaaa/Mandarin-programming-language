package ast;
import token.TokenInit;

public class infixExpression implements astt.Expression {
    TokenInit token;
    String infixOperator;
    astt.Expression rightExp, leftExp;

    public infixExpression(TokenInit token, String infixOperator, astt.Expression rightExp, astt.Expression leftExp) {
        this.token = token;
        this.infixOperator = infixOperator;
        this.rightExp = rightExp;
        this.leftExp = leftExp;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, infixOperator: %s, rightExp: %s, leftExp: %s}", token.tokString, infixOperator, rightExp.String(), leftExp.String());
        return out;
    }
    @Override
    public void expressionNode() {

    }

    public astt.Expression getRightExp() {
        return this.rightExp;
    }

    public astt.Expression getLeftExp() {
        return this.leftExp;
    }

    public String getOperator() {
        return this.infixOperator;
    }

}
