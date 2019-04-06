package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNamedElement;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public abstract class RutaQualifiedIdentifierHolder extends ANTLRPsiNode implements RutaPsiNode {
    public RutaQualifiedIdentifierHolder(@NotNull ASTNode node) {
        super(node);
    }
    public PsiNamedElement getNamedElementList(){
        return findChildByClass(RutaQualifiedIdentifier.class);
    }
}
