package vosk.ruta.ui.highlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.apache.uima.ruta.parser.debug.RutaLexer;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaLanguage;
import vosk.ruta.RutaLexerWithState;


import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class RutaSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey SEMI =
            createTextAttributesKey("SEMI", DefaultLanguageHighlighterColors.SEMICOLON);

    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey("BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

    public static final TextAttributesKey BRACKETS =
            createTextAttributesKey("BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey DOT =
            createTextAttributesKey("DOT", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey IDENTIFIER =
            createTextAttributesKey("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey ARROW =
            createTextAttributesKey("ARROW", DefaultLanguageHighlighterColors.OPERATION_SIGN);

    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
//
//    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] SEMI_KEYS = new TextAttributesKey[]{SEMI};
    private static final TextAttributesKey[] Identifier_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] KW_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] DOT_KEYS = new TextAttributesKey[]{DOT};
    private static final TextAttributesKey[] BRACKET_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] ARROW_KEYS = new TextAttributesKey[]{ARROW};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] BLOCKCOMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        RutaLexerWithState lexer = new RutaLexerWithState(null);
        return new ANTLRLexerAdaptor(RutaLanguage.INSTANCE, lexer);
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {

        if(tokenType==null){
            return EMPTY_KEYS;
        }
        if (!(tokenType instanceof TokenIElementType)) {
            return EMPTY_KEYS;
        }
        TokenIElementType token = (TokenIElementType) tokenType;
        switch (token.getANTLRTokenType()) {
            case RutaLexer.Identifier:
                return Identifier_KEYS;
            case RutaLexer.SEMI:
                return SEMI_KEYS;
            case RutaLexer.DOT:
                return DOT_KEYS;
            case RutaLexer.StringLiteral:
                return STRING_KEYS;
            case RutaLexer.LBRACK:
            case RutaLexer.RBRACK:
                return BRACKET_KEYS;
            case RutaLexer.THEN:
            case RutaLexer.THEN2:
                return ARROW_KEYS;
            case RutaLexer.LINE_COMMENT:
                return COMMENT_KEYS;
            case RutaLexer.COMMENT:
            case RutaLexer.DocComment:
                return BLOCKCOMMENT_KEYS;

            default:
                return KW_KEYS;

        }

//        if (tokenType.equals(RutaTypes.SEPARATOR)) {
//            return SEPARATOR_KEYS;
//        } else if (tokenType.equals(RutaTypes.IDENTIFIER)) {
//            return KEY_KEYS;
//        } else if (tokenType.equals(RutaTypes.VALUE)) {
//            return VALUE_KEYS;
//        } else if (tokenType.equals(RutaTypes.COMMENT)) {
//            return COMMENT_KEYS;
//        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
//            return BAD_CHAR_KEYS;
//        } else {
//            return EMPTY_KEYS;
//        }
//        return EMPTY_KEYS;
    }
}
