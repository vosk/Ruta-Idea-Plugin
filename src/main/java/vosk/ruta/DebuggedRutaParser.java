package vosk.ruta;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.debug.DebugEventListener;
import org.apache.uima.ruta.parser.debug.RutaParser;

/**
 * This extension of the Parser class allows to catch and annotate some parse errors
 */
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

    /**
     * The super method throws exceptions, we notify the {@link DebugEventListener}.
     * @param e exception the parser encountered.
     */
    @Override
    public void emitErrorMessage(RecognitionException e) {
        dbg.recognitionException(e);
        state.failed=false; //this is really important for skip-retry recovery
        super.emitErrorMessage(e);
    }
}
