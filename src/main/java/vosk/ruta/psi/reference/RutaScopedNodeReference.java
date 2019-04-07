package vosk.ruta.psi.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.RutaScopedNode;

public abstract class RutaScopedNodeReference extends PsiReferenceBase<RutaScopedNode> {
    protected PsiElement subPathUpTo;

    public RutaScopedNodeReference(@NotNull RutaScopedNode element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    public void setSubPathUpTo(PsiElement psiElement){
        this.subPathUpTo =psiElement;
    }

}
