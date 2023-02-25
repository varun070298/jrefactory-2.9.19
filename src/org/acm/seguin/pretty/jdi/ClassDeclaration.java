/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;

/**
 *  Holds a class declaration. Contains the list of modifiers for the class and the javadoc comments.
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class ClassDeclaration extends BaseJDI {
   private ASTClassDeclaration clazz;
   
   
   /**
    *  Constructor for the ClassDeclaration JavaDoc creator.
    *
    * @param  clazz Create JavaDoc for this node.
    */
   public ClassDeclaration(ASTClassDeclaration clazz) {
      super();
      this.clazz = clazz;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("class", clazz);
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, bundle.getString("class.tags"));
   }


   /**
    *  Makes sure all the java doc components are present. For classes and interfaces, this means a date and an author.
    */
   public void finish() {
      //  Description of the class
      jdi.require("", DescriptionPadder.find(bundle, "class.descr"));

      //  Require the other tags
      int childNo = clazz.skipAnnotations();
      Node child = clazz.jjtGetChild(childNo);
      RequiredTags.getTagger().addTags(bundle, "class", ((ASTUnmodifiedClassDeclaration)child).getName(), jdi);
   }


}

