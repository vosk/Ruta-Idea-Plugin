package org.antlr.intellij.adaptor.parser;

import com.intellij.psi.tree.IElementType;

/**
 * The factory is needed because the {@link org.antlr.runtime.debug.DebugEventListener}
 * is provided strings and we need to map them to IElementType
 * This is given to {@link ANTLRParserAdaptor} which is passed on to {@link ANTLRPsiBuilderDebugListener}
 * to handle the  PsiBuilder Markers
 */
public interface  ContextPsiElementFactory {


    IElementType getRuleIElementFor(String rule);

    String getErrorStringFor(String rule, Exception e);
}
