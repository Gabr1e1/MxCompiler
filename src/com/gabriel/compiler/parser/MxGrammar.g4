grammar MxGrammar;

program : (classDeclaration | functionDeclaration | variableDeclaration)*;

classDeclaration : 'class' name = Identifier '{' (variableDeclaration | functionDeclaration)* '}';

variableDeclaration
    : typename Identifier ('=' expression)? ';'
    | typename (Identifier ',')? Identifier ';'
    ;

typename : Identifier | arrayType;

expression
    : basicExpression                                             # basicExpr
    | '(' expression ')'                                          # parenthesesExpr
    | expression '.' Identifier                                   # memberExpr
    | newExpression                                               # creatorExpr
    | expression '[' expression ']'                               # arrayExpr
    | expression '(' expressionList ')'                           # funcExpr
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
    | expression '&&' expression                                  # binaryExpr
    | expression '||' expression                                  # binaryExpr
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

unaryExpression
    : basicExpression
    | unaryExpression op = ('++' | '--')   //invalidating (a + b)++
    | op = ('++' | '--' | '+' | '-') unaryExpression
    ;

newExpression
    : 'new' type = Identifier ('[' expression ']')+ '[]'*
    | 'new' type = Identifier
    ;

functionDeclaration
    : returnType = typename functionIdentifier = Identifier '(' parameterList? ')'
        block
    ;

parameter : typename Identifier;
parameterList : parameter (',' parameter)*;

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
    : 'return' expression? ';'
    | 'break;'
    | 'continue;'
    ;

Identifier : [a-zA-Z_][a-zA-Z_0-9]*;
PrimitiveType : 'bool' | 'int' | 'void' | 'string';
arrayType: Identifier ('[]')+;

NumLiteral : ('-')? [0-9]+;
fragment StringCharacter : (~["\\\r\n]) | '\\' | '\n' | '\\"';
StringLiteral : '"' StringCharacter* '"';
BoolLiteral : 'true' | 'false';
NullLiteral : 'null';
This : 'this';

WS : [ \t\r\n]+ -> skip;
BlockComment : '/*' .*? '*/' -> skip;
LineComment : '/*' .*? '*/' ~[^\r\n]* -> skip;