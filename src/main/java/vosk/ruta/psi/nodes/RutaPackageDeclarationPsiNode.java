package vosk.ruta.psi.nodes;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import vosk.ruta.psi.nodes.path.RutaScopePath;

public class RutaPackageDeclarationPsiNode extends RutaScopedNode {


    public RutaPackageDeclarationPsiNode(@NotNull ASTNode node) {
        super(node);
    }

//    @Override
//    public RutaScopePath getScopePath() {
//        if (path == null)
//            buildPath(RutaScopePath.TYPE.PACKAGE);
//
//        return path;
//    }

    @Override
    public RutaScopePath.TYPE getScopePathType() {
        return RutaScopePath.TYPE.PACKAGE;
    }
}
