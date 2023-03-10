/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;

import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTLiteral;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.Node;


/**
 *  Orders the items in a class according to dependencies for final static constants.
 *
 *@author     Mike Atkinson
 *@created    Jun 19, 2003
 *@since      JRefactory 2.7.00
 */
public class FixupFinalStaticOrder extends Ordering {

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
	public FixupFinalStaticOrder() {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  obj1  Description of Parameter
	 *@param  obj2  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public int compare(Object obj1, Object obj2)
	{
		boolean obj1IsStatic = false;
		boolean obj1IsFinal  = false;
		boolean obj2IsStatic = false;
		boolean obj2IsFinal  = false;

                // only process obj1 if it is a final static Field Declaration.
		Object data = ((SimpleNode) obj1).jjtGetFirstChild();
		if (data instanceof ASTClassBodyDeclaration) {
			data = ((ASTClassBodyDeclaration) data).jjtGetFirstChild();
		} else if (data instanceof ASTInterfaceMemberDeclaration) {
			data = ((ASTInterfaceMemberDeclaration) data).jjtGetFirstChild();
		}

		//  Now that we have data, determine the type of data
		if (data instanceof ASTFieldDeclaration) {
			obj1IsStatic = ((ASTFieldDeclaration) data).isStatic();
			obj1IsFinal = ((ASTFieldDeclaration) data).isFinal();
		} else {
			return 0;
		}
                
      if (!obj1IsStatic || !obj1IsFinal) {
         return 0;
      }
      ASTFieldDeclaration field1 = (ASTFieldDeclaration) data;
      int childNo = field1.skipAnnotations();
      ASTVariableDeclarator declar1 = (ASTVariableDeclarator) field1.jjtGetChild(childNo+1);
      String name1 = ((ASTVariableDeclaratorId) declar1.jjtGetFirstChild()).getName();

      // only process obj2 if it is a final static Field Declaration.
		data = ((SimpleNode) obj2).jjtGetFirstChild();
		if (data instanceof ASTClassBodyDeclaration) {
			data = ((ASTClassBodyDeclaration) data).jjtGetFirstChild();
		} else if (data instanceof ASTInterfaceMemberDeclaration) {
			data = ((ASTInterfaceMemberDeclaration) data).jjtGetFirstChild();
		}


		//  Now that we have data, determine the type of data
		if (data instanceof ASTFieldDeclaration) {
			obj2IsStatic = ((ASTFieldDeclaration) data).isStatic();
			obj2IsFinal = ((ASTFieldDeclaration) data).isFinal();
		} else {
			return 0;
		}
                
      if (!obj2IsStatic || !obj2IsFinal) {
         return 0;
      }
      
      ASTFieldDeclaration field2 = (ASTFieldDeclaration) data;
      childNo = field2.skipAnnotations();
      ASTVariableDeclarator declar2 = (ASTVariableDeclarator) field2.jjtGetChild(childNo+1);
      String name2 = ((ASTVariableDeclaratorId) declar2.jjtGetFirstChild()).getName();
      
      // search for name1 in elements of obj2
      for (int i=1; i<declar2.jjtGetNumChildren(); i++) {
         Node node = declar2.jjtGetChild(i);
         if (contains(node, name1)) {
            return -1;
         }
      }
      
      // search for name2 in elements of obj1
      for (int i=1; i<declar1.jjtGetNumChildren(); i++) {
         Node node = declar1.jjtGetChild(i);
         if (contains(node, name2)) {
            return 1;
         }
      }

		return 0;
	}

        private boolean contains(Node node, String name) {
            if (node instanceof ASTLiteral) {
                if (name.equals(((ASTLiteral)node).getName())) {
                    return true;
                }
            } else if (node instanceof ASTName) {
                if (name.equals(((ASTName)node).getName())) {
                    return true;
                }
            }
            for (int i=0; i<node.jjtGetNumChildren(); i++) {
                Node childNode = node.jjtGetChild(i);
                if (contains(childNode, name)) {
                    return true;
                }
            }

                return false;
        }
	/**
	 *  Return the index of the item in the order array
	 *
	 *@param  object  the object we are checking
	 *@return         the objects index if it is found or 7 if it is not
	 */
	protected int getIndex(Object object) {
            return 0;
	}
        
        
}
