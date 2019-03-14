package vosk.ruta;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.debug.DebugEventListener;
import org.apache.uima.ruta.parser.debug.RutaParser;

public class DebuggedRutaParser extends RutaParser {
    public DebuggedRutaParser(TokenStream input) {
        super(input);
    }

    public DebuggedRutaParser(TokenStream input, int port, RecognizerSharedState state) {
        super(input, port, state);
    }

    public DebuggedRutaParser(TokenStream input, DebugEventListener dbg) {
        super(input, dbg);
    }
    @Override
    public void emitErrorMessage(RecognitionException e) {
        dbg.recognitionException(e);
        super.emitErrorMessage(e);
    }
}
