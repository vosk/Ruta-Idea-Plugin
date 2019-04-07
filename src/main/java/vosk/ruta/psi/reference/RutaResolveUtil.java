package vosk.ruta.psi.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import vosk.ruta.psi.nodes.RutaScopedNode;
import vosk.ruta.psi.nodes.path.RutaPathUtil;

public class RutaResolveUtil {
    private RutaResolveUtil(){

    }

    public static PsiElement resolve(RutaIdentiferReference reference){
        RutaScopedNode scope = RutaPathUtil.getScope(reference.getElement());
        if(scope==null){
            return null;
        }
        PsiFileSystemItem referencedFile = RutaPathUtil.getReferencedFile(scope, reference.getElement());
        if(referencedFile!=null){
            return referencedFile;
        }

        //TODO: use a processor, extend for other types etc.


        return null;
    }
}
