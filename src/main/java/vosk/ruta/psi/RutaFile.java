package vosk.ruta.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaFileType;
import vosk.ruta.RutaLanguage;
import vosk.ruta.psi.nodes.RutaScope;
import vosk.ruta.psi.nodes.RutaScopePath;
import vosk.ruta.psi.types.RutaTypeIndex;

import javax.swing.*;
import java.util.Arrays;

public class RutaFile extends PsiFileBase implements RutaScope {
    private final RutaTypeIndex index = new RutaTypeIndex();
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

    @Override
    public PsiElement rootOfScope() {
        return this;
    }

    @Override
    @NotNull
    public RutaScopePath getScopePath() {
        VirtualFile sourceRootForFile = ProjectFileIndex.getInstance(this.getProject()).getSourceRootForFile(this.getVirtualFile());
        if(sourceRootForFile==null){
            return RutaScopePath.builder().build();
        }
        String relativePath = VfsUtilCore.getRelativePath(this.getVirtualFile(), sourceRootForFile, '/');
        if(relativePath==null){
            return RutaScopePath.builder().build();
        }
        String[] split = relativePath.split("/");

        RutaScopePath.Builder builder = RutaScopePath.builder();
        Arrays.stream(split)
                .filter(str-> !str.equals(this.getName()))
                .forEachOrdered(builder::postFix);
        return builder.build();
    }

    public RutaTypeIndex getIndex() {
        return index;
    }
}