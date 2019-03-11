package vosk.ruta.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaFileType;
import vosk.ruta.RutaLanguage;

import javax.swing.*;

public class RutaFile extends PsiFileBase {
    public RutaFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RutaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return RutaFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Ruta File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}