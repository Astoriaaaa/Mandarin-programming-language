package lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import token.TokenInit;
import token.Tokens;

public class GenerateTokens {
    LexerInit lexer;
    ArrayList<TokenInit> List;
    public GenerateTokens(String input) {
        LexerInit lexerr = new LexerInit(input);
        this.lexer = lexerr;
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

    public Boolean isMandInt(char num) {
        char[] ints = {'〇', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'};
        Character[] characterArray = new Character[ints.length];
        for (int i = 0; i < ints.length; i++) {
            characterArray[i] = ints[i];
        }

        List<Character> list = Arrays.asList(characterArray);

        return list.contains(num);
    }

    public int convertMandarin(String num) {
        String ints[] = {"〇", "一","二", "三", "四","五", "六", "七","八","九","十"};
        List<String> list = new ArrayList<>(Arrays.asList(ints));
        if(num.length() == 1) {
            return list.indexOf(num);
        } else if (num.length() == 2 && num.charAt(0) == '十') {
            return 10 + convertMandarin(num.substring(1));
        } else if(num.length() == 2) {
            return 10 * convertMandarin(num.substring(0, 1));
        } else if(num.length() == 3) {
            return 10 * convertMandarin(num.substring(0, 1)) + convertMandarin(num.substring(2));
        }
        return -1;
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
        } else if (isMandInt(lexer.curChar)) {
            type = Tokens.INT;
            int pos = lexer.curPos;
            while(isMandInt(peakChar(lexer))) {
                lexer.nextChar();
            }
            while(Character.isAlphabetic(peakChar(lexer)) || Character.isDigit(peakChar(lexer))) {
                type = Tokens.ILEGAL;
                lexer.nextChar();
            }
            int englishNum = convertMandarin(lexer.input.substring(pos, lexer.curPos + 1));
            if (englishNum == -1) {
                type = Tokens.ILEGAL;
            }
            lit = Integer.toString(englishNum);
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
        
        char c = lexer.curChar;
        while(c == ' ') {
            lexer.nextChar();
            c = lexer.curChar;
        }
        
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

        while (Character.isDigit(peakChar(lexer)) || Character.isLetter(peakChar(lexer)) || isMandInt(peakChar(lexer))) {
            type = Tokens.ILEGAL;
            lexer.nextChar();
        }
        lit = lexer.input.substring(start, lexer.curPos + 1);
        if(Tokens.isToken(lit)) {
            type = Tokens.identType(lit);
        }
        TokenInit tok = new TokenInit(type, lit);
        return tok;
    }
    public static void main(String[] args) {
        String input = "设 func = 功能(a, b) {返回 a + b}; func(四, 五) + func(四, 五`);";
        
        GenerateTokens tokens = new GenerateTokens(input);
        
        for (int i = 0; i < tokens.List.size(); i++) {
            TokenInit tok = tokens.List.get(i);
            System.err.println(tok.tokString);
        }
        
    }

}