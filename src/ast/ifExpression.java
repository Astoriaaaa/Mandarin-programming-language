package ast;
import token.*;

public class ifExpression implements astt.Expression {
    TokenInit token;
    astt.Expression condition;
    blockStatements ifExcution;
    ifExpression elseExcution;
    
    public ifExpression(TokenInit token, astt.Expression condition, blockStatements ifExcution, ifExpression elseExcution) {
        this.token = token;
        this.condition = condition;
        this.ifExcution = ifExcution;
        this.elseExcution = elseExcution;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String String() {
        String condition;
        String elseExcution;

        TokenInit tok = new TokenInit("NULL", "null");
        nullExpression nullexp = new nullExpression(tok, "null");

        if(this.condition == null) {
            condition = nullexp.String();
        } else {
            condition = this.condition.String();
        }
        if(this.elseExcution == null) {
            elseExcution = nullexp.String();
        } else {
            elseExcution = this.elseExcution.String();
        }
        
        String out = String.format("{token: %s, condition: %s, ifExcution: %s, elseExcution: %s}", 
        token.tokString, condition, ifExcution.String(), elseExcution);
        return out;
    }

    @Override
    public String tokenLiteral() {
        return token.tokString;
    }

    public astt.Expression getCondition() {
        return this.condition;
    }

    public blockStatements getBlockStatements() {
        return this.ifExcution;
    }

    public ifExpression getNextIfExpression() {
        return this.elseExcution;
    }
}
