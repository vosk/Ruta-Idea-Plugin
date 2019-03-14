package org.antlr.intellij.adaptor.parser;

import com.intellij.lang.Language;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import javassist.compiler.SyntaxError;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.debug.BlankDebugEventListener;
import org.apache.uima.ruta.extensions.RutaParseRuntimeException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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
    protected final Deque<PsiBuilder.Marker> markers = new ArrayDeque<PsiBuilder.Marker>();
    private Deque<Exception> errorStack= new ArrayDeque<>();
    private Deque<String> callStack = new ArrayDeque<>();


//TODO
//    protected final List<TokenIElementType> tokenElementTypes;
//    protected final List<RuleIElementType> ruleElementTypes;

    /** Map an error's start char index (usually start of a token) to the error object. */
    protected Map<Integer, SyntaxError> tokenToErrorMap = new HashMap<>();

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

    protected final Deque<PsiBuilder.Marker> getMarkers() {
        return markers;
    }

//    protected final List<TokenIElementType> getTokenElementTypes() {
//        return tokenElementTypes;
//    }

//    protected final List<RuleIElementType> getRuleElementTypes() {
//        return ruleElementTypes;
//    }

    @Override
    public void consumeToken(Token t){
        builder.advanceLexer();
    }
    @Override
    public void consumeHiddenToken(Token t){
        builder.advanceLexer();
    }
    @Override
    public void recognitionException(RecognitionException e){

        if(e instanceof MismatchedTokenException){
            PsiBuilder.Marker mark = getBuilder().mark();
//            PsiBuilder.Marker mark = builder.mark();
            builder.advanceLexer();
            System.out.println(e.getMessage());
            mark.error(e.toString());
        }
        else{
            errorStack.push(e);
        }

//        marker.error(e.getMessage());
    }


    public void recognitionException(RutaParseRuntimeException e){

        errorStack.push(e);
//        marker.error(e.getMessage());
    }
    @Override
    public void enterRule(String grammarFileName, String ruleName){

        callStack.push(ruleName);
        System.out.println("enter"+ruleName+markers.size());
        ProgressIndicatorProvider.checkCanceled();
        markers.push(getBuilder().mark());
    }
    @Override
    public void exitRule(String grammarFileName, String ruleName){
        System.out.println("exit"+ruleName+markers.size());
        ProgressIndicatorProvider.checkCanceled();
        PsiBuilder.Marker marker = markers.pop();

        if(errorStack.isEmpty()){
            marker.done(factory.getRuleIElementFor(callStack));
            //marker.done(new RuleIElementType(1,ruleName,language));
        }else {

            marker.error(callStack.pop());//factory.getErrorStringFor(callStack,errorStack));
        }

    }

//    @Override
//    public void visitTerminal(TerminalNode node) {
//        builder.advanceLexer();
//    }
//
//    /** Summary. For any syntax error thrown by the parser, there will be an
//     *  ErrorNode in the parse tree and this method will process it.
//     *  All errors correspond to actual tokens in the input except for
//     *  missing token errors.
//     *
//     *  There are there are multiple error situations to consider:
//     *
//     *  1. Extraneous token. The parse tree will have an ErrorNode for token.
//     *
//     *  2. Token mismatch. The parse tree will have an ErrorNode for token.
//     *
//     *  3. Missing token. The parse tree will have an ErrorNode but
//     *     it does not correspond to any bit of the input. We underline
//     *     the current token.
//     *
//     *  4. NoViableAlt (input inconsistent with any rule alt).
//     *     The parse tree will have an ErrorNode for token.
//     *
//     *  5. Tokens consumed to resync the parser during recovery.
//     *     The parse tree will have an ErrorNode for each token.
//     *
//     *  This is complicated by errors that occur at EOF but I have
//     *  modified error strategy to add error nodes for EOF if needed.
//     *
//     *  Another complication. During prediction, we might match n
//     *  tokens and then fail on the n+1 token, leading to NoViableAltException.
//     *  But, it's offending token is at n+1 not current token where
//     *  prediction started (which we use to find syntax errors). So,
//     *  SyntaxError objects return start not offending token in this case.
//     */
//    public void visitErrorNode(ErrorNode node) {
//        ProgressIndicatorProvider.checkCanceled();
//
//        Token badToken = node.getSymbol();
//        boolean isConjuredToken = badToken.getTokenIndex()<0;
//        int nodeStartIndex = badToken.getStartIndex();
//        SyntaxError error = tokenToErrorMap.getRule(nodeStartIndex);
//
//        if ( error!=null ) {
//            PsiBuilder.Marker errorMarker = builder.mark();
//            if ( badToken.getStartIndex()>=0 &&
//                    badToken.getType()!=Token.EOF &&
//                    !isConjuredToken )
//            {
//                // we advance lexer if error occurred at a real token
//                // Missing tokens should highlight the token at the missing position
//                // but can't consume a token that does not exist.
//                builder.advanceLexer();
//            }
//            String message = String.format("%s%n", error.getMessage());
//            errorMarker.error(message);
//        }
//        else {
//            if ( isConjuredToken ) {
//                PsiBuilder.Marker errorMarker = builder.mark();
//                errorMarker.error(badToken.getText()); // says "<missing X>" or similar
//            }
//            else {
//                // must be a real token consumed during recovery; just consume w/o highlighting it as an error
//                builder.advanceLexer();
//            }
//        }
//    }
//    @Override
//    public void enter
//
//    @Override
//    public void enterEveryRule(ParserRuleContext ctx) {
//        ProgressIndicatorProvider.checkCanceled();
//        markers.push(getBuilder().mark());
//    }
//
//    @Override
//    public void exitEveryRule(ParserRuleContext ctx) {
//        ProgressIndicatorProvider.checkCanceled();
//        PsiBuilder.Marker marker = markers.pop();
//        marker.done(getRuleElementTypes().getRule(ctx.getRuleIndex()));
//    }
}
