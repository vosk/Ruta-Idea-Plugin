package org.antlr.intellij.adaptor.bridge;

import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;

import java.util.ArrayDeque;
import java.util.Deque;

/** Make a PsiBuilder look like a source of ANTLR tokens. PsiBuilder
 *  Quick primer: the source can be advanced, rewound or peek-ed
 *  Handles marking and backtracking just fine, consume maps to advanceLexer()
 */
public class PsiBuilderAsTokenStream implements TokenStream {
    protected PsiBuilder builder;
//TODO No token factory in ANTLR3
    //    protected TokenFactory tokenFactory = CommonTokenFactory.DEFAULT;

    Deque<Integer> markStack = new ArrayDeque<>();
    public PsiBuilderAsTokenStream(PsiBuilder builder) {
        this.builder = builder;
        markStack.push(0);
    }

    @Override
    public Token LT(int k) {
        Token t=null;
        int idx=0;
        for(int i=0;i<k;i++){
            do {
                t=lookAhead(markStack.peek()+idx);
                idx++;
            }
            while(t.getChannel()!=Token.DEFAULT_CHANNEL);

        }

        return t;
    }

    @Override
    public int range() {
        throw new UnsupportedOperationException("psibuilder");
//        return 0;
    }

    @Override
    public Token get(int i) {
        return lookAhead(i);
//        return null;
    }

    @Override
    public TokenSource getTokenSource() {
        throw new UnsupportedOperationException("psibuilder");
//        return null;
    }

    @Override
    public String toString(int start, int stop) {
        throw new UnsupportedOperationException("psibuilder");
//        return null;
    }

    @Override
    public String toString(Token start, Token stop) {
        throw new UnsupportedOperationException("psibuilder");
//        return null;
    }

    @Override
    public void consume() {

        if(markStack.size()==1){
            CommonToken t;
            do {
                t=lookAhead(0);
                builder.advanceLexer();
            }
            while(t.getChannel()!=Token.DEFAULT_CHANNEL);
            return;
        }
        else{
            CommonToken t;
            do {
                t=lookAhead(markStack.peek());
                markStack.push(markStack.pop()+1);
            }
            while(t.getChannel()!=Token.DEFAULT_CHANNEL);
        }

    }

    @Override
    public int LA(int i) {
        return LT(i).getType();
    }

    @Override
    public int mark() {
        markStack.push(markStack.peek());//New Stack
        return markStack.size();
    }

    @Override
    public int index() {
        return markStack.peek();
    }

    @Override
    public void rewind(int marker) {
        while(markStack.size()>=marker){
            markStack.pop();
        }
    }

    @Override
    public void rewind() {
        rewind(markStack.size());
        mark();
    }

    @Override
    public void release(int marker) {
        throw new UnsupportedOperationException("psibuilder");
    }

    @Override
    public void seek(int index) {
        if(markStack.size()==1)
            throw new UnsupportedOperationException("psibuilder");
        markStack.pop();
        markStack.push(index-1);
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("psibuilder");
    }

    @Override
    public String getSourceName() {
        return builder.getProject().getProjectFile().getName();
//        return null;
    }

    private CommonToken lookAhead(int i){
        ProgressIndicatorProvider.checkCanceled();
        TokenIElementType  ideaTType = (TokenIElementType) builder.lookAhead(i-1);

        int type = ideaTType!=null ? ideaTType.getANTLRTokenType() : Token.EOF;

        int channel =ideaTType!= null ? ideaTType.getChannel():Token.DEFAULT_CHANNEL;
        String text =  ideaTType != null ? ideaTType.toString(): "";

        int start = 0;

        int length = 0; // text != null ? text.length() : 0;
        int stop = 0 ; //start + length - 1;
        // PsiBuilder doesn't provide line, column info
        int line = 0;
        int charPositionInLine = 0;
        //TODO input: null?
//        Token t = new CommonToken(null,type, channel, start, stop);
        CommonToken t = new CommonToken(type,text);
        t.setChannel(channel);
        t.setStartIndex(start);
        t.setStopIndex(stop);
//        builder.advanceLexer();
//		System.out.println("TOKEN: "+t);
        return t;
    }
}
