package vosk.ruta.psi;

import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.parser.ContextPsiElementFactory;
import org.antlr.intellij.adaptor.psi.PsiElementFactory;
import org.antlr.intellij.adaptor.psi.PsiLanguageElementFactory;
import vosk.ruta.RutaLanguage;
import vosk.ruta.RutaParserLogic;

import java.util.Deque;

public class RutaContextPsiElementFactory implements ContextPsiElementFactory {

    private final PsiLanguageElementFactory factory;
    public RutaContextPsiElementFactory() {

        this.factory = PsiElementFactory.get(RutaLanguage.INSTANCE);
    }

    @Override
    public IElementType getRuleIElementFor(Deque<String> callStack) {
        String peek = callStack.pop();
        IElementType type= factory.getRule(peek);
        if(type!=null){
            return type;
        }
        if(RutaParserLogic.ruleIsIdentifierOwner(peek)){
            return factory.getOrRegisterAsRule(peek,true);
        }
        else {
            return factory.getOrRegisterAsRule(peek,false);
        }
    }

    @Override
    public String getErrorStringFor(Deque<String> callStack, Deque<Exception> errorStack) {
        return null;
    }
}
