

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

//import CMMInterpreterParser.ExprContext;












import java.util.List;

/**
 * Created by cxq on 16/1/2.
 */
public class DefPhase extends CMMBaseListener {

    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();//域parser tree——Map<ParseTree,Scope>
    ParseTreeProperty<Symbol.Type> types = new ParseTreeProperty<>();//类型parser tree——Map<ParseTree,Symbol.Type>
    GlobalScope globals;//全局域
    Scope currentScope;//当前域

    //将域s存入域树
    void saveScope(ParserRuleContext ctx, Scope s) {
        scopes.put(ctx, s);
    }
    //将类型type存入类型树
    public void saveType(ParserRuleContext ctx, Symbol.Type type) {
        types.put(ctx, type);
    }
    
    //进入程序：新建全局域、更新当前域为全局
    public void enterProgram(CMMParser.ProgramContext ctx) {
        globals = new GlobalScope(null);
        currentScope = globals;
    }
    public void exitProgram(CMMParser.ProgramContext ctx) {

    }
    
    
   //定义语句：区分变量类型并在当前域声明这个变量
    public void exitDeclStmt(CMMParser.DeclStmtContext ctx) {
    	Symbol.Type type;
    	List varList = ctx.deAs();
    	IdentifierSymbol var;
    	for (int i=0; i<varList.size(); i++){
    		//array型
    		if (((CMMParser.DeAsContext)varList.get(i)).variable().getText().contains("[")){
    			 switch (ctx.start.getText()){
                 case "char":
                     type =  Symbol.Type.CHAR_LIST;
                     break;
                 case "bool":
                     type =  Symbol.Type.BOOL_LIST;
                     break;
                 case "double":
                     type =  Symbol.Type.DOUBLE_LIST;
                     break;
                 case "int":
                     type =  Symbol.Type.INT_LIST;
                     break;
                 default:
                     type =  Symbol.Type.INVALID;
                     break;
    			 }
    			 var = new IdentifierSymbol(((CMMParser.DeAsContext)varList.get(i)).variable().getText(), type);
                 var.name = var.name.substring(0, var.name.indexOf("["));
    		}
    		//single型
    		else {
                switch (ctx.start.getText()) {
                case "char":
                    type =  Symbol.Type.CHAR;
                    break;
                case "bool":
                    type =  Symbol.Type.BOOL;
                    break;
                case "double":
                    type =  Symbol.Type.DOUBLE;
                    break;
                case "int":
                    type =  Symbol.Type.INT;
                    break;
                default:
                    type =  Symbol.Type.INVALID;
                    break;
                }
            var = new IdentifierSymbol(((CMMParser.DeAsContext)varList.get(i)).variable().getText(), type);
    		}
    		////////////////////////////////////////////////////////////////////////////
    		currentScope.define(var);//在本层声明此变量'
//    		if(e==0){
//    			CheckSymbol.error(((CMMParser.DeAsContext)varList.get(i)).variable().start, "Duplicate variable " + var.getName());
//    		}
    	}
    }
    public void exitAssignStmt(CMMParser.AssignStmtContext ctx){
    	
    }
    
    //进语句块：当前域为新建的local，更新域树
    public void enterStmtBlock(CMMParser.StmtBlockContext ctx) {
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
    }
    //出语句块：当前域设为parent域
    public void exitStmtBlock(CMMParser.StmtBlockContext ctx) {
        currentScope = currentScope.getParentScope();
    }
    //进while语句：当前域为新建的local，更新域树
    public void enterWhileStmt(CMMParser.WhileStmtContext ctx) {
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
    }
    //出while语句：当前域设为parent域
    public void exitWhileStmt(CMMParser.WhileStmtContext ctx) {
        currentScope = currentScope.getParentScope();
    }
    public void enterForStmt(CMMParser.WhileStmtContext ctx) {
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
    }
    //出while语句：当前域设为parent域
    public void exitForStmt(CMMParser.WhileStmtContext ctx) {
        currentScope = currentScope.getParentScope();
    }
    public void enterIfStmt(CMMParser.WhileStmtContext ctx) {
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
    }
    //出if语句：当前域设为parent域
    public void exitIfStmt(CMMParser.WhileStmtContext ctx) {
        currentScope = currentScope.getParentScope();
    }
    
    //出变量表达式：如果是a[2]类型的，将数组类型改为single类型，并更新type映射
    public void exitExpVariable(CMMParser.ExpVariableContext ctx){
    	String name = ctx.variable().getText();
        if (name.contains("[")) {
            name = name.substring(0, name.indexOf("["));
        }
        Symbol.Type type;
        Symbol var = currentScope.getSymbol(name);
        if (var != null) {
            switch (var.type) {
                case BOOL_LIST:
                    type = Symbol.Type.BOOL;
                    break;
                case CHAR_LIST:
                    type = Symbol.Type.CHAR;
                    break;
                case DOUBLE_LIST:
                    type = Symbol.Type.DOUBLE;
                    break;
                case INT_LIST:
                    type = Symbol.Type.INT;
                    break;
                case INVALID:
                    type = Symbol.Type.INVALID;
                    break;
                default:
                    type = var.type;
                    break;
            }
            saveType(ctx, type);
        }
    } 
    
    //出加减表达式：改变type
    public void exitAddSub(CMMParser.AddSubContext ctx) {
        if (ctx.expr(0) != null && ctx.expr(1) != null) {
            if (types.get(ctx.expr(0)) != null && types.get(ctx.expr(1)) != null) {
                if (types.get(ctx.expr(0)) != types.get(ctx.expr(1))) {
                    Symbol.Type lType = types.get(ctx.expr(0));
                    Symbol.Type rType = types.get(ctx.expr(1));
                    if ((lType == Symbol.Type.INT || lType == Symbol.Type.DOUBLE) &&
                            (rType == Symbol.Type.INT || rType == Symbol.Type.DOUBLE) ) {
                        if (lType == Symbol.Type.DOUBLE || rType == Symbol.Type.DOUBLE) {
                            saveType(ctx, Symbol.Type.DOUBLE);
                        }else {
                            saveType(ctx, Symbol.Type.INT);
                        }
                    } else {
                        saveType(ctx, Symbol.Type.INVALID);
                    }
                } else {
                    saveType(ctx, types.get(ctx.expr(0)));
                }
            } else {
                saveType(ctx, Symbol.Type.INVALID);
            }
        } else {
            saveType(ctx, Symbol.Type.INVALID);
        }
    }
    //出乘除表达式：改变type
    public void exitMulDiv(CMMParser.MulDivContext ctx) {
    	if (ctx.expr(0) != null && ctx.expr(1) != null) {
            if (types.get(ctx.expr(0)) != null && types.get(ctx.expr(1)) != null) {
                if (types.get(ctx.expr(0)) != types.get(ctx.expr(1))) {
                    Symbol.Type lType = types.get(ctx.expr(0));
                    Symbol.Type rType = types.get(ctx.expr(1));
                    if ((lType == Symbol.Type.INT || lType == Symbol.Type.DOUBLE) &&
                            rType == Symbol.Type.INT || rType == Symbol.Type.DOUBLE ) {
                        if (lType == Symbol.Type.DOUBLE || rType == Symbol.Type.DOUBLE) {
                            saveType(ctx, Symbol.Type.DOUBLE);
                        } else {
                            saveType(ctx, Symbol.Type.INT);
                        }
                    } else {
                        saveType(ctx, Symbol.Type.INVALID);
                    }
                } else {
                    saveType(ctx, types.get(ctx.expr(0)));
                }
            } else {
                saveType(ctx, Symbol.Type.INVALID);
            }
        } else {
            saveType(ctx, Symbol.Type.INVALID);
        }
    }
    //出模表达式：前后表达式必须为int型
    public void exitMod(CMMParser.ModContext ctx){
    	if (ctx.expr(0) != null && ctx.expr(1) != null) {
            if (types.get(ctx.expr(0)) != null && types.get(ctx.expr(1)) != null) {
                if (types.get(ctx.expr(0))==Symbol.Type.INT&&types.get(ctx.expr(1))==Symbol.Type.INT) {
                	saveType(ctx, Symbol.Type.INT);
                }else{
                	saveType(ctx, Symbol.Type.INVALID);
                }
            }else{
            	saveType(ctx, Symbol.Type.INVALID);
            }
    	}else{
    		saveType(ctx, Symbol.Type.INVALID);
    	}
    }
    //出负值表达式：类型与除去负号的表达式相同
    public void exitOppo(CMMParser.OppoContext ctx) {
    	if (types.get(ctx.expr()) != null&&(types.get(ctx.expr()) == Symbol.Type.INT||types.get(ctx.expr()) == Symbol.Type.DOUBLE)){
    		saveType(ctx, types.get(ctx.expr()));
    	}else{
    		saveType(ctx, Symbol.Type.INVALID);
    	}
    }
    //出括号：保留原类型
    public void exitParens(CMMParser.ParensContext ctx) {
        if (ctx.expr()!=null) {
            if (types.get(ctx.expr()) != null) {
                saveType(ctx, types.get(ctx.expr()));
            } else {
                saveType(ctx, Symbol.Type.INVALID);
            }
        } else {
            saveType(ctx, Symbol.Type.INVALID);
        }
    }
    public void exitConstBool(CMMParser.ConstBoolContext ctx){
    	if (ctx.getText().equals("true") || ctx.getText().equals("false")) {
            saveType(ctx, Symbol.Type.BOOL);
        } else {
            saveType(ctx, Symbol.Type.INVALID);
        }
    }
    public void exitCompOr(CMMParser.CompOrContext ctx){
    	saveType(ctx, Symbol.Type.BOOL);
    }
    public void exitCompAnd(CMMParser.CompAndContext ctx){
    	saveType(ctx, Symbol.Type.BOOL);
    }
    public void exitCompNot(CMMParser.CompNotContext ctx){
    	saveType(ctx, Symbol.Type.BOOL);
    }
    public void exitCompRl(CMMParser.CompRlContext ctx){
    	saveType(ctx, Symbol.Type.BOOL);
    }
    public void exitParensComp(CMMParser.ParensCompContext ctx){
    	saveType(ctx, Symbol.Type.BOOL);
    }
    
    //出常值：直接保留类型
    public void exitConstantExpr(CMMParser.ConstantContext ctx){
    	if(ctx.Int()!=null){
    		saveType(ctx, Symbol.Type.INT);
    	}
    	if(ctx.Double()!=null){
    		saveType(ctx, Symbol.Type.DOUBLE);
    	}
    	if(ctx.bool()!=null){
    		saveType(ctx, Symbol.Type.BOOL);
    	}
    	if(ctx.Char()!=null){
    		saveType(ctx, Symbol.Type.CHAR);
    	}
    }
    public void exitExpConstant(CMMParser.ExpConstantContext ctx){
    	if(ctx.constant().Int()!=null){
    		saveType(ctx, Symbol.Type.INT);
    	}
    	if(ctx.constant().Double()!=null){
    		saveType(ctx, Symbol.Type.DOUBLE);
    	}
    	if(ctx.constant().bool()!=null){
    		saveType(ctx, Symbol.Type.BOOL);
    	}
    	if(ctx.constant().Char()!=null){
    		saveType(ctx, Symbol.Type.CHAR);
    	}
    }
    public void exitInt(CMMParser.IntContext ctx){
    	saveType(ctx, Symbol.Type.INVALID);
    }
    public void exitDouble(CMMParser.DoubleContext ctx){
    	types.put(ctx, Symbol.Type.DOUBLE);
    }
    public void exitChar(CMMParser.ConstantContext ctx){
    	types.put(ctx, Symbol.Type.CHAR);
    }
    public void exitBool(CMMParser.ConstantContext ctx){
    	if (ctx.getText().equals("true") || ctx.getText().equals("false")) {
            saveType(ctx, Symbol.Type.BOOL);
        } else {
            saveType(ctx, Symbol.Type.INVALID);
        }
    }
//    
//    //出否定语句：只有BOOL型可以，其余全部是INVALID
//    public void exitNotExpr(CMMParser.NotExprContext ctx){
//    	if (ctx.expr()!=null) {
//            if (types.get(ctx.expr()) == Symbol.Type.BOOL){
//            	saveType(ctx, Symbol.Type.BOOL);
//            }else{
//            	saveType(ctx, Symbol.Type.INVALID);
//            }
//    	}else{
//    		saveType(ctx, Symbol.Type.INVALID);
//        }
//    }
//   
}
