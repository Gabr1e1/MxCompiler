package com.gabriel.compiler.error;

//not recoverable
public class SemanticError {
    public static class TypeMismatch extends Error {
        public TypeMismatch(String _msg) {
            super("Type Mismatch: " + _msg);
        }
    }

    public static class InvalidOperation extends Error {
        public InvalidOperation(String _msg) {
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

    public static class VoidType extends Error {
        public VoidType(String _msg, String scopeName) {
            super("Type cannot be void: " + _msg + " in " + scopeName + " scope");
        }
    }

    public static class GeneralError extends Error {
        public GeneralError(String _msg, String scopeName) {
            super(_msg + " in " + scopeName + " scope");
        }
    }

    public static class NoMainFunction extends Error {
        public NoMainFunction() {
            super("No main function found");
        }
    }
}
