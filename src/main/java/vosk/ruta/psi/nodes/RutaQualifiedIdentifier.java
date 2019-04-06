package vosk.ruta.psi.nodes;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.intellij.adaptor.psi.Trees;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaLanguage;

public class RutaQualifiedIdentifier extends ASTWrapperPsiElement implements PsiNamedElement {
    public RutaQualifiedIdentifier(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {

        IElementType type =  this.getNode().getElementType();

        PsiElement newID = Trees.createTreeFromText(getProject(),
                RutaLanguage.INSTANCE,
                getContext(),
                name,
                type);
        if ( newID!=null ) {
            return this.replace(newID);
        }
        return this;
    }

}
