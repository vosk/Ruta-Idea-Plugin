package org.antlr.intellij.adaptor.psi;

import com.intellij.lang.Language;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds all {@link PsiLanguageElementFactory}, which in turn hold all {@link com.intellij.psi.tree.IElementType}
 */
public class PsiElementFactory {

    private static final Map<Language, PsiLanguageElementFactory> factories = new ConcurrentHashMap<>();

    public static synchronized PsiLanguageElementFactory get(Language language) {
        if (!factories.containsKey(language)) {
            factories.put(language, new PsiLanguageElementFactory(language));
        }
        return factories.get(language);
    }
}
