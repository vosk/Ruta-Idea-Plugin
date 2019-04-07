package vosk.ruta.psi.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vosk.ruta.psi.nodes.RutaScopedNode;
import vosk.ruta.psi.nodes.path.RutaPathUtil;

public class RutaFileReference extends vosk.ruta.psi.reference.RutaScopedNodeReference {
    public RutaFileReference(@NotNull RutaScopedNode element) {
        /** WARNING: You must send up the text range or you get this error:
         * "Cannot find manipulator for PsiElement(ID) in org.antlr.jetbrains.sample.RutaElementReference"...
         *  when you click on an identifier.  During codeInsight you get this
         *  error too if you don't impl handleElementRename().
         *
         *  The range is relative to start of the token; I guess for
         *  qualified references we might want to use just a part of the name.
         *  Or we might look inside string literals for stuff.
         */
        super(element, new TextRange(0, element.getTextLength()));
    }

    /** Change the REFERENCE's ID node (not the targeted def's ID node)
     *  to reflect a codeInsight.
     *
     *  Without this method, we get an error ("Cannot find manipulator...").
     *
     *  getElement() refers to the identifier node that references the definition.
     */
    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return myElement.getNamedElementList().setName(newElementName);
    }


    @Nullable
    @Override
    public PsiElement resolve() {
        return RutaPathUtil.getReferencedFile(myElement,this.subPathUpTo);
    }


}
