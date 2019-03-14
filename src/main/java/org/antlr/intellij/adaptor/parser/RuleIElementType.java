package org.antlr.intellij.adaptor.parser;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Represents a specific ANTLR rule invocation in the language of the plug-in and is the
 *  intellij "token type" of an interior PSI tree node. The IntelliJ equivalent
 *  of ANTLR RuleNode.getRuleIndex() method or maybe RuleNode itself.
 *
 *  Intellij Lexer token types are instances of IElementType.
 *  We differentiate between parse tree subtree roots and tokens with
 *  {@link RuleIElementType} and {@link TokenIElementType}.
 */
public class RuleIElementType extends IElementType {
    private final String ruleName;
    private final boolean isOwner;

    public RuleIElementType(String ruleName,
                            boolean isOwner,
                            @NotNull @NonNls String debugName,
                            @Nullable Language language)
    {
        super(debugName, language);
        this.ruleName = ruleName;
        this.isOwner = isOwner;
    }

    public String getRuleName() {
        return ruleName;
    }

    public boolean isOwner() {
        return isOwner;
    }
}
