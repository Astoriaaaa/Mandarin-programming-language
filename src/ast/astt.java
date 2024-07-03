package ast;

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

}

