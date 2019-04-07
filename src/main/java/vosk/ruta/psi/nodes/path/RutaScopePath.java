package vosk.ruta.psi.nodes.path;

import java.util.LinkedList;

/**
 * Holder for a qualified name, or path to a file, a file element etc
 * the file extension for files is omitted.
 */
public class RutaScopePath {
    public static final String RELATIVE = "<relative>";

    public enum TYPE {FILE, PACKAGE}

    private final LinkedList<String> path;

    private final TYPE type;

    private RutaScopePath(LinkedList<String> path, TYPE type) {

        this.path = path;
        this.type = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean equal(RutaScopePath scopePath) {
        return path.equals(scopePath.path);
    }

    public static class Builder {
        LinkedList<String> path = new LinkedList<>();

        public Builder prefix(String pref) {
            path.addFirst(pref);
            return this;
        }

        public Builder postFix(String pref) {
            path.addLast(pref);
            return this;
        }

        public RutaScopePath build(TYPE type) {
            return new RutaScopePath(path, type);
        }

    }

    public LinkedList<String> breadCrumbs() {
        return path;
    }

    public RutaScopePath getPackagePath() {
        if (this.type == TYPE.PACKAGE)
            return this;
        LinkedList<String> newPath =  new LinkedList<>(path);
        newPath.removeLast();
        return new RutaScopePath(newPath, TYPE.FILE);
    }

    public TYPE getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.join(".", path);
    }
}
