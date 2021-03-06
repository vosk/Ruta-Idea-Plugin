package vosk.ruta.psi.nodes;


import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.intellij.adaptor.psi.ANTLRPsiLeafNode;
import org.antlr.intellij.adaptor.psi.PsiElementFactory;
import org.antlr.intellij.adaptor.psi.Trees;
import org.antlr.runtime.Token;
import org.apache.uima.ruta.parser.debug.RutaParser;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vosk.ruta.RutaLanguage;
import vosk.ruta.psi.reference.RutaIdentiferReference;

/** From doc: "Every element which can be renamed or referenced
 *             needs to implement com.intellij.psi.PsiNamedElement interface."
 *
 *  So, all leaf nodes that represent variables, functions, classes, or
 *  whatever in your plugin language must be instances of this not just
 *  LeafPsiElement.  Your ASTFactory should create this kind of object for
 *  ID tokens. This node is for references *and* definitions because you can
 *  highlight a function and say "jump to definition". Also we want defs
 *  to be included in "find usages." Besides, there is no context information
 *  in the AST factory with which you could decide whether this node
 *  is a definition or a reference.
 *
 *  PsiNameIdentifierOwner (vs PsiNamedElement) implementations are the
 *  corresponding subtree roots that define symbols.
 *
 *  You can click on an ID in the editor and ask for a codeInsight for any node
 *  of this type.
 */
public class RutaIdentifierPsiNode extends ANTLRPsiLeafNode implements PsiNameIdentifierOwner {
	public RutaIdentifierPsiNode(IElementType type, CharSequence text) {
		super(type, text);
	}

	@Override
	public String getName() {
		return getText();
	}

	/** Alter this node to have text specified by the argument. Do this by
	 *  creating a new node through parsing of an ID and then doing a
	 *  replace.
	 */
	@Override
	public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
		if ( getParent()==null ) return this; // weird but it happened once
		/*
		IElementType elType = getParent().getNode().getElementType();
		String kind = "??? ";
		if ( elType instanceof RuleIElementType ) {
			int ruleIndex = ((RuleIElementType) elType).getRuleIndex();
			if ( ruleIndex == RULE_call_expr ) {
				kind = "call ";
			}
			else if ( ruleIndex == RULE_statement ) {
				kind = "assign ";
			}
			else if ( ruleIndex == RULE_function ) {
				kind = "func def ";
			}
		}
		System.out.println("RutaIdentifierPsiNode.setName("+name+") on "+
			                   kind+this+" at "+Integer.toHexString(this.hashCode()));
		*/
		//TODO this doesnt work
		IElementType type = PsiElementFactory.get(RutaLanguage.INSTANCE).getToken(RutaParser.Identifier, Token.DEFAULT_CHANNEL);

		PsiElement newID = Trees.createLeafFromText(getProject(),
		                                            RutaLanguage.INSTANCE,
		                                            getContext(),
		                                            name,
													type);
		if ( newID!=null ) {
			return this.replace(newID); // use replace on leaves but replaceChild on ID nodes that are part of defs/decls.
		}
		return this;
//		return null;
	}

	/** Create and return a PsiReference object associated with this ID
	 *  node. The reference object will be asked to resolve this ref
	 *  by using the text of this node to identify the appropriate definition
	 *  site. The definition site is typically a subtree for a function
	 *  or variable definition whereas this reference is just to this ID
	 *  leaf node.
	 *
	 *  As the AST factory has no context and cannot create different kinds
	 *  of PsiNamedElement nodes according to context, every ID node
	 *  in the tree will be of this type. So, we distinguish references
	 *  from definitions or other uses by looking at context in this method
	 *  as we have parent (context) information.
	 */
	@Override
	public PsiReference getReference() {
//		PsiReference reference = getParent().getReference();
//		if(reference instanceof RutaScopedNode){
//			((RutaScopedNode) reference).setSubPathUpTo(this);
//		}
//		return reference;
		return new RutaIdentiferReference(this);
	}


	@Nullable
	@Override
	public PsiElement getNameIdentifier() {
		return this;
	}

	@Nullable
	@Override
	public PsiElement getIdentifyingElement() {
		return this;
	}
}
