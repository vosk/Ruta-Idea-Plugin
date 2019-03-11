package vosk.ruta;

import com.intellij.lang.Language;

public class RutaLanguage extends Language {
    public static final RutaLanguage INSTANCE = new RutaLanguage();

    private RutaLanguage() {
        super("Ruta");
    }
}
