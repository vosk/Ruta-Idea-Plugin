package org.antlr.intellij.adaptor.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.psi.tree.IElementType;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.debug.DebugParser;
import org.jetbrains.annotations.NotNull;

/**
 * An adaptor that makes an ANTLR parser look like a PsiParser.
 */
public abstract class ANTLRParserAdaptor implements PsiParser {
    protected final Language language;
    protected final DebugParser parser;
    private ContextPsiElementFactory factory;

    /**
     * Create a jetbrains adaptor for an ANTLR parser object. When
     * the IDE requests a {@link #parse(IElementType, PsiBuilder)},
     * the token stream will be set on the parser.
     */
    public ANTLRParserAdaptor(Language language, DebugParser parser,ContextPsiElementFactory factory) {
        this.language = language;
        this.parser = parser;
        this.factory = factory;
    }


    public Language getLanguage() {
        return language;
    }

    @NotNull
    @Override
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        ProgressIndicatorProvider.checkCanceled();


        TokenStream stream = new PsiBuilderAsTokenStream(builder);
        ANTLRPsiBuilderDebugListener parserListener = new ANTLRPsiBuilderDebugListener(language, builder,factory);
        TokenStream tokens = stream;

        parser.setTokenStream(tokens);
        parser.setDebugListener(parserListener);

        PsiBuilder.Marker rootMarker = builder.mark();
        int lastStart=-1;
        while(!builder.eof())
        {
            try {
                //This should nominally not happen, but hey, error recovery here... if the parser fails to move on,
                // this is the only place that we could possibly recover.
                if(builder.getCurrentOffset()<=lastStart){
                    break;
                }
                lastStart=builder.getCurrentOffset();
                parse(parser, root);
            }
            catch( Exception e){
                recoverFromException(e,builder);
            }
        }

        while (!builder.eof()) {
            ProgressIndicatorProvider.checkCanceled();
            builder.advanceLexer();
        }
        rootMarker.done(root);

        return builder.getTreeBuilt(); // calls the ASTFactory.createComposite() etc...
    }

    protected abstract void recoverFromException(Exception e, PsiBuilder builder);

    protected abstract void parse(DebugParser parser, IElementType root) throws RecognitionException;

}
