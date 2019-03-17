package vosk.ruta.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaFileType;
import vosk.ruta.RutaLanguage;
import vosk.ruta.psi.nodes.RutaScope;
import vosk.ruta.psi.nodes.RutaScopePath;
import vosk.ruta.psi.types.RutaTypeIndex;

import javax.swing.*;

public class RutaFile extends PsiFileBase implements RutaScope {
    private final RutaScopePath path;
    private final RutaTypeIndex index = new RutaTypeIndex();
    public RutaFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RutaLanguage.INSTANCE);
//        viewProvider.getFileType()
        //TODO extract package path
        path=RutaScopePath.builder().build();
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

    @Override
    public PsiElement rootOfScope() {
        return this;
    }

    @Override
    public RutaScopePath getScopePath() {
        return path;
    }

    public RutaTypeIndex getIndex() {
        return index;
    }
}