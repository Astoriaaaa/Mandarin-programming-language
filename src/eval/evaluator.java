package eval;

import ast.astt;
import object.objBool;
import object.objInt;
import object.objNull;
import object.objString;
import parser.GenerateAST;
import java.util.ArrayList;

public class evaluator {
    
    object.objInterface value;
    ArrayList <String> errors = new ArrayList<>();
    public evaluator(String program) {
        parser.GenerateAST ast = new GenerateAST(program);
        ast.Program parsedProgram = ast.getParsedProgram();
        this.value = evalProgram(parsedProgram);
    }

    public String getValue() {
        return value.Inspect();
    }

    public object.objInterface evalProgram(ast.Program node) {
        for(int i = 0; i < node.getStatements().size(); i++) {
            object.objInterface stmt = evalStatements(node.getStatements().get(i));
            
            if(stmt != null) {
                return stmt;
            }
        }
        return null;
    }

    public object.objInterface evalStatements(astt.Statement node) {
        if (node instanceof ast.letStatement) {
            return null;
        } else if (node instanceof ast.returnStatement) {
            return null;
        } else if (node instanceof ast.expressionStatement){
            ast.expressionStatement exprStmt = (ast.expressionStatement) node;
            return evalExpression(exprStmt.getExpression());
        }
        return null;

    }

    public object.objInterface evalExpression(astt.Expression node) {
        if(node instanceof ast.integerLiteral) {
            ast.integerLiteral exprStmt = (ast.integerLiteral) node;
            int value = exprStmt.getValue();
            object.objInt obj = new objInt(value);
            return obj;
        } else if (node instanceof ast.boolLiteral) {
            ast.boolLiteral exprStmt = (ast.boolLiteral) node;
            String value = exprStmt.getValue();
            object.objBool obj = new objBool(value);
            return obj;
        } else if (node instanceof ast.stringLiteral) {
            ast.stringLiteral exprStmt = (ast.stringLiteral) node;
            String value = exprStmt.getValue();
            object.objString obj = new objString(value);
            return obj;
        } else if (node instanceof ast.nullExpression) {
            ast.nullExpression exprStmt = (ast.nullExpression) node;
            String value = exprStmt.getValue();
            object.objNull obj = new objNull(value);
            return obj;
        } else if (node instanceof ast.prefixExpression) {
            ast.prefixExpression exprStmt = (ast.prefixExpression) node;
            object.objInterface exp = evalExpression(exprStmt.getRightExp());
            String val = "";
            if(exp != null) {
                if(exprStmt.getOperator() == "!") {
                    if(exp instanceof object.objBool) {
                        val = Boolean.toString(!Boolean.parseBoolean(exp.Inspect()));
                    } else if(exp instanceof object.objInt) {
                        val = "false";
                        if(exp.Inspect().equals("0")) {
                            val = "true";
                        }
                    } else {
                        this.errors.add("Right Expression not Bool or Int");
                    }
                }
                else {
                    val = exp.Inspect();
                    int num = -Integer.parseInt(val);
                    val = Integer.toString(num);
                }
                object.objBool obj = new objBool(val);
                return obj;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        evaluator eval = new evaluator("!null");
        System.out.println(eval.getValue());
    }

}
