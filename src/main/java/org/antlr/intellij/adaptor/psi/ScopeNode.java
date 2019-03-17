package org.antlr.intellij.adaptor.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

public interface ScopeNode {

    PsiElement resolve(PsiReference reference);

}
