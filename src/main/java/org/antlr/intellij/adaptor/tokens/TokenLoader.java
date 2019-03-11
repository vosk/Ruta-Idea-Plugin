package org.antlr.intellij.adaptor.tokens;

import com.intellij.lang.Language;
import org.antlr.intellij.adaptor.psi.PsiElementFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TokenLoader {

    public void load(Language language, InputStream toLoad) throws IOException {
        Properties props = new Properties();
        Map<String,Integer> values= new HashMap<>();
        props.load(toLoad);
        props.forEach((key,value) -> values.put((String) key, Integer.valueOf((String) value)));
        PsiElementFactory.get(language).register(values, str-> str.equals(str.toUpperCase()));

    }
}
