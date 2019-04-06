package vosk.ruta.ui.annotator;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.RutaFile;

import java.util.Objects;

public class RenameIdentifierFromPathQuickFix implements LocalQuickFix, IntentionAction {
    @Override
    @NotNull
    public String getFamilyName() {
        return "rename identifier to match file ";
    }


    @Override
    @NotNull
    public String getText() {
        return getName();
    }

    @Override
    public boolean isAvailable(@NotNull final Project project, final Editor editor, final PsiFile file) {
        if(!(file instanceof RutaFile)){
            return false;
        }
        PsiElement psiElement = file.findElementAt(editor.getCaretModel().getOffset());
        PsiNamedElement psiNamedElement = namedElementFromElement(psiElement);
        return psiNamedElement!=null && !(psiNamedElement instanceof PsiFile);
    }

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, final PsiFile file) throws IncorrectOperationException {
        doFix(Objects.requireNonNull(file.findElementAt(editor.getCaretModel().getOffset())));
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }

    private static void doFix(@NotNull PsiElement namedIdentifer) throws IncorrectOperationException {
       namedIdentifer = namedElementFromElement(namedIdentifer);
        if(namedIdentifer != null){
            System.out.println(((PsiNamedElement) namedIdentifer).getName());
            RutaFile file= (RutaFile) namedIdentifer.getContainingFile();
            ((PsiNamedElement) namedIdentifer).setName(file.getScopePath().getPackagePath().toString());
        }

    }


    @Override
    public void applyFix(@NotNull final Project project, @NotNull final ProblemDescriptor descriptor) {
        PsiElement element = descriptor.getPsiElement();
        element=namedElementFromElement(element);
        if (element == null) return;

        doFix(element);
    }

    private static PsiNamedElement namedElementFromElement(PsiElement element){
        return PsiTreeUtil.getParentOfType(element,PsiNamedElement.class);
    }
}
