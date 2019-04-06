package vosk.ruta.psi.nodes;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RutaDeclarationPsiNode extends ASTWrapperPsiElement implements PsiNameIdentifierOwner {


    public RutaDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        IdentifierPsiNode[] childrenByClass = this.findChildrenByClass(IdentifierPsiNode.class);
        if (childrenByClass.length == 1)
            return childrenByClass[0];
        return null;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        throw new IncorrectOperationException("Not implemented");
    }


    @Override
    public int getTextOffset() {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier != null) {
            return nameIdentifier.getTextOffset();
        } else {
            return this.getNode().getStartOffset();
        }
    }
}
