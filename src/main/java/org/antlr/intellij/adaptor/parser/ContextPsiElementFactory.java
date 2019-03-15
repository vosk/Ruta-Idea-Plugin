package org.antlr.intellij.adaptor.parser;

import com.intellij.psi.tree.IElementType;

public interface  ContextPsiElementFactory {


    IElementType getRuleIElementFor(String rule);

    String getErrorStringFor(String rule, Exception e);
}
