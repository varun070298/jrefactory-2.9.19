/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTAnnotationTypeDeclaration;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ai.MethodAnalyzer;
import org.acm.seguin.pretty.ai.RequiredTags;


/**
 *  Holds a method declaration in a class
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class AnnotationTypeDeclaration extends BaseJDI {
   ASTAnnotationTypeDeclaration method;

   /**
    *  Constructor for the AnnotationMethodDeclaration JavaDoc creator.
    *
    * @param  method  Create JavaDoc for this node.
    */
   public AnnotationTypeDeclaration(ASTAnnotationTypeDeclaration method) {
      super();
      this.method = method;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("annotation.type", method);
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, bundle.getProperty("annotation.type.tags",""));
   }


   /**
    *  Makes sure all the java doc components are present. 
    */
   public void finish() {
      finish("");
   }


   /**
    *  Makes sure all the java doc components are present. 
    *
    * @param  className  Description of Parameter
    */
   public void finish(String className) {
      jdi.require("", DescriptionPadder.find(bundle, "annotation.type.descr"));
      RequiredTags.getTagger().addTags(bundle, "annotation.type", method.getName(), jdi);
   }

}

