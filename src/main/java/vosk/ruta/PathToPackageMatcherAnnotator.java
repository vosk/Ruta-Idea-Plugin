package vosk.ruta;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.RutaFile;
import vosk.ruta.psi.nodes.RutaPackageDeclarationPsiNode;
import vosk.ruta.psi.nodes.RutaScopePath;

public class PathToPackageMatcherAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof RutaPackageDeclarationPsiNode) {
            RutaScopePath scopePath = ((RutaPackageDeclarationPsiNode) element).getScopePath();
            PsiFile containingFile = element.getContainingFile();
            if(containingFile instanceof RutaFile){
                RutaScopePath fileScopePath = ((RutaFile) containingFile).getScopePath();
                if(!fileScopePath.equal(scopePath))
                    holder.createErrorAnnotation(element.getTextRange(), "Package does not match file path");
            }

        }
    }
}
