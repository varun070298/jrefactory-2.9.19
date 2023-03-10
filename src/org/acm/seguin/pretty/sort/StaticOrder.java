/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;

import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTInitializer;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.SimpleNode;


/**
 *  Orders the items in a class according to type.
 *
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    August 3, 1999
 */
public class StaticOrder extends Ordering {
	//  Instance variables
	private boolean staticFirst;


	/**
	 *  Constructor for the StaticOrder object <P>
	 *
	 *  The string should either be "instance", "static", or "class". "instance"
	 *  means that instance variables should go first. Either of the other two
	 *  ordering strings indicate that the class variables or methods should go
	 *  first.
	 *
	 *@param  ordering  A user specified string that describes the order.
	 */
	public StaticOrder(String ordering) {
		char ch = ordering.trim().charAt(0);
		staticFirst = ((ch == 'c') || (ch == 'C') ||
				(ch == 's') || (ch == 'S'));
	}


	/**
	 *  Return the index of the item in the order array
	 *
	 *@param  object  the object we are checking
	 *@return         the objects index if it is found or 7 if it is not
	 */
	protected int getIndex(Object object) {
		Object data = ((SimpleNode) object).jjtGetFirstChild();
		if (data instanceof ASTClassBodyDeclaration) {
			data = ((ASTClassBodyDeclaration) data).jjtGetFirstChild();
		}
		else if (data instanceof ASTInterfaceMemberDeclaration) {
			data = ((ASTInterfaceMemberDeclaration) data).jjtGetFirstChild();
		}

		boolean currentIsStatic = false;

		//  Now that we have data, determine the type of data
		if (data instanceof ASTFieldDeclaration) {
			currentIsStatic = ((ASTFieldDeclaration) data).isStatic();
		}
		else if (data instanceof ASTEnumDeclaration) {
			currentIsStatic = ((ASTEnumDeclaration) data).isStatic();
		}
		else if (data instanceof ASTConstructorDeclaration) {
			currentIsStatic = false;
		}
		else if (data instanceof ASTMethodDeclaration) {
			currentIsStatic = ((ASTMethodDeclaration) data).isStatic();
		}
		else if (data instanceof ASTNestedInterfaceDeclaration) {
			currentIsStatic = ((ASTNestedInterfaceDeclaration) data).isStatic();
		}
		else if (data instanceof ASTNestedClassDeclaration) {
			currentIsStatic = ((ASTNestedClassDeclaration) data).isStatic();
		}
		else if (data instanceof ASTInitializer) {
			currentIsStatic = ((ASTInitializer) data).isUsingStatic();
		}
		else {
			return 2;
		}

		int result = 0;
		if (currentIsStatic) {
			if (staticFirst) {
				result = 0;
			}
			else {
				result = 1;
			}
		}
		else {
			if (staticFirst) {
				result = 1;
			}
			else {
				result = 0;
			}
		}

		return result;
	}
}
