package vosk.ruta.psi.reference;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.IdentifierPsiNode;

public class VariableReference extends RutaElementReference {
	public VariableReference(@NotNull IdentifierPsiNode element) {
		super(element);
	}

	@Override
	public boolean isDefSubtree(PsiElement def) {

		return false;//return def instanceof VardefSubtree;
	}
}
