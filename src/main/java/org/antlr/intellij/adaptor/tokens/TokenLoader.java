//package org.antlr.intellij.adaptor.tokens;
//
//import com.intellij.lang.Language;
//import org.antlr.intellij.adaptor.psi.PsiElementFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import java.util.function.Predicate;
//
//public class TokenLoader {
//
//    public void load(Language language, InputStream toLoad, Predicate<Map.Entry<Integer, String>> isToken) throws IOException {
//        Properties props = new Properties();
//        Map<String, Integer> values = new HashMap<>();
//        props.load(toLoad);
//        props.forEach((key, value) -> values.put((String) key, Integer.valueOf((String) value)));
//        PsiElementFactory.get(language).register(values, isToken);
//
//    }
//}
