// Generated from C:/Users/oleksandr.polishchuk/Desktop/oleksandrsfolder/gitrepo/KNU-3-year/Second semester/Compilers/Lexer/src/main/java/org/example/antlr4\expr.g4 by ANTLR 4.12.0

    package org.example;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link exprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface exprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link exprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(exprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_list(exprParser.Expression_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(exprParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#global_get}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_get(exprParser.Global_getContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#global_set}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_set(exprParser.Global_setContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#global_result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_result(exprParser.Global_resultContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_inline_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_inline_call(exprParser.Function_inline_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#require_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequire_block(exprParser.Require_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#pir_inline}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPir_inline(exprParser.Pir_inlineContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#pir_expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPir_expression_list(exprParser.Pir_expression_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition(exprParser.Function_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_definition_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition_body(exprParser.Function_definition_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_definition_header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition_header(exprParser.Function_definition_headerContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_name(exprParser.Function_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_definition_params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition_params(exprParser.Function_definition_paramsContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_definition_params_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition_params_list(exprParser.Function_definition_params_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_definition_param_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition_param_id(exprParser.Function_definition_param_idContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#return_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statement(exprParser.Return_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call(exprParser.Function_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_call_param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call_param_list(exprParser.Function_call_param_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_call_params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call_params(exprParser.Function_call_paramsContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_param(exprParser.Function_paramContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_unnamed_param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_unnamed_param(exprParser.Function_unnamed_paramContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_named_param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_named_param(exprParser.Function_named_paramContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#function_call_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_call_assignment(exprParser.Function_call_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#all_result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAll_result(exprParser.All_resultContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#elsif_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsif_statement(exprParser.Elsif_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#if_elsif_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_elsif_statement(exprParser.If_elsif_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(exprParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#unless_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnless_statement(exprParser.Unless_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_statement(exprParser.While_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#for_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_statement(exprParser.For_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#init_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit_expression(exprParser.Init_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#all_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAll_assignment(exprParser.All_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#for_init_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_init_list(exprParser.For_init_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#cond_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond_expression(exprParser.Cond_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#loop_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop_expression(exprParser.Loop_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#for_loop_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_loop_list(exprParser.For_loop_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#statement_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_body(exprParser.Statement_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#statement_expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_expression_list(exprParser.Statement_expression_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(exprParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#dynamic_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDynamic_assignment(exprParser.Dynamic_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#int_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_assignment(exprParser.Int_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#float_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat_assignment(exprParser.Float_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#string_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_assignment(exprParser.String_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#initial_array_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitial_array_assignment(exprParser.Initial_array_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#array_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_assignment(exprParser.Array_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#array_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_definition(exprParser.Array_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#array_definition_elements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_definition_elements(exprParser.Array_definition_elementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#array_selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_selector(exprParser.Array_selectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#dynamic_result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDynamic_result(exprParser.Dynamic_resultContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#dynamic_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDynamic_(exprParser.Dynamic_Context ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#int_result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_result(exprParser.Int_resultContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#float_result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat_result(exprParser.Float_resultContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#string_result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_result(exprParser.String_resultContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#comparison_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison_list(exprParser.Comparison_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(exprParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#comp_var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_var(exprParser.Comp_varContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(exprParser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#rvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRvalue(exprParser.RvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#break_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_expression(exprParser.Break_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#literal_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral_t(exprParser.Literal_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#float_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat_t(exprParser.Float_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#int_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_t(exprParser.Int_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#bool_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_t(exprParser.Bool_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#nil_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNil_t(exprParser.Nil_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#id_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_(exprParser.Id_Context ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#id_global}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_global(exprParser.Id_globalContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#id_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_function(exprParser.Id_functionContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#terminator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminator(exprParser.TerminatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#else_token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_token(exprParser.Else_tokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link exprParser#crlf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCrlf(exprParser.CrlfContext ctx);
}