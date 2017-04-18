// Generated from F:/课件/大三上/CMMInterpreter/src\CMM.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CMMParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CMMVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CMMParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CMMParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(CMMParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#stmtBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtBlock(CMMParser.StmtBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#declStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclStmt(CMMParser.DeclStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#deAs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeAs(CMMParser.DeAsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(CMMParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(CMMParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(CMMParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#breakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(CMMParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#readStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadStmt(CMMParser.ReadStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#writeStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStmt(CMMParser.WriteStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#assignStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(CMMParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(CMMParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varID}
	 * labeled alternative in {@link CMMParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarID(CMMParser.VarIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varArray}
	 * labeled alternative in {@link CMMParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarArray(CMMParser.VarArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(CMMParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code double}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDouble(CMMParser.DoubleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constBool}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstBool(CMMParser.ConstBoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code char}
	 * labeled alternative in {@link CMMParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChar(CMMParser.CharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompOr}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOr(CMMParser.CompOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompAnd}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompAnd(CMMParser.CompAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompNot}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompNot(CMMParser.CompNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CompRl}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompRl(CMMParser.CompRlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParensComp}
	 * labeled alternative in {@link CMMParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParensComp(CMMParser.ParensCompContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Mod}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMod(CMMParser.ModContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Oppo}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOppo(CMMParser.OppoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpConstant}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpConstant(CMMParser.ExpConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(CMMParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(CMMParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(CMMParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpVariable}
	 * labeled alternative in {@link CMMParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpVariable(CMMParser.ExpVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(CMMParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(CMMParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link CMMParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(CMMParser.StringContext ctx);
}