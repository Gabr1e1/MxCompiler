Antlr4 -> CST

build AST
1. types of node: declaration(variable, function, class), expression(unary, binary...), statement(assignment, flow control, new)
2. each node has a pointer to a symbol table(var -> type, function->, class->), maintain a stack while traversing the tree
3. other properties:
    1. declaration: identifier, type
    2. expression: operator, subexpr
    3. statement: operator, lhs, rhs
    4. new: =declaration
4. how to represent a type
    1. string: straightforward
    2. type: base, array(type, size list(-1 for undetermined))
