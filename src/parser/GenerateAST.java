package parser;

import ast.Identifier;
import ast.astt;
import ast.expressionStatement;
import ast.infixExpression;
import ast.integerLiteral;
import ast.letStatement;
import ast.prefixExpression;
import ast.returnStatement;
import java.util.ArrayList;
import java.util.HashMap;
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
        HashMap<String, prefixParse> prefixMap = new HashMap<>();
        HashMap<String, parseInfix> infixMap = new HashMap<>();
        HashMap<String, Integer> predcedences = new HashMap<>();
        

        public ParserInit(ArrayList<TokenInit> aT) {
            this.arrToken = aT;
            this.curtok = null;
            this.peaktok = aT.get(0);
            this.pos = -1;
            if (aT.size() > 1) {
                nextToken();
            }
            this.predcedences.put("LOWEST", 0);
            this.predcedences.put(Tokens.EQ, 1);
            this.predcedences.put(Tokens.GT, 2);
            this.predcedences.put(Tokens.LT, 2);
            this.predcedences.put(Tokens.PLUS, 3);
            this.predcedences.put(Tokens.MUL, 4);
            this.predcedences.put(Tokens.MINUS, 5);
            this.predcedences.put(Tokens.NEQ, 5);
            this.predcedences.put(Tokens.LB, 6);


            this.prefixMap.put(Tokens.INT, this :: parseIntegerLiteral);
            //this.prefixMap.put(Tokens.INT, parseIdentifier);
            this.prefixMap.put(Tokens.MINUS, this :: parsePrefixExp);
            this.prefixMap.put(Tokens.MINUS, this :: parsePrefixExp);
            this.prefixMap.put(Tokens.LB, this :: parseGroupExpression);

            this.infixMap.put(Tokens.PLUS, this :: parseInfixExp);
            this.infixMap.put(Tokens.MUL, this:: parseInfixExp);
            this.infixMap.put(Tokens.MINUS, this:: parseInfixExp);


        }

        public ast.integerLiteral parseIntegerLiteral(ParserInit p) {
            System.out.println("here");
            int num = Integer.parseInt(p.curtok.tokLiteral);
            TokenInit tok = p.curtok;
            ast.integerLiteral exp = new integerLiteral(tok, num);
            return exp;
        }
        public ast.prefixExpression parsePrefixExp(ParserInit p) {
            String operator = p.curtok.tokLiteral;
            TokenInit tok = p.curtok;
            p.nextToken();
            astt.Expression rightExp = parseExpression(p, pred("LOWEST"));
            ast.prefixExpression exp = new prefixExpression(tok, operator, rightExp);
            return exp;
        }

        public ast.infixExpression parseInfixExp(ParserInit p, astt.Expression leftExp) {
            String operator = p.curtok.tokLiteral;
            TokenInit tok = p.curtok;
            p.nextToken();
            astt.Expression rightExp = parseExpression(p, pred("LOWEST"));
            ast.infixExpression exp = new infixExpression(tok, operator, rightExp, leftExp);
            return exp;
        }

        public astt.Expression parseGroupExpression(ParserInit p) {
            p.nextToken();
            astt.Expression exp = parseExpression(p, pred("LOWEST"));
            if (p.peaktok.tokType == token.Tokens.RB) {
                p.nextToken();
                return exp;
            }
            return null;
        }

        public int pred(String tok) {
            if (this.predcedences.containsKey(tok)) {
                return this.predcedences.get(tok);
            }
            return this.predcedences.get("LOWEST");
        }

        public prefixParse getPrefix(String tok) {
            return this.prefixMap.get(tok);
        }

        public parseInfix getInfix(String tok) {
            return this.infixMap.get(tok);
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
            p.nextToken();
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
        astt.Expression exp = parseExpression(p, 0);
        ast.expressionStatement stmt = new expressionStatement(tok, exp);
        if (p.curtok.tokType == Tokens.SEMICOLON) {
            p.nextToken();
        }

        return stmt;

    }

    public astt.Expression parseExpression(ParserInit p, int precedence) {
        prefixParse prefix = p.getPrefix(p.curtok.tokType);
        if (prefix == null) {
            p.errors.add("Invalid prefix " + p.curtok.tokType);
            return null;
        }

        astt.Expression leftExp = prefix.parsePrefix(p);
        System.out.println(p.peaktok.tokType);
        if (p.peaktok.tokType != token.Tokens.SEMICOLON && precedence < p.pred(p.peaktok.tokType)) {
            parseInfix infix = p.getInfix(p.peaktok.tokType);

            if(infix == null){
                return leftExp;
            }
            p.nextToken();
            leftExp = infix.parseInfix(p, leftExp);
        }
        System.out.println(leftExp.String());
        return leftExp;
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
        GenerateAST ast = new GenerateAST("let five = (8 * (9 * (6 - 2)))");
    }

}
