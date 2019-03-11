import com.intellij.testFramework.ParsingTestCase;
import vosk.ruta.RutaParserDefinition;

public class ErrorParsingTestCase extends ParsingTestCase {
    public ErrorParsingTestCase() {
        super("", "ruta", new RutaParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "./src/test/resources/error";
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
