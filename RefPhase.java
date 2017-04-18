
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;



import java.util.List;
import java.util.Stack;

public class RefPhase extends CMMBaseListener{
	ParseTreeProperty<Scope> scopes;//域的树
    GlobalScope globals;//全局域
    Scope currentScope;//当前域
    Stack<Symbol.Type> stack = new Stack<Symbol.Type>();//类型栈
    ParseTreeProperty<Symbol.Type> types;//类型树
    int counter = 0;
    boolean error = false;//是否出错
    
    //构造函数
    public RefPhase(GlobalScope globals, ParseTreeProperty<Scope> scopes, ParseTreeProperty<Symbol.Type> types) {
        this.globals = globals;
        this.scopes = scopes;
        this.types = types;
    }
    //返回TYPE穷举中的类型String
    public String checkType(Symbol.Type type) {
        String typeName;
        switch (type) {
            case BOOL:
                typeName = "bool";
                break;
            case CHAR:
                typeName = "char";
                break;
            case INT:
                typeName = "int";
                break;
            case DOUBLE:
                typeName = "double";
                break;
            case BOOL_LIST:
                typeName = "bool";
                break;
            case CHAR_LIST:
                typeName = "char";
                break;
            case INT_LIST:
                typeName = "int";
                break;
            case DOUBLE_LIST:
                typeName = "double";
                break;
            default:
                typeName = "unknown";
                break;
        }
        return typeName;
    }
    //进入项目，将当前域设为全局
    public void enterProgram(CMMParser.ProgramContext ctx) {
        currentScope = globals;
    }
    //进入大括号，在域树中找到当前代码所属的域
    public void enterStmtBlock(CMMParser.StmtBlockContext ctx) {
        currentScope = scopes.get(ctx);
    }
    //出大括号，返回上一层域
    public void exitStmtBlock(CMMParser.StmtBlockContext ctx) {
        currentScope = currentScope.getParentScope();
    }
    //进while循环，循环层数加一，在域树中找到当前代码所属的域
    public void enterWhileStmt(CMMParser.WhileStmtContext ctx) {
    	counter++;
    }
    //出while循环，循环层数减一，返回上一层域
    public void exitWhileStmt(CMMParser.WhileStmtContext ctx) {
    	counter--;
    }
    //进while循环，循环层数加一，在域树中找到当前代码所属的域
    public void enterForStmt(CMMParser.ForStmtContext ctx) {
    	counter++;
    }
    //出while循环，循环层数减一，返回上一层域
    public void exitForStmt(CMMParser.ForStmtContext ctx) {
    	counter--;
    }
    //出赋值语句：
    public void exitAssignStmt(CMMParser.AssignStmtContext ctx) {
    	CMMParser.ExprContext expr = ctx.expr();//获取等号右边的表达式
    	CMMParser.VariableContext var = ctx.variable();//获取等号左边的变量
        Symbol symbol;
        //获取变量名
        String name = var.getText();
        if (name.contains("[")) {
            name = name.substring(0, name.indexOf("["));
        }
        //通过变量名从当前域获取symbol，并从类型树中获取表达式的类型
        symbol = currentScope.getSymbol(name);
        Symbol.Type type = types.get(expr);
        //将其中数组类型转换为single类型
        if (symbol != null && type!= null) {
            Symbol.Type temp;
            switch (symbol.type) {
                case INT_LIST:
                    temp = Symbol.Type.INT;
                    break;
                case BOOL_LIST:
                    temp = Symbol.Type.BOOL;
                    break;
                case CHAR_LIST:
                    temp = Symbol.Type.CHAR;
                    break;
                case DOUBLE_LIST:
                    temp = Symbol.Type.DOUBLE;
                    break;
                default:
                    temp = symbol.type;
                    break;
            }
            
//            if (temp == type){
//            	CheckSymbol.error(expr.start, "can not match type " + " and " );
//            }
            if (temp != type) {//等号左右类型不同
                if ((temp == Symbol.Type.INT || temp == Symbol.Type.DOUBLE|| temp == Symbol.Type.BOOL)
                        && (type == Symbol.Type.DOUBLE || type ==Symbol.Type.INT)) {
//                	if(temp == Symbol.Type.INT&&type == Symbol.Type.DOUBLE){
//                		types.removeFrom(var);
//                		if(symbol.type==Symbol.Type.INT)
//                			types.put(var, Symbol.Type.DOUBLE);
//                		else if(symbol.type==Symbol.Type.INT)
//                			types.put(var, Symbol.Type.DOUBLE_LIST);
//                	}
//                	if(temp == Symbol.Type.BOOL){
//                		if()
//                	}
                	//保持原类型
                } else {
                    String typeL;
                    String typeR;
                    typeL = checkType(temp);
                    typeR = checkType(type);
                    error = true;
                    CheckSymbol.error(expr.start, "can not match type " + typeL + " and " + typeR);
                }
            }
        }
    }
    //出大小比较，类型必须是CHAR、INR、DOUBLE
    public void exitCompRl(CMMParser.CompRlContext ctx) {
    	CMMParser.ExprContext exprL = ctx.expr(0);//左边表达式
    	CMMParser.ExprContext exprR;//右边表达式
        if (ctx.expr().size() > 1) {
            exprR = ctx.expr(1);
            if (exprL != null && exprR != null) {
                Symbol.Type typeL = types.get(exprL);
                Symbol.Type typeR = types.get(exprR);
                switch (typeL) {
                    case INT_LIST:
                    	typeL = Symbol.Type.INT;
                        break;
                    case BOOL_LIST:
                    	typeL = Symbol.Type.BOOL;
                        break;
                    case CHAR_LIST:
                    	typeL = Symbol.Type.CHAR;
                        break;
                    case DOUBLE_LIST:
                    	typeL = Symbol.Type.DOUBLE;
                        break;
                    default:
                    	typeL = typeL;
                        break;
                }
                switch (typeR) {
                case INT_LIST:
                	typeR = Symbol.Type.INT;
                    break;
                case BOOL_LIST:
                	typeR = Symbol.Type.BOOL;
                    break;
                case CHAR_LIST:
                	typeR = Symbol.Type.CHAR;
                    break;
                case DOUBLE_LIST:
                	typeR = Symbol.Type.DOUBLE;
                    break;
                default:
                	typeR = typeR;
                    break;
            }
                if ((typeL == Symbol.Type.CHAR && typeR == Symbol.Type.CHAR) || ((typeL == Symbol.Type.INT || typeL == Symbol.Type.DOUBLE) && (typeR == Symbol.Type.INT || typeR == Symbol.Type.DOUBLE))) {
                    // 类型符合
                } else {
                    error = true;
                    CheckSymbol.error(exprL.start, "the type of the variable is illegal");
                }
            } else {
                // 表达式为空
            }
        } else {
            if (exprL != null) {
                if (types.get(exprL) == Symbol.Type.BOOL || types.get(exprL) == Symbol.Type.INT || types.get(exprL) == Symbol.Type.DOUBLE) {
                    // 类型符合
                } else {
                    error = true;
                    CheckSymbol.error(ctx.expr(0).start, "variable must be bool type");
                }
            } else {
                //表达式为空
            }
        }
    }
    
    //声明语句：要对数组型和single型分别判断类型
    public void exitDeclStmt(CMMParser.DeclStmtContext ctx) {
        String typeL=ctx.type().getText();
        String typeR;
        List varDeclList=ctx.deAs();
        for (int i=0; i<varDeclList.size(); i++){
        	//CMMParser.DeAsContext currentDeAs=((CMMParser.DeAsContext)varDeclList.get(i));
        	CMMParser.DeAsContext currentDeAs=ctx.deAs(i);
        	int exprSize=((CMMParser.DeAsContext)varDeclList.get(i)).expr().size();
        	if (currentDeAs.expr() != null && exprSize > 1){
        		String sNum=(String) currentDeAs.variable().getText().subSequence(currentDeAs.variable().getText().indexOf('[')+1, currentDeAs.variable().getText().indexOf(']'));
        		int num=0;
        		if(sNum!=""&&!sNum.equals(null)){
        			num=Integer.valueOf(sNum);
        		}
        		if(sNum==""||sNum.equals(null)||num==exprSize){
        			for(int j=0;j<exprSize;j++){
        				//CMMParser.ExprContext e=currentDeAs.expr().get(j);
        				Symbol.Type t=types.get(currentDeAs.expr(j));
        				typeR = checkType(t);
        				if (!typeL.equals(typeR)) {//等号左右类型不同
        	                if ((typeL.equals("int") || typeL.equals("double")||typeL.equals("bool"))
        	                        && (typeR.equals("double") || typeR.equals("int"))) {
        	                	//保持原类型
        	                } else {
        	                    
        	                    error = true;
        	                    CheckSymbol.error(currentDeAs.variable().start, "can not match type " + typeL + " and " + typeR);
        	                }
        	            }
        			}
        		}else{
        			CheckSymbol.error(ctx.start, "the declaim is illegal ");
                    error = true;
        		}
        	}else if(currentDeAs.expr() != null && exprSize == 1){
        		
        		typeR=checkType(types.get(currentDeAs.expr(0)));
        		if (!typeL.equals(typeR)) {//等号左右类型不同
	                if ((typeL.equals("int") || typeL.equals("double")||typeL.equals("bool"))
	                        && (typeR.equals("double") || typeR.equals("int"))) {
	                	//保持原类型
	                } else {
	                    
	                    error = true;
	                    CheckSymbol.error(currentDeAs.variable().start, "can not match type " + typeL + " and " + typeR);
	                }
	            }
        	}else{
            	
            }
        }
        
    }
    //出数组变量
    public void exitVarArray(CMMParser.VarArrayContext ctx) {
        String name = ctx.array().Identi().getText();
        Symbol var = currentScope.getSymbol(name);
        if (var == null) {
            CheckSymbol.error(ctx.getStart(), "no such variable:" + name);
            error = true;
        }
        CMMParser.ExprContext expr = ctx.array().expr();
        if (expr != null) {
            Symbol.Type type = types.get(expr);
            if (type != null) {
        		
                if (type == Symbol.Type.INT) {
//                	int num=Integer.valueOf(expr.getText());
//                	if(num<0){
//                		CheckSymbol.error(expr.start, "array index is illegal");
//                        error = true;
//                	} 
                }else{
                	CheckSymbol.error(expr.start, "array index is illegal");
                    error = true;
                }
            } else {
                //未知类型
                System.out.println(expr);
            }
        }
    }
    //出single变量，检查是否存在此变量
    public void exitVarID(CMMParser.VarIDContext ctx) {
        String name = ctx.getText();
        Symbol var = currentScope.getSymbol(name);
        if (var == null) {
            CheckSymbol.error(ctx.getStart(), "no such variable:" + name);
            error = true;
        }
    }
    //出break，设置当前域和计数器
    public void exitBreakStmt(CMMParser.BreakStmtContext ctx) {
        if (counter > 0) {
        	counter--;
        	currentScope=currentScope.getParentScope();
        } else {
            error = true;
            CheckSymbol.error(ctx.start, "can not break in statement other than while and for");
        }
    }
    
}
