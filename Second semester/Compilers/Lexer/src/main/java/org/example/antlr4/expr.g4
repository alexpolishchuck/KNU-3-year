grammar expr;

@header
{
    package org.example;
}

prog : expression_list* EOF;

expression_list : expression terminator
                | expression_list expression terminator
                | terminator
                ;

expression : function
           | function_call
           | return
           | assignment
           | if_statement
           | unless_statement;


elsif_statement : if_elsif_statement;

if_elsif_statement : ELSIF boolean_comparison CRLF statement_body
                   | ELSIF boolean_comparison CRLF statement_body ELSE CRLF statement_body
                   | ELSIF boolean_comparison CRLF statement_body if_elsif_statement
                   ;

if_statement : IF boolean_comparison CRLF statement_body END
             | IF boolean_comparison CRLF statement_body ELSE CRLF statement_body END
             | IF boolean_comparison CRLF statement_body elsif_statement END
             ;

unless_statement : UNLESS boolean_comparison CRLF statement_body END
                 | UNLESS boolean_comparison CRLF statement_body ELSE CRLF statement_body END
                 | UNLESS boolean_comparison CRLF statement_body elsif_statement END
                 ;

statement_body : expression terminator
               | RETRY terminator
               | BREAK terminator
               | statement_body expression terminator
               | statement_body RETRY terminator
               | statement_body BREAK terminator
               ;

function: function_header function_body END;

function_body : (expression_list)+;

function_header : DEF IDENTIFIER function_params;

function_params: ((LEFT_RBRACKET IDENTIFIER (COMMA IDENTIFIER)*? RIGHT_RBRACKET) | LEFT_RBRACKET RIGHT_RBRACKET)?;

function_call_params: (LEFT_RBRACKET (dynamic_parameter | rvalue) (COMMA (dynamic_parameter | rvalue))*? RIGHT_RBRACKET);

function_call : name=IDENTIFIER params=function_call_params
              | name=IDENTIFIER LEFT_RBRACKET RIGHT_RBRACKET
              ;
dynamic_parameter : IDENTIFIER
        | function_parameter_result;

function_parameter_result: function_parameter_result (boolean_comparison | bit_operation | numeric_operation ) function_parameter_result
                         | NOT?int_result
                         | NOT?float_result
                         | NOT?string_result
                         | NOT?function_call;
terminator : SEMICOLON
           | CRLF;

return : rvalue
       | IDENTIFIER
       | function_call;

lvalue: IDENTIFIER;

value: rvalue | lvalue;

assignment: IDENTIFIER assign_sign assignment_right_part;

assignment_right_part: int_result
                     | float_result
                     | boolean_res
                     | string_result;

int_result : int_result op=EXP int_result
           | int_result op=( MUL | DIV | MOD ) int_result
           | int_result op=( PLUS | MINUS ) int_result
           | LEFT_RBRACKET int_result RIGHT_RBRACKET
           | INT
           | IDENTIFIER
           ;

float_result : float_result op=EXP float_result
             | float_result op=( MUL | DIV | MOD ) float_result
             | int_result op=( MUL | DIV | MOD ) float_result
             | float_result op=( MUL | DIV | MOD ) int_result
             | float_result op=( PLUS | MINUS ) float_result
             | int_result op=( PLUS | MINUS )  float_result
             | float_result op=( PLUS | MINUS )  int_result
             | LEFT_RBRACKET float_result RIGHT_RBRACKET
             | FLOAT
             | IDENTIFIER
             ;

string_result : string_result op=MUL int_result
              | int_result op=MUL string_result
              | string_result op=PLUS string_result
              | STRING
              | IDENTIFIER
              ;

boolean_res: not_val?TRUE
           | not_val?FALSE
           | not_val?IDENTIFIER
           | not_val?(float_result | string_result | int_result) (AND not_val?(float_result | string_result | int_result))
           | not_val?(float_result | string_result | int_result) (OR not_val?(float_result | string_result | int_result))
           | not_val?(float_result | string_result | int_result) (boolean_comparison not_val?(float_result | string_result | int_result))
           | boolean_res AND boolean_res
           | boolean_res OR boolean_res
           | boolean_res boolean_comparison boolean_res
           | not_val?(LEFT_RBRACKET boolean_res RIGHT_RBRACKET);

bit_operation: BIT_AND
              | BIT_OR
              | BIT_XOR
              | BIT_NOT
              | BIT_SHL
              | BIT_SHR;

numeric_operation : EXP
                 | MOD
                 | DIV
                 | MUL
                 | PLUS
                 | MINUS;

boolean_comparison:
           | EQUAL
           | NOT_EQUAL
           | GREATER
           | LESS
           | LESS_EQUAL
           | GREATER_EQUAL;


assign_sign: BIT_AND_ASSIGN
           | BIT_OR_ASSIGN
           | BIT_XOR_ASSIGN
           | BIT_SHL_ASSIGN
           | BIT_SHR_ASSIGN
           | ASSIGN
           | PLUS_ASSIGN
           | MINUS_ASSIG
           | MUL_ASSIGN
           | DIV_ASSIGN
           | MOD_ASSIGN
           | EXP_ASSIGN;

rvalue: STRING
| INT
| FLOAT
| NIL
| TRUE
| FALSE
| NIL;

not_val: NOT+;

STRING : '"' ( '\\"' | . )*? '"'
        | '\'' ( '\\\'' | . )*? '\'';

COMMA : ',';
SEMICOLON : ';';
CRLF : '\r'? '\n';

REQUIRE : 'require';
END : 'end';
DEF : 'def';
RETURN : 'return';

IF: 'if';
ELSE : 'else';
ELSIF : 'elsif';
UNLESS : 'unless';
WHILE : 'while';
BREAK : 'break';
FOR : 'for';

TRUE : 'true';
FALSE : 'false';

PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
MOD : '%';
EXP : '**';

EQUAL : '==';
NOT_EQUAL : '!=';
GREATER : '>';
LESS : '<';
LESS_EQUAL : '<=';
GREATER_EQUAL : '>=';

ASSIGN : '=';
PLUS_ASSIGN : '+=';
MINUS_ASSIGN : '-=';
MUL_ASSIGN : '*=';
DIV_ASSIGN : '/=';
MOD_ASSIGN : '%=';
EXP_ASSIGN : '**=';

BIT_AND : '&';
BIT_OR : '|';
BIT_XOR : '^';
BIT_NOT : '~';
BIT_SHL : '<<';
BIT_SHR : '>>';

BIT_AND_ASSIGN : '&=';
BIT_OR_ASSIGN : '|=';
BIT_XOR_ASSIGN : '^=';
BIT_SHL_ASSIGN : '<<=';
BIT_SHR_ASSIGN : '>>=';

AND : 'and' | '&&';
OR : 'or' | '||';
NOT : 'not' | '!';

LEFT_RBRACKET : '(';
RIGHT_RBRACKET : ')';
LEFT_SBRACKET : '[';
RIGHT_SBRACKET : ']';
LEFT_BRACE : '{';
RIGHT_BRACE : '}';

NIL : 'nil';

COMMENT : (('#' ~[\r\n]* '\r'? '\n')|('=begin' .*? '\r'? '\n' '=end')) -> skip;
WS : [ \t]+ -> skip;

INT : [0-9]+;
FLOAT : [0-9]+'.'[0-9]+;
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;