package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.Module;

public class AsmCodeGenerator {
    public AsmCodeGenerator(Module module) {
        //SSA Destruction
        var ssaDestructor = new SSADestructor();
        module.functions.forEach(ssaDestructor::exec);
        System.err.println("SSA Destructed");

        //TODO: Instruction Selection

        //TODO: Register Allocation
    }
}
