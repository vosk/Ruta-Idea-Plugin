package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNamedElement;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/**
 *  Every element in the PSI tree that contains a {@link RutaQualifiedIdentifier} uses this as a base
 *  examples are {@link RutaPackageDeclarationPsiNode}, {@link RutaScriptPsiNode}
 */
public abstract class RutaQualifiedIdentifierHolder extends ANTLRPsiNode implements RutaPsiNode {
    public RutaQualifiedIdentifierHolder(@NotNull ASTNode node) {
        super(node);
    }

    /**
     * Get subtree that contains the whole identifier
     * @return PsiNamedElement that contains the whole subtree of the identifier
     */
    public PsiNamedElement getNamedElementList(){
        return findChildByClass(RutaQualifiedIdentifier.class);
    }
}
