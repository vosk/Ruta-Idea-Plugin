package vosk.ruta.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.parser.ContextPsiElementFactory;
import org.antlr.intellij.adaptor.parser.RuleIElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.antlr.intellij.adaptor.psi.PsiElementFactory;
import org.antlr.intellij.adaptor.psi.PsiLanguageElementFactory;
import vosk.ruta.RutaLanguage;
import vosk.ruta.RutaParserLogic;
import vosk.ruta.psi.nodes.RutaDeclarationPsiNode;
import vosk.ruta.psi.nodes.RutaPackageDeclarationPsiNode;
import vosk.ruta.psi.nodes.RutaQualifiedIdentifier;
import vosk.ruta.psi.nodes.RutaScriptPsiNode;

public class RutaContextPsiElementFactory implements ContextPsiElementFactory {

    private final PsiLanguageElementFactory factory;
    public RutaContextPsiElementFactory() {

        this.factory = PsiElementFactory.get(RutaLanguage.INSTANCE);
    }


    @Override
    public IElementType getRuleIElementFor(String rule) {
        IElementType type= factory.getRule(rule);
        if(type!=null){
            return type;
        }
        if(RutaParserLogic.ruleIsIdentifierOwner(rule)){
            return factory.getOrRegisterAsRule(rule,true);
        }
        else {
            return factory.getOrRegisterAsRule(rule,false);
        }
    }

    @Override
    public String getErrorStringFor(String rule, Exception e) {
        return rule+" : "+e.toString();
    }

    public PsiElement mapASTtoPsi(ASTNode node){
        if(node.getElementType() instanceof RuleIElementType){
            RuleIElementType elementType= (RuleIElementType) node.getElementType();
            switch (elementType.getRuleName())
            {
                case "declaration":
                  return new RutaDeclarationPsiNode(node);
                case "dottedIdentifier":
                case "dottedIdentifier2":
                    return new RutaQualifiedIdentifier(node);
                case "packageDeclaration":
                    return new RutaPackageDeclarationPsiNode(node);
                case "importStatement":
                    return new RutaScriptPsiNode(node);


            }
        }
        return new ANTLRPsiNode(node);

    }

    public static int countChildren(ASTNode node, IElementType type){
        int count=0;
        ASTNode child=node.getFirstChildNode();
        while(child!=null){
            if(child.getElementType()==type){
                count++;
            }
            child=child.getTreeNext();
        }
        return count;

    }
}
