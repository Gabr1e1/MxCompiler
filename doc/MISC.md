## Antlr4 -> CST

## build AST
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
Note: Antlr用visitor访问的时候如果不重载的话返回最后一个儿子返回的值

## AST -> LLVM IR
LLVM IR is quite complicated. I think I will take these 3 steps:
1. "Engineering a Compiler" Ch5, 6 --> at least learn about SSA
2. PDF
3. ref manual

*Adapted* LLVM IR:
SSA: entry allocation: %inst.toString() + scope + num(in case of conflict)
handling class: struct + function
Functions & BasicBlocks & Instructions are supposed to be doubly-linked
1. Module
2. Function
3. BasicBlock
4. Instruction
   1. ret
   2. unary, binary
   3. alloca, load, store
   4. branch, cmp
   5. phi
5. Type
   1. void, integer, pointer
   2. label
   3. aggregate: array, structure
   4. constant: int, string

User-Use-Usee Design Pattern:
1. Value -> User -> Instruction
2. Value keeps track of user using this value, instruction is a type of user