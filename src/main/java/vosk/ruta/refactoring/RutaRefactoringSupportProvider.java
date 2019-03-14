// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package vosk.ruta.refactoring;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ilyas
 */
public class RutaRefactoringSupportProvider extends RefactoringSupportProvider {

    public static final RutaRefactoringSupportProvider INSTANCE = new RutaRefactoringSupportProvider();

    @Override
    public boolean isSafeDeleteAvailable(@NotNull PsiElement element) {
//        return element instanceof GrTypeDefinition ||
//                element instanceof GrField ||
//                element instanceof GrMethod;
        return false;
    }

    /**
     * @return handler for introducing local variables in Groovy
     */
//    @Override
//    @Nullable
//    public RefactoringActionHandler getIntroduceVariableHandler() {
//        return new GrIntroduceVariableHandler();
//    }
//
//    @Override
//    @Nullable
//    public RefactoringActionHandler getExtractMethodHandler() {
//        return new GroovyExtractMethodHandler();
//    }
//
//    @Override
//    public ChangeSignatureHandler getChangeSignatureHandler() {
//        return new GrChangeSignatureHandler();
//    }

    @Override
    public boolean isInplaceRenameAvailable(@NotNull PsiElement elementToRename, PsiElement nameSuggestionContext) {
        //local vars & params renames GrVariableInplaceRenameHandler
//
//        if (nameSuggestionContext != null && nameSuggestionContext.getContainingFile() != elementToRename.getContainingFile()) return false;
//        if (!(elementToRename instanceof GrLabeledStatement)) {
//            return false;
//        }
//        SearchScope useScope = PsiSearchHelper.getInstance(elementToRename.getProject()).getUseScope(elementToRename);
//        if (!(useScope instanceof LocalSearchScope)) return false;
//        PsiElement[] scopeElements = ((LocalSearchScope)useScope).getScope();
//        if (scopeElements.length > 1) {
//            return false;
//        }
//
//        PsiFile containingFile = elementToRename.getContainingFile();
//        return PsiTreeUtil.isAncestor(containingFile, scopeElements[0], false);
        return false;

    }

    @Override
    public boolean isInplaceIntroduceAvailable(@NotNull PsiElement element, PsiElement context) {
//        if (context == null || context.getContainingFile() != element.getContainingFile()) return false;
//        return true;
        return false;
    }

    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement element, @Nullable PsiElement context) {
//        if (context == null || context.getContainingFile() instanceof GroovyFile) return false;
//        PsiElement parent = context.getParent();
//
//        //don't try to inplace rename aliased imported references
//        if (parent instanceof GrReferenceElement) {
//            GroovyResolveResult result = ((GrReferenceElement)parent).advancedResolve();
//            PsiElement fileResolveContext = result.getCurrentFileResolveContext();
//            if (fileResolveContext instanceof GrImportStatement && ((GrImportStatement)fileResolveContext).isAliasedImport()) {
//                return false;
//            }
//        }
//        return element instanceof GrMember;
        return true;
    }

//    @Override
//    public RefactoringActionHandler getIntroduceFieldHandler() {
//        return new GrIntroduceFieldHandler();
//    }
//
//    @Override
//    public RefactoringActionHandler getIntroduceParameterHandler() {
//        return new GrIntroduceParameterHandler();
//    }
//
//    @Override
//    public RefactoringActionHandler getIntroduceConstantHandler() {
//        return new GrIntroduceConstantHandler();
//    }

//    @Nullable
//    @Override
//    public RefactoringActionHandler getPullUpHandler() {
////        return new JavaPullUpHandler();
//    }

}
