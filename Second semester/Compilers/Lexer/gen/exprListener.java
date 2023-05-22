// Generated from C:/Users/oleksandr.polishchuk/Desktop/oleksandrsfolder/gitrepo/KNU-3-year/Second semester/Compilers/Lexer/src/main/java/org/example/antlr4\expr.g4 by ANTLR 4.12.0

    package org.example;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link exprParser}.
 */
public interface exprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link exprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(exprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(exprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(exprParser.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(exprParser.Expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(exprParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(exprParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#global_get}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_get(exprParser.Global_getContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#global_get}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_get(exprParser.Global_getContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#global_set}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_set(exprParser.Global_setContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#global_set}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_set(exprParser.Global_setContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#global_result}.
	 * @param ctx the parse tree
	 */
	void enterGlobal_result(exprParser.Global_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#global_result}.
	 * @param ctx the parse tree
	 */
	void exitGlobal_result(exprParser.Global_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_inline_call}.
	 * @param ctx the parse tree
	 */
	void enterFunction_inline_call(exprParser.Function_inline_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_inline_call}.
	 * @param ctx the parse tree
	 */
	void exitFunction_inline_call(exprParser.Function_inline_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#require_block}.
	 * @param ctx the parse tree
	 */
	void enterRequire_block(exprParser.Require_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#require_block}.
	 * @param ctx the parse tree
	 */
	void exitRequire_block(exprParser.Require_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#pir_inline}.
	 * @param ctx the parse tree
	 */
	void enterPir_inline(exprParser.Pir_inlineContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#pir_inline}.
	 * @param ctx the parse tree
	 */
	void exitPir_inline(exprParser.Pir_inlineContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#pir_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterPir_expression_list(exprParser.Pir_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#pir_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitPir_expression_list(exprParser.Pir_expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(exprParser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(exprParser.Function_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_definition_body}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_body(exprParser.Function_definition_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_definition_body}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_body(exprParser.Function_definition_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_definition_header}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_header(exprParser.Function_definition_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_definition_header}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_header(exprParser.Function_definition_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_name}.
	 * @param ctx the parse tree
	 */
	void enterFunction_name(exprParser.Function_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_name}.
	 * @param ctx the parse tree
	 */
	void exitFunction_name(exprParser.Function_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_definition_params}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_params(exprParser.Function_definition_paramsContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_definition_params}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_params(exprParser.Function_definition_paramsContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_definition_params_list}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_params_list(exprParser.Function_definition_params_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_definition_params_list}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_params_list(exprParser.Function_definition_params_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_definition_param_id}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition_param_id(exprParser.Function_definition_param_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_definition_param_id}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition_param_id(exprParser.Function_definition_param_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(exprParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(exprParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_call}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call(exprParser.Function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_call}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call(exprParser.Function_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_call_param_list}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_param_list(exprParser.Function_call_param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_call_param_list}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_param_list(exprParser.Function_call_param_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_call_params}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_params(exprParser.Function_call_paramsContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_call_params}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_params(exprParser.Function_call_paramsContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_param(exprParser.Function_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_param(exprParser.Function_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_unnamed_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_unnamed_param(exprParser.Function_unnamed_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_unnamed_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_unnamed_param(exprParser.Function_unnamed_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_named_param}.
	 * @param ctx the parse tree
	 */
	void enterFunction_named_param(exprParser.Function_named_paramContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_named_param}.
	 * @param ctx the parse tree
	 */
	void exitFunction_named_param(exprParser.Function_named_paramContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#function_call_assignment}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_assignment(exprParser.Function_call_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#function_call_assignment}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_assignment(exprParser.Function_call_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#all_result}.
	 * @param ctx the parse tree
	 */
	void enterAll_result(exprParser.All_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#all_result}.
	 * @param ctx the parse tree
	 */
	void exitAll_result(exprParser.All_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#elsif_statement}.
	 * @param ctx the parse tree
	 */
	void enterElsif_statement(exprParser.Elsif_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#elsif_statement}.
	 * @param ctx the parse tree
	 */
	void exitElsif_statement(exprParser.Elsif_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#if_elsif_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_elsif_statement(exprParser.If_elsif_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#if_elsif_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_elsif_statement(exprParser.If_elsif_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(exprParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(exprParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#unless_statement}.
	 * @param ctx the parse tree
	 */
	void enterUnless_statement(exprParser.Unless_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#unless_statement}.
	 * @param ctx the parse tree
	 */
	void exitUnless_statement(exprParser.Unless_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(exprParser.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(exprParser.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_statement(exprParser.For_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_statement(exprParser.For_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#init_expression}.
	 * @param ctx the parse tree
	 */
	void enterInit_expression(exprParser.Init_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#init_expression}.
	 * @param ctx the parse tree
	 */
	void exitInit_expression(exprParser.Init_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#all_assignment}.
	 * @param ctx the parse tree
	 */
	void enterAll_assignment(exprParser.All_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#all_assignment}.
	 * @param ctx the parse tree
	 */
	void exitAll_assignment(exprParser.All_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#for_init_list}.
	 * @param ctx the parse tree
	 */
	void enterFor_init_list(exprParser.For_init_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#for_init_list}.
	 * @param ctx the parse tree
	 */
	void exitFor_init_list(exprParser.For_init_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#cond_expression}.
	 * @param ctx the parse tree
	 */
	void enterCond_expression(exprParser.Cond_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#cond_expression}.
	 * @param ctx the parse tree
	 */
	void exitCond_expression(exprParser.Cond_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#loop_expression}.
	 * @param ctx the parse tree
	 */
	void enterLoop_expression(exprParser.Loop_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#loop_expression}.
	 * @param ctx the parse tree
	 */
	void exitLoop_expression(exprParser.Loop_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#for_loop_list}.
	 * @param ctx the parse tree
	 */
	void enterFor_loop_list(exprParser.For_loop_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#for_loop_list}.
	 * @param ctx the parse tree
	 */
	void exitFor_loop_list(exprParser.For_loop_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#statement_body}.
	 * @param ctx the parse tree
	 */
	void enterStatement_body(exprParser.Statement_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#statement_body}.
	 * @param ctx the parse tree
	 */
	void exitStatement_body(exprParser.Statement_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#statement_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_expression_list(exprParser.Statement_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#statement_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_expression_list(exprParser.Statement_expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(exprParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(exprParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#dynamic_assignment}.
	 * @param ctx the parse tree
	 */
	void enterDynamic_assignment(exprParser.Dynamic_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#dynamic_assignment}.
	 * @param ctx the parse tree
	 */
	void exitDynamic_assignment(exprParser.Dynamic_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#int_assignment}.
	 * @param ctx the parse tree
	 */
	void enterInt_assignment(exprParser.Int_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#int_assignment}.
	 * @param ctx the parse tree
	 */
	void exitInt_assignment(exprParser.Int_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#float_assignment}.
	 * @param ctx the parse tree
	 */
	void enterFloat_assignment(exprParser.Float_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#float_assignment}.
	 * @param ctx the parse tree
	 */
	void exitFloat_assignment(exprParser.Float_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#string_assignment}.
	 * @param ctx the parse tree
	 */
	void enterString_assignment(exprParser.String_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#string_assignment}.
	 * @param ctx the parse tree
	 */
	void exitString_assignment(exprParser.String_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#initial_array_assignment}.
	 * @param ctx the parse tree
	 */
	void enterInitial_array_assignment(exprParser.Initial_array_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#initial_array_assignment}.
	 * @param ctx the parse tree
	 */
	void exitInitial_array_assignment(exprParser.Initial_array_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#array_assignment}.
	 * @param ctx the parse tree
	 */
	void enterArray_assignment(exprParser.Array_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#array_assignment}.
	 * @param ctx the parse tree
	 */
	void exitArray_assignment(exprParser.Array_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#array_definition}.
	 * @param ctx the parse tree
	 */
	void enterArray_definition(exprParser.Array_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#array_definition}.
	 * @param ctx the parse tree
	 */
	void exitArray_definition(exprParser.Array_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#array_definition_elements}.
	 * @param ctx the parse tree
	 */
	void enterArray_definition_elements(exprParser.Array_definition_elementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#array_definition_elements}.
	 * @param ctx the parse tree
	 */
	void exitArray_definition_elements(exprParser.Array_definition_elementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#array_selector}.
	 * @param ctx the parse tree
	 */
	void enterArray_selector(exprParser.Array_selectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#array_selector}.
	 * @param ctx the parse tree
	 */
	void exitArray_selector(exprParser.Array_selectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#dynamic_result}.
	 * @param ctx the parse tree
	 */
	void enterDynamic_result(exprParser.Dynamic_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#dynamic_result}.
	 * @param ctx the parse tree
	 */
	void exitDynamic_result(exprParser.Dynamic_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#dynamic_}.
	 * @param ctx the parse tree
	 */
	void enterDynamic_(exprParser.Dynamic_Context ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#dynamic_}.
	 * @param ctx the parse tree
	 */
	void exitDynamic_(exprParser.Dynamic_Context ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#int_result}.
	 * @param ctx the parse tree
	 */
	void enterInt_result(exprParser.Int_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#int_result}.
	 * @param ctx the parse tree
	 */
	void exitInt_result(exprParser.Int_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#float_result}.
	 * @param ctx the parse tree
	 */
	void enterFloat_result(exprParser.Float_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#float_result}.
	 * @param ctx the parse tree
	 */
	void exitFloat_result(exprParser.Float_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#string_result}.
	 * @param ctx the parse tree
	 */
	void enterString_result(exprParser.String_resultContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#string_result}.
	 * @param ctx the parse tree
	 */
	void exitString_result(exprParser.String_resultContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#comparison_list}.
	 * @param ctx the parse tree
	 */
	void enterComparison_list(exprParser.Comparison_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#comparison_list}.
	 * @param ctx the parse tree
	 */
	void exitComparison_list(exprParser.Comparison_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(exprParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(exprParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#comp_var}.
	 * @param ctx the parse tree
	 */
	void enterComp_var(exprParser.Comp_varContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#comp_var}.
	 * @param ctx the parse tree
	 */
	void exitComp_var(exprParser.Comp_varContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(exprParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(exprParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void enterRvalue(exprParser.RvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void exitRvalue(exprParser.RvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#break_expression}.
	 * @param ctx the parse tree
	 */
	void enterBreak_expression(exprParser.Break_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#break_expression}.
	 * @param ctx the parse tree
	 */
	void exitBreak_expression(exprParser.Break_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#literal_t}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_t(exprParser.Literal_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#literal_t}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_t(exprParser.Literal_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#float_t}.
	 * @param ctx the parse tree
	 */
	void enterFloat_t(exprParser.Float_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#float_t}.
	 * @param ctx the parse tree
	 */
	void exitFloat_t(exprParser.Float_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#int_t}.
	 * @param ctx the parse tree
	 */
	void enterInt_t(exprParser.Int_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#int_t}.
	 * @param ctx the parse tree
	 */
	void exitInt_t(exprParser.Int_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#bool_t}.
	 * @param ctx the parse tree
	 */
	void enterBool_t(exprParser.Bool_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#bool_t}.
	 * @param ctx the parse tree
	 */
	void exitBool_t(exprParser.Bool_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#nil_t}.
	 * @param ctx the parse tree
	 */
	void enterNil_t(exprParser.Nil_tContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#nil_t}.
	 * @param ctx the parse tree
	 */
	void exitNil_t(exprParser.Nil_tContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#id_}.
	 * @param ctx the parse tree
	 */
	void enterId_(exprParser.Id_Context ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#id_}.
	 * @param ctx the parse tree
	 */
	void exitId_(exprParser.Id_Context ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#id_global}.
	 * @param ctx the parse tree
	 */
	void enterId_global(exprParser.Id_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#id_global}.
	 * @param ctx the parse tree
	 */
	void exitId_global(exprParser.Id_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#id_function}.
	 * @param ctx the parse tree
	 */
	void enterId_function(exprParser.Id_functionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#id_function}.
	 * @param ctx the parse tree
	 */
	void exitId_function(exprParser.Id_functionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#terminator}.
	 * @param ctx the parse tree
	 */
	void enterTerminator(exprParser.TerminatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#terminator}.
	 * @param ctx the parse tree
	 */
	void exitTerminator(exprParser.TerminatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#else_token}.
	 * @param ctx the parse tree
	 */
	void enterElse_token(exprParser.Else_tokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#else_token}.
	 * @param ctx the parse tree
	 */
	void exitElse_token(exprParser.Else_tokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#crlf}.
	 * @param ctx the parse tree
	 */
	void enterCrlf(exprParser.CrlfContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#crlf}.
	 * @param ctx the parse tree
	 */
	void exitCrlf(exprParser.CrlfContext ctx);
}