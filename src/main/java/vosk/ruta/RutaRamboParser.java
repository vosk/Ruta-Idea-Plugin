package vosk.ruta;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.debug.DebugEventListener;
import org.apache.uima.ruta.parser.debug.RutaParser;

public class RutaRamboParser extends RutaParser {
    public RutaRamboParser(TokenStream input) {
        super(input);
    }

    public RutaRamboParser(TokenStream input, int port, RecognizerSharedState state) {
        super(input, port, state);
    }

    public RutaRamboParser(TokenStream input, DebugEventListener dbg) {
        super(input, dbg);
    }
    @Override
    public void emitErrorMessage(RecognitionException e) {
        dbg.recognitionException(e);
        super.emitErrorMessage(e);
    }
}
