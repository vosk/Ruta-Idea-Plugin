package vosk.ruta;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.RuleIElementType;
import org.antlr.runtime.RecognitionException;
import org.apache.uima.ruta.RutaModule;
import org.apache.uima.ruta.parser.debug.RutaParser;

public final class RutaParserLogic {


    private RutaParserLogic() {

    }

    public static boolean tokenIsIdentifier(int antlrToken) {
        return antlrToken == RutaParser.Identifier;
    }

    /**
     * This generally is needed to call the correct antlr method of the parser based on the root element type.
     * {@link RutaParserDefinition} uses this, also the {@link vosk.ruta.psi.nodes.RutaQualifiedIdentifierHolder}
     * @param parser to run based on root element type
     * @param root the subtree root type, determines which parser method to call.
     * @throws RecognitionException by the parser
     */
    public static void parse(RutaParser parser, IElementType root) throws RecognitionException {
        if ( root instanceof IFileElementType) {
            RutaModule rutaModule = parser.file_input(root.toString());
            System.out.println(rutaModule);
        }else if( root instanceof TokenIElementType){ //Hack! parser is not involved
            parser.getTokenStream().consume(); // Consume a token
        } else if( root instanceof RuleIElementType){
            switch(((RuleIElementType) root).getRuleName()){

                case "identifier":
                    parser.variableDeclaration();
                    break;
                case "dottedIdentifier":
                    parser.dottedIdentifier();
                    break;
            }
            parser.file_input(root.toString());

        }
        else{
            throw new UnsupportedOperationException("Cannot parser from this root");
        }

    }


    public static boolean ruleIsIdentifierOwner(String peek) {
//        if("declaration".equals(peek)){
//            return true;
//        }
        return false;
    }

    public static boolean isSomekindOfDefinition(IElementType el) {
        if ( el instanceof RuleIElementType) {
            switch  (((RuleIElementType) el).getRuleName()) {
                case "importStatement":
                case "declaration":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public static boolean isSomekindOfDefinition(PsiElement el ){
        if(el==null){
            return false;
        }
        if(el.getNode()==null){
            return false;
        }
        if(el.getNode().getElementType()==null){
            return false;
        }
        return isSomekindOfDefinition(el.getNode().getElementType());

    }

    /**
     * The plan to recover currently is just to skip until the next SEMI
     *  Without this, the parser simply give ups and skips the rest of the file.
     * @param e
     * @param builder to advance until the next statement
     */

    public static void recoverFromException(Exception e, PsiBuilder builder) {
        while(true){
            IElementType tokenType = builder.getTokenType();
            if(tokenType==null){
                break ;
            }
            assert tokenType instanceof  TokenIElementType;

            if(((TokenIElementType)tokenType).getANTLRTokenType()==RutaParser.SEMI){
                builder.advanceLexer();
                break;
            }
            else{
                builder.advanceLexer();
            }

        }

    }
}
