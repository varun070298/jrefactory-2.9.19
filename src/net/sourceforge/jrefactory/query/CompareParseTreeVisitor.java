/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package net.sourceforge.jrefactory.query;

import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTAdditiveExpression;
import net.sourceforge.jrefactory.ast.ASTAllocationExpression;
import net.sourceforge.jrefactory.ast.ASTAndExpression;
import net.sourceforge.jrefactory.ast.ASTArgumentList;
import net.sourceforge.jrefactory.ast.ASTArguments;
import net.sourceforge.jrefactory.ast.ASTArrayDimsAndInits;
import net.sourceforge.jrefactory.ast.ASTArrayInitializer;
import net.sourceforge.jrefactory.ast.ASTAssertionStatement;
import net.sourceforge.jrefactory.ast.ASTAssignmentOperator;
import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.ASTBlockStatement;
import net.sourceforge.jrefactory.ast.ASTBooleanLiteral;
import net.sourceforge.jrefactory.ast.ASTBreakStatement;
import net.sourceforge.jrefactory.ast.ASTCastExpression;
import net.sourceforge.jrefactory.ast.ASTClassBody;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTConditionalAndExpression;
import net.sourceforge.jrefactory.ast.ASTConditionalExpression;
import net.sourceforge.jrefactory.ast.ASTConditionalOrExpression;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTContinueStatement;
import net.sourceforge.jrefactory.ast.ASTDoStatement;
import net.sourceforge.jrefactory.ast.ASTEmptyStatement;
import net.sourceforge.jrefactory.ast.ASTEqualityExpression;
import net.sourceforge.jrefactory.ast.ASTExclusiveOrExpression;
import net.sourceforge.jrefactory.ast.ASTExplicitConstructorInvocation;
import net.sourceforge.jrefactory.ast.ASTExpression;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTForInit;
import net.sourceforge.jrefactory.ast.ASTForStatement;
import net.sourceforge.jrefactory.ast.ASTForUpdate;
import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTFormalParameters;
import net.sourceforge.jrefactory.ast.ASTIfStatement;
import net.sourceforge.jrefactory.ast.ASTImportDeclaration;
import net.sourceforge.jrefactory.ast.ASTInclusiveOrExpression;
import net.sourceforge.jrefactory.ast.ASTInitializer;
import net.sourceforge.jrefactory.ast.ASTInstanceOfExpression;
import net.sourceforge.jrefactory.ast.ASTInterfaceBody;
import net.sourceforge.jrefactory.ast.ASTInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTLabeledStatement;
import net.sourceforge.jrefactory.ast.ASTLiteral;
import net.sourceforge.jrefactory.ast.ASTLocalVariableDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTMultiplicativeExpression;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTNameList;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTNullLiteral;
import net.sourceforge.jrefactory.ast.ASTPackageDeclaration;
import net.sourceforge.jrefactory.ast.ASTPostfixExpression;
import net.sourceforge.jrefactory.ast.ASTPreDecrementExpression;
import net.sourceforge.jrefactory.ast.ASTPreIncrementExpression;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.ASTPrimaryPrefix;
import net.sourceforge.jrefactory.ast.ASTPrimarySuffix;
import net.sourceforge.jrefactory.ast.ASTPrimitiveType;
import net.sourceforge.jrefactory.ast.ASTRelationalExpression;
import net.sourceforge.jrefactory.ast.ASTResultType;
import net.sourceforge.jrefactory.ast.ASTReturnStatement;
import net.sourceforge.jrefactory.ast.ASTShiftExpression;
import net.sourceforge.jrefactory.ast.ASTStatement;
import net.sourceforge.jrefactory.ast.ASTStatementExpression;
import net.sourceforge.jrefactory.ast.ASTStatementExpressionList;
import net.sourceforge.jrefactory.ast.ASTSwitchLabel;
import net.sourceforge.jrefactory.ast.ASTSwitchStatement;
import net.sourceforge.jrefactory.ast.ASTSynchronizedStatement;
import net.sourceforge.jrefactory.ast.ASTThrowStatement;
import net.sourceforge.jrefactory.ast.ASTTryStatement;
import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnaryExpression;
import net.sourceforge.jrefactory.ast.ASTUnaryExpressionNotPlusMinus;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.ASTVariableInitializer;
import net.sourceforge.jrefactory.ast.ASTWhileStatement;
import net.sourceforge.jrefactory.ast.SimpleNode;

import net.sourceforge.jrefactory.ast.ASTTypeParameterList;
import net.sourceforge.jrefactory.ast.ASTTypeParameter;
import net.sourceforge.jrefactory.ast.ASTTypeArguments;
import net.sourceforge.jrefactory.ast.ASTReferenceTypeList;
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.ast.ASTActualTypeArgument;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTGenericNameList;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumElement;
import net.sourceforge.jrefactory.ast.ASTIdentifier;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.ASTMemberValuePairs;
import net.sourceforge.jrefactory.ast.ASTMemberValuePair;
import net.sourceforge.jrefactory.ast.ASTMemberValue;
import net.sourceforge.jrefactory.ast.ASTMemberValueArrayInitializer;
import net.sourceforge.jrefactory.ast.ASTAnnotationTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotationTypeMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotationMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstantDeclaration;
import net.sourceforge.jrefactory.ast.ASTJSPBody;

import net.sourceforge.jrefactory.parser.JavaParserVisitor;


/**
 *  This object allows the user to compare two parse trees. The most basic
 *  instance of this type, it only checks that the types are the same and that
 *  the number of children are the same.<P>
 *
 *  <B>Usage:</B> <BR>
 *  <TT>Boolean result = (Boolean) node.jjtAccept(new
 *  CompareParseTreeVisitor(), other);</TT> <BR>
 *
 *
 *@author    Chris Seguin
 *@author    Mike Atkinson
 */
class CompareParseTreeVisitor implements JavaParserVisitor {
	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(SimpleNode node, Object data)
	{
		System.out.println("You should not be executing this code!");
		return Boolean.FALSE;
	}

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
   public Object visit(ASTJSPBody node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTTypeParameterList node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTTypeParameter node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTTypeArguments node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTReferenceTypeList node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTReferenceType node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTClassOrInterfaceType node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTActualTypeArgument node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTTypeParameters node, Object data) {
		return defaultComparison(node, data);
        }  

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTGenericNameList node, Object data) {
		return defaultComparison(node, data);
        }  


	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTEnumDeclaration node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTEnumElement node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
	 *
         *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
         *@since        JRefactory 2.7.00
         */
        public Object visit(ASTIdentifier node, Object data) {
		return defaultComparison(node, data);
        }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTAnnotationTypeDeclaration node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTAnnotationTypeMemberDeclaration node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTAnnotationMethodDeclaration node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTConstantDeclaration node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTAnnotation node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTMemberValuePairs node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTMemberValuePair node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTMemberValue node, Object data) {
		return defaultComparison(node, data);
   }

	/**
	 *  Visit a node, comparing it with that supplied in data.
    *
    *  It used the defaultComparison() method to do the comparison.
	 *  
	 *@param  node  The node we are visiting
	 *@param  data  The simple node to compare
	 *@return       Boolean.TRUE or Boolean.FALSE
    *@since        JRefactory 2.7.00
    */
    public Object visit(ASTMemberValueArrayInitializer node, Object data) {
		return defaultComparison(node, data);
   }



	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTCompilationUnit node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPackageDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTImportDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTTypeDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTClassDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTUnmodifiedClassDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTClassBody node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTNestedClassDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTClassBodyDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTInterfaceDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTNestedInterfaceDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTUnmodifiedInterfaceDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTInterfaceBody node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTInterfaceMemberDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTFieldDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTVariableDeclarator node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTVariableDeclaratorId node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTVariableInitializer node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTArrayInitializer node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTMethodDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTMethodDeclarator node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTFormalParameters node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTFormalParameter node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTConstructorDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTExplicitConstructorInvocation node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTInitializer node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTType node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPrimitiveType node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTResultType node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTName node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTNameList node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTAssignmentOperator node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTConditionalExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTConditionalOrExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTConditionalAndExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTInclusiveOrExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTExclusiveOrExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTAndExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTEqualityExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTInstanceOfExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTRelationalExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTShiftExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTAdditiveExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTMultiplicativeExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTUnaryExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPreIncrementExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPreDecrementExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPostfixExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTCastExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPrimaryExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPrimaryPrefix node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPrimarySuffix node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTLiteral node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTBooleanLiteral node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTNullLiteral node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTArguments node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTArgumentList node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTAllocationExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTArrayDimsAndInits node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTLabeledStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTBlock node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTBlockStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTLocalVariableDeclaration node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTEmptyStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTStatementExpression node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTSwitchStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTSwitchLabel node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTIfStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTWhileStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTDoStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTForStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTForInit node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTStatementExpressionList node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTForUpdate node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTBreakStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTContinueStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTReturnStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTThrowStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTSynchronizedStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTTryStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  Visit the assertion node
	 *
	 *@param  node  the node
	 *@param  data  the data needed to perform the visit
	 *@return       the result of visiting the node
	 */
	public Object visit(ASTAssertionStatement node, Object data)
	{
		return defaultComparison(node, data);
	}


	/**
	 *  This method is the default comparison function
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Boolean defaultComparison(SimpleNode node, Object data)
	{
		//System.out.println("Comparing:  " + node.getClass().getName() + " to " + data.getClass().getName());

		if (node.getClass() != data.getClass()) {
			//System.out.println("Different classes");
			return Boolean.FALSE;
		}

		SimpleNode other = (SimpleNode) data;
		if (other.jjtGetNumChildren() != node.jjtGetNumChildren()) {
			//System.out.println("Different lengths");
			return Boolean.FALSE;
		}

		int last = node.jjtGetNumChildren();
		for (int ndx = 0; ndx < last; ndx++) {
			Node nextNodeChild = node.jjtGetChild(ndx);
			Node nextOtherChild = other.jjtGetChild(ndx);

			Object result = nextNodeChild.jjtAccept(this, nextOtherChild);
			if (result.equals(Boolean.FALSE)) {
				//System.out.println("Different child #" + ndx + "   " + node.toString() + "/" + node.getClass().getName() + " to " + data.toString() + "/" + data.getClass().getName());
				return Boolean.FALSE;
			}
		}

		//System.out.println("Match");
		return Boolean.TRUE;
	}
}

