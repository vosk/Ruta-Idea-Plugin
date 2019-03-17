package vosk.ruta.psi.nodes;

import java.util.LinkedList;

public class RutaScopePath {
    public static final String RELATIVE="<relative>";
    private final LinkedList<String> path;
    private RutaScopePath(LinkedList<String> path){

        this.path = path;
    }

    public static Builder builder(){
        return new Builder();
    }

    public boolean equal(RutaScopePath scopePath) {
        return path.equals(scopePath.path);
    }

    public static class Builder{
        LinkedList<String> path=new LinkedList<>();

        public Builder prefix(String pref){
            path.addFirst(pref);
            return this;
        }
        public Builder postFix(String pref){
            path.addLast(pref);
            return this;
        }
        public RutaScopePath build(){
            return new RutaScopePath(path);
        }

    }
}
