//package vosk.ruta;
//
//import com.intellij.lang.ASTNode;
//import com.intellij.lang.ParserDefinition;
//import com.intellij.lang.PsiParser;
//import com.intellij.lexer.Lexer;
//import com.intellij.openapi.project.Project;
//import com.intellij.psi.FileViewProvider;
//import com.intellij.psi.PsiElement;
//import com.intellij.psi.PsiFile;
//import com.intellij.psi.TokenType;
//import com.intellij.psi.tree.IFileElementType;
//import com.intellij.psi.tree.TokenSet;
//import com.simpleplugin.psi.RutaFile;
//import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
//import org.antlr.intellij.adaptor.lexer.TokenIElementType;
//import org.apache.uima.ruta.parser.RutaLexer;
//import org.apache.uima.ruta.parser.RutaParser;
//import org.jetbrains.annotations.NotNull;
//import vosk.ruta.psi.RutaTypes;
//
//import java.util.List;
//
//public class RutaParserDefinition implements ParserDefinition {
//    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
////    public static final TokenSet COMMENTS = TokenSet.create(RutaTypes.COMMENT);
//
//    public static final IFileElementType FILE = new IFileElementType(RutaLanguage.INSTANCE);
//
//    static {
//        PSIElementTypeFactory.defineLanguageIElementTypes(RutaLanguage.INSTANCE,
//                RutaParser.tokenNames,
//                RutaParser.ruleNames);
//        List<TokenIElementType> tokenIElementTypes =
//                PSIElementTypeFactory.getTokenIElementTypes(RutaLanguage.INSTANCE);
//        ID = tokenIElementTypes.get(RutaLexer.Identifier);
//    }
//
//    @NotNull
//    @Override
//    public Lexer createLexer(Project project) {
//        return new RutaLexerAdapter();
//    }
//
//    @NotNull
//    public TokenSet getWhitespaceTokens() {
//        return WHITE_SPACES;
//    }
//
//    @NotNull
//    public TokenSet getCommentTokens() {
//        return TokenSet.EMPTY;
//    }
//
//    @NotNull
//    public TokenSet getStringLiteralElements() {
//        return TokenSet.EMPTY;
//    }
//
//    @NotNull
//    public PsiParser createParser(final Project project) {
//        return new RutaParser();
//    }
//
//    @Override
//    public IFileElementType getFileNodeType() {
//        return FILE;
//    }
//
//    public PsiFile createFile(FileViewProvider viewProvider) {
//        return new RutaFile(viewProvider);
//    }
//
//    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
//        return SpaceRequirements.MAY;
//    }
//
//    @NotNull
//    public PsiElement createElement(ASTNode node) {
//        return RutaTypes.Factory.createElement(node);
//    }
//}