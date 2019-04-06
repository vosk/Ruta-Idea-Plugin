package org.antlr.intellij.adaptor.parser;

import com.intellij.lang.Language;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.debug.BlankDebugEventListener;
import org.apache.uima.ruta.extensions.RutaParseRuntimeException;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This {@link org.antlr.runtime.debug.DebugEventListener} informs a {@link PsiBuilder}
 * about rule entries and exits by constructing markers whenever that happens. Also, does
 * some error handling, but the parser must indeed call
 * {@link org.antlr.runtime.debug.DebugEventListener#recognitionException}.
 * Because the default Ruta Parser does not, we have {@link vosk.ruta.DebuggedRutaParser}
 */
public class ANTLRPsiBuilderDebugListener extends BlankDebugEventListener {
    protected final Language language;
    protected final PsiBuilder builder;
    protected final ContextPsiElementFactory factory;

    private static class CallPly {
        final PsiBuilder.Marker marker;
        final int startOffset;
        final String ruleName;
        Exception error; //This fires after generating ply

        private CallPly(PsiBuilder.Marker marker, String ruleName, int startOffset) {
            this.ruleName = ruleName;
            this.marker = marker;
            this.startOffset = startOffset;
        }
    }

    private Deque<CallPly> callStack = new ArrayDeque<>();


    public ANTLRPsiBuilderDebugListener(Language language, PsiBuilder builder, ContextPsiElementFactory factory) {
        this.language = language;
        this.builder = builder;
        this.factory = factory;
    }

    protected final Language getLanguage() {
        return language;
    }

    protected final PsiBuilder getBuilder() {
        return builder;
    }

    @Override
    public void consumeToken(Token t) {//TODO these are not actually visited
        builder.advanceLexer();
    }

    @Override
    public void consumeHiddenToken(Token t) { //TODO these are not actually visited
        builder.advanceLexer();
    }

    @Override
    public void recognitionException(RecognitionException e) {

        if (e instanceof MismatchedTokenException) {
            PsiBuilder.Marker mark = getBuilder().mark();
            builder.advanceLexer();
            mark.error(e.toString());
        } else {
            assert callStack.peek() != null;
            callStack.peek().error = e;
        }
    }


    public void recognitionException(RutaParseRuntimeException e) {

        assert callStack.peek() != null;
        callStack.peek().error = e;
    }

    @Override
    public void enterRule(String grammarFileName, String ruleName) {
        ProgressIndicatorProvider.checkCanceled();
        callStack.push(new CallPly(getBuilder().mark(), ruleName, builder.getCurrentOffset()));


    }

    @Override
    public void exitRule(String grammarFileName, String ruleName) {
        assert callStack.peek() !=null;
        CallPly ply = callStack.pop();
        ProgressIndicatorProvider.checkCanceled();

        if (ply.error == null) {
            if (builder.getCurrentOffset() == ply.startOffset) {//Nothing moved, pretend it didn't happen
                ply.marker.drop();
            } else {
                ply.marker.done(factory.getRuleIElementFor(ply.ruleName));
            }
        } else {
            ply.marker.error(factory.getErrorStringFor(ply.ruleName, ply.error));
        }

    }
}