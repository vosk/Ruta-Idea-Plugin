package org.antlr.intellij.adaptor.psi;

import com.intellij.lang.Language;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PsiElementFactory {

    private static final Map<Language, PsiLanguageElementFactory> factories = new ConcurrentHashMap<>();

    public static synchronized PsiLanguageElementFactory get(Language language) {
        if (!factories.containsKey(language)) {
            factories.put(language, new PsiLanguageElementFactory(language));
        }
        return factories.get(language);
    }
}
