package vosk.ruta;

import org.apache.uima.ruta.parser.debug.RutaParser;

public final class RutaParserLogic {

    private RutaParserLogic() {

    }

    public static boolean tokenIsIdentifier(int antlrToken) {
        return antlrToken == RutaParser.Identifier;
    }

}
