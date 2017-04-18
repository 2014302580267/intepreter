
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;



import java.util.List;
import java.util.Stack;

public class RefPhase extends CMMBaseListener{
	ParseTreeProperty<Scope> scopes;//�����
    GlobalScope globals;//ȫ����
    Scope currentScope;//��ǰ��
    Stack<Symbol.Type> stack = new Stack<Symbol.Type>();//����ջ
    ParseTreeProperty<Symbol.Type> types;//������
    int counter = 0;
    boolean error = false;//�Ƿ����
    
    //���캯��
    public RefPhase(GlobalScope globals, ParseTreeProperty<Scope> scopes, ParseTreeProperty<Symbol.Type> types) {
        this.globals = globals;
        this.scopes = scopes;
        this.types = types;
    }
    //����TYPE����е�����String
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
    //������Ŀ������ǰ����Ϊȫ��
    public void enterProgram(CMMParser.ProgramContext ctx) {
        currentScope = globals;
    }
    //��������ţ����������ҵ���ǰ������������
    public void enterStmtBlock(CMMParser.StmtBlockContext ctx) {
        currentScope = scopes.get(ctx);
    }
    //�������ţ�������һ����
    public void exitStmtBlock(CMMParser.StmtBlockContext ctx) {
        currentScope = currentScope.getParentScope();
    }
    //��whileѭ����ѭ��������һ�����������ҵ���ǰ������������
    public void enterWhileStmt(CMMParser.WhileStmtContext ctx) {
    	counter++;
    }
    //��whileѭ����ѭ��������һ��������һ����
    public void exitWhileStmt(CMMParser.WhileStmtContext ctx) {
    	counter--;
    }
    //��whileѭ����ѭ��������һ�����������ҵ���ǰ������������
    public void enterForStmt(CMMParser.ForStmtContext ctx) {
    	counter++;
    }
    //��whileѭ����ѭ��������һ��������һ����
    public void exitForStmt(CMMParser.ForStmtContext ctx) {
    	counter--;
    }
    //����ֵ��䣺
    public void exitAssignStmt(CMMParser.AssignStmtContext ctx) {
    	CMMParser.ExprContext expr = ctx.expr();//��ȡ�Ⱥ��ұߵı��ʽ
    	CMMParser.VariableContext var = ctx.variable();//��ȡ�Ⱥ���ߵı���
        Symbol symbol;
        //��ȡ������
        String name = var.getText();
        if (name.contains("[")) {
            name = name.substring(0, name.indexOf("["));
        }
        //ͨ���������ӵ�ǰ���ȡsymbol�������������л�ȡ���ʽ������
        symbol = currentScope.getSymbol(name);
        Symbol.Type type = types.get(expr);
        //��������������ת��Ϊsingle����
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
            if (temp != type) {//�Ⱥ��������Ͳ�ͬ
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
                	//����ԭ����
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
    //����С�Ƚϣ����ͱ�����CHAR��INR��DOUBLE
    public void exitCompRl(CMMParser.CompRlContext ctx) {
    	CMMParser.ExprContext exprL = ctx.expr(0);//��߱��ʽ
    	CMMParser.ExprContext exprR;//�ұ߱��ʽ
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
                    // ���ͷ���
                } else {
                    error = true;
                    CheckSymbol.error(exprL.start, "the type of the variable is illegal");
                }
            } else {
                // ���ʽΪ��
            }
        } else {
            if (exprL != null) {
                if (types.get(exprL) == Symbol.Type.BOOL || types.get(exprL) == Symbol.Type.INT || types.get(exprL) == Symbol.Type.DOUBLE) {
                    // ���ͷ���
                } else {
                    error = true;
                    CheckSymbol.error(ctx.expr(0).start, "variable must be bool type");
                }
            } else {
                //���ʽΪ��
            }
        }
    }
    
    //������䣺Ҫ�������ͺ�single�ͷֱ��ж�����
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
        				if (!typeL.equals(typeR)) {//�Ⱥ��������Ͳ�ͬ
        	                if ((typeL.equals("int") || typeL.equals("double")||typeL.equals("bool"))
        	                        && (typeR.equals("double") || typeR.equals("int"))) {
        	                	//����ԭ����
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
        		if (!typeL.equals(typeR)) {//�Ⱥ��������Ͳ�ͬ
	                if ((typeL.equals("int") || typeL.equals("double")||typeL.equals("bool"))
	                        && (typeR.equals("double") || typeR.equals("int"))) {
	                	//����ԭ����
	                } else {
	                    
	                    error = true;
	                    CheckSymbol.error(currentDeAs.variable().start, "can not match type " + typeL + " and " + typeR);
	                }
	            }
        	}else{
            	
            }
        }
        
    }
    //���������
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
                //δ֪����
                System.out.println(expr);
            }
        }
    }
    //��single����������Ƿ���ڴ˱���
    public void exitVarID(CMMParser.VarIDContext ctx) {
        String name = ctx.getText();
        Symbol var = currentScope.getSymbol(name);
        if (var == null) {
            CheckSymbol.error(ctx.getStart(), "no such variable:" + name);
            error = true;
        }
    }
    //��break�����õ�ǰ��ͼ�����
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
