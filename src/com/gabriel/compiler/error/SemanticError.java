package com.gabriel.compiler.error;

//not recoverable
public class SemanticError {
    public static class TypeMismatch extends Error {
        public TypeMismatch(String _msg) {
            super("IRType Mismatch: " + _msg);
        }
    }

    public static class InvalidOperation extends Error {
        public InvalidOperation(String _msg) {
            super("Invalid Operation: " + _msg);
        }
    }

    public static class InvalidStatement extends Error {
        public InvalidStatement(String _msg) {
            super("Invalid Statement: " + _msg);
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
            super("Invalid IRType: " + _msg + " in " + scopeName + " scope");
        }
    }

    public static class InvalidJump extends Error {
        public InvalidJump(String _msg) {
            super("Invalid Jump: " + _msg);
        }
    }

    public static class VoidType extends Error {
        public VoidType(String _msg, String scopeName) {
            super("IRType cannot be void: " + _msg + " in " + scopeName + " scope");
        }
    }

    public static class GeneralError extends Error {
        public GeneralError(String _msg, String scopeName) {
            super(_msg + " in " + scopeName + " scope");
        }
    }

    public static class NoMainFunction extends Error {
        public NoMainFunction() {
            super("No correct main function found");
        }
    }

    public static class LvalueRequired extends Error {
        public LvalueRequired() {
            super("Should be left value");
        }
    }

    public static class NoReturnStatement extends Error {
        public NoReturnStatement(String _msg) {
            super("No Return statement in " + _msg);
        }
    }
}
