import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

public class RutaCodeInsightTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() { return "./src/test/resources/codeInsight"; }

//    public void testCompletion() {
//        myFixture.configureByFiles("CompleteTestData.java", "DefaultTestData.simple");
//        myFixture.complete(CompletionType.BASIC, 1);
//        List<String> strings = myFixture.getLookupElementStrings();
//        assertTrue(strings.containsAll(Arrays.asList("key with spaces", "language", "message", "tab", "website")));
//        assertEquals(5, strings.size());
//    }
//
//    public void testAnnotator() {
//        myFixture.configureByFiles("AnnotatorTestData.java", "DefaultTestData.simple");
//        myFixture.checkHighlighting(false, false, true, true);
//    }
//
//    public void testFormatter() {
//        myFixture.configureByFiles("FormatterTestData.simple");
//        CodeStyle.getLanguageSettings(myFixture.getFile()).SPACE_AROUND_ASSIGNMENT_OPERATORS = true;
//        CodeStyle.getLanguageSettings(myFixture.getFile()).KEEP_BLANK_LINES_IN_CODE = 2;
//        WriteCommandAction.writeCommandAction(getProject()).run(() -> {
//            CodeStyleManager.getInstance(getProject()).reformatText(myFixture.getFile(),
//                    ContainerUtil.newArrayList(myFixture.getFile().getTextRange()));
//        });
//        myFixture.checkResultByFile("DefaultTestData.simple");
//    }

    public void testRename() {
        myFixture.configureByFiles( "renameBefore.ruta");
        myFixture.renameElementAtCaret("declRenamed");
        myFixture.checkResultByFile("renameBefore.ruta", "renameAfter.ruta", false);
    }

//    public void testFolding() {
//        myFixture.configureByFiles("DefaultTestData.simple");
//        myFixture.testFolding(getTestDataPath() + "/FoldingTestData.java");
//    }

//    public void testFindUsages() {
//        Collection<UsageInfo> usageInfos = myFixture.testFindUsages("FindUsagesTestData.simple", "FindUsagesTestData.java");
//        assertEquals(1, usageInfos.size());
//    }

//    public void testCommenter() {
//        myFixture.configureByText(SimpleFileType.INSTANCE, "<caret>website = http://en.wikipedia.org/");
//        CommentByLineCommentAction commentAction = new CommentByLineCommentAction();
//        commentAction.actionPerformedImpl(getProject(), myFixture.getEditor());
//        myFixture.checkResult("#website = http://en.wikipedia.org/");
//        commentAction.actionPerformedImpl(getProject(), myFixture.getEditor());
//        myFixture.checkResult("website = http://en.wikipedia.org/");
//    }
//
//    public void testReference() {
//        myFixture.configureByFiles("ReferenceTestData.java", "DefaultTestData.simple");
//        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
//        assertEquals("http://en.wikipedia.org/", ((SimpleProperty) element.getReferences()[0].resolve()).getValue());
//    }
}