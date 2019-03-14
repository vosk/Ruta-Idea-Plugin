import com.intellij.testFramework.ParsingTestCase;
import vosk.ruta.RutaParserDefinition;

public class UnterminatedStringParsingTestCase extends ParsingTestCase {
    public UnterminatedStringParsingTestCase() {
        super("", "ruta", new RutaParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "./src/test/resources/unterminatedString";
    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}
