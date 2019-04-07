package vosk.ruta.ui.annotator;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.RutaFile;
import vosk.ruta.psi.nodes.RutaPackageDeclarationPsiNode;
import vosk.ruta.psi.nodes.path.RutaScopePath;

public class PathToPackageMatcherAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {

        if (element instanceof RutaPackageDeclarationPsiNode) {
            RutaPackageDeclarationPsiNode declarationPsiNode = ((RutaPackageDeclarationPsiNode) element);
            RutaScopePath packageScopePath = declarationPsiNode.getFullScopePath();
            PsiFile containingFile = declarationPsiNode.getContainingFile();
            if(containingFile instanceof RutaFile){
                RutaScopePath fileScopePath = ((RutaFile) containingFile).getScopePath().getPackagePath();
                if(!fileScopePath.equal(packageScopePath)) {
                    Annotation errorAnnotation = holder.createErrorAnnotation(declarationPsiNode.getNamedElementList(), "Package " + packageScopePath + " does not match file path: " + fileScopePath);
                    errorAnnotation.registerFix(new RenameIdentifierFromPathQuickFix());
                }

            }

        }
    }
}
