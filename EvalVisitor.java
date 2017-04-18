//import com.sun.corba.se.impl.activation.ServerMain;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
//import sun.java2d.cmm.kcms.CMM;
//import sun.util.resources.cldr.my.CalendarData_my_MM;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by wzq on 2017/1/1.
 */
public class EvalVisitor extends CMMBaseVisitor<Identifier> {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    GlobalScope global;
    Scope currentScope;
    boolean hasError = false;
    boolean isBreak = false;
    static WaitInput wi = new WaitInput();

    void saveScope(ParserRuleContext ctx, Scope s) {
        scopes.put(ctx, s);
    }

    ParseTreeProperty<String> values = new ParseTreeProperty<String>();

    public void setValue(ParseTree node, String value) {
        values.put(node, value);
    }

    public String getValue(ParseTree node) {
        return values.get(node);
    }

    @Override
    public Identifier visitProgram(CMMParser.ProgramContext ctx) {
        global = new GlobalScope(null);
        currentScope = global;
        return visitChildren(ctx);
    }

    //declAssignStmt: type deAs(',' deAs)*';';

    @Override
    public Identifier visitDeclStmt(CMMParser.DeclStmtContext ctx) {
        String type = ctx.getChild(0).getText();
        setValue(ctx, type);
        return visitChildren(ctx);
    }

    @Override
    //    deAs: variable '=' ((expr|'{' (expr (',' expr)*)? '}'))? ;
    public Identifier visitDeAs(CMMParser.DeAsContext ctx) {
        String type = getValue(ctx.getParent());
        Identifier var = new Identifier();
        if (ctx.getChildCount() == 1 && !hasError) {
            String id = visit(ctx.variable()).getId();
            if (visit(ctx.variable()).getVarType().equals("array")) {
                String arrayname = ctx.variable().getChild(0).getChild(0).getText();
                Integer size = Integer.valueOf(ctx.variable().getChild(0).getChild(2).getText());
                for (int i = 0; i < size; i++) {
                    try {
                        Identifier newVar = new Identifier();
                        if (type.equals("double")) {
                            Double dd = Double.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(dd));
                        } else if (type.equals("int")) {
                            Integer in = Integer.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(in));
                        } else if (type.equals("char")) {
                            String ss = String.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(ss));
                        } else if (type.equals("bool")) {
                            String ss = String.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(ss));
                        }
                        newVar.setType(type);
                        newVar.setVarType("id");
                        id = arrayname + '[' + i + ']';
                        newVar.setId(id);
                        int a=currentScope.define(newVar);
                        if(a==-1){
                            CheckSymbol.error(ctx.start, "Identifier can not be defined twice! " + ctx.getText());
                        }
                    } catch (Exception e) {
                        id = arrayname + '[' + i + ']';
                        int b=currentScope.define(new Identifier("id", type, id));
                        if(b==-1){
                            CheckSymbol.error(ctx.start, "Identifier can not be defined twice! " + ctx.getText());
                        }
                    }
                }
            } else {
                Identifier newVar = new Identifier();
                newVar.setType(type);
                newVar.setVarType("id");
                newVar.setId(id);
                int c=currentScope.define(newVar);
                if(c==-1){
                    CheckSymbol.error(ctx.start, "Identifier can not be defined twice! " + ctx.getText());
                }

            }
        } else if (!hasError) {
            String id = visit(ctx.variable()).getId();
            if (visit(ctx.variable()).getVarType().equals("array")) {
                String arrayname = ctx.variable().getChild(0).getChild(0).getText();
                Integer size = Integer.valueOf(ctx.variable().getChild(0).getChild(2).getText());
                for (int i = 0; i < size; i++) {
                    try {
                        Identifier newVar = new Identifier();
                        if (type.equals("double")) {
                            Double dd = Double.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(dd));
                        } else if (type.equals("int")) {
                            Integer in = Integer.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(in));
                        } else if (type.equals("char")) {
                            String ss = String.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(ss));
                        } else if (type.equals("bool")) {
                            String ss = String.valueOf(visit(ctx.expr(i)).getValue());
                            newVar.setValue(String.valueOf(ss));
                        }
                        newVar.setType(type);
                        newVar.setVarType("id");
                        id = arrayname + '[' + i + ']';
                        newVar.setId(id);
                        int a=currentScope.define(newVar);
                        if(a==-1){
                            CheckSymbol.error(ctx.start, "Identifier can not be defined twice! " + ctx.getText());
                        }
                    } catch (Exception e) {
                        id = arrayname + '[' + i + ']';
                        int a= currentScope.define(new Identifier("id", type, id));
                        if(a==-1){
                            CheckSymbol.error(ctx.start, "Identifier can not be defined twice! " + ctx.getText());
                        }
                    }
                }
            } else {
                Identifier newVar = new Identifier();
                newVar.setType(type);
                newVar.setVarType("id");
                newVar.setId(id);
                newVar.setValue(visit(ctx.expr(0)).getValue());
                int a=    currentScope.define(newVar);
                if(a==-1){
                    CheckSymbol.error(ctx.start, "Identifier can not be defined twice! " + ctx.getText());
                }

            }

        }
        return visitChildren(ctx);
    }

    @Override
//    forStmt : 'for' '(' ((assignStmt ';')|declStmt| expr ';') compare ';' assignStmt ')' stmtBlock;
   public  Identifier visitForStmt(CMMParser.ForStmtContext ctx){
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
        visit(ctx.getChild(1));
        if(ctx.declStmt()==null){
            if(ctx.expr()==null){
                visit(ctx.assignStmt(0));
                Identifier var = visit(ctx.compare());
                while (!isBreak && var.getValue().equals("true")){
                    visit(ctx.stmtBlock());
                    visit(ctx.assignStmt(1));
                    var = visit(ctx.compare());
                }
                isBreak = false;
                currentScope = currentScope.getParentScope();
                return new Identifier();
            }
            else{
                visit(ctx.expr());
                Identifier var = visit(ctx.compare());
                while (!isBreak && var.getValue().equals("true")){
                    visit(ctx.stmtBlock());
                    visit(ctx.assignStmt(0));
                    var = visit(ctx.compare());
                }
                isBreak = false;
                currentScope = currentScope.getParentScope();
                return new Identifier();
            }
        }else{
            visit(ctx.declStmt());
            Identifier var = visit(ctx.compare());
            while (!isBreak && var.getValue().equals("true")){
                visit(ctx.stmtBlock());
                visit(ctx.assignStmt(0));
                var = visit(ctx.compare());
            }
            isBreak = false;
            currentScope = currentScope.getParentScope();
            return new Identifier();
        }


    }

    public void logError(String msg) {
        UIdemo te = UIdemo.getInstance();
        RSyntaxTextArea ta = te.getOutputAre();
        ta.append("Error: "+msg+"\n");
        try {
            ta.addLineHighlight(ta.getLineCount() - 2, Color.RED);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
//        System.out.println(ta.getLineCount());
    }
    @Override
//    assignStmt: variable '=' expr;
    public Identifier visitAssignStmt(CMMParser.AssignStmtContext ctx){
        String id=visit(ctx.variable()).getId();
        Identifier var=(Identifier)currentScope.getSymbol(id);
        Identifier expr=visit(ctx.expr());
        if (!hasError&& !isBreak && currentScope.getSymbol(id) == null ||( var.getType()!=null &&var.getType().equals("arrayp"))){
            hasError = true;
            logError("Identifier " + id + " does not exit! " + ctx.getText());
            return null;
        }
        if (!hasError&& !isBreak){
            try {
                String type = expr.getType();
                if (var.getType() != null) {
                    if (var.getType().equals("double") && (type.equals("int") || type.equals("double"))) {
                        if (expr.getValue() != null) {
                            var.setValue(String.valueOf(Double.valueOf(expr.getValue())));
                        } else {
                            var.setValue(null);
                        }
                    } else if (var.getType().equals("int") && (type.equals("int") || type.equals("double"))) {
                        if (expr.getValue() != null) {
                            var.setValue(String.valueOf(Double.valueOf(expr.getValue()).intValue()));
                        } else {
                            var.setValue(null);
                        }
                    } else if (var.getType().equals("char") && type.equals("char")) {
                        if (expr.getValue() != null) {
                            var.setValue(expr.getValue());
                        } else {
                            var.setValue(null);
                        }
                    } else if (var.getType().equals("bool") && type.equals("bool")) {
                        if (expr.getValue() != null) {
                            var.setValue(expr.getValue());
                        } else {
                            var.setValue(null);
                        }
                    } else {
                        hasError = true;
                        CheckSymbol.error(ctx.start, "Can not assign type " + type + " to " + var.getType());
                        //                        a.setValue(String.valueOf(Double.valueOf(value.getValue())));
                    }
                }
            }catch(Exception e) {
                hasError = true;
                CheckSymbol.error(ctx.start, "unknown type!");
            }
            var.setId(id);
        }
        return var;
    }

//
//    Identifier:Identi  #varID
//|array         #varArray;
    @Override
    public Identifier visitVarID(CMMParser.VarIDContext ctx){
        Identifier newVar=new Identifier();
        newVar.setVarType("id");
        newVar.setId(ctx.getText());
        return newVar;
    }

    @Override
    public Identifier visitVarArray(CMMParser.VarArrayContext ctx){
        Identifier newVar=new Identifier();
        Identifier result=visit(ctx.array());
        if(!hasError){
            newVar.setId(result.getId());
            newVar.setVarType(result.getVarType());
        }
        return newVar;
    }

    @Override
    public Identifier visitArray(CMMParser.ArrayContext ctx){
        String varType = "array";
        Identifier var = new Identifier();
        try {
            if (!visit(ctx.expr()).getType().equals("int")) {
                hasError = true;
                CheckSymbol.error(ctx.start, "Array Index must be int! " + ctx.getText());
                return null;
            }
        } catch (Exception e) {

        }
        if (!hasError){
            var.setVarType(varType);
            String subId = ctx.getChild(0).getText();
            String id = "";
            Identifier arr = (Identifier) currentScope.getSymbol(subId);
            if (ctx.getChildCount()>3) {
                String value = visit(ctx.expr()).getValue();
                int va = Double.valueOf(value).intValue();
                if (arr != null && arr.getType().equals("arrayp")){
                    int length = Integer.parseInt(arr.getVarType());
                    if (va > length -1){
                        hasError = true;
//                        logError("Array Index out of boundry! " + ctx.getText());
                        CheckSymbol.error(ctx.start, "Array Index out of boundry! " + ctx.getText());
                        return null;
                    }
                }
                if (va < 0) {
                    hasError = true;
//                    logError("Array Index out of boundry! " + ctx.getText());
                    CheckSymbol.error(ctx.start, "Array Index out of boundry! " + ctx.getText());
                    return null;
                }
                value = String.valueOf(va);
                id = subId + "[" + value + "]";
            } else {
                id = subId;
            }
            var.setId(id);
        }
        return var;

    }
//
//    expr: expr ('*'|'/') expr  #MulDiv
//    |expr ('+'|'-') expr   #AddSub
//    |expr('%') expr         #Mod
//    |'-' expr               #Oppo
//    |variable              #ExpVariable
//    |constant             #ExpConstant
//    |'('expr')'          #Parens
//    ;

    @Override
    public Identifier visitParens(CMMParser.ParensContext ctx){
        return visit(ctx.expr());
    }

    @Override
    public Identifier visitExpConstant(CMMParser.ExpConstantContext ctx){
        return visit(ctx.constant());
    }


    @Override
    public Identifier visitExpVariable(CMMParser.ExpVariableContext ctx){
        String id=visit(ctx.variable()).getId();
        if(!hasError){
            Identifier var=(Identifier)currentScope.getSymbol(id);
            if(var.getType()!=null&& !var.getType().equals("array")&& currentScope.getSymbol(id)!=null){
                return (Identifier)currentScope.getSymbol(id);
            }else{
                hasError=true;
                CheckSymbol.error(ctx.start, "Identifier " + id + " does not exist! " + ctx.getParent().getText());
            }
        }
        return null;
    }

    @Override
    public Identifier visitOppo(CMMParser.OppoContext ctx){
        Identifier var=(Identifier) visit(ctx.expr());
        var.setValue(String.valueOf(Double.valueOf(var.getValue())*(-1)));
        return var;
    }

    @Override
    public Identifier visitAddSub(CMMParser.AddSubContext ctx){
        Identifier newVar=new Identifier();
        String add=new String();
        String sub=new String();
        if(!hasError){
            String type1=visit(ctx.expr(0)).getType();
            String type2=visit(ctx.expr(1)).getType();
            if((type1.equals("double")&&type2.equals("double"))
                    ||(type1.equals("int")&&type2.equals("double"))
                    ||(type1.equals("double")&&type2.equals("int"))){
                Double left=Double.valueOf(visit(ctx.expr(0)).getValue());
                Double right=Double.valueOf(visit(ctx.expr(1)).getValue());
                add=String.valueOf(left+right);
                sub=String.valueOf(left-right);
                newVar.setType("double");
            }else if(type1.equals("int")&&type2.equals("int")){
                int left=Integer.valueOf(visit(ctx.expr(0)).getValue());
                int right=Integer.valueOf(visit(ctx.expr(1)).getValue());
                add=String.valueOf(left+right);
                sub=String.valueOf(left-right);
                newVar.setType("int");
            }else if((type1!=null && type2.equals("char")) && (type2!=null && type1.equals("char"))){
                hasError = true;
                CheckSymbol.error(ctx.expr(0).start, "Can not add char! " +ctx.getParent().getText());
            }
            if(ctx.getText().contains("+")){
                newVar.setValue(add);
                newVar.setVarType("const");
                return newVar;
            }
            else{
                newVar.setValue(sub);
                newVar.setVarType("const");
                return newVar;
            }

        }
        return null;
    }
//    expr: expr ('*'|'/') expr  #MulDiv
    @Override
    public Identifier visitMulDiv(CMMParser.MulDivContext ctx){
        Identifier newVar=new Identifier();
        String mul=new String();
        String div=new String();
        String type1=visit(ctx.expr(0)).getType();
        String type2=visit(ctx.expr(1)).getType();
        if(!hasError){
            if((type1.equals("double")&&type2.equals("double"))
                    ||(type1.equals("int")&&type2.equals("double"))
                    ||(type1.equals("double")&&type2.equals("int"))){
                Double left=Double.valueOf(visit(ctx.expr(0)).getValue());
                Double right=Double.valueOf(visit(ctx.expr(1)).getValue());
                mul=String.valueOf(left*right);
                if ((right.equals(0.0) || (type2.equals("int") && right.intValue() == 0) )&&ctx.getText().contains("/")){
                    hasError = true;
                    CheckSymbol.error(ctx.start, "Can not divide by 0! " + ctx.getParent().getText());
                    div = null;
                }else{
                    div=String.valueOf(left/right);
                }
                newVar.setType("double");
            }else if(type1.equals("int")&&type2.equals("int")){
                int left=Integer.valueOf(visit(ctx.expr(0)).getValue());
                int right=Integer.valueOf(visit(ctx.expr(1)).getValue());
                mul=String.valueOf(left*right);
                if (right==0&&ctx.getText().contains("/")){
                    hasError = true;
                    CheckSymbol.error(ctx.start, "Can not divide by 0! " + ctx.getParent().getText());
                    div = null;
                }else{
                    div=String.valueOf(left/right);
                }
                if(ctx.getText().contains("*") || left%right==0){
                    newVar.setType("int");
                }else{
                newVar.setType("double");}
            }else{
                hasError = true;
                CheckSymbol.error(ctx.expr(0).start, "Can not divide " + type1 +"with"+type2);
            }
            if(ctx.getText().contains("*")){
                newVar.setValue(mul);
                newVar.setVarType("const");
                return newVar;
            }
            else{
                newVar.setValue(div);
                newVar.setVarType("const");
                return newVar;
            }

        }
        return null;
    }

    @Override
    public Identifier visitMod(CMMParser.ModContext ctx){
        Identifier newVar=new Identifier();
        String mod=new String();
        if(!hasError){
            String type1=visit(ctx.expr(0)).getType();
            String type2=visit(ctx.expr(1)).getType();
            if(type1.equals("int")&&type2.equals("int")){
                int left=Integer.valueOf(visit(ctx.expr(0)).getValue());
                int right=Integer.valueOf(visit(ctx.expr(1)).getValue());
                if (right==0){
                    hasError = true;
                    CheckSymbol.error(ctx.start, "Can not divide by 0! " + ctx.getParent().getText());
                    mod = null;
                }else{
                    mod=String.valueOf(left%right);
                }
                newVar.setType("int");
            }else{
                hasError = true;
                CheckSymbol.error(ctx.expr(0).start, "Can not mod " + type1 +"with"+type2);
            }
            newVar.setVarType("const");
            return newVar;
            }
        return null;
    }
//
//    constant:Int #int
//|double   #double
//|Bool  #bool
//|Char   #char
//    ;

    @Override
    public Identifier visitInt(CMMParser.IntContext ctx){
        Identifier newVar=new Identifier();
        String value=String.valueOf(ctx.Int().getText());
        newVar.setValue(value);
        newVar.setVarType("const");
        newVar.setType("int");
        return newVar;
    }

    @Override
    public Identifier visitDouble(CMMParser.DoubleContext ctx){
        Identifier newVar=new Identifier();
        String value=String.valueOf(ctx.Double().getText());
        newVar.setValue(value);
        newVar.setVarType("const");
        newVar.setType("double");
        return newVar;
    }

    @Override
    public Identifier visitConstBool(CMMParser.ConstBoolContext ctx){
        Identifier newVar=new Identifier();
        String value=String.valueOf(ctx.bool().getText());
        newVar.setValue(value);
        newVar.setVarType("const");
        newVar.setType("bool");
        return newVar;
    }
    @Override
    public Identifier visitChar(CMMParser.CharContext ctx){
        Identifier newVar=new Identifier();
        String value=String.valueOf(ctx.Char().getText());
        newVar.setValue(value);
        newVar.setVarType("const");
        newVar.setType("char");
        return newVar;
    }


//    compare: 'not' compare  # CompNot
//|compare '||' compare # CompOr
//| compare '&&' compare # CompAnd
//| expr (Relation expr)? # CompRl
//| '(' compare ')'    # ParensComp

    @Override
    public Identifier visitParensComp(CMMParser.ParensCompContext ctx){
        return visit(ctx.compare());
    }

    @Override
    public Identifier visitCompNot(CMMParser.CompNotContext ctx){
        Identifier var=visit(ctx.compare());
        if(!hasError && var.getValue().equals("true")){
            var.setValue("false");
        }else if(!hasError && var.getValue().equals("false")){
            var.setValue("true");
        }
        return var;
    }

    @Override
    public Identifier visitCompOr(CMMParser.CompOrContext ctx){
        Identifier compare1=visit(ctx.compare(0));
        Identifier compare2=visit(ctx.compare(1));
            if(compare1.getValue().equals("false")&&compare2.getValue().equals("false")){
                compare1.setValue("false");
            }else{
                compare1.setValue("true");
            }
            return compare1;
    }


    @Override
    public Identifier visitCompAnd(CMMParser.CompAndContext ctx){
        Identifier compare1=visit(ctx.compare(0));
        Identifier compare2=visit(ctx.compare(1));
            if(compare1.getValue().equals("true")&&compare2.getValue().equals("true")){
                compare1.setValue("true");
            }else{
                compare1.setValue("false");
            }
            return compare1;
        }

//     | expr (Relation expr)?   #CompRl
    public Identifier visitCompRl(CMMParser.CompRlContext ctx){
        Identifier newVar=new Identifier();
        newVar.setVarType("const");
        newVar.setType("bool");
        if(!hasError){
            if(ctx.expr().size()==1){
                Identifier var=visit(ctx.expr(0));
                String type=var.getType();
                String value=var.getValue();
                if((type.equals("double")&& Double.valueOf(value)!=0.0)
                        ||(type.equals("int")&& Integer.valueOf(value)!=0)
                        ||(type.equals("bool")&& !value.equals("false"))
                        ||type.equals("char")){
                    newVar.setValue("true");
                }else{
                    newVar.setValue("false");
                }
            }else{
                Identifier expr1=visit(ctx.expr(0));
                String type1=expr1.getType();
                Identifier expr2=visit(ctx.expr(1));
                String type2=expr2.getType();

                String type="";
                if((type1.equals("int")||type1.equals("double"))&&(type2.equals("int")||type2.equals("double"))){
                    type="double";
                }else if(type1.equals("char")&&type2.equals("char")){
                    type="char";
                }
                boolean result=false;
                Double Dleft=0.0;
                Double Dright=0.0;
                String Sleft="";
                String Rleft="";
                if(type.equals("double")) {
                     Dleft = Double.valueOf(expr1.getValue());
                     Dright = Double.valueOf(expr2.getValue());
                }
                if(type.equals("char")){
                     Sleft=String.valueOf(expr1.getValue());
                     Rleft=String.valueOf(expr2.getValue());
                }
                if(ctx.Relation().getText().equals(">")){
                    if(type.equals("double")){
                        result=Dleft>Dright;
                    }else if(type.equals("char")){
                        result=Sleft.charAt(0)>Rleft.charAt(0);
                    }
                    if(result) newVar.setValue("true");
                    else newVar.setValue("false");
                }else if(ctx.Relation().getText().equals("<")){
                    if(type.equals("double")){
                        result=Dleft<Dright;
                    }else if(type.equals("char")){
                        result=Sleft.charAt(0)<Rleft.charAt(0);
                    }
                    if(result) newVar.setValue("true");
                    else newVar.setValue("false");
                }else if(ctx.Relation().getText().equals("<=")){
                    if(type.equals("double")){
                        result=Dleft<=Dright;
                    }else if(type.equals("char")){
                        result=Sleft.charAt(0)<=Rleft.charAt(0);
                    }
                    if(result) newVar.setValue("true");
                    else newVar.setValue("false");
                }else if(ctx.Relation().getText().equals(">=")){
                    if(type.equals("double")){
                        result=Dleft>=Dright;
                    }else if(type.equals("char")){
                        result=Sleft.charAt(0)>=Rleft.charAt(0);
                    }
                    if(result) newVar.setValue("true");
                    else newVar.setValue("false");
                }else if(ctx.Relation().getText().equals("==")){
                    if(type.equals("double")){
                        result= (Dleft-Dright==0);
                    }else if(type.equals("char")){
                        result= (Sleft.charAt(0)==Rleft.charAt(0));
                    }
                    if(result) newVar.setValue("true");
                    else newVar.setValue("false");
                }else if(ctx.Relation().getText().equals("!=") ||ctx.Relation().getText().equals("<>")){
                    if(type.equals("double")){
                        result=!(Dleft-Dright==0);
                    }else if(type.equals("char")){
                        result= !(Sleft.charAt(0)==Rleft.charAt(0));
                    }
                    if(result) newVar.setValue("true");
                    else newVar.setValue("false");
                }else{

                }
            }
        }
        return newVar;
    }

//    ifStmt: 'if' '(' compare ')' stmtBlock ('elif' '(' compare ')' stmtBlock)* ('else' stmtBlock)?;
@Override
    public Identifier visitIfStmt(CMMParser.IfStmtContext ctx){
        currentScope=new LocalScope(currentScope);
        saveScope(ctx,currentScope);

        int num=ctx.compare().size();
        boolean flag=false;

        for(int i=0;i<num;i++){
            Identifier bool=visit(ctx.compare(i));
            if(bool.getValue().equals("true")){
                visit(ctx.stmtBlock(i));
                flag=true;
                break;
            }
        }
        if(!flag){
            if(ctx.stmtBlock().size()>num){
                visit(ctx.stmtBlock(num));
            }
        }
        currentScope=currentScope.getParentScope();
        return new Identifier();
    }

    @Override
    public Identifier visitWhileStmt(CMMParser.WhileStmtContext ctx) {
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
        Identifier var = visit(ctx.compare());
        while (!isBreak && var.getValue().equals("true")){
            visit(ctx.stmtBlock());
            var = visit(ctx.compare());
        }
        isBreak = false;
        currentScope = currentScope.getParentScope();
        return new Identifier();
    }

    @Override
    public Identifier visitStmtBlock(CMMParser.StmtBlockContext ctx) {
        currentScope =new LocalScope(currentScope);
        saveScope(ctx,currentScope);
//        int num=ctx.getChildCount();
//        for (int i=0;i<num;i++){
//            visitChildren(ctx.stmt(i));
//        }
        visitChildren(ctx);
        currentScope=currentScope.getParentScope();
        return new Identifier();
//        return visitChildren(ctx);
    }
    @Override
    public Identifier visitString(CMMParser.StringContext ctx) {
        ctx.getChildCount();
        Identifier var = new Identifier();
        String result = ctx.getChild(0).getText();
        for (int i = 1; i < ctx.getChildCount()-1; i++){
            if (ctx.getChild(i).getText().equals("\\")) {
                result += ctx.getChild(i).getText();
            } else {
                result += ctx.getChild(i).getText() + " ";
            }
        }
        var.setType("string");
        var.setVarType("const");
        var.setValue(result);
        return var;
    }

    @Override
    public Identifier visitBreakStmt(CMMParser.BreakStmtContext ctx) {
        isBreak = true;
        return visitChildren(ctx);
    }






    //    writeStmt:'write' '(' expr|String ')'';';
    @Override
    public Identifier visitWriteStmt(CMMParser.WriteStmtContext ctx) {
        Identifier var = visit(ctx.getChild(2));
        if (!hasError && !isBreak){
//        System.out.print(var.getVarType());
            if (ctx.string() != null){
                UIdemo te = UIdemo.getInstance();
                RSyntaxTextArea ta = te.getOutputAre();
//                String s = ctx.string().getText();
                String s = visit(ctx.string()).getValue();
                s = s.substring(1, s.length()-1);
                s = s.replace("\\n", "\n");
                s = s.replace("\\\"", "\"");
                ta.append(s + "\n");
            }else if(var.getVarType()!= null && var.getVarType().equals("const")) {
                String s = var.getValue();
                if (var.getType() != null && var.getType().equals("char")){
                    s = s.substring(1, s.length()-1);
                }
                UIdemo te = UIdemo.getInstance();
                RSyntaxTextArea ta = te.getOutputAre();
                ta.append(s + "\n");

            } else {
                UIdemo te = UIdemo.getInstance();
                RSyntaxTextArea ta = te.getOutputAre();
                if (currentScope.getSymbol(var.getId())==null){
                    hasError = true;
                    logError("Identifier " + var.getId() + " does not exit! " + ctx.getText());
                    return null;
                }
                String s = ((Identifier)currentScope.getSymbol(var.getId())).getValue();
                if (var.getType() != null && var.getType().equals("char")){
                    s = s.substring(1, s.length()-1);
                }
//                System.out.print(var.getId());
//                ta.append(var.getId());
//                System.out.print(": ");
//                ta.append(": ");
                //            System.out.println(memory.get(var.getId()).getValue());
//                System.out.println(s);
                ta.append(s + "\n");
            }
        }
        return visitChildren(ctx);
    }

    public Identifier visitReadStmt(CMMParser.ReadStmtContext ctx) {
//        UIdemo.main.suspend();
        String input=readStringFromDialog("Please enter:");
        Identifier v = visit(ctx.variable());
        Identifier var = (Identifier) currentScope.getSymbol(ctx.getChild(2).getText());
        if (!hasError && var == null){
            hasError = true;
            logError("Identifier " +ctx.getChild(2).getText() + " does not exit! " + ctx.getText());
        }
        if (!hasError&& !isBreak && var.getType() != null) {
            if (var.getType().equals("double")) {
                if (!isNum(input)) {
                    hasError = true;
                    CheckSymbol.error(ctx.start, "Input " + input + " must be double type! " + ctx.getText());
                }else {
                    var.setValue(String.valueOf(Double.valueOf(input)));
                }
            } else if(var.getType().equals("int")) {
                if (!isInt(input)) {
                    hasError = true;
                    CheckSymbol.error(ctx.start, "Input " + input + " must be int type! " + ctx.getText());
                }else {
                    var.setValue(String.valueOf(Double.valueOf(input).intValue()));
                }
            } else if(var.getType().equals("char")) {
                if (input.length() > 1) {
                    hasError = true;
                    CheckSymbol.error(ctx.start, "Input " + input + " must be char type! " + ctx.getText());
                }else {
                    var.setValue( "\'" + input + "\'");
                }
            } else if(var.getType().equals("bool")) {
                if (!input.equals("true") && !input.equals("false")) {
                    hasError = true;
                    CheckSymbol.error(ctx.start, "Input " + input + " must be bool type! " + ctx.getText());
                }else {
                    var.setValue(input);
                }
            } else {
                var.setValue(String.valueOf(Double.valueOf(input)));
            }
        }

        return visitChildren(ctx);
    }
    // is double
    public static boolean isNum(String str){
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
    // is int
    public static boolean isInt(String str){
        return str.matches("^[-+]?(([0-9]+)([.]([0]+))?|([.]([0]+))?)$");
    }

    private static String readStringFromDialog(String prompt){
        return JOptionPane.showInputDialog(prompt);
}








}
