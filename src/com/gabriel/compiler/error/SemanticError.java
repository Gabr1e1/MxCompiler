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
            super("Redeclare: " + _msg + " in " + scopeName + " scope");
        }
    }

    public static class NotDeclared extends Error {
        public NotDeclared(String _msg, String scopeName) {
            super("NotDeclared: " + _msg + " in " + scopeName + " scope");
        }
    }

    public static class InvalidType extends Error {
        public InvalidType(String _msg, String scopeName) {
            super("Invalid Type: " + _msg + " in " + scopeName + " scope");
        }
    }
}
