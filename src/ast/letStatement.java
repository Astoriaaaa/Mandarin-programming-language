package ast;
import token.TokenInit;

public class letStatement implements astt.Statement {
    TokenInit token;
    Identifier Identifier;
    ast.expressionStatement Value;

    public letStatement(TokenInit token, Identifier Identifier, ast.expressionStatement Value) {
        this.token = token;
        this.Identifier = Identifier;
        this.Value = Value;
    }

    @Override
    public String tokenLiteral() {
        return token.toString();
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, Identifer: %s, Value: %s}", token.tokLiteral, Identifier.name, Value.String());
        return out;
    }
    @Override
    public void statementNode() {

    }

}

