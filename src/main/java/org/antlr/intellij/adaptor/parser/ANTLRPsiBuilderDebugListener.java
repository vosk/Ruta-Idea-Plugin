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

/** This is how we build an intellij PSI tree from an ANTLR parse tree.
 *  We let the ANTLR parser build its kind of ParseTree and then
 *  we convert to a PSI tree in one go using a standard ANTLR ParseTreeListener.
 *
 *  The list of SyntaxError objects are pulled from the parser and used
 *  for error message highlighting (error nodes don't have the info).
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
//            PsiBuilder.Marker mark = builder.mark();
            builder.advanceLexer();
            System.out.println(e.getMessage());
            mark.error(e.toString());
        } else {
            callStack.peek().error = e;
        }
    }


    public void recognitionException(RutaParseRuntimeException e) {

        callStack.peek().error = e;
    }

    @Override
    public void enterRule(String grammarFileName, String ruleName) {
        ProgressIndicatorProvider.checkCanceled();
        callStack.push(new CallPly(getBuilder().mark(), ruleName, builder.getCurrentOffset()));


    }

    @Override
    public void exitRule(String grammarFileName, String ruleName) {
        CallPly ply = callStack.pop();
        ProgressIndicatorProvider.checkCanceled();

        if (ply.error == null) {
            if (builder.getCurrentOffset() == ply.startOffset) {//Nothing moved, pretend it didn't happen
                ply.marker.drop();
            } else {
                ply.marker.done(factory.getRuleIElementFor(ply.ruleName));
            }

            //marker.done(new RuleIElementType(1,ruleName,language));
        } else {
            ply.marker.error(factory.getErrorStringFor(ply.ruleName, ply.error));
        }

    }
}