import com.intellij.testFramework.ParsingTestCase;
import vosk.ruta.RutaParserDefinition;

public class MediumParsingTestCase extends ParsingTestCase {
    public MediumParsingTestCase() {
        super("", "ruta", new RutaParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "./src/test/resources/medium";
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
