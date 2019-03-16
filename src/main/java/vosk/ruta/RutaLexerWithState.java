package vosk.ruta;

import org.antlr.intellij.adaptor.lexer.LexerException;
import org.antlr.intellij.adaptor.lexer.StateRecoverableLexer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
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

    /**
     * This is called by the lexer and the override allows the @{@link org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
     * to catch some silent errors
     * @param e
     */
    @Override
    public void reportError(RecognitionException e) {

        state.channel= Token.HIDDEN_CHANNEL;
        emit();
        throw new LexerException(e,state.token);
    }
}
