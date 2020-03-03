package com.gabriel.compiler;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import com.gabriel.compiler.parser.*;
import com.gabriel.compiler.frontend.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //Build Concrete Syntax Tree
        CharStream code = CharStreams.fromFileName("./testcases/small_test.mx");
        MxGrammarLexer lexer = new MxGrammarLexer(code);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MxGrammarParser parser = new MxGrammarParser(tokens);
        ParseTree CST = parser.program();

//        Build AST from CST created above
        ASTBuilder builder = new ASTBuilder();
        builder.visit(CST);
        System.out.println("AST successfully created");
    }
}
