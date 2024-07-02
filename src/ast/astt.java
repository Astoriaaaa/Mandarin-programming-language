package ast;
import java.util.ArrayList;
import token.TokenInit;

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
        @Override
        public void expressionNode() {

        }

    }

    // public class fnExpression implements Expression {
    //     TokenInit token;
    //     ArrayList <Identifier> params;
    //     BlockStatements body;

    //     @Override
    //     public String tokenLiteral() {
    //         return token.toString();
    //     }
    //     @Override
    //     public String String() {
    //         String out = "fn( ";
    //         for (int i = 0; i < params.size(); i++) {
    //             out += params.get(i).name + ", ";
    //         }
    //         out += ")" + " { " + body.String() + " }";
    //         return out;
    //     }
    //     @Override
    //     public void expressionNode() {

    //     }
    // }

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
            
            for (int i = 0; i < params.size(); i++) {
                out += params.get(i) + ", ";
            }
            return function.String() + "(" + out + ")";
        }

        @Override
        public void expressionNode() {

        }
    }  
}

