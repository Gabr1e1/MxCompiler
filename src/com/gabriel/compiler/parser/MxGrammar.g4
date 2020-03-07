grammar MxGrammar;

program : declaration*;

declaration : classDeclaration | functionDeclaration | variableDeclaration;

classDeclaration : 'class' name = Identifier '{' (variableDeclaration | functionDeclaration)* '}' ';';

variableDeclaration
    : typename Identifier ('=' expression) ';'
    | typename (Identifier ',')* Identifier ';'
    ;
functionDeclaration
    : returnType = typename? functionIdentifier = Identifier '(' parameterList? ')'
        block
    ;

typename : Identifier | arrayType;

expression
    : basicExpression                                             # basicExpr
    | '(' expression ')'                                          # parenthesesExpr
    | expression '(' expressionList? ')'                          # funcExpr
    | expression '[' expression ']'                               # arrayExpr
    | expression '.' Identifier                                   # memberExpr
    | newExpression                                               # creatorExpr
    | expression op = ('++' | '--')                               # suffixExpr
    | op = ('++' | '--' | '+' | '-') expression                   # unaryExpr
    | op = ('~' | '!') expression                                 # unaryExpr
    | expression op = ('*' | '/' | '%') expression                # binaryExpr
    | expression op = ('+' | '-') expression                      # binaryExpr
    | expression op = ('<<' | '>>') expression                    # binaryExpr
    | expression op = ('<' | '>' | '>=' | '<=') expression        # cmpExpr
    | expression op = ('==' | '!=' ) expression                   # cmpExpr
    | expression op = '&' expression                              # binaryExpr
    | expression op = '^' expression                              # binaryExpr
    | expression op = '|' expression                              # binaryExpr
    | expression op = '&&' expression                             # logicExpr
    | expression op = '||' expression                             # logicExpr
    | <assoc=right> expression '=' expression                     # assignmentExpr
    ;
expressionList : expression (',' expression)*;

literal
    : NumLiteral
    | StringLiteral
    | BoolLiteral
    | NullLiteral
    ;

basicExpression
    : literal
    | This
    | Identifier
    ;

newExpression
    : 'new' error = Identifier ('[' expression ']')* ('[' ']')+ ('[' expression ']')+
    | 'new' type = Identifier ('[' expression ']')+ ('[' ']')*
    | 'new' type = Identifier
    ;

parameter : typename Identifier;
parameterList : (parameter (',' parameter)*);

block : '{' statement* '}';

statement
    : block
    | variableDeclaration
    | controlStatement
    | conditionalStatement
    | jumpStatement
    | expression? ';'
    ;

controlStatement
    : 'for' '(' init = expression? ';' condition = expression? ';' increment = expression? ')' statement    #forStatement
    | 'while' '(' condition = expression? ')' statement    #whileStatement
    ;
conditionalStatement : 'if' '(' expression ')' statement ('else' statement)?;
jumpStatement
    : 'return' expression? ';'   # returnStmt
    | 'break;'                   # breakStmt
    | 'continue;'                # continueStmt
    ;



NumLiteral : ('-')? [0-9]+;
fragment StringCharacter : (~["\\\r\n]) | '\\' | '\n' | '\\"';
StringLiteral : '"' StringCharacter* '"';
BoolLiteral : 'true' | 'false';
NullLiteral : 'null';
This : 'this';
Identifier : [a-zA-Z_][a-zA-Z_0-9]*;
PrimitiveType : 'bool' | 'int' | 'void' | 'string';
arrayType: Identifier ('[' ']')+;

WS : [ \t\r\n]+ -> skip;
BlockComment : '/*' .*? '*/' -> skip;
LineComment : '//' ~[\r\n]* -> skip;