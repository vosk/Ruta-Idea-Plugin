package vosk.ruta.psi.nodes;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class RutaQualifiedIdentifier extends ASTWrapperPsiElement   {
    public RutaQualifiedIdentifier(@NotNull ASTNode node) {
        super(node);
    }

//    @Nullable
//    @Override
//    public PsiElement getNameIdentifier() {
//        return this.getLastChild();
//    }
//
//    @Override
//    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
//        return this;
//    }
}
