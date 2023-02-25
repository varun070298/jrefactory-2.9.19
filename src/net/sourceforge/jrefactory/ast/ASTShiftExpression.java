/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package net.sourceforge.jrefactory.ast;

import java.util.Enumeration;
import java.util.Vector;
import net.sourceforge.jrefactory.parser.JavaParser;
import net.sourceforge.jrefactory.parser.JavaParserVisitor;


/**
 *  Performs bitwise shift operations
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class ASTShiftExpression extends SimpleNode {
   private Vector names;


   /**
    *  Constructor for the ASTShiftExpression node.
    *
    * @param  identifier  Description of Parameter
    */
   public ASTShiftExpression(int identifier) {
      super(identifier);
      names = new Vector();
   }


   /**
    *  Constructor for the ASTShiftExpression node.
    *
    * @param  parser      The JavaParser that created this ASTShiftExpression node.
    * @param  identifier  The id of this node (JJTSHIFTEXPRESSION).
    */
   public ASTShiftExpression(JavaParser parser, int identifier) {
      super(parser, identifier);
      names = new Vector();
   }


   /**
    *  Get the object's names
    *
    * @return    the names in an enumeration
    */
   public Enumeration getNames() {
      return names.elements();
   }


   /**
    *  Set the object's name
    *
    * @param  newName  the new name
    */
   public void addName(String newName) {
      if (newName != null) {
         names.addElement(newName.intern());
      }
   }


   /**
    *  Accept the visitor.
    *
    * @param  visitor  An implementation of JavaParserVisitor that processes the ASTShiftExpression node.
    * @param  data     Some data being passed between the visitor methods.
    * @return          Usually the data parameter (possibly modified).
    */
   public Object jjtAccept(JavaParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}

