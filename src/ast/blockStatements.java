package ast;
import token.*;

public class blockStatements implements astt.Statement {
    TokenInit token;
    Program statements;

    public blockStatements(TokenInit token, Program statements) {
        this.token = token;
        this.statements = statements;
    }

    @Override
    public String tokenLiteral() {
        return token.tokLiteral;
    }
    @Override
    public String String() {
        String out = String.format("{token: %s, statements: %s}", token.tokString, statements.String());
        return out;
    }
    @Override
    public void statementNode() {

    }

    public Program getStatements() {
        return this.statements;
    }
}

