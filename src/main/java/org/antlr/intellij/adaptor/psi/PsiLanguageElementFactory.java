package org.antlr.intellij.adaptor.psi;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.RuleIElementType;
import org.antlr.runtime.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PsiLanguageElementFactory {
    private final Language language;
    private final Map<Integer, String> tokenIndexToString = new ConcurrentHashMap<>();
    private final Map<String, Integer> tokenStringToIndex = new ConcurrentHashMap<>();
    private final Map<Integer, IElementType> tokenElementFromString = new ConcurrentHashMap<>();

    public PsiLanguageElementFactory(Language language) {
        this.language = language;
    }

    public synchronized void register(Map<String, Integer> map, Predicate<String> isLeaf) {
        Map<Integer, String> mapInverted =
                map.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        tokenStringToIndex.putAll(map);
        tokenIndexToString.putAll(mapInverted);
        mapInverted.entrySet()
                .stream()
                .forEach(entry ->{
                    IElementType type;
                    if(isLeaf.test(entry.getValue())){
                        type = new TokenIElementType(entry.getKey(), Token.DEFAULT_CHANNEL,entry.getValue(),language);
                    } else {
                        type = new RuleIElementType(entry.getKey(),entry.getValue(),language);
                    }
                    tokenElementFromString.put(entry.getKey(),type);

                });
    }

    public IElementType get(String name) {
        Integer index = tokenStringToIndex.get(name);
        if(index ==null){
            return null;
        }
        return tokenElementFromString.get(index);
    }

    public IElementType getOrRegisterRule(String name) {
        IElementType exists= get(name);
        if(exists != null){
            return exists;
        }
        Integer maxInt = tokenIndexToString.keySet().stream().reduce(Integer::max).orElse(1);
        maxInt++;
        Map<String, Integer>data = new HashMap();
        data.put(name,maxInt);
        register(data,str->false);
        return get(name);
    }

    public IElementType get(int index) {
        return tokenElementFromString.get(index);
    }
}

