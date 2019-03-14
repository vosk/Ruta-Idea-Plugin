package vosk.ruta;

import org.antlr.intellij.adaptor.lexer.StateRecoverableLexer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognizerSharedState;
import org.apache.uima.ruta.parser.debug.RutaLexer;

public class RutaLexerWithState extends RutaLexer implements StateRecoverableLexer {


    public RutaLexerWithState(CharStream input) {
        super(input);
    }

    public RutaLexerWithState(CharStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public RecognizerSharedState getState(){
        return state;
    }

    public void setState(RecognizerSharedState state){
        this.state=state;
    }
}
