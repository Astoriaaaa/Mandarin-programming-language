package eval;

import ast.*;
import java.util.ArrayList;
import java.util.HashMap;
import object.enviroment;
import object.objArr;
import object.objBool;
import object.objFn;
import object.objInt;
import object.objInterface;
import object.objNull;
import object.objString;
import parser.GenerateAST;

public class evaluator {
    
    object.objInterface value;
    ArrayList <String> errors = new ArrayList<>();
    ArrayList <String> parseErrors = new ArrayList<>();
    public evaluator(String program) {
        parser.GenerateAST ast = new GenerateAST(program);
        ast.Program parsedProgram = ast.getParsedProgram();
        this.parseErrors = ast.getErrors();
        if(parseErrors.isEmpty()) {
            this.value = evalProgram(parsedProgram);
            // if (output instanceof objInt) {
            //     String num = output.Inspect();
            //     num = convertToMand(num);
            //     this.value = new objString(num);
            // } else if (output instanceof objBool) {
            //     this.value = new objString(output.Inspect());
            // }

        }
        
        
    }

    public ArrayList<String> getErrors() {
        return this.errors;
    }

    public String getValue() {
        if (value == null) {
            return "";
        }
        return value.Inspect();
    }

    public objInterface evalProgram(ast.Program node) {
        object.enviroment env = new enviroment(null, new HashMap<>());
        
        for(int i = 0; i < node.getStatements().size(); i++) {
            
            object.objInterface stmt = evalStatements(node.getStatements().get(i), env);
            
            if(stmt != null) {
                return stmt;
            }
        }
        System.out.println("returned null eval program/n");
        return null;
        
    }

    public objInterface evalBlockStatments(ast.blockStatements node, enviroment env) {
        for(int i = 0; i < node.getStatements().getStatements().size(); i++) {
            object.objInterface stmt = evalStatements(node.getStatements().getStatements().get(i), env);
            
            if(stmt != null) {
                return stmt;
            }
        }
        return null;
    }

    public objInterface evalStatements(astt.Statement node, enviroment env) {
        if (node instanceof ast.letStatement) {
            letStatement stmt = (letStatement) node;
            objInterface obj = evalExpression(stmt.getExp().getExpression(), env);
            env.set(stmt.getIdent().getName(), obj);
        } else if (node instanceof ast.returnStatement) {
            returnStatement stmt = (returnStatement) node;
            return evalExpression(stmt.getExpression().getExpression(), env);
        } else if (node instanceof ast.expressionStatement) {
            ast.expressionStatement exprStmt = (ast.expressionStatement) node;
            return evalExpression(exprStmt.getExpression(), env);
        }
        return null;

    }

    public objInterface evalExpression(astt.Expression node, enviroment env) {
        if (node instanceof ast.integerLiteral) {
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
            object.objInterface exp = evalExpression(exprStmt.getRightExp(), env);
            return evalPrefix(exp, exprStmt.getOperator());
        } else if (node instanceof infixExpression) {
            ast.infixExpression exprStmt = (ast.infixExpression) node;
            object.objInterface rightExp = evalExpression(exprStmt.getRightExp(), env);
            object.objInterface leftExp = evalExpression(exprStmt.getLeftExp(), env);
            String op = exprStmt.getOperator();
            return evalInfix(rightExp, leftExp, op);
        } else if (node instanceof ifExpression) {
            ast.ifExpression exprStmt = (ast.ifExpression) node;
            return evalIfExpression(exprStmt, env);
        } else if (node instanceof Identifier) {
            return evalIdentifier(node, env);
        } else if (node instanceof fnExpression) {
            fnExpression exprStmt = (fnExpression) node;
            return new objFn(exprStmt.getParams(), exprStmt.getBody(), env);
        } else if (node instanceof callExpression) {
            callExpression exprStmt = (callExpression) node;
            object.objInterface obj = evalExpression(exprStmt.getFunction(), env);
            if (obj == null || obj.Type() != "FUNCTION_OBJ") {
                return null;
            }

            ArrayList<objInterface> objs = new ArrayList<>();
            for (int i = 0; i < exprStmt.getParams().size(); i++) {
                objInterface exp = evalExpression(exprStmt.getParams().get(i), env);
                if (exp == null) {
                    return null;
                }
                objs.add(exp);
            }

            return applyFunction(obj, objs, env);
        } else if (node instanceof arrayExpression) {
            arrayExpression expr = (arrayExpression) node;
            ArrayList<objInterface> objs = new ArrayList<>();
            for (int i = 0; i < expr.getLen(); i++) {
                objInterface obj = evalExpression(expr.getIndex(i), env);
                objs.add(obj);
            }
            return new objArr(objs);
        } else if (node instanceof arrayCallExpression) {
            arrayCallExpression expr = (arrayCallExpression) node;
            objInterface obj = evalIdentifier(expr.getIdent(), env);
            objInterface index = evalExpression(expr.getIndex(), env);
            if (index.Type() != "INT") {
                errors.add("Expected: INT Object, Got: " + index.Type());
                return null;
            }
            int val = Integer.parseInt(index.Inspect());
            if (obj.Type() != "ARRAY") {
                errors.add("Expected: Array Object, Got: " + obj.Type());
                return null;
            }

            objArr arr = (objArr) obj;
            objInterface value = arr.getIndex(val);
            if(value == null) {
                errors.add("Array out of bounds");
                return null;
            }
            return value;
        }
        return null;
    }

    public objInterface applyFunction(objInterface function, ArrayList<objInterface> params, enviroment env) {
        enviroment new_env = new enviroment(env, new HashMap<String, objInterface>());
        objFn fn = (objFn) function;
        if(params.size() != fn.getParams().size()) {
            errors.add("Invalid number of parameters");
            return null;
        }

        for (int i = 0; i < fn.getParams().size(); i++) {
            new_env.set(fn.getParams().get(i).getName(), params.get(i));
        }

        return evalBlockStatments(fn.getBody(), new_env);

    }

    public objInterface evalIdentifier(astt.Expression node, enviroment env) {
        Identifier exprStmt = (Identifier) node;
            objInterface obj = env.get(exprStmt);
            if (obj == null && env.getOuter() == null) {
                this.errors.add(String.format("%s is not defined", exprStmt.getName()));
                return null;
            } else if (obj == null) {
                return evalIdentifier(node, env.getOuter());
            }
            return obj;
    }

    public objInterface evalIfExpression(ast.ifExpression node, enviroment env) {
        if (node == null) {
            return null;
        }
        else if(node.getCondition() == null) {
            enviroment env2 = new enviroment(env, new HashMap<>());
            return evalBlockStatments(node.getBlockStatements(), env2);
        }
        objInterface condition = evalExpression(node.getCondition(), env);
        if (condition instanceof objInt ) {
            if(!condition.Inspect().equals("0")) {
                enviroment env2 = new enviroment(env, new HashMap<>());
                return evalBlockStatments(node.getBlockStatements(), env2);
            }
            return evalIfExpression(node.getNextIfExpression(), env);
        } else if (condition instanceof objBool) {
            if(!condition.Inspect().equals("false")) {
                enviroment env2 = new enviroment(env, new HashMap<>());
                return evalBlockStatments(node.getBlockStatements(), env2);
            }
            return evalIfExpression(node.getNextIfExpression(), env);
        } 
        this.errors.add("Condition must be BOOLEAN, GOT: " + condition.Type());

        System.out.println("returned null if exp/n");
        return null;

        
    }

    public objInterface evalPrefix(objInterface exp, String op) {
        String val = "";
        if(exp != null) {
            if(op == "!") {
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
        System.out.println("returned null prefix exp/n");
        return null;
    }

    public objInterface evalInfix(objInterface rightExp, objInterface leftExp, String op) {
        if (rightExp instanceof object.objInt && leftExp instanceof object.objInt) {
            object.objInterface val = evalIntegerInfix(rightExp, leftExp, op);
            if(val != null) {
                return val;
            }
        } else if (rightExp instanceof object.objString && leftExp instanceof object.objString) {
            object.objInterface val = evalStringInfix(rightExp, leftExp, op);
            if(val != null) {
                return val;
            }
        } else if (op == "==") {
            Boolean val = rightExp.Inspect() == leftExp.Inspect();
            return new objBool(Boolean.toString(val));
        } else if (op == "!=") {
            Boolean val = rightExp.Inspect() != leftExp.Inspect();
            return new objBool(Boolean.toString(val));
        } else if (!(rightExp instanceof object.objNull && leftExp instanceof object.objNull)) {
            this.errors.add("Type mismatch: " + rightExp.Type() + " and " + leftExp.Type());
        } else {
            this.errors.add("Unknown Operation: " + rightExp.Type() + " " + op + " " + leftExp.Type());
        }
        System.out.println("returned null infix exp/n");
        return null;
    }

    public objInterface evalIntegerInfix(objInterface right, objInterface left, String op) {
        int rightt = Integer.parseInt(right.Inspect());
        int leftt = Integer.parseInt(left.Inspect());

        if (op == "+") {
            return new objInt((rightt + leftt));
        } else if (op == "-") {
            return new objInt((leftt - rightt));
        } else if (op == "*") {
            return new objInt((leftt * rightt));
        } else if (op == "/") {
            return new objInt((leftt / rightt));
        } else if (op == "==") {
            return new objBool(Boolean.toString(leftt == rightt));
        } else if (op == ">") {
            return new objBool(Boolean.toString(leftt > rightt));
        } else if (op == "<") {
            return new objBool(Boolean.toString(leftt < rightt));
        } else if (op == "!=") {
            return new objBool(Boolean.toString(leftt != rightt));
        }
        System.out.println("returned null evalintinfix/n");
        return null;
    }

    public objString evalStringInfix(objInterface right, objInterface left, String op) {
        if (op == "+") {
            return new objString(left.Inspect() + right.Inspect());
        }
        this.errors.add("Not valid operation: STRING " + op + "STRING ");
        System.out.println("returned null string exp/n");
        return null;
    }

    public static void main(String[] args) {
        evaluator eval = new evaluator("设 add = 功能(num) {如果 (num == 一) {返回 一} 结尾 返回 num + add(num - 一)}; add(五)");
        //evaluator eval = new evaluator("设 func = 功能(a, b) {返回 a + b}; func(四, 五) + func(四, 五);");
        //evaluator eval = new evaluator("真的");
        System.out.println(eval.parseErrors.toString());
        System.out.println(eval.errors.toString());
        System.out.println(eval.getValue());
    }

}
