package vosk.ruta;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RutaFileType extends LanguageFileType {
    public static final RutaFileType INSTANCE = new RutaFileType();

    private RutaFileType() {
        super(RutaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Ruta file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Ruta language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "ruta";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return RutaIcons.FILE;
    }
}
