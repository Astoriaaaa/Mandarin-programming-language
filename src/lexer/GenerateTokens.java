package lexer;

import java.util.ArrayList;
import token.TokenInit;
import token.Tokens;

public class GenerateTokens {
    LexerInit lexer;
    ArrayList<TokenInit> List;
    public GenerateTokens(String input) {
        LexerInit lexer = new LexerInit(input);
        this.lexer = lexer;
        this.List = new ArrayList<>();
        this.List = GenerateList(lexer);
    }

    public ArrayList<TokenInit> returnList() {
        return List;
    }

    public ArrayList<TokenInit> GenerateList(LexerInit lexer) {
        while(lexer.curChar != 0) {
            TokenInit tok = readToken(lexer);
            if (tok != null) {
                List.add(tok);
            }
            lexer.nextChar();
            
        }

        TokenInit eof = new TokenInit("EOF", "0");
        List.add(eof);
        return List;
    }

    public TokenInit readToken(LexerInit lexer) {
        skipSpaces(lexer);
        String type = null;
        String lit = null;
        if (lexer.curChar == 0) {
            return null;
        } else if (lexer.curChar == '+') {
            type = Tokens.PLUS;
            lit = "+";
        } else if (lexer.curChar == '-') {
            type = Tokens.MINUS;
            lit = "-";
        } else if (lexer.curChar == '!') {
            type = Tokens.EXCLAIM;
            lit = "!";
        } else if (lexer.curChar == '*') {
            type = Tokens.MUL;
            lit = "*";
        } else if (lexer.curChar == '(') {
            type = Tokens.LB;
            lit = "(";
        } else if (lexer.curChar == ')') {
            type = Tokens.RB;
            lit = ")";
        } else if (lexer.curChar == '[') {
            type = Tokens.LS;
            lit = "[";
        } else if (lexer.curChar == ']') {
            type = Tokens.RS;
            lit = "]";
        } else if (lexer.curChar == '{') {
            type = Tokens.LP;
            lit = "{";
        } else if (lexer.curChar == '}') {
            type = Tokens.RP;
            lit = "}";
        } else if (lexer.curChar == ';') {
            type = Tokens.SEMICOLON;
            lit = ";";
        } else if (lexer.curChar == '\"') {
            return readString(lexer);
        } else if (lexer.curChar == ',') {
            type = Tokens.COMMA;
            lit = ",";
        } else if (lexer.curChar == '=') {
            if (peakChar(lexer) == '=') {
                type = Tokens.EQ;
                lit = "==";
                lexer.nextChar();
            } else {
                type = Tokens.ASSIGN;
                lit = "=";
            }
        } else if (lexer.curChar == '!') {
            if (peakChar(lexer) == '=') {
                type = Tokens.NEQ;
                lit = "!=";
                lexer.nextChar();
            }
        } else if (lexer.curChar == '<') {
            type = Tokens.LT;
            lit = "<";
        } else if (lexer.curChar == '>') {
            type = Tokens.GT;
            lit = ">";
        } else if (Character.isDigit(lexer.curChar)) {
            type = Tokens.INT;
            int pos = lexer.curPos;
            while(Character.isDigit(peakChar(lexer))) {
                lexer.nextChar();
            }
            if(Character.isAlphabetic(peakChar(lexer))) {
                while(Character.isAlphabetic(peakChar(lexer)) || Character.isDigit(peakChar(lexer))) {
                    type = Tokens.ILEGAL;
                    lexer.nextChar();
                }
            }
            
            lit = lexer.input.substring(pos, lexer.curPos + 1);
        } else if (Character.isLetter(lexer.curChar)) {
            return readIdent(lexer);
        }

        if (type == null && lit == null) {
            type = Tokens.ILEGAL;
            lit = Character.toString(lexer.curChar);
        }
        TokenInit tok = new TokenInit(type, lit);
        return tok;
    }

    public void skipSpaces(LexerInit lexer) {
        if(lexer.curPos != 0) {
            System.out.println("invoked after " + lexer.input.charAt(lexer.curPos - 1));
        }
        char c = lexer.curChar;
        int count = 0;
        while(c == ' ') {
            lexer.nextChar();
            c = lexer.curChar;
            count += 1;
        }
        System.out.println(count);
    }

    public char peakChar(LexerInit lexer) {
        if(lexer.finalPos <= lexer.curPos) {
            return 0;
        }
        return lexer.input.charAt(lexer.curPos + 1);
    }
   
    public token.TokenInit readString(LexerInit lexer) {
        String type = Tokens.STRING;
        String lit;
        if(peakChar(lexer) == '"') {
            lit = "";
        } else {
            lexer.nextChar();
            int start = lexer.curPos;
            while(lexer.curChar != '"' && lexer.curChar != 0) {
                lexer.nextChar();
            }
            if (lexer.curChar == 0) {
                type = Tokens.ILEGAL;
            }
            lit = lexer.input.substring(start, lexer.curPos);
        }
        
        TokenInit tok = new TokenInit(type, lit);
        return tok;
    }

    public token.TokenInit readIdent(LexerInit lexer) {
        String type = Tokens.IDENTIFIER;
        String lit;
        int start = lexer.curPos;
        while(Character.isLetter(peakChar(lexer)) && lexer.curChar != 0) {
            lexer.nextChar();
        }
        if(Character.isDigit(peakChar(lexer))){
            while (Character.isDigit(peakChar(lexer)) || Character.isAlphabetic((peakChar(lexer)))) {
                type = Tokens.ILEGAL;
                lexer.nextChar();
            }
        }
        lit = lexer.input.substring(start, lexer.curPos + 1);
        if(Tokens.isToken(lit)) {
            type = Tokens.identType(lit);
        }
        TokenInit tok = new TokenInit(type, lit);
        return tok;
    }
    public static void main(String[] args) {
        String input = "!false";
        
        GenerateTokens tokens = new GenerateTokens(input);
        
        for (int i = 0; i < tokens.List.size(); i++) {
            TokenInit tok = tokens.List.get(i);
            System.out.println(tok.tokString);
        }
        
    }

}