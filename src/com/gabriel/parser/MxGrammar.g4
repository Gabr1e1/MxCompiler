grammar MxGrammar;

program : (class_definition | function_definition | variable_definition)*;

class_definition : 'class' name = Identifier '{' (variable_definition | function_definition)* '}';

variable_definition
    : typename Identifier ('=' expression)? ';'
    | typename (Identifier ',')? Identifier ';'
    ;

typename : Primitive_type | Array_type;

expression
    : basic_expression
    | expression '.' Identifier
    | new_expression
    | expression '[' expression ']'
    | expression '(' expression_list ')'
    | unary_expression
    | '(' expression ')'
    | op = ('~' | '!') expression
    | expression op = ('*' | '/' | '%') expression
    | expression op = ('+' | '-') expression
    | expression op = ('<<' | '>>') expression
    | expression op = ('<' | '>' | '>=' | '<=') expression
    | expression op = ('==' | '!=' ) expression
    | expression op = '&' expression
    | expression op = '^' expression
    | expression op = '|' expression
    | expression '&&' expression
    | expression '||' expression
    | <assoc=right> expression '=' expression
    ;
expression_list : expression (',' expression)*;

literal
    : NumLiteral
    | StringLiteral
    | BoolLiteral
    | NullLiteral
    ;

basic_expression
    : literal
    | This
    | Identifier
    ;

unary_expression
    : basic_expression
    | unary_expression op = ('++' | '--')   //invalidating (a + b)++
    | op = ('++' | '--' | '+' | '-') unary_expression
    ;

new_expression
    : 'new' typename ('[' expression ']')+ '[]'*
    | 'new' typename
    ;

function_definition
    : return_type = typename function_definition_identifier = Identifier '(' parameter_list? ')'
        block
    ;

parameter : typename Identifier;
parameter_list : parameter (',' parameter)*;

block : '{' statement* '}';

statement
    : block
    | variable_definition
    | control_statement
    | conditional_statement
    | jump_statement
    | expression? ';'
    ;

control_statement
    : 'for' '(' init = expression? ';' condition = expression? ';' increment = expression? ')' statement
    | 'while' '(' condition = expression? ')' statement
    ;
conditional_statement : 'if' '(' expression ')' statement ('else' statement)?;
jump_statement
    : 'return' expression? ';'
    | 'break;'
    | 'continue;'
    ;

Primitive_type : 'bool' | 'int' | 'void' | 'string';
Array_type : Primitive_type ('[]')+;

NumLiteral : ('-')? [0-9]+;
fragment StringCharacter : (~["\\\r\n]) | '\\' | '\n' | '\\"';
StringLiteral : '"' StringCharacter* '"';
BoolLiteral : 'true' | 'false';
NullLiteral : 'null';
This : 'this';
Identifier : [a-zA-Z_][a-zA-Z_0-9]*;

WS : [ \t\r\n]+ -> skip;
Block_comment : '/*' .*? '*/' -> skip;
Line_comment : '/*' .*? '*/' ~[^\r\n]* -> skip;