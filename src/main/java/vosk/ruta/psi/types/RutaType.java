package vosk.ruta.psi.types;

import com.intellij.psi.PsiElement;

public class RutaType {
    private final String parent;
    private final String name;
    private final PsiElement whereTofind;
    private final RutaTypeIndex features= new RutaTypeIndex();

    public RutaType(String parent, String name, PsiElement whereTofind) {
        this.parent = parent;
        this.name = name;
        this.whereTofind = whereTofind;
    }

    public String getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public RutaTypeIndex getFeatures() {
        return features;
    }

    public PsiElement getWhereTofind() {
        return whereTofind;
    }
}
