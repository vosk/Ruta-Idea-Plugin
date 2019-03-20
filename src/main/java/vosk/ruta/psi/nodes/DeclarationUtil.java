package vosk.ruta.psi.nodes;

import com.intellij.psi.PsiElement;
import vosk.ruta.psi.types.RutaType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public final class DeclarationUtil {
    private DeclarationUtil() {
    }

    public static Collection<RutaType> generateTypes(PsiElement node) {
        ArrayList<RutaType> types = new ArrayList<>();

        String baseType = null;
        PsiElement child = node.getFirstChild();

        return null;
    }

    private static void appendTypes(PsiElement child,
                                    Collection<RutaType> collection,
                                    Function<PsiElement, PsiElement> typeSelector,
                                    Predicate<PsiElement> termination) {
        while (child != null && !termination.test(child)) {
            
        }

    }
}
