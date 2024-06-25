package parser;

import ast.Identifier;
import ast.astt;
import ast.expressionStatement;
import ast.integerLiteral;
import ast.letStatement;
import ast.returnStatement;
import java.util.ArrayList;
import lexer.GenerateTokens;
import token.TokenInit;
import token.Tokens;

public class GenerateAST {
    
    public class ParserInit {
        ArrayList<TokenInit> arrToken;
        TokenInit curtok;
        TokenInit peaktok;
        int pos;
        ArrayList <String> errors = new ArrayList<>();
        public ParserInit(ArrayList<TokenInit> aT) {
            this.arrToken = aT;
            this.curtok = null;
            this.peaktok = aT.get(0);
            this.pos = -1;
            if (aT.size() > 1) {
                nextToken();
            }
        }

        public void nextToken() {
            pos += 1;
            curtok = peaktok;
            if(curtok.tokType != Tokens.EOF) {
                peaktok = arrToken.get(pos + 1);
            }
        }
    }

    public GenerateAST(String input) {
        GenerateTokens gt = new GenerateTokens(input);
        ArrayList <TokenInit> arrTokens = gt.returnList();
        ParserInit p = new ParserInit(arrTokens);
        ArrayList <astt.Statement> statements = parseProgram(p);
        ast.Program program = new ast.Program(statements);
        if(p.errors.isEmpty()) {
            System.out.println(program.String());
        } else {
            while(!p.errors.isEmpty()) {
                System.out.println(p.errors.get(0));
                p.errors.remove(0);
            }
        }
    }

    public ArrayList <astt.Statement> parseProgram(ParserInit p) {
        ArrayList <astt.Statement> statements = new ArrayList<>();
        while(p.curtok.tokType != Tokens.EOF) {
            if(p.curtok.tokType == Tokens.LET) {
                statements.add(parseLetStatement(p));
            } 
            else if (p.curtok.tokType == token.Tokens.RETURN) {
                statements.add(parseReturnStatement(p));
            } else {
                statements.add(parseExpressionStatement(p));
            }
        }
        return statements;
    } 

    public ast.letStatement parseLetStatement(ParserInit p) {
        ast.Identifier ident = null;
        token.TokenInit tok = p.curtok;
        
        if (peakExpect(p, Tokens.IDENTIFIER)) {
            ident = new Identifier(p.curtok.tokLiteral, p.curtok);
        }
        peakExpect(p, Tokens.ASSIGN);
        p.nextToken();
        ast.expressionStatement exp = parseExpressionStatement(p);
        ast.letStatement stmt = new letStatement(tok, ident, exp);
        return stmt;
    }

    public ast.returnStatement parseReturnStatement(ParserInit p) {
        TokenInit tok = p.curtok;
        p.nextToken();
        ast.expressionStatement exp = parseExpressionStatement(p);
        returnStatement stmt = new returnStatement(tok, exp);
        return stmt;
    }

    public ast.expressionStatement parseExpressionStatement(ParserInit p) {
        TokenInit tok = p.curtok;
        astt.Expression exp = parseExpression(p);
        ast.expressionStatement stmt = new expressionStatement(tok, exp);
        if (p.curtok.tokType == Tokens.SEMICOLON) {
            p.nextToken();
        }

        return stmt;

    }

    public astt.Expression parseExpression(ParserInit p) {
        ast.integerLiteral exp = new integerLiteral(p.curtok, Integer.parseInt(p.curtok.tokLiteral));
        p.nextToken();
        return exp;
    }

    public Boolean peakExpect(ParserInit p, String expect) {
        if(p.peaktok.tokType != expect) {
            p.errors.add("Expected token: " + expect + " Got: " + p.peaktok.tokType);
            p.nextToken();
            return false;
        }
        p.nextToken();
        return true;
    }

    public static void main(String[] args) {
        GenerateAST ast = new GenerateAST("let = 9");
    }

}
