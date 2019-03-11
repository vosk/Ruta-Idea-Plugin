package vosk.ruta.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaLanguage;

public class RutaElementType extends IElementType {
    public RutaElementType(@NotNull @NonNls String debugName) {
        super(debugName, RutaLanguage.INSTANCE);
    }
}
