package vosk.ruta.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaLanguage;

public class RutaTokenType extends IElementType {
    public RutaTokenType(@NotNull @NonNls String debugName) {
        super(debugName, RutaLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "SimpleTokenType." + super.toString();
    }
}
