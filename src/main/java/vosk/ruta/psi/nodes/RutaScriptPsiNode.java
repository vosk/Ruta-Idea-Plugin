package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.path.RutaScopePath;

public class RutaScriptPsiNode extends RutaScopedNode  {


    public RutaScriptPsiNode(@NotNull ASTNode node) {
        super(node);
    }

//    @Nullable
//    @Override
//    public PsiElement getNameIdentifier() {
//        return this.getNamedElementList();
//    }
////    @Override
////    public RutaScopePath getScopePath() {
////        if (path == null)
////            buildPath(RutaScopePath.TYPE.FILE);
////
////        return path;
////    }
//
//    @Override
//    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
//        PsiElement nameIdentifier = getNameIdentifier();
//        if(!(nameIdentifier instanceof PsiNamedElement))
//            return null;
//        return ((PsiNamedElement) nameIdentifier).setName(name);
//    }
//    @Override
//    public PsiReference getReference(){
//        return new RutaFileReference(this);
//    }

    @Override
    public RutaScopePath.TYPE getScopePathType() {
        return RutaScopePath.TYPE.FILE;
    }
}
