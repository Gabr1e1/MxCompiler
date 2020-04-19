package com.gabriel.compiler;

import com.gabriel.compiler.IR.IRBuilder;
import com.gabriel.compiler.IR.IRPrinter;
import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.error.SyntaxErrorListener;
import com.gabriel.compiler.frontend.ASTBuilder;
import com.gabriel.compiler.frontend.ASTNode;
import com.gabriel.compiler.frontend.SemanticChecker;
import com.gabriel.compiler.optimization.CFGSimplifier;
import com.gabriel.compiler.optimization.MSDCE;
import com.gabriel.compiler.optimization.Mem2Reg;
import com.gabriel.compiler.optimization.Optimizer;
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
        String path = "./code.mx";

        //        Build Concrete Syntax Tree
        CharStream code = CharStreams.fromFileName(args.length != 0 ? args[0] : path);
        MxGrammarLexer lexer = new MxGrammarLexer(code);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MxGrammarParser parser = new MxGrammarParser(tokens);
        ANTLRErrorListener errorListener = new SyntaxErrorListener();
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);


        ASTNode.Program root = null;
        try {
            // syntax check and build CST
            ParseTree CST = parser.program();

            // Build AST from CST created above
            ASTBuilder builder = new ASTBuilder();
            root = (ASTNode.Program) builder.visit(CST);
            System.out.println("AST successfully created");

            //Print AST
//            ASTPrinter printer = new ASTPrinter();
//            printer.visit(root);

            //Semantic Check
            SemanticChecker checker = new SemanticChecker();
            checker.visit(root);
        } catch (Error err) {
            System.out.println(err.toString());
            exit(1);
        }

        IRBuilder ir = new IRBuilder();
        var module = ir.visit(root);

        IRPrinter irCodeGen = new IRPrinter("./testcases/mycode.ll");
        irCodeGen.visit((Module) module);
        System.out.println("IR Successfully generated");

        Optimizer optimizer = new Optimizer((Module) module);
        optimizer.addPass(new Mem2Reg(), new CFGSimplifier(), new MSDCE(), new CFGSimplifier());
        optimizer.optimize();

        IRPrinter irCodeGen2 = new IRPrinter("./testcases/mycode_opt.ll");
        irCodeGen2.visit((Module) module);
        System.out.println("Optimized IR Successfully generated");
    }
}
