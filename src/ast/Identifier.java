package ast;
import token.TokenInit;

public class Identifier {
    String name; 
    TokenInit token;
    public Identifier(String name, TokenInit token) {
        this.token = token;
        this.name = name;
    }
}

