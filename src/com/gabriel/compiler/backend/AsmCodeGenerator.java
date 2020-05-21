package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.Module;

public class AsmCodeGenerator {
    public AsmCodeGenerator(Module module) {
        //SSA Destruction
        var ssaDestructor = new SSADestructor();
        module.functions.forEach(ssaDestructor::exec);
        System.err.println("SSA Destructed");

        var program = (AsmStruct.Program) (new InstSelection()).visit(module);
        (new AsmPrinter("./testcases/mycode.s")).visit(program);

        //TODO: Register Allocation
    }
}
