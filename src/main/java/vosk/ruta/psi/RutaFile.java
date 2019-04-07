package vosk.ruta.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.RutaFileType;
import vosk.ruta.RutaLanguage;
import vosk.ruta.psi.nodes.path.RutaScopePath;
import vosk.ruta.psi.types.RutaTypeIndex;

import javax.swing.*;
import java.util.Arrays;

public class RutaFile extends PsiFileBase  {
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

    @NotNull
    public RutaScopePath getScopePath() {
        RutaScopePath.Builder builder = RutaScopePath.builder();
        VirtualFile sourceRootForFile = ProjectFileIndex.getInstance(this.getProject()).getSourceRootForFile(this.getVirtualFile());
        if (sourceRootForFile == null) {
            return builder.postFix(this.getName()).build(RutaScopePath.TYPE.FILE);
        }
        String relativePath = VfsUtilCore.getRelativePath(this.getVirtualFile(), sourceRootForFile, VfsUtilCore.VFS_SEPARATOR_CHAR);
        if (relativePath == null) {
            return builder.postFix(this.getName()).build(RutaScopePath.TYPE.FILE);
        }
        String[] split = relativePath.split(String.valueOf(VfsUtilCore.VFS_SEPARATOR_CHAR));
        split[split.length - 1] = this.getVirtualFile().getNameWithoutExtension();
        Arrays.stream(split)
                .forEachOrdered(builder::postFix);
        return builder.build(RutaScopePath.TYPE.FILE);
    }

    public RutaTypeIndex getIndex() {
        return index;
    }
}