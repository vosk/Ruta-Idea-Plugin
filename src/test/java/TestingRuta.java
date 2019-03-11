import com.google.common.io.Resources;
import org.antlr.runtime.*;
import org.antlr.runtime.debug.ParseTreeBuilder;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;
import org.apache.uima.fit.internal.ResourceManagerFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.ruta.RutaModule;
import org.apache.uima.ruta.RutaScriptFactory;
import org.apache.uima.ruta.TypeUsageInformation;
import org.apache.uima.ruta.action.ActionFactory;
import org.apache.uima.ruta.condition.ConditionFactory;
import org.apache.uima.ruta.engine.RutaEngine;
import org.apache.uima.ruta.expression.ExpressionFactory;
import org.apache.uima.ruta.extensions.RutaExternalFactory;
import org.apache.uima.ruta.parser.debug.RutaLexer;
import org.apache.uima.ruta.parser.debug.RutaParser;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestingRuta {
    static final TreeAdaptor adaptor = new CommonTreeAdaptor() {
        public Object create(Token payload) {
            return new CommonTree(payload);
        }
    };
    public static class Basic extends RutaEngine {
        public RutaModule load(String str) throws RecognitionException {
            return super.loadScriptByString(str);
        }
    }

    @Test
    public void RunEngine() throws IOException, RecognitionException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ResourceInitializationException {
//        Basic engine = new Basic();
//
//
//        Method method = engine.getClass().getDeclaredMethod("createParser");
//        method.setAccessible(true);



        URL url = Resources.getResource("basic/ParsingTestData.ruta");
        String text = Resources.toString(url, StandardCharsets.UTF_8);
        CharStream st = new ANTLRStringStream(text);
        RutaLexer lexer = new RutaLexer(st);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ParseTreeBuilder builder = new ParseTreeBuilder("prog");


        RutaParser parser = new RutaParser(tokens,builder);

        TypeUsageInformation typeUsageInformation = new TypeUsageInformation();
        ActionFactory actionFactory = new ActionFactory(typeUsageInformation);
        ConditionFactory conditionFactory = new ConditionFactory(typeUsageInformation);
        ExpressionFactory expressionFactory = new ExpressionFactory(typeUsageInformation);
        RutaScriptFactory scriptFactory = new RutaScriptFactory(expressionFactory,
                typeUsageInformation);
        scriptFactory.setContext(null);

        parser.setScriptFactory(scriptFactory);
        parser.setExpressionFactory(expressionFactory);
        parser.setActionFactory(actionFactory);
        parser.setConditionFactory(conditionFactory);
        parser.setExternalFactory(new RutaExternalFactory());
        parser.setContext(null);
        parser.setResourcePaths(new String[]{});
        parser.setResourceManager( ResourceManagerFactory.newResourceManager());

        RutaModule script =parser.file_input("test");
        System.out.println(script);
        System.out.println(builder.getTree());

    }
}
