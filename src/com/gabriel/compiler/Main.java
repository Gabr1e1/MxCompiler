package com.gabriel.compiler;

import com.gabriel.compiler.frontend.ASTBuilder;
import com.gabriel.compiler.parser.MxGrammarLexer;
import com.gabriel.compiler.parser.MxGrammarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws Exception {
//        Build Concrete Syntax Tree
        CharStream code = CharStreams.fromFileName("./testcases/small_test.mx");
        MxGrammarLexer lexer = new MxGrammarLexer(code);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MxGrammarParser parser = new MxGrammarParser(tokens);
        ParseTree CST = parser.program();

//        Build AST from CST created above
        try {
            ASTBuilder builder = new ASTBuilder();
            builder.visit(CST);
            System.out.println("AST successfully created");
        } catch (Exception e) {
            System.out.println(e.toString());
            exit(1);
        }
    }
}
