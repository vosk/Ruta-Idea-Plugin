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
            RutaScopePath packageScopePath = ((RutaPackageDeclarationPsiNode) element).getScopePath();
            PsiFile containingFile = element.getContainingFile();
            if(containingFile instanceof RutaFile){
                RutaScopePath fileScopePath = ((RutaFile) containingFile).getScopePath();
                if(!fileScopePath.equal(packageScopePath))
                    holder.createErrorAnnotation(element.getTextRange(), "Package "+packageScopePath+" does not match file path: "+fileScopePath);
            }

        }
    }
}
