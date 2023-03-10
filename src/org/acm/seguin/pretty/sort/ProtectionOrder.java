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
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ModifierHolder;


/**
 *  Orders the items in a class according to type.
 *
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    August 3, 1999
 */
public class ProtectionOrder extends Ordering {
	//  Instance variables
	private boolean publicFirst;

	private final static int PUBLIC = 1;
	private final static int PROTECTED = 2;
	private final static int PACKAGE = 3;
	private final static int PRIVATE = 4;



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
	public ProtectionOrder(String ordering) {
		publicFirst = ordering.equalsIgnoreCase("public");
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

		int protection = 0;

		//  Now that we have data, determine the type of data
		if (data instanceof ASTEnumDeclaration) {
			protection = getProtection((ASTEnumDeclaration) data);
		}
		else if (data instanceof ASTFieldDeclaration) {
			protection = getProtection((ASTFieldDeclaration) data);
		}
		else if (data instanceof ASTConstructorDeclaration) {
			protection = getProtection((ASTConstructorDeclaration) data);
		}
		else if (data instanceof ASTMethodDeclaration) {
			protection = getProtection((ASTMethodDeclaration) data);
		}
		else if (data instanceof ASTNestedInterfaceDeclaration) {
			protection = getProtection((ASTNestedInterfaceDeclaration) data);
		}
		else if (data instanceof ASTNestedClassDeclaration) {
			protection = getProtection((ASTNestedClassDeclaration) data);
		}
		else {
			return 100;
		}

		if (publicFirst) {
			return protection;
		}
		else {
			return -protection;
		}
	}


	/**
	 *  Gets the Protection attribute of the ProtectionOrder object
	 *
	 *@param  mods  Description of Parameter
	 *@return       The Protection value
	 */
	private int getProtection(ModifierHolder mods) {
		if (mods.isPrivate()) {
			return PRIVATE;
		}
		else if (mods.isProtected()) {
			return PROTECTED;
		}
		else if (mods.isPublic()) {
			return PUBLIC;
		}
		else {
			return PACKAGE;
		}
	}
}
