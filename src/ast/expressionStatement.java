package ast;
import token.*;

public class expressionStatement implements astt.Statement {
    TokenInit token;
    astt.Expression Expression;

    public expressionStatement(TokenInit token, astt.Expression exp) {
        this.token = token;
        this.Expression = exp;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        return Expression.String();
    }
    @Override
    public void statementNode() {

    }
}
