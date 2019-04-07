package vosk.ruta.psi.nodes;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.intellij.adaptor.psi.Trees;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaLanguage;

/**
 * Ruta defines subtrees such as dottedIdentifier, dottedIdentifer2 that contain identifiers,
 * separated primarily with dots.
 */
public class RutaQualifiedIdentifier extends ASTWrapperPsiElement implements PsiNamedElement {
    public RutaQualifiedIdentifier(@NotNull ASTNode node) {
        super(node);
    }

    /**
     * Rename a qualified name, with dots and all.
     * According to the sdk guide, the best way to rename something is to create a subtree from scratch, and
     * replace the current node. This needs the correct parsing extension to work properly, add stuff accordingly
     * in {@link vosk.ruta.RutaParserLogic}
     *
     * @param name to set
     * @return this when operation fails, or replacement node when success
     * @throws IncorrectOperationException when the rename cannot succeed
     */
    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {

        IElementType type = this.getNode().getElementType();

        PsiElement newID = Trees.createTreeFromText(getProject(),
                RutaLanguage.INSTANCE,
                getContext(),
                name,
                type);
        if (newID != null) {
            return this.replace(newID);
        }
        return this;
    }

    @Override
    public PsiReference getReference() {
        return getParent().getReference();
    }


//    @Nullable
//    @Override
//    public PsiElement getNameIdentifier() {
//        return this;
//    }
//
//    @Nullable
//    @Override
//    public PsiElement getIdentifyingElement() {
//        return this;
//    }

}
