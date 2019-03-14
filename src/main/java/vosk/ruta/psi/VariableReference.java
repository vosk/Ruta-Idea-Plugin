package vosk.ruta.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class VariableReference extends RutaElementReference {
	public VariableReference(@NotNull IdentifierPsiNode element) {
		super(element);
	}

	@Override
	public boolean isDefSubtree(PsiElement def) {

		return false;//return def instanceof VardefSubtree;
	}
}
