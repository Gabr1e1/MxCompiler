package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.Module;

public class AsmCodeGenerator {
    public AsmCodeGenerator(Module module) {
        //SSA Destruction
        var ssaDestructor = new SSADestructor();
        module.functions.forEach(ssaDestructor::exec);
        System.err.println("SSA Destructed");

        //Instruction Selection
        var program = (AsmStruct.Program) (new InstSelection()).visit(module);
        (new AsmPrinter("./testcases/mycode-before.s")).visit(program);
        System.err.println("Instruction Selection Done");

        //Register Allocation
        new RegAllocator(program);
        program.cleanUp();
        (new AsmPrinter("./testcases/mycode-after.s")).visit(program);
        System.err.println("Register Allocation Done");
    }
}
