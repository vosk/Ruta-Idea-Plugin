package vosk.ruta.brace;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.psi.PsiElementFactory;
import org.antlr.intellij.adaptor.psi.PsiLanguageElementFactory;
import org.antlr.runtime.Token;
import org.apache.uima.ruta.parser.debug.RutaParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vosk.ruta.RutaLanguage;

public class RutaBraceMatcher implements PairedBraceMatcher {


    @NotNull
    @Override
    public BracePair[] getPairs() {
        PsiLanguageElementFactory factory= PsiElementFactory.get(RutaLanguage.INSTANCE);
        return new BracePair[]{
                new BracePair(
                        factory.getOrRegisterAsToken(RutaParser.LPAREN, Token.DEFAULT_CHANNEL),
                        factory.getOrRegisterAsToken(RutaParser.RPAREN, Token.DEFAULT_CHANNEL),
                        false
                        ),
                new BracePair(
                        factory.getOrRegisterAsToken(RutaParser.LCURLY, Token.DEFAULT_CHANNEL),
                        factory.getOrRegisterAsToken(RutaParser.RCURLY, Token.DEFAULT_CHANNEL),
                        true
                )
        };
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
