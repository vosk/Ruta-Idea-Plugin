package vosk.ruta;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class RutaIcons {
    public static Icon FILE;

    static {
        try {
            FILE = IconLoader.getIcon("/icon/uima.png");
        } catch (Exception e) {

        }

    }
}
