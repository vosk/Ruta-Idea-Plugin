//package vosk.ruta.highlight;
//
//import com.intellij.lexer.Lexer;
//import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
//import com.intellij.openapi.editor.HighlighterColors;
//import com.intellij.openapi.editor.colors.TextAttributesKey;
//import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
//import com.intellij.psi.tree.IElementType;
//import org.jetbrains.annotations.NotNull;
//import vosk.ruta.RutaLexerAdapter;
//import vosk.ruta.psi.RutaTypes;
//
//import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
//
//public class RutaSyntaxHighlighter extends SyntaxHighlighterBase {
//    public static final TextAttributesKey SEPARATOR =
//            createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
//    public static final TextAttributesKey KEY =
//            createTextAttributesKey("SIMPLE_KEY", DefaultLanguageHighlighterColors.KEYWORD);
//    public static final TextAttributesKey VALUE =
//            createTextAttributesKey("SIMPLE_VALUE", DefaultLanguageHighlighterColors.STRING);
//    public static final TextAttributesKey COMMENT =
//            createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
//    public static final TextAttributesKey BAD_CHARACTER =
//            createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
//
//    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
//    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
//    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEY};
//    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
//    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
//    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
//
//    @NotNull
//    @Override
//    public Lexer getHighlightingLexer() {
//        return new RutaLexerAdaptor();
//    }
//
//    @NotNull
//    @Override
//    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
//
//        if (tokenType.equals(RutaTypes.KW_DECLARE)) {
//            return KEY_KEYS;
//        }
//        if (tokenType.equals(RutaTypes.KW_STRING)) {
//            return KEY_KEYS;
//        }
//        if (tokenType.equals(RutaTypes.KW_SCRIPT)) {
//            return KEY_KEYS;
//        }
//
////        if (tokenType.equals(RutaTypes.SEPARATOR)) {
////            return SEPARATOR_KEYS;
////        } else if (tokenType.equals(RutaTypes.KEY)) {
////            return KEY_KEYS;
////        } else if (tokenType.equals(RutaTypes.VALUE)) {
////            return VALUE_KEYS;
////        } else if (tokenType.equals(RutaTypes.COMMENT)) {
////            return COMMENT_KEYS;
////        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
////            return BAD_CHAR_KEYS;
////        } else {
////            return EMPTY_KEYS;
////        }
//          return EMPTY_KEYS;
//    }
//}