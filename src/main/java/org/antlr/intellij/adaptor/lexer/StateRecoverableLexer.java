package org.antlr.intellij.adaptor.lexer;

import org.antlr.runtime.RecognizerSharedState;

public interface  StateRecoverableLexer   {

    RecognizerSharedState getState();
    void setState(RecognizerSharedState state);
}
