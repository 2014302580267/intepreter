// Generated from F:/课件/大三上/CMMInterpreter/src\CMM.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CMMParser}.
 */
public interface CMMListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CMMParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CMMParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CMMParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(CMMParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(CMMParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#stmtBlock}.
	 * @param ctx the parse tree
	 */
	void enterStmtBlock(CMMParser.StmtBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#stmtBlock}.
	 * @param ctx the parse tree
	 */
	void exitStmtBlock(CMMParser.StmtBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void enterDeclStmt(CMMParser.DeclStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void exitDeclStmt(CMMParser.DeclStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#deAs}.
	 * @param ctx the parse tree
	 */
	void enterDeAs(CMMParser.DeAsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#deAs}.
	 * @param ctx the parse tree
	 */
	void exitDeAs(CMMParser.DeAsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CMMParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CMMParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(CMMParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(CMMParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(CMMParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(CMMParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(CMMParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(CMMParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#readStmt}.
	 * @param ctx the parse tree
	 */
	void enterReadStmt(CMMParser.ReadStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#readStmt}.
	 * @param ctx the parse tree
	 */
	void exitReadStmt(CMMParser.ReadStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#writeStmt}.
	 * @param ctx the parse tree
	 */
	void enterWriteStmt(CMMParser.WriteStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#writeStmt}.
	 * @param ctx the parse tree
	 */
	void exitWriteStmt(CMMParser.WriteStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(CMMParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(CMMParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(CMMParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(CMMParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varID}
	 * labeled alternative in {@link CMMParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVarID(CMMParser.VarIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varID}
	 * labeled alternative in {@link CMMParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVarID(CMMParser.VarIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varArray}
	 * labeled alternative in {@link CMMParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVarArray(CMMParser.VarArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varArray}
	 * labeled alternative in {@link CMMParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVarArray(CMMParser.VarArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterInt(CMMParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitInt(CMMParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code double}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterDouble(CMMParser.DoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code double}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitDouble(CMMParser.DoubleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constBool}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstBool(CMMParser.ConstBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constBool}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstBool(CMMParser.ConstBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code char}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterChar(CMMParser.CharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code char}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitChar(CMMParser.CharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompOr}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompOr(CMMParser.CompOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompOr}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompOr(CMMParser.CompOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompAnd}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompAnd(CMMParser.CompAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompAnd}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompAnd(CMMParser.CompAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompNot}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompNot(CMMParser.CompNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompNot}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompNot(CMMParser.CompNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CompRl}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompRl(CMMParser.CompRlContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CompRl}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompRl(CMMParser.CompRlContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParensComp}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterParensComp(CMMParser.ParensCompContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParensComp}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitParensComp(CMMParser.ParensCompContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mod}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMod(CMMParser.ModContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mod}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMod(CMMParser.ModContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Oppo}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOppo(CMMParser.OppoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Oppo}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOppo(CMMParser.OppoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpConstant}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpConstant(CMMParser.ExpConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpConstant}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpConstant(CMMParser.ExpConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(CMMParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(CMMParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(CMMParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(CMMParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParens(CMMParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParens(CMMParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpVariable}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpVariable(CMMParser.ExpVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpVariable}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpVariable(CMMParser.ExpVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(CMMParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(CMMParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(CMMParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(CMMParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link CMMParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(CMMParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CMMParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(CMMParser.StringContext ctx);
}