package org.antlr.intellij.adaptor.lexer;

import org.antlr.runtime.Token;

public class LexerException extends RuntimeException {
    private final Exception originalException;


    private final Token forcedEmission;

    public LexerException(Exception cause, Token forcedEmission) {
        this.originalException = cause;
        this.forcedEmission = forcedEmission;
    }

    public Exception getOriginalException() {
        return originalException;
    }

    public Token getForcedEmission() {
        return forcedEmission;
    }

}
