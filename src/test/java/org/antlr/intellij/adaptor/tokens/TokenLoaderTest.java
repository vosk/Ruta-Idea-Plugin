package org.antlr.intellij.adaptor.tokens;

import org.junit.Test;
import vosk.ruta.RutaLanguage;

import java.io.IOException;

public class TokenLoaderTest {

    @Test
    public void loadRutaTokens() throws IOException {
        TokenLoader loader = new TokenLoader();
        loader.load(RutaLanguage.INSTANCE,this.getClass().getResourceAsStream("/ruta/antlr/RutaParser.tokens"));
    }

}