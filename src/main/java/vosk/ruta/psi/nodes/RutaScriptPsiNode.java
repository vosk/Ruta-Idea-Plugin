package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vosk.ruta.psi.nodes.path.RutaScopePath;

import java.util.LinkedList;

public class RutaScriptPsiNode extends RutaScopedNode implements PsiNameIdentifierOwner {


    public RutaScriptPsiNode(@NotNull ASTNode node) {
        super(node);
    }

    public PsiFile getReferencedFile(){
        RutaScopePath scopePath = getScopePath();
        VirtualFile sourceRootForFile;
        sourceRootForFile = ProjectFileIndex.getInstance(this.getProject()).getSourceRootForFile(this.getContainingFile().getVirtualFile());
        if(sourceRootForFile==null){
            return null;
        }
        LinkedList<String> strings = scopePath.breadCrumbs();
        String file = strings.removeLast();
        strings.addLast(file);
        VirtualFile currentFile=sourceRootForFile;
        for(String path:strings){
            VirtualFile next=null;
            VirtualFile[] children = currentFile.getChildren();
            if (children == null) return null;
            for (VirtualFile child : children) {
                if (path.equals(child.getNameWithoutExtension())) {
                    next=child;
                    break;
                }
            }
            if(next==null){
                return null;
            }
            currentFile=next;
        }

        return PsiManager.getInstance(this.getProject()).findFile(currentFile);
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        return getReferencedFile();
    }
    @Override
    public RutaScopePath getScopePath() {
        if (path == null)
            buildPath(RutaScopePath.TYPE.FILE);

        return path;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        PsiElement nameIdentifier = getNameIdentifier();
        if(!(nameIdentifier instanceof PsiNamedElement))
            return null;
        return ((PsiNamedElement) nameIdentifier).setName(name);
    }
}
