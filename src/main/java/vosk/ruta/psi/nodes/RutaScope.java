package vosk.ruta.psi.nodes;

import com.intellij.psi.PsiElement;

public interface RutaScope  {
    default boolean contains(RutaScope other){
        PsiElement psiElement = other.rootOfScope();
        while (psiElement!=null){
            if(psiElement==this){
                return true;
            }
            psiElement=psiElement.getParent();
        }
        return false;
    }
    PsiElement rootOfScope();
    RutaScopePath getScopePath();
}
