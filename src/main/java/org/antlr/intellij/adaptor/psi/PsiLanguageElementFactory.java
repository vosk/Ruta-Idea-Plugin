package org.antlr.intellij.adaptor.psi;

import com.intellij.lang.Language;

import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.RuleIElementType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * {@link com.intellij.psi.tree.IElementType} are registered by JetBrains, they are not supposed to be
 * constructed constantly. Therefore they are cached in these factories
 */
public class PsiLanguageElementFactory {
    private final Language language;

    private final Map<String, RuleIElementType> ruleElements = new ConcurrentHashMap<>();
    private final Map<Integer, Map<Integer, TokenIElementType>> tokens = new ConcurrentHashMap<>();

    public PsiLanguageElementFactory(Language language) {
        this.language = language;
    }

    public RuleIElementType getRule(String name) {
        return ruleElements.get(name);
    }

    public RuleIElementType getOrRegisterAsRule(String name,boolean owner) {
        RuleIElementType exists = getRule(name);
        if (exists != null) {
            return exists;
        }
        ruleElements.put(name, new RuleIElementType(name,owner,name,language));
        return getRule(name);
    }


    public TokenIElementType getToken(int antlrToken, int channel) {
        Map<Integer, TokenIElementType> t = tokens.get(antlrToken);
        if (t != null) {
            return t.get(channel);

        }
        return null;
    }

    public TokenIElementType getOrRegisterAsToken(int antlrToken, int channel) {
        TokenIElementType got = getToken(antlrToken, channel);
        if (got != null) {
            return got;
        }
        if (!tokens.containsKey(antlrToken)) {
            tokens.put(antlrToken, new ConcurrentHashMap<>());
        }
        tokens.get(antlrToken).put(channel, new TokenIElementType(antlrToken,
                channel,
               String.valueOf(antlrToken),
                language)
        );
        return getToken(antlrToken, channel);
    }

}

