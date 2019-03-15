package vosk.ruta.psi;

import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.parser.ContextPsiElementFactory;
import org.antlr.intellij.adaptor.psi.PsiElementFactory;
import org.antlr.intellij.adaptor.psi.PsiLanguageElementFactory;
import vosk.ruta.RutaLanguage;
import vosk.ruta.RutaParserLogic;

public class RutaContextPsiElementFactory implements ContextPsiElementFactory {

    private final PsiLanguageElementFactory factory;
    public RutaContextPsiElementFactory() {

        this.factory = PsiElementFactory.get(RutaLanguage.INSTANCE);
    }


    @Override
    public IElementType getRuleIElementFor(String rule) {
        IElementType type= factory.getRule(rule);
        if(type!=null){
            return type;
        }
        if(RutaParserLogic.ruleIsIdentifierOwner(rule)){
            return factory.getOrRegisterAsRule(rule,true);
        }
        else {
            return factory.getOrRegisterAsRule(rule,false);
        }
    }

    @Override
    public String getErrorStringFor(String rule, Exception e) {
        return rule+" : "+e.toString();
    }
}
