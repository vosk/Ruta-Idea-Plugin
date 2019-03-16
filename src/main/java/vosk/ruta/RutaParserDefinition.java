package vosk.ruta;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor;
import org.antlr.intellij.adaptor.parser.RuleIElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.antlr.intellij.adaptor.psi.PsiElementFactory;
import org.antlr.intellij.adaptor.psi.PsiLanguageElementFactory;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.debug.DebugParser;
import org.apache.uima.fit.internal.ResourceManagerFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.ruta.RutaScriptFactory;
import org.apache.uima.ruta.TypeUsageInformation;
import org.apache.uima.ruta.action.ActionFactory;
import org.apache.uima.ruta.condition.ConditionFactory;
import org.apache.uima.ruta.expression.ExpressionFactory;
import org.apache.uima.ruta.extensions.RutaExternalFactory;
import org.apache.uima.ruta.parser.debug.RutaLexer;
import org.apache.uima.ruta.parser.debug.RutaParser;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.RutaContextPsiElementFactory;
import vosk.ruta.psi.RutaFile;

public class RutaParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE =
            new IFileElementType(RutaLanguage.INSTANCE);

    public final RutaContextPsiElementFactory contextPsiElementFactory = new RutaContextPsiElementFactory();
    public final static PsiLanguageElementFactory elementfactory = PsiElementFactory.get(RutaLanguage.INSTANCE);

    static {

//TODO
//        PSIElementTypeFactory.defineLanguageIElementTypes(RutaLanguage.INSTANCE,
//                RutaLanguageParser.tokenNames,
//                RutaLanguageParser.ruleNames);
//        List<TokenIElementType> tokenIElementTypes =
//                PSIElementTypeFactory.getTokenIElementTypes(RutaLanguage.INSTANCE);
//        ID = tokenIElementTypes.getRule(RutaLanguageLexer.ID);
    }

    public RutaParserDefinition() {
    }
    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        RutaLexerWithState lexer = new RutaLexerWithState(null);
        return new ANTLRLexerAdaptor(RutaLanguage.INSTANCE, lexer);
    }

    @NotNull
    public PsiParser createParser(final Project project) {

        final RutaParser parser;
        try {
            parser = getBlankRutaParser();
        } catch (ResourceInitializationException e) { //TODO wtf is this exception
            e.printStackTrace();
            return null;
        }
        return new ANTLRParserAdaptor(RutaLanguage.INSTANCE, parser, contextPsiElementFactory) {
            @Override
            protected void recoverFromException(Exception e, PsiBuilder builder) {
                RutaParserLogic.recoverFromException(e, builder);
            }

            @Override
            protected void parse(DebugParser parser, IElementType root) throws RecognitionException {
                RutaParserLogic.parse((RutaParser) parser, root);
            }
        };
    }

    public static RutaParser getBlankRutaParser() throws ResourceInitializationException {
        RutaParser parser = new DebuggedRutaParser(new TokenStream() { //TODO dummy need or NullPointer
            @Override
            public Token LT(int k) {
                return null;
            }

            @Override
            public int range() {
                return 0;
            }

            @Override
            public Token get(int i) {
                return null;
            }

            @Override
            public TokenSource getTokenSource() {
                return null;
            }

            @Override
            public String toString(int start, int stop) {
                return null;
            }

            @Override
            public String toString(Token start, Token stop) {
                return null;
            }

            @Override
            public void consume() {

            }

            @Override
            public int LA(int i) {
                return 0;
            }

            @Override
            public int mark() {
                return 0;
            }

            @Override
            public int index() {
                return 0;
            }

            @Override
            public void rewind(int marker) {

            }

            @Override
            public void rewind() {

            }

            @Override
            public void release(int marker) {

            }

            @Override
            public void seek(int index) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public String getSourceName() {
                return null;
            }
        }, null);

        //TODO do I really need these here? maybe for live-preview in the future
        TypeUsageInformation typeUsageInformation = new TypeUsageInformation();
        ActionFactory actionFactory = new ActionFactory(typeUsageInformation);
        ConditionFactory conditionFactory = new ConditionFactory(typeUsageInformation);
        ExpressionFactory expressionFactory = new ExpressionFactory(typeUsageInformation);
        RutaScriptFactory scriptFactory = new RutaScriptFactory(expressionFactory,
                typeUsageInformation);
        scriptFactory.setContext(null);

        parser.setScriptFactory(scriptFactory);
        parser.setExpressionFactory(expressionFactory);
        parser.setActionFactory(actionFactory);
        parser.setConditionFactory(conditionFactory);
        parser.setExternalFactory(new RutaExternalFactory());
        parser.setContext(null);
        parser.setResourcePaths(new String[]{});
        parser.setResourceManager(ResourceManagerFactory.newResourceManager());

        return parser;
    }

    /**
     * "Tokens of those types are automatically skipped by PsiBuilder."
     */
    @NotNull
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(elementfactory.getOrRegisterAsToken(RutaLexer.WS,Token.HIDDEN_CHANNEL));
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return TokenSet.create(
                elementfactory.getOrRegisterAsToken(RutaLexer.COMMENT,Token.HIDDEN_CHANNEL),
                elementfactory.getOrRegisterAsToken(RutaLexer.LINE_COMMENT,Token.HIDDEN_CHANNEL),
                elementfactory.getOrRegisterAsToken(RutaLexer.DocComment,Token.HIDDEN_CHANNEL)
                );
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.create(
                elementfactory.getOrRegisterAsToken(RutaLexer.StringLiteral,Token.HIDDEN_CHANNEL)
        );
    }

    /**
     * What is the IFileElementType of the root parse tree node? It
     * is called from {@link #createFile(FileViewProvider)} at least.
     */
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    /**
     * Create the root of your PSI tree (a PsiFile).
     * <p>
     * From IntelliJ IDEA Architectural Overview:
     * "A PSI (Program Structure Interface) file is the root of a structure
     * representing the contents of a file as a hierarchy of elements
     * in a particular programming language."
     * <p>
     * PsiFile is to be distinguished from a FileASTNode, which is a parse
     * tree node that eventually becomes a PsiFile. From PsiFile, we can getRule
     * it back via: {@link PsiFile#getNode}.
     */
    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new RutaFile(viewProvider);
    }

    /**
     * Convert from *NON-LEAF* parse node (AST they call it)
     * to PSI node. Leaves are created in the AST factory.
     * Rename re-factoring can cause this to be
     * called on a TokenIElementType since we want to rename ID nodes.
     * In that case, this method is called to create the root node
     * but with ID type. Kind of strange, but we can simply create a
     * ASTWrapperPsiElement to make everything work correctly.
     * <p>
     * RuleIElementType.  Ah! It's that ID is the root
     * IElementType requested to parse, which means that the root
     * node returned from parsetree->PSI conversion.  But, it
     * must be a CompositeElement! The adaptor calls
     * rootMarker.done(root) to finish off the PSI conversion.
     * See {@link ANTLRParserAdaptor#parse(IElementType root,
     * PsiBuilder)}
     * <p>
     * If you don't care to distinguish PSI nodes by type, it is
     * sufficient to create a {@link ANTLRPsiNode} around
     * the parse tree node
     */
    @NotNull
    public PsiElement createElement(ASTNode node) {
        IElementType elType = node.getElementType();
        if (elType instanceof TokenIElementType) {
            return new ANTLRPsiNode(node);
        }
        if (!(elType instanceof RuleIElementType)) {
            return new ANTLRPsiNode(node);
        }
//TODO        RuleIElementType ruleElType = (RuleIElementType) elType;
//        switch ( ruleElType.getRuleIndex() ) {
//            case RutaLanguageParser.RULE_function :
//                return new FunctionSubtree(node, elType);
//            case RutaLanguageParser.RULE_vardef :
//                return new VardefSubtree(node, elType);
//            case RutaLanguageParser.RULE_formal_arg :
//                return new ArgdefSubtree(node, elType);
//            case RutaLanguageParser.RULE_block :
//                return new BlockSubtree(node);
//            case RutaLanguageParser.RULE_call_expr :
//                return new CallSubtree(node);
//            default :
//                return new ANTLRPsiNode(node);
//        }
        return new ANTLRPsiNode(node);
    }
}
