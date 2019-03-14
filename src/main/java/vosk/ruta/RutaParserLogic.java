package vosk.ruta;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.RuleIElementType;
import org.antlr.runtime.RecognitionException;
import org.apache.uima.ruta.parser.debug.RutaParser;

public final class RutaParserLogic {

    private RutaParserLogic() {

    }

    public static boolean tokenIsIdentifier(int antlrToken) {
        return antlrToken == RutaParser.Identifier;
    }

    public static void parse(RutaParser parser, IElementType root) throws RecognitionException {
        if ( root instanceof IFileElementType) {
            parser.file_input(root.toString());
        }else if( root instanceof TokenIElementType){ //Hack! parser is not involved
            parser.getTokenStream().consume(); // Consume a token
        } else if( root instanceof RuleIElementType){
            RuleIElementType cast = (RuleIElementType) root;
            switch(((RuleIElementType) root).getRuleName()){

                case "identifer":
                    parser.variableDeclaration();
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
}
