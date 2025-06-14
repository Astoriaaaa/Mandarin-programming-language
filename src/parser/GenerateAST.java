package parser;

import ast.*;
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
        HashMap<String, infixParse> infixMap = new HashMap<>();
        HashMap<String, Integer> predcedences = new HashMap<>();

        public ArrayList<String> getErrors() {
            return this.errors;
        }
    
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
            this.predcedences.put(Tokens.MINUS, 4);
            this.predcedences.put(Tokens.MUL, 5);
            this.predcedences.put(Tokens.NEQ, 5);
            this.predcedences.put(Tokens.LB, 6);
            this.predcedences.put(Tokens.LS, 6);
            


            this.prefixMap.put(Tokens.INT, this :: parseIntegerLiteral);
            this.prefixMap.put(Tokens.IDENTIFIER, this :: parseIdentifier);
            this.prefixMap.put(Tokens.MINUS, this :: parsePrefixExp);
            this.prefixMap.put(Tokens.EXCLAIM, this :: parsePrefixExp);
            this.prefixMap.put(Tokens.LB, this :: parseGroupExpression);
            this.prefixMap.put(Tokens.NULL, this :: parseNullExpression);
            this.prefixMap.put(Tokens.STRING, this :: parseStringExpression);
            this.prefixMap.put(Tokens.IF, this :: parseIfExpression);
            this.prefixMap.put(Tokens.TRUE, this :: parseBoolLiteral);
            this.prefixMap.put(Tokens.FALSE, this :: parseBoolLiteral);
            this.prefixMap.put(Tokens.FN, this :: parseFnExpression);
            this.prefixMap.put(Tokens.LS, this :: parseArrayExpression);

            this.infixMap.put(Tokens.PLUS, this :: parseInfixExp);
            this.infixMap.put(Tokens.MUL, this:: parseInfixExp);
            this.infixMap.put(Tokens.MINUS, this:: parseInfixExp);
            this.infixMap.put(Tokens.LB, this :: parseFnCall);
            this.infixMap.put(Tokens.LS, this :: parseArrayCallExpression);
            this.infixMap.put(Tokens.GT, this :: parseInfixExp);
            this.infixMap.put(Tokens.EQ, this :: parseInfixExp);
            this.infixMap.put(Tokens.NEQ, this :: parseInfixExp);
            this.infixMap.put(Tokens.LT, this :: parseInfixExp);

        }

        public ast.arrayCallExpression parseArrayCallExpression(ParserInit p, astt.Expression leftExp) {
            System.out.println("parseArrayCallExpression");
            TokenInit tok = p.curtok;
            astt.Expression array = leftExp;
            if (!(array instanceof Identifier)) {
                p.errors.add("Expected: IDENTIFIER, Got: " + tok.tokLiteral);
                return null;
            }
            p.nextToken();
            if(p.curtok.tokType == Tokens.RS) {
                p.errors.add("Expected EXPRESSION GOT NULL");
                return null;
            }
            astt.Expression index = parseExpression(p, pred("LOWEST"));
            if (!peakExpect(p, Tokens.RS)) {
                return null;
            }
            Identifier ident = (Identifier) array;
            return new arrayCallExpression(tok, ident, index);
        }

        public ast.arrayExpression parseArrayExpression(ParserInit p) {
            System.out.println("parseArrayExpression");
            TokenInit tok = p.curtok;
            ArrayList<astt.Expression> exps = new ArrayList<>();
            
            if (!p.peaktok.tokType.equals(Tokens.RS)) {
                p.nextToken();
                astt.Expression exp = parseExpression(p, pred("LOWEST"));
                if(exp == null) {
                    return null;
                }
                exps.add(exp);
                while(p.peaktok.tokType.equals(Tokens.COMMA)) {
                    p.nextToken();
                    p.nextToken();
                    astt.Expression expp = parseExpression(p, pred("LOWEST"));
                    if(exp == null) {
                        return null;
                    }
                    exps.add(expp);
                }
            }

            if (!peakExpect(p, Tokens.RS)) {
                return null;
            }

            ast.arrayExpression arrExp = new arrayExpression(tok, exps);
            return arrExp;
            
        }

        public ast.callExpression parseFnCall(ParserInit p, astt.Expression leftExp) {
            System.out.println("parseFnCall");
            TokenInit tok = p.curtok;
            astt.Expression function = leftExp;
            ArrayList<astt.Expression> idents = new ArrayList<>();
            
            if (p.curtok.tokType == Tokens.LB) {
                p.nextToken();
                astt.Expression exp = parseExpression(p, pred("LOWEST"));
                if(exp == null) {
                    return null;
                }
                idents.add(exp);
                while(p.peaktok.tokType.equals(Tokens.COMMA)) {
                    p.nextToken();
                    p.nextToken();
                    astt.Expression expp = parseExpression(p, pred("LOWEST"));
                    if(exp == null) {
                        return null;
                    }
                    idents.add(expp);
                }
            }


            if(!peakExpect(p, Tokens.RB)) {
                p.errors.add("Expected token: ) GOT: " + p.curtok.tokType);
                return null;
            }

            ast.callExpression callExp = new callExpression(tok, function, idents);
            return callExp;

        }

        public ast.fnExpression parseFnExpression(ParserInit p) {
            System.out.println("parseFnExpression");
            TokenInit tok = p.curtok;
            if(!peakExpect(p, Tokens.LB)) {
                return null;
            }
            
            ArrayList<ast.Identifier> idents = new ArrayList<>();
            if (p.peaktok.tokType == Tokens.IDENTIFIER) {
                p.nextToken();
                idents.add(parseIdentifier(p));
                
            } else if( p.peaktok.tokType != Tokens.RB) {
                p.errors.add("Expected: " + Tokens.RB + " Got: " + p.curtok.tokType);
                return null;
            }
           
            while(p.peaktok.tokType == Tokens.COMMA) {
                p.nextToken();
                p.nextToken();
                if (p.curtok.tokType != Tokens.IDENTIFIER) {
                    p.errors.add("Expected: " + Tokens.IDENTIFIER + " Got: " + p.curtok.tokType);
                    return null;
                }
                idents.add(parseIdentifier(p));
            }

            if(!peakExpect(p, Tokens.RB)) {
                return null;
            }

            if(!peakExpect(p, Tokens.LP)) {
                return null;
            }

            ast.blockStatements stmts = parseBlockStatements(p);
            fnExpression fn = new fnExpression(tok, idents, stmts);
            return fn;


        }

        public ast.boolLiteral parseBoolLiteral(ParserInit p) {
            System.out.println("parseBoolLiteral");
            return new boolLiteral(p.curtok, p.curtok.tokLiteral);
        }

        public ast.ifExpression parseElseExpression(ParserInit p) {
            System.out.println("parseElseExpression");

            if(p.peaktok.tokType == Tokens.ENDIF) {
                System.out.println("endiffffffff");
                p.nextToken();
                return null;
            } else if (p.peaktok.tokType != Tokens.ELSEIF && p.peaktok.tokType != Tokens.ELSE) {
                p.errors.add("Expected token: " + Tokens.ENDIF + " Got: " + p.peaktok.tokType);
                return null;
            } 
            p.nextToken();
            return parseIfExpression(p);
        }

        public ast.blockStatements parseBlockStatements(ParserInit p) {
            System.out.println("parseBlockStatements");

            TokenInit tok = p.curtok;
            p.nextToken();
            ArrayList <astt.Statement> stmts = new ArrayList<>();
            
            while(p.curtok.tokType != Tokens.RP) {
                astt.Statement stmt = parseStatement(p);

                if(stmt == null) {
                    return null;
                }
                stmts.add(stmt);
                p.nextToken();
                

            }
            ast.Program program = new Program(stmts);
            ast.blockStatements bstmts = new blockStatements(tok, program);
            return bstmts;
        }

        public ast.ifExpression parseIfExpression(ParserInit p) {
            System.out.println("parseIfExpression");

            TokenInit tok = p.curtok;
            astt.Expression condition = null;
            ifExpression elseExcution;

            System.out.println(tok.tokLiteral + " parse if");

            if(p.curtok.tokType != Tokens.ELSE) {
                if(!peakExpect(p, Tokens.LB)) {
                    return null;
                }
                p.nextToken();
                if(p.curtok.tokType == Tokens.RB) {
                    p.errors.add("Missing condition: if ()");
                    return null;
                }
                condition = parseExpression(p, p.pred("LOWEST"));
                if(condition == null) {
                    return null;
                }
                if(!peakExpect(p, Tokens.RB)) {
                    return null;
                }
            }
            
            
            if(!peakExpect(p, Tokens.LP)) {
                return null;
            }

            System.out.println(p.curtok.tokLiteral + " block stmt");
            ast.blockStatements stmts = parseBlockStatements(p);

            if (stmts == null) {
                return null;
            }

            
            System.out.println(p.curtok.tokType +  " parse else");
            elseExcution = parseElseExpression(p);

            if (elseExcution == null && p.curtok.tokType != Tokens.ENDIF) {
                return null;
            }

            // if(p.curtok.tokType != Tokens.ENDIF || p.peaktok.tokType == Tokens.SEMICOLON) {
            //     p.nextToken();
            // }
            
            
            ifExpression exp = new ifExpression(tok, condition, stmts, elseExcution);
            System.out.println("doneeee " + p.curtok.tokType);
            return exp;

        }

        public ast.stringLiteral parseStringExpression(ParserInit p) {
            System.out.println("parseStringExpression");

            TokenInit tok = p.curtok;
            String Value = p.curtok.tokLiteral;
            stringLiteral exp = new stringLiteral(tok, Value);
            return exp;
        }

        public ast.Identifier parseIdentifier(ParserInit p) {
            System.out.println("parseIdentifier");
            return new Identifier(p.curtok, p.curtok.tokLiteral);
        }

        public ast.nullExpression parseNullExpression(ParserInit p) {
            System.out.println("parseNullExpression");
            return new nullExpression(p.curtok, "null");
        }

        public ast.integerLiteral parseIntegerLiteral(ParserInit p) {
            System.out.println("parseIntegerLiteral");
            int num = Integer.parseInt(p.curtok.tokLiteral);
            TokenInit tok = p.curtok;
            ast.integerLiteral exp = new integerLiteral(tok, num);
            return exp;
        }

        public ast.prefixExpression parsePrefixExp(ParserInit p) {
            System.out.println("parsePrefixExp");
            String operator = p.curtok.tokLiteral;
            TokenInit tok = p.curtok;
            p.nextToken();
            astt.Expression rightExp = parseExpression(p, pred("LOWEST"));
            ast.prefixExpression exp = new prefixExpression(tok, operator, rightExp);
            return exp;
        }

        public ast.infixExpression parseInfixExp(ParserInit p, astt.Expression leftExp) {
            System.out.println("parseInfixExp");
            String operator = p.curtok.tokLiteral;
            TokenInit tok = p.curtok;
            p.nextToken();
            astt.Expression rightExp = parseExpression(p, pred(tok.tokLiteral));
            ast.infixExpression exp = new infixExpression(tok, operator, rightExp, leftExp);
            return exp;
        }

        public astt.Expression parseGroupExpression(ParserInit p) {
            System.out.println("parseGroupExpression");
            p.nextToken();
            astt.Expression exp = parseExpression(p, pred("LOWEST"));
            if (p.peaktok.tokType == token.Tokens.RB) {
                p.nextToken();
                return exp;
            }
            return null;
        }

        public int pred(String tok) {
            System.out.println("pred");
            if (this.predcedences.containsKey(tok)) {
                return this.predcedences.get(tok);
            }
            return this.predcedences.get("LOWEST");
        }

        public prefixParse getPrefix(String tok) {
            System.out.println("getPrefix");
            return this.prefixMap.get(tok);
        }

        public infixParse getInfix(String tok) {
            System.out.println("getInfix");
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

    ast.Program program;
    ArrayList<String> errors;

    public GenerateAST(String input) {
        GenerateTokens gt = new GenerateTokens(input);
        ArrayList <TokenInit> arrTokens = gt.returnList();
        ParserInit p = new ParserInit(arrTokens);
        ArrayList <astt.Statement> statements = parseProgram(p);
        this.program = new ast.Program(statements);
        this.errors = p.getErrors();
        // if(p.errors.isEmpty()) {
        //     System.out.println(program.String()); //chnage program.String() function
        // } else {
        //     for(int i = 0; i < p.errors.size(); i++) {
        //         System.out.println(p.errors.get(i));
        //     }
        // }
    }

    public ArrayList<String> getErrors() {
        return this.errors;
    }

    public ast.Program getParsedProgram() {
        return this.program;
    }

    public ArrayList <astt.Statement> parseProgram(ParserInit p) {
        System.out.println("parseProgram");
        ArrayList <astt.Statement> statements = new ArrayList<>();
        while(p.curtok.tokType != Tokens.EOF && p.errors.isEmpty()) {
            statements.add(parseStatement(p));
            p.nextToken();
        }
        return statements;
    } 

    public astt.Statement parseStatement(ParserInit p) {
        System.out.println("parseStatement");
        ArrayList <astt.Statement> statements = new ArrayList<>();
        if(p.curtok.tokType == Tokens.LET) {
            return parseLetStatement(p);
        } 
        else if (p.curtok.tokType == token.Tokens.RETURN) {
            return parseReturnStatement(p);
        } else {
            return parseExpressionStatement(p);
        }
        
    }

    public ast.letStatement parseLetStatement(ParserInit p) {
        System.out.println("parseLetStatement");
        ast.Identifier ident = null;
        token.TokenInit tok = p.curtok;
        
        if (peakExpect(p, Tokens.IDENTIFIER)) {
            ident = new Identifier(p.curtok, p.curtok.tokLiteral);
        }
        peakExpect(p, Tokens.ASSIGN);
        p.nextToken();
        ast.expressionStatement exp = parseExpressionStatement(p);
        ast.letStatement stmt = new letStatement(tok, ident, exp);
        return stmt;
    }

    public ast.returnStatement parseReturnStatement(ParserInit p) {
        System.out.println("parseReturnStatement");
        TokenInit tok = p.curtok;
        p.nextToken();
        ast.expressionStatement exp;
        if (p.curtok.tokType == token.Tokens.SEMICOLON) {
            TokenInit nullTok = new TokenInit("NULL", "null");
            nullExpression expNull = new nullExpression(nullTok, "null");
            exp = new expressionStatement(nullTok, expNull);
        } else {
            exp = parseExpressionStatement(p);
        }
        returnStatement stmt = new returnStatement(tok, exp);
        return stmt;
    }

    public ast.expressionStatement parseExpressionStatement(ParserInit p) {
        System.out.println("parseExpressionStatement");
        TokenInit tok = p.curtok;
        astt.Expression exp = parseExpression(p, 0);
        ast.expressionStatement stmt = new expressionStatement(tok, exp);
        if (p.peaktok.tokType == Tokens.SEMICOLON) {
            p.nextToken();
        }

        return stmt;

    }

    public astt.Expression parseExpression(ParserInit p, int precedence) {
        System.out.println("parseExpression");
        prefixParse prefix = p.getPrefix(p.curtok.tokType);
        if (prefix == null) {
            p.errors.add("Invalid prefix " + p.curtok.tokType);
            return null;
        }

        astt.Expression leftExp = prefix.parsePrefix(p);
        
        while (p.peaktok.tokType != token.Tokens.SEMICOLON && precedence < p.pred(p.peaktok.tokType)) {
            infixParse infix = p.getInfix(p.peaktok.tokType);

            if(infix == null){
                return leftExp;
            }
            p.nextToken();
            leftExp = infix.parseInfix(p, leftExp);
        }
        return leftExp;
    } 

    public Boolean peakExpect(ParserInit p, String expect) {
        System.out.println("peakExpect");
        if(p.peaktok.tokType != expect) {
            p.errors.add("Expected token: " + expect + " Got: " + p.peaktok.tokType);
            p.nextToken();
            return false;
        }
        p.nextToken();
        return true;
    }

    public static void main(String[] args) {
        //String input = "设 a = 四十五; 如果 (a ==  四十五) {返回  四十五 * 五} 结尾;";
        String input = "设 a = 四十五; 四十五 + 四十五;  ";
        GenerateAST ast = new GenerateAST(input);
    }

}
