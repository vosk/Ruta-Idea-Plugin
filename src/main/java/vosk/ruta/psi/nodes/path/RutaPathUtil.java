package vosk.ruta.psi.nodes.path;

import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import vosk.ruta.psi.nodes.RutaScopedNode;

import java.util.LinkedList;

public class RutaPathUtil {
    private RutaPathUtil() {
    }

    ;

    public static PsiFileSystemItem getReferencedFile(RutaScopedNode element, PsiElement upto) {
        RutaScopePath scopePath = element.buildPath(element.getScopePathType(),upto);
        VirtualFile sourceRootForFile;
        sourceRootForFile = ProjectFileIndex.getInstance(element.getProject()).getSourceRootForFile(element.getContainingFile().getVirtualFile());
        if (sourceRootForFile == null) {
            return null;
        }
        LinkedList<String> strings = scopePath.breadCrumbs();
        VirtualFile currentFile = sourceRootForFile;
        for (String path : strings) {
            VirtualFile next = null;
            VirtualFile[] children = currentFile.getChildren();
            if (children == null) return null;
            for (VirtualFile child : children) {
                if (path.equals(child.getNameWithoutExtension())) {
                    next = child;
                    break;
                }
            }
            if (next == null) {
                return null;
            }
            currentFile = next;
        }
        PsiFile psiFile = PsiManager.getInstance(element.getProject()).findFile(currentFile);
        if(psiFile!=null){
            return psiFile;
        }
        return PsiManager.getInstance(element.getProject()).findDirectory(currentFile);
    }

    public static RutaScopedNode getScope(PsiElement element) {
        RutaScopedNode reference = PsiTreeUtil.getParentOfType(element, RutaScopedNode.class);
        return  reference;
    }
}
