package com.gabriel.compiler.error;

import com.gabriel.compiler.frontend.Type;

public class SemanticError {
    public static class TypeMismatch extends Exception {
        public TypeMismatch(String _msg) {
            super("Type Mismatch: " + _msg);
        }
    }

    public static class Redeclare extends Exception {
        public Redeclare(String _msg, String scopeName) {
            super("Redeclare: " + _msg + "in" + scopeName);
        }
    }}
