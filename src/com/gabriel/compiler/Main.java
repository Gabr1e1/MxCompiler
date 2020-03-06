package com.gabriel.compiler;

import com.gabriel.compiler.error.SyntaxErrorListener;
import com.gabriel.compiler.frontend.ASTBuilder;
import com.gabriel.compiler.frontend.ASTNode;
import com.gabriel.compiler.frontend.ASTPrinter;
import com.gabriel.compiler.frontend.TypeChecker;
import com.gabriel.compiler.parser.MxGrammarLexer;
import com.gabriel.compiler.parser.MxGrammarParser;
import org.antlr.v4.runtime.ANTLRErrorListener;
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
        ANTLRErrorListener errorListener = new SyntaxErrorListener();
        parser.addErrorListener(errorListener);


        ASTNode.Program root;
        try {
            // syntax check and build CST
            ParseTree CST = parser.program();

            // Build AST from CST created above
            ASTBuilder builder = new ASTBuilder();
            root = (ASTNode.Program) builder.visit(CST);
            System.out.println("AST successfully created");

            //Print AST
            ASTPrinter printer = new ASTPrinter();
            printer.visit(root);

            //Check Type
            TypeChecker checker = new TypeChecker();
            checker.visit(root);
        } catch (Error err) {
            System.out.println(err.toString());
            exit(1);
        }


    }
}
