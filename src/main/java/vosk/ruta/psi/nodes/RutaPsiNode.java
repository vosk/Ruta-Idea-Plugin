package vosk.ruta.psi.nodes;

import com.intellij.psi.PsiElement;

public interface RutaPsiNode extends PsiElement {


    default RutaScope getNarrowestScope(){
        PsiElement current=this;
        do
        {
            if(current instanceof  RutaScope) {
                return (RutaScope) current;
            }
            current= current.getParent();
        }
        while(current!=null);
        return null;
    }

}
