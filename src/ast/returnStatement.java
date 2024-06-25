package ast;
import token.TokenInit;

public class returnStatement implements astt.Statement {
    TokenInit token;
    ast.expressionStatement Value;

    public returnStatement(TokenInit token, ast.expressionStatement Value) {
        this.token = token;
        this.Value = Value;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, Value: %s}", token.tokLiteral, Value.String());
        return out;
    }
    @Override
    public void statementNode() {

    }

    
}

