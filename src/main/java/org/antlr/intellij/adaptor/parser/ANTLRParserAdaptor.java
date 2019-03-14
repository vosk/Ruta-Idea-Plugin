package org.antlr.intellij.adaptor.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.bridge.PsiBuilderAsTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.debug.DebugParser;
import org.antlr.runtime.tree.ParseTree;
import org.apache.uima.ruta.extensions.RutaParseRuntimeException;
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


//        TokenSource source = new PsiBuilderAsTokenSource(builder);
        TokenStream stream = new PsiBuilderAsTokenStream(builder);
        ANTLRPsiBuilderDebugListener parserListener = new ANTLRPsiBuilderDebugListener(language, builder,factory);
        TokenStream tokens = stream;
//        TokenStream tokens = new DebugTokenStream(new CommonTokenStream(source),parserListener);


//        BlankDebugEventListener parserListener = new BlankDebugEventListener();

        parser.setTokenStream(tokens);
        parser.setDebugListener(parserListener);

//        parser.setErrorHandler(new ErrorStrategyAdaptor()); // tweaks missing tokens
//        parser.removeErrorListeners();
//        parser.addErrorListener(new SyntaxErrorListener()); // trap errors
        ParseTree parseTree = null;
        PsiBuilder.Marker rootMarker = builder.mark();
//        PsiBuilder.Marker rollbackMarker = builder.mark();
        try {
            parse(parser, root);
        }
        catch( RutaParseRuntimeException e){
            parserListener.recognitionException(e);
        }
        catch (RecognitionException e) {
            parserListener.recognitionException(e);
//            e.printStackTrace();
        } finally {
//            rollbackMarker.rollbackTo();
        }
//        rollbackMarker.done();


        // Now convert ANTLR parser tree to PSI tree by mimicking subtree
        // enter/exit with mark/done calls. I *think* this creates their parse
        // tree (AST as they call it) when you call {@link PsiBuilder#getTreeBuilt}


//        ANTLRParseTreeToPSIConverter listener = createListener(parser, root, builder);
        ;
//        ParseTreeWalker.DEFAULT.walk(listener, parseTree);
        while (!builder.eof()) {
            ProgressIndicatorProvider.checkCanceled();
            builder.advanceLexer();
        }
        rootMarker.done(root);
        // NOTE: parse tree returned from parse will be the
        // usual ANTLR tree ANTLRParseTreeToPSIConverter will
        // convert that to the analogous jetbrains AST nodes
        // When parsing an entire file, the root IElementType
        // will be a IFileElementType.
        //
        // When trying to rename IDs and so on, you get a
        // dummy root and a type arg identifier IElementType.
        // This results in a weird tree that has for example
        // (ID (expr (primary ID))) with the ID IElementType
        // as a subtree root as well as the appropriate leaf
        // all the way at the bottom.  The dummy ID root is a
        // CompositeElement and created by
        // ParserDefinition.createElement() despite having
        // being TokenIElementType.
//        rootMarker.done(root);
        return builder.getTreeBuilt(); // calls the ASTFactory.createComposite() etc...
    }

    protected abstract void parse(DebugParser parser, IElementType root) throws RecognitionException;
//
//    protected ANTLRParseTreeToPSIConverter createListener(Parser parser, IElementType root, PsiBuilder builder) {
//        return new ANTLRParseTreeToPSIConverter(language, parser, builder);
//    }
}
