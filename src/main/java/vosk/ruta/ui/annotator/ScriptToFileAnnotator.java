package vosk.ruta.ui.annotator;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.RutaScriptPsiNode;
import vosk.ruta.psi.nodes.path.RutaScopePath;

public class ScriptToFileAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {

        if (element instanceof RutaScriptPsiNode) {
            RutaScriptPsiNode script = ((RutaScriptPsiNode) element);
            RutaScopePath path=script.getScopePath();
            PsiFile referencedFile = script.getReferencedFile();
            if(referencedFile==null) {
                Annotation errorAnnotation = holder.createErrorAnnotation(script.getNamedElementList(), "File " + path + " does not exist");
            }

        }
    }

}
