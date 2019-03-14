package org.antlr.intellij.adaptor.parser;

import com.intellij.psi.tree.IElementType;

import java.util.Deque;

public interface  ContextPsiElementFactory {


    IElementType getRuleIElementFor(Deque<String> callStack);

    String getErrorStringFor(Deque<String> callStack, Deque<Exception> errorStack);
}
