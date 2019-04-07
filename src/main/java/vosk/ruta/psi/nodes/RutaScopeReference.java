//package vosk.ruta.psi.nodes;
//
//import com.intellij.psi.PsiElement;
//
//public interface RutaScopeReference{
//    default boolean contains(RutaScopeReference other){
//        PsiElement psiElement = other.rootOfScope();
//        while (psiElement!=null){
//            if(psiElement==this){
//                return true;
//            }
//            psiElement=psiElement.getParent();
//        }
//        return false;
//    }
//    PsiElement rootOfScope();
////    RutaScopePath getScopePath();
//}
