package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class RutaPackageDeclarationPsiNode extends ANTLRPsiNode implements RutaScope, RutaPsiNode{

    private RutaScopePath path;
    public RutaPackageDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement rootOfScope() {
        return this;
    }

    @Override
    public void subtreeChanged() {
            path=null;
    }

    private void buildPath(){
        RutaScopePath.Builder builder = RutaScopePath.builder();
        PsiNamedElement identifierList = getNamedElementList();
        if(identifierList==null){
            builder.build();
            return;
        }
        PsiElement psiChild = identifierList.getFirstChild();
        while(psiChild!=null){
            if(psiChild instanceof  IdentifierPsiNode){
                builder.postFix(((IdentifierPsiNode) psiChild).getName());
            }
            psiChild=psiChild.getNextSibling();
        }
        path=builder.build();

    }
    public PsiNamedElement getNamedElementList(){
        return findChildByClass(RutaQualifiedIdentifier.class);
    }
    @Override
    public RutaScopePath getScopePath() {
        if(path==null)
            buildPath();

        return path;
    }
}
