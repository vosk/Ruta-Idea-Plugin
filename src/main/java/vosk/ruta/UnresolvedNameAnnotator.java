package vosk.ruta;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.IdentifierPsiNode;

public class UnresolvedNameAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof IdentifierPsiNode) {
            holder.createErrorAnnotation(element.getTextRange(), "Unresolved property");
        }
    }
}
