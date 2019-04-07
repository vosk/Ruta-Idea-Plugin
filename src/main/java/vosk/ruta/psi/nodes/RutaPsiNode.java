package vosk.ruta.psi.nodes;

import com.intellij.psi.PsiElement;

public interface RutaPsiNode extends PsiElement {


//    default RutaScopeReference getNarrowestScope(){
//        PsiElement current=this;
//        do
//        {
//            if(current instanceof RutaScopeReference) {
//                return (RutaScopeReference) current;
//            }
//            current= current.getParent();
//        }
//        while(current!=null);
//        return null;
//    }

}
