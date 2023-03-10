/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;

import net.sourceforge.jrefactory.parser.ChildrenVisitor;
import net.sourceforge.jrefactory.ast.ASTClassBody;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTInterfaceBody;
import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.parser.JavaParserTreeConstants;

/**
 *  Visitor that traverses an AST and removes a specified field
 *
 *@author    Chris Seguin
 */
public class RemoveFieldVisitor extends ChildrenVisitor {
	private String fieldName;
	private SimpleNode fieldDecl;


	/**
	 *  Constructor for RemoveFieldVisitor that specifies the field to remove
	 *
	 *@param  field  the name of the field
	 */
	public RemoveFieldVisitor(String field)
	{
		fieldName = field;
		fieldDecl = null;
	}


	/**
	 *  Gets the FieldDeclaration attribute of the RemoveFieldVisitor object
	 *
	 *@return    The FieldDeclaration value
	 */
	public SimpleNode getFieldDeclaration()
	{
		return fieldDecl;
	}


	/**
	 *  Visit a class body
	 *
	 *@param  node  the class body node
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTClassBody node, Object data)
	{
		Object result = removeField(node);
		if (result == null) {
			result = super.visit(node, data);
		}
		return result;
	}


	/**
	 *  Visit an interface body
	 *
	 *@param  node  the interface body node
	 *@param  data  data for the visitor
	 *@return       the field that was removed
	 */
	public Object visit(ASTInterfaceBody node, Object data)
	{
		Object result = removeField(node);
		if (result == null) {
			result = super.visit(node, data);
		}
		return result;
	}


	/**
	 *  Skip nested classes
	 *
	 *@param  node  the nested class
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTNestedClassDeclaration node, Object data)
	{
		return null;
	}


	/**
	 *  Skip nested interfaces
	 *
	 *@param  node  the nested interface
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTNestedInterfaceDeclaration node, Object data)
	{
		return null;
	}



	/**
	 *  Have we found the field declaration that we are going to move?
	 *
	 *@param  next  Description of Parameter
	 *@return       The Found value
	 */
	private boolean isFound(SimpleNode next)
	{
		if (!(next instanceof ASTFieldDeclaration)) {
			return false;
		}

		int loop = next.jjtGetNumChildren();
		for (int ndx = 1; ndx < loop; ndx++) {
			if (checkDeclaration(next, ndx)) {
				return true;
			}
		}

		return false;
	}


	/**
	 *  Determines if we are visiting a node that has multiple fields declared in
	 *  a single statement
	 *
	 *@param  field  Description of Parameter
	 *@return        The Multiple value
	 */
	private boolean isMultiple(SimpleNode field)
	{
		return (field.jjtGetNumChildren() > 2);
	}


	/**
	 *  Remove the field, if it is the correct one. Return the field declaration
	 *  that was removed
	 *
	 *@param  node  The node we are considering removing the field from
	 *@return       The field declaration
	 */
	private Object removeField(SimpleNode node)
	{
		int loop = node.jjtGetNumChildren();
		for (int ndx = 0; ndx < loop; ndx++) {
			SimpleNode next = (SimpleNode) node.jjtGetChild(ndx);
			SimpleNode possible = (SimpleNode) next.jjtGetFirstChild();
			if (isFound(possible)) {
				if (isMultiple(possible)) {
					removeMultiple((ASTFieldDeclaration) possible, next instanceof ASTClassBodyDeclaration);
				} else {
					removeSingle(node, next, ndx);
				}
				return next;
			}
		}
		return null;
	}


	/**
	 *  Removes a field declaration where only a single variable was declared
	 *
	 *@param  node  the class or interface body node
	 *@param  next  the container for the field declaration
	 *@param  ndx   the index of the node to delete
	 */
	private void removeSingle(SimpleNode node, SimpleNode next, int ndx)
	{
		fieldDecl = next;
		node.jjtDeleteChild(ndx);
	}


	/**
	 *  Removes a field that is declared as one of many
	 *
	 *@param  next     the field declaration
	 *@param  isClass  was this in a class or an interface
	 */
	private void removeMultiple(ASTFieldDeclaration next, boolean isClass)
	{
		if (isClass) {
			fieldDecl = new ASTClassBodyDeclaration(JavaParserTreeConstants.JJTCLASSBODYDECLARATION);
		} else {
			fieldDecl = new ASTInterfaceMemberDeclaration(JavaParserTreeConstants.JJTINTERFACEMEMBERDECLARATION);
		}

		//  Create the field declaration
		ASTFieldDeclaration afd = new ASTFieldDeclaration(JavaParserTreeConstants.JJTFIELDDECLARATION);
		fieldDecl.jjtInsertChild(afd, 0);

		//  Copy the type
		afd.jjtInsertChild(next.jjtGetFirstChild(), 0);

		//  Find the variable and remove it from the old and add it to the new
		int loop = next.jjtGetNumChildren();
		for (int ndx = 1; ndx < loop; ndx++) {
			if (checkDeclaration(next, ndx)) {
				afd.jjtInsertChild(next.jjtGetChild(ndx), 1);
				next.jjtDeleteChild(ndx);
				return;
			}
		}
	}


	/**
	 *  Checks a single variable declaration to see if it is the one we are
	 *  looking for
	 *
	 *@param  next   the field declaration that we are checking
	 *@param  index  the index of the id that we are checking
	 *@return        true if we have found the field
	 */
	private boolean checkDeclaration(SimpleNode next, int index)
	{
		ASTVariableDeclarator decl = (ASTVariableDeclarator) next.jjtGetChild(index);
		ASTVariableDeclaratorId id = (ASTVariableDeclaratorId) decl.jjtGetFirstChild();
		return (id.getName().equals(fieldName));
	}
}
