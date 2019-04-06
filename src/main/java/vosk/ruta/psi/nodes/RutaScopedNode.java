package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.path.RutaScopePath;

public abstract class RutaScopedNode extends RutaQualifiedIdentifierHolder implements RutaScope, RutaPsiNode {

    protected RutaScopePath path;

    public RutaScopedNode(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement rootOfScope() {
        return this;
    }

    @Override
    public void subtreeChanged() {
        path = null;
    }

    protected void buildPath(RutaScopePath.TYPE type) {
        RutaScopePath.Builder builder = RutaScopePath.builder();
        PsiNamedElement identifierList = getNamedElementList();
        if (identifierList == null) {
            builder.build(type);
            return;
        }
        PsiElement psiChild = identifierList.getFirstChild();
        while (psiChild != null) {
            if (psiChild instanceof IdentifierPsiNode) {
                builder.postFix(((IdentifierPsiNode) psiChild).getName());
            }
            psiChild = psiChild.getNextSibling();
        }
        path = builder.build(type);

    }
}

