import com.intellij.testFramework.ParsingTestCase;
import vosk.ruta.RutaParserDefinition;

public class BasicParsingTestCase extends ParsingTestCase {
    public BasicParsingTestCase() {
        super("", "ruta", new RutaParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "./src/test/resources/basic";
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
