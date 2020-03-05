package com.gabriel.compiler.error;

public class SyntaxError extends Error {
    public SyntaxError(int a, int b, String msg) {
        super(String.format("Syntax Error at line %d:%d %s", a, b, msg));
    }
}
