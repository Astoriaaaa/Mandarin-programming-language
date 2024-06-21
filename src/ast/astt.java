package ast;
import token.TokenInit;

import java.util.ArrayList;

public class astt {

    public interface Node {
        public String tokenLiteral();
        public String String();
    }

    public interface Expression extends Node {
        void expressionNode();
    }

    public interface Statement extends Node{
        void statementNode();
    }

    public class Program implements  Node {
        ArrayList <Statement> statements;
        @Override
        public String tokenLiteral() {
            if (!statements.isEmpty()) {
                return statements.get(0).tokenLiteral();
            }
            return "";
        }
        @Override
        public String String() {
            if (!statements.isEmpty()) {
                return statements.get(0).String();
            }
            return "";
        }
    }

    public class Identifier {
        TokenInit token;
        String name; 
    }

    public class letStatement implements Statement {
        TokenInit token;
        Identifier Identifier;
        Expression Value;
        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return "let " + Identifier.name + " = " + Value.String();
        }

        public void statementNode() {

        }

    }

    public class returnStatement implements Statement {
        TokenInit token;
        Expression Value;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return "return " + Value.String();
        }

        public void statementNode() {

        }

        
    }

    public class BlockStatements implements Statement {
        TokenInit token;
        ArrayList<Statement> statements;

        @Override
        public String tokenLiteral() {
            if (!statements.isEmpty()) {
                return statements.get(0).tokenLiteral();
            }
            return "";
        }
        @Override
        public String String() {
            if (!statements.isEmpty()) {
                return statements.get(0).String();
            }
            return "";
        }

        public void statementNode() {

        }
    }

    public class expressionStatement implements Statement {
        TokenInit token;
        Expression Expression;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return Expression.String();
        }

        public void statementNode() {

        }
    }

    public class integerLiteral implements Expression {
        TokenInit token;
        int Value;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return Integer.toString(Value);
        }

        public void expressionNode() {

        }

    }

    public class stringLiteral implements Expression {
        TokenInit token;
        String Value;
        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return Value;
        }

        public void expressionNode() {

        }
    }

    public class boolLiteral implements Expression {
        TokenInit token;
        boolean Value;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return Boolean.toString(Value);
        }

        public void expressionNode() {

        }

    }

    public class prefixExpression implements Expression {
        TokenInit token;
        String operator;
        Expression rightExp;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return operator + " " + rightExp.String();
        }

        public void expressionNode() {

        }

    }

    public class infixExpression implements Expression {
        TokenInit token;
        String operator;
        Expression leftExp;
        Expression rightExp;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            return leftExp.String() + " " + operator + " " + rightExp.String();
        }

        public void expressionNode() {

        }

    }

    public class fnExpression implements Expression {
        TokenInit token;
        ArrayList <Identifier> params;
        BlockStatements body;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            String out = "fn( ";
            ArrayList <String> paramsList = new ArrayList<>();
            for (int i = 0; i < params.size(); i++) {
                out += params.get(i).name + ", ";
            }
            out += ")" + " { " + body.String() + " }";
            return out;
        }

        public void expressionNode() {

        }
    }

    public class callExpression implements Expression {
        TokenInit token;
        Expression function;
        ArrayList<String> params;

        @Override
        public String tokenLiteral() {
            return token.toString();
        }
        @Override
        public String String() {
            String out = "";
            ArrayList <String> params = new ArrayList<>();
            for (int i = 0; i < params.size(); i++) {
                out += params.get(i) + ", ";
            }
            return function.String() + "(" + out + ")";
        }

        public void expressionNode() {

        }
    }

}
