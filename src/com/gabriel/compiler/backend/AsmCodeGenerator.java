package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.IRPrinter;
import com.gabriel.compiler.IR.Module;

public class AsmCodeGenerator {
    public AsmCodeGenerator(Module module) {
        //SSA Destruction
        var ssaDestructor = new SSADestructor();
        module.functions.forEach(ssaDestructor::exec);
        System.err.println("SSA Destructed");

        IRPrinter irCodeGen3 = new IRPrinter("./testcases/mycode_opt_2.ll");
        irCodeGen3.visit(module);

        //Instruction Selection
        var program = (AsmStruct.Program) (new InstSelection()).visit(module);
//        (new AsmPrinter("./testcases/mycode-before.s")).visit(program);
        System.err.println("Instruction Selection Done");

//        new Peephole(program, false);

        //Register Allocation
        new RegAllocator(program);
        program.cleanUp();
        new Peephole(program, true);

        (new AsmPrinter("./output.s")).visit(program);
        System.err.println("Compilation Done");
    }
}
