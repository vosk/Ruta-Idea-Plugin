package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.path.RutaScopePath;

public abstract class RutaScopedNode extends RutaQualifiedIdentifierHolder implements  RutaPsiNode {

    protected RutaScopePath path;

    public RutaScopedNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void subtreeChanged() {
        path = null;
    }
//
//    protected void buildPath(RutaScopePath.TYPE type) {
//       path=buildPath(type,null);
//
//    }

    public RutaScopePath buildPath(RutaScopePath.TYPE type, PsiElement upto) {
        RutaScopePath.Builder builder = RutaScopePath.builder();
        PsiNamedElement identifierList = getNamedElementList();
        if (identifierList == null) {
            builder.build(type);
            return builder.build(type);
        }
        PsiElement psiChild = identifierList.getFirstChild();
        while (psiChild != null) {
            if (psiChild instanceof RutaIdentifierPsiNode) {
                builder.postFix(((RutaIdentifierPsiNode) psiChild).getName());
            }
            if(psiChild == upto){
                break;
            }
            psiChild = psiChild.getNextSibling();

        }
        return builder.build(type);

    }
    public abstract RutaScopePath.TYPE getScopePathType();

    public RutaScopePath getFullScopePath(){
        if(path==null){
            path=buildPath(getScopePathType(),null);
        }
        return path;
    }

}

