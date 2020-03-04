package com.gabriel.compiler.error;

//not recoverable
public class SemanticError {
    public static class TypeMismatch extends Error {
        public TypeMismatch(String _msg) {
            super("Type Mismatch: " + _msg);
        }
    }

    public static class Redeclare extends Error {
        public Redeclare(String _msg, String scopeName) {
            super("Redeclare: " + _msg + "in" + scopeName);
        }
    }
}
