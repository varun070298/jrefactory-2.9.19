/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;


import java.util.Enumeration;
import net.sourceforge.jrefactory.parser.JavaParserVisitor;
import net.sourceforge.jrefactory.parser.Token;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTActualTypeArgument;
import net.sourceforge.jrefactory.ast.ASTAdditiveExpression;
import net.sourceforge.jrefactory.ast.ASTAllocationExpression;
import net.sourceforge.jrefactory.ast.ASTAndExpression;
import net.sourceforge.jrefactory.ast.ASTArgumentList;
import net.sourceforge.jrefactory.ast.ASTArguments;
import net.sourceforge.jrefactory.ast.ASTArrayDimsAndInits;
import net.sourceforge.jrefactory.ast.ASTArrayInitializer;
import net.sourceforge.jrefactory.ast.ASTAssertionStatement;
import net.sourceforge.jrefactory.ast.ASTAssignmentOperator;
import net.sourceforge.jrefactory.ast.ASTAnnotationTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotationTypeMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.ASTMemberValuePairs;
import net.sourceforge.jrefactory.ast.ASTMemberValuePair;
import net.sourceforge.jrefactory.ast.ASTMemberValue;
import net.sourceforge.jrefactory.ast.ASTMemberValueArrayInitializer;
import net.sourceforge.jrefactory.ast.ASTAnnotationMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstantDeclaration;

import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.ASTBlockStatement;
import net.sourceforge.jrefactory.ast.ASTBooleanLiteral;
import net.sourceforge.jrefactory.ast.ASTBreakStatement;
import net.sourceforge.jrefactory.ast.ASTCastExpression;
import net.sourceforge.jrefactory.ast.ASTClassBody;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;

import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTConditionalAndExpression;
import net.sourceforge.jrefactory.ast.ASTConditionalExpression;
import net.sourceforge.jrefactory.ast.ASTConditionalOrExpression;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTContinueStatement;
import net.sourceforge.jrefactory.ast.ASTDoStatement;
import net.sourceforge.jrefactory.ast.ASTEmptyStatement;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumElement;
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
import net.sourceforge.jrefactory.ast.ASTGenericNameList;
import net.sourceforge.jrefactory.ast.ASTIdentifier;
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
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTReferenceTypeList;
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
import net.sourceforge.jrefactory.ast.ASTTypeArguments;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTTypeParameter;
import net.sourceforge.jrefactory.ast.ASTTypeParameterList;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTUnaryExpression;
import net.sourceforge.jrefactory.ast.ASTUnaryExpressionNotPlusMinus;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.ASTVariableInitializer;
import net.sourceforge.jrefactory.ast.ASTWhileStatement;
import net.sourceforge.jrefactory.ast.ASTJSPBody;

import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.util.FileSettings;
import org.acm.seguin.util.MissingSettingsException;

import org.acm.seguin.pretty.sort.FixupFinalStaticOrder;
import org.acm.seguin.pretty.jdi.*;

import net.sourceforge.jrefactory.parser.NamedToken;


/**
 *  This object simply reflects all the processing back to the individual nodes.
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @created    March 4, 1999
 */
public class PrettyPrintVisitor implements JavaParserVisitor {
   //  Instance Variables
   SpecialTokenVisitor specialTokenVisitor;
   private boolean enclosingIfStatement = false;
   private boolean withinArguments = false;
   // when true we are within an IfStatement
   private FieldSizeLookAhead fsla;
   private LocalVariableLookAhead lvla;

   private final static String[] ENUM_MODIFIERS = new String[]{"static", "transient", "volatile", "final", "public", "protected", "private"};

   private final static String[] CLASS_DECLARATION_MODIFIERS = new String[]{"final", "public", "abstract", "strictfp" };

   private final static String[] NESTED_CLASS_MODIFIERS = new String[]{"static", "abstract", "strictfp", "final", "public", "protected", "private" };

   private final static String[] INTERFACE_MODIFIERS = new String[]{"strictfp", "abstract", "public"};

   private final static String[] NESTED_INTERFACE_MODIFIERS = new String[]{"static", "strictfp", "abstract", "final", "public", "protected", "private"};

   private final static String[] FIELD_MODIFIERS = new String[]{"static", "transient", "volatile", "final", "public", "protected", "private"};

   private final static String[] METHOD_MODIFIERS = new String[]{"synchronized", "static", "native", "volatile", "final", "public", "protected", "private"};

   private final static String[] CONSTRUCTOR_MODIFIERS = new String[]{"public", "protected", "private", "id"};


   /**  Constructor for the PrettyPrintVisitor object */
   public PrettyPrintVisitor() {
      specialTokenVisitor = new SpecialTokenVisitor();

      fsla = new FieldSizeLookAhead(PrintData.DFS_ALIGN_EQUALS);
      lvla = new LocalVariableLookAhead();
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(SimpleNode node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTJSPBody node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }



   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTTypeParameterList node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Traverse the children
      int countChildren = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < countChildren; ndx++) {
         if (ndx > 0) {
            jjtAcceptSpecial(node, printData, ("comma." + (ndx - 1)));
            printData.appendText(", ");
         }
         Node child = node.jjtGetChild(ndx);
         child.jjtAccept(this, data);
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTTypeParameter node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Print the name of the node
      int countChildren = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < countChildren; ndx++) {
         if (ndx == 1) {
            jjtAcceptSpecial(node, printData, "extends.");
            printData.appendText(" extends ");
         } else if (ndx > 1) {
            jjtAcceptSpecial(node, printData, "and.");
            printData.appendText(" & ");
         }
         Node child = node.jjtGetChild(ndx);
         child.jjtAccept(this, data);
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTTypeParameters node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      jjtAcceptSpecial(node, printData, "<.");
      printData.appendText("<");

      node.childrenAccept(this, data);
      //  Traverse the children

      printData.appendText(">");
      jjtAcceptSpecial(node, printData, ">.");

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTTypeArguments node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Print the name of the node
      jjtAcceptSpecial(node, printData, "<.");
      printData.appendText("<");

      int countChildren = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < countChildren; ndx++) {
         if (ndx > 0) {
            jjtAcceptSpecial(node, printData, ("comma." + (ndx - 1)));
            printData.appendText(", ");
         }
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      jjtAcceptSpecial(node, printData, ">.");
      printData.appendText(">");

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTReferenceTypeList node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      int countChildren = node.jjtGetNumChildren();
      if (countChildren > 0) {
         for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
               jjtAcceptSpecial(node, printData, ("comma." + (ndx - 1)));
               printData.appendText(", ");
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
         }
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTReferenceType node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Traverse the children
      int childrenCount = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < childrenCount; ndx++) {
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      //  Add the array
      int count = node.getArrayCount();
      for (int ndx = 0; ndx < count; ndx++) {
         jjtAcceptSpecial(node, printData, "[." + ndx);
         printData.appendText("[");
         jjtAcceptSpecial(node, printData, "]." + ndx);
         printData.appendText("]");
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTClassOrInterfaceType node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Traverse the children
      boolean first = true;
      int childrenCount = node.jjtGetNumChildren();
      int count = 0;
      for (int ndx = 0; ndx < childrenCount; ndx++) {
         Node child = node.jjtGetChild(ndx);
         if (child instanceof ASTIdentifier) {
            if (!first) {
               jjtAcceptSpecial(node, printData, "period." + count);
               printData.appendText(".");
               count++;
            }
            first = false;
         }
         child.jjtAccept(this, data);
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTActualTypeArgument node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      if (node.hasWildcard()) {
         jjtAcceptSpecial(node, printData, "?");
         printData.appendText("?");
      }

      if (node.hasExtends()) {
         jjtAcceptSpecial(node, printData, "extends");
         printData.appendText(" extends ");
      }
      if (node.hasSuper()) {
         jjtAcceptSpecial(node, printData, "super");
         printData.appendText(" super ");
      }

      //  Traverse the children
      int childrenCount = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < childrenCount; ndx++) {
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTGenericNameList node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Traverse the children
      int countChildren = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < countChildren; ndx++) {
         Node child = node.jjtGetChild(ndx);
         if (ndx > 0 && child instanceof ASTName) {
            // ASTClassOrInterfaceType
            jjtAcceptSpecial(node, printData, ("comma." + (ndx - 1)));
            printData.appendText(", ");
         }
         child.jjtAccept(this, data);
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTEnumDeclaration node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Print any tokens
      EnumDeclaration enumJDI = new EnumDeclaration(node);
      printData.beginEnum();
      //jjtAcceptSpecials(enumJDI, node, printData, ENUM_MODIFIERS);

      //  Print the annotation and class special tokens
      int childNo=0;
      SimpleNode child = (SimpleNode)node.jjtGetFirstChild();
      while (child instanceof ASTAnnotation) {
         if (childNo==0) {
            printData.indent();
            node.jjtAccept(specialTokenVisitor, new SpecialTokenData(enumJDI, node.getSpecial("@.0"), printData));
         }
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo==0) {
         jjtAcceptSpecials(enumJDI, node, printData, CLASS_DECLARATION_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(enumJDI, child.getSpecial("enum"), printData));
      }

      //  Make sure that the javadoc stuff is there
      if (isJavadocRequired(enumJDI, node, printData)) {
         enumJDI.finish();
         enumJDI.printJavaDocComponents(printData);
      }
      
      //  Indent and include the modifiers
      childNo=0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         printData.indent();
         if (childNo>0) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
         }
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo>0) {
         printData.indent();
         jjtAcceptSpecials(node, printData, CLASS_DECLARATION_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(enumJDI, child.getSpecial("enum"), printData));
      }

      // print the modifiers
      printData.indent();
      String modifiers = node.getModifiersString(printData.getModifierOrder());
      printData.appendKeyword(modifiers);

      printData.appendText("enum ");

      // print the enum name (an Identifier).
      node.jjtGetChild(childNo++).jjtAccept(this, data);

      printData.appendText(" { ");

      //  Traverse the children
      boolean printingElements = true;
      int countChildren = node.jjtGetNumChildren();
      for (int ndx = childNo; ndx < countChildren; ndx++) {
         child = (SimpleNode)node.jjtGetChild(ndx);
         if (!(child instanceof ASTEnumElement)) {
            if (printingElements) {
               jjtAcceptSpecial(node, printData, "semicolon");
               printData.appendText(";");
               printingElements = false;
               printData.incrIndent();
               printData.newline();
            }
         }
         if (ndx > 1 && printingElements) {
            jjtAcceptSpecial(node, printData, ("comma." + (ndx - 1)));
            printData.appendText(", ");
         }
         child.jjtAccept(this, data);
      }
      jjtAcceptSpecial(node, printData, "end");
      if (!printingElements) {
         printData.decrIndent();
         printData.indent();
         printData.appendText("}");
      } else {
         printData.appendText(" }");
      }
      //  Finish the entry
      printData.newline();
      printData.endEnum();

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTIdentifier node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      jjtAcceptSpecial(node, printData, "id");
      printData.appendText(node.getName());

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.7.00
    */
   public Object visit(ASTEnumElement node, Object data) {
      PrintData printData = (PrintData)data;  //  Get the data

      printData.appendText(node.getName());
      for (int ndx = 1; ndx < node.jjtGetNumChildren(); ndx++) {
         Node child = node.jjtGetChild(ndx);
         child.jjtAccept(this, data);
      }

      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTAnnotationTypeDeclaration node, Object data) {
      PrintData printData = (PrintData)data; //  Get the data
      AnnotationTypeDeclaration typeJDI = new AnnotationTypeDeclaration(node);

      printData.beginInterface();

      int childNo=0;
      if (node.hasAnyChildren()) {
         //  Print the annotation and class special tokens
         SimpleNode child = (SimpleNode)node.jjtGetFirstChild();
         while (child instanceof ASTAnnotation) {
            if (childNo==0) {
               printData.indent();
               node.jjtAccept(specialTokenVisitor, new SpecialTokenData(typeJDI, node.getSpecial("@.0"), printData));
            }
            child = (SimpleNode)node.jjtGetChild(++childNo);
         }
         if (childNo==0) {
            jjtAcceptSpecials(typeJDI, node, printData, new String[] {"abstract","public","strictfp"});
            child.jjtAccept(specialTokenVisitor, new SpecialTokenData(typeJDI, child.getSpecial("enum"), printData));
         }
   
         //  Make sure that the javadoc stuff is there
         if (isJavadocRequired(typeJDI, node, printData)) {
            typeJDI.finish(printData.getCurrentClassName());
            typeJDI.printJavaDocComponents(printData);
         }

         //  Indent and include the modifiers
         childNo=0;
         child = (SimpleNode)node.jjtGetChild(childNo);
         while (child instanceof ASTAnnotation) {
            printData.indent();
            if (childNo>0) {
               jjtAcceptSpecial(node, printData, "@."+childNo);
            }
            child.jjtAccept(this, data);
            child = (SimpleNode)node.jjtGetChild(++childNo);
         }
         if (childNo>0) {
            printData.indent();
            child.jjtAccept(specialTokenVisitor, new SpecialTokenData(typeJDI, child.getSpecial("enum"), printData));
         }
      } else {
         jjtAcceptSpecials(typeJDI, node, printData, new String[] {"abstract","public","strictfp"});
         //  Make sure that the javadoc stuff is there
         if (typeJDI.isRequired()) {
            typeJDI.finish(node.getName());
            typeJDI.printJavaDocComponents(printData);
         }
      }


      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      // Handle return param
      jjtAcceptSpecials(node, printData, new String[] {"@","@interface","id","{"});
      printData.appendText("@interface ");
      printData.appendText(node.getName());
      printData.classBrace();
      printData.beginBlock();
      for (int ndx = childNo; ndx < node.jjtGetNumChildren(); ndx++) {
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }
      printData.classBrace();
      printData.endBlock();
      jjtAcceptSpecial(node, printData, "}");
      printData.newline();
      printData.endInterface();
      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTAnnotationTypeMemberDeclaration node, Object data) {
      PrintData printData = (PrintData)data; //  Get the data
      for (int ndx = 0; ndx < node.jjtGetNumChildren(); ndx++) {
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }
      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTAnnotationMethodDeclaration node, Object data) {
      PrintData printData = (PrintData)data; //  Get the data
      AnnotationMethodDeclaration methodJDI = new AnnotationMethodDeclaration(node);

      printData.beginMethod();

      //  Print the annotation method special tokens
      jjtAcceptSpecials(methodJDI, node, printData, new String[] {"public","abstract"});
      if (isJavadocRequired(methodJDI, node, printData)) {
         methodJDI.finish(printData.getCurrentClassName());
         methodJDI.printJavaDocComponents(printData);
      }
      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      // Handle return param
      node.jjtGetChild(0).jjtAccept(this, data);
      printData.space();
      // Handle the method name
      node.jjtGetChild(1).jjtAccept(this, data);
      jjtAcceptSpecials(methodJDI, node, printData, new String[] {"(",")"});
      printData.appendText("()");
      if (node.jjtGetNumChildren()>2) {
         jjtAcceptSpecial(node, printData, "default");
         printData.appendText(" default ");
         node.jjtGetChild(2).jjtAccept(this, data);
      }

      jjtAcceptSpecial(node, printData, ";");
      printData.appendText(";");
      printData.endMethod();
      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTConstantDeclaration node, Object data) {
      PrintData printData = (PrintData)data; //  Get the data
      ConstantDeclaration constJDI = new ConstantDeclaration(node);

      printData.beginField();

      //  Print the annotation method special tokens
      jjtAcceptSpecials(constJDI, node, printData, new String[] {"public","static","final"});
      if (isJavadocRequired(constJDI, node, printData)) {
         constJDI.finish();
         constJDI.printJavaDocComponents(printData);
      }
      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      // Handle the type
      node.jjtGetChild(0).jjtAccept(this, data);
      printData.space();
      // Handle the method name
      for (int ndx = 1; ndx < node.jjtGetNumChildren(); ndx++) {
         if (ndx>1) {
            jjtAcceptSpecial(node, printData, ("comma." + (ndx - 2)));
            printData.appendText(",");
         }
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      jjtAcceptSpecial(node, printData, ";");
      printData.appendText(";");
      printData.newline();
      printData.endField();

      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTAnnotation node, Object data) {
      PrintData printData = (PrintData)data; //  Get the data

      if ( !(   node.jjtGetParent() instanceof ASTFormalParameter
             || node.jjtGetParent() instanceof ASTMemberValue)) {
         printData.indent();
      }
      printData.appendText("@");
      node.jjtGetChild(0).jjtAccept(this, data);
      if (!node.isMarkerAnnotation()) {
         printData.appendText("(");
         printData.incrIndent();
      }
      for (int ndx = 1; ndx < node.jjtGetNumChildren(); ndx++) {
         Node child = node.jjtGetChild(ndx);
         child.jjtAccept(this, data);
      }
      if (!node.isMarkerAnnotation()) {
         printData.decrIndent();
         printData.appendText(")");
      }
      printData.space();

      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTMemberValuePairs node, Object data) {
      PrintData printData = (PrintData)data;
      if (node.jjtGetNumChildren()>2) {
         printData.indent();
      }
      for (int ndx = 0; ndx < node.jjtGetNumChildren(); ndx++) {
         Node child = node.jjtGetChild(ndx);
         if (ndx>0) {
            jjtAcceptSpecial(node, printData, ("comma."+(ndx-1)));
            printData.appendText(", ");
         }
         if (node.jjtGetNumChildren()>2) {
            printData.indent();
         }
         child.jjtAccept(this, data);
      }
      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTMemberValuePair node, Object data) {
      PrintData printData = (PrintData)data;
      node.jjtGetChild(0).jjtAccept(this, data);
      jjtAcceptSpecial(node, printData, "=");
      printData.appendText("=");
      node.jjtGetChild(1).jjtAccept(this, data);
      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTMemberValue node, Object data) {
      PrintData printData = (PrintData)data;
      jjtAcceptSpecial(node, printData, "@");
      node.jjtGetChild(0).jjtAccept(this, data);
      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    * @since        JRefactory 2.9.04
    */
   public Object visit(ASTMemberValueArrayInitializer node, Object data) {
      PrintData printData = (PrintData)data;
      jjtAcceptSpecial(node, printData, "{");
      printData.appendText("{");
      for (int ndx = 0; ndx < node.jjtGetNumChildren(); ndx++) {
         Node child = node.jjtGetChild(ndx);
         if (ndx>0) {
            jjtAcceptSpecial(node, printData, ("comma."+(ndx-1)));
            printData.appendText(", ");
         }
         child.jjtAccept(this, data);
      }
      jjtAcceptSpecial(node, printData, "}");
      printData.appendText("}");
      return data; //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTCompilationUnit node, Object data) {
      PrintData printData = (PrintData)data;

      // This whole section is to enable sorting of imports while keeping
      // the single line comments on the same line as the import declaration
      // with it, and not the subsequent AST node where the line comment is
      // stored by the parser.
      if (printData.isSortTop()) {
         //System.out.println("BEFORE");
         //for (int i=0; i<node.jjtGetNumChildren(); i++) {
         //   SimpleNode child = (SimpleNode)node.jjtGetChild(i);
         //   if (child instanceof ASTImportDeclaration) {
         //      ASTImportDeclaration is = (ASTImportDeclaration)child;
         //      System.out.println("is="+is.getImage());
         //      Token token = is.getSpecial("import");
         //      while (token!=null) {
         //         System.out.println("  token="+token);
         //         token = token.specialToken;
         //      }
         //   }
         //}
   
         //System.out.println("MUNGING");
         java.util.Map isMap = new java.util.HashMap();
         Object firstComment = new Object();
         ASTImportDeclaration previs = null;
         SimpleNode lastToReplace = null;
         NamedToken lastToReplaceNamed = null;
         
         for (int i=0; i<node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode)node.jjtGetChild(i);
            if (child instanceof ASTImportDeclaration) {
               ASTImportDeclaration is = (ASTImportDeclaration)child;
               removeLastToken(isMap, is, previs, "import", firstComment);
               previs = is;
            } else if (previs != null && child instanceof ASTTypeDeclaration) {
               SimpleNode grandChild = (SimpleNode)child.jjtGetChild(0);
               //System.out.println("  grandChild="+child.getImage()+", grandChild.specials="+grandChild.specials);
               if (grandChild.specials != null) {
                  for (Enumeration e = grandChild.specials.elements(); e.hasMoreElements(); ) {
                     Object obj = e.nextElement();
                     //System.out.println("obj.class = "+obj.getClass());
                     if (obj instanceof NamedToken) {
                        NamedToken namedToken = (NamedToken)obj;
                        //System.out.println("namedToken="+namedToken.getID());
                        removeLastToken(isMap, grandChild, previs, namedToken.getID(), firstComment);
                        lastToReplace = grandChild;
                        lastToReplaceNamed = namedToken;
                     }
                  }
               }
               previs=null;
               child.specials = null;
            }
         }
         Token firstToken = null;
         for (int i=0; i<node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode)node.jjtGetChild(i);
            if (child instanceof ASTImportDeclaration) {
               firstToken = child.getSpecial("import");
               child.specials = new java.util.Vector();
               break;
            }
         }

         //System.out.println("MUNGED");
         //for (int i=0; i<node.jjtGetNumChildren(); i++) {
         //   SimpleNode child = (SimpleNode)node.jjtGetChild(i);
         //   if (child instanceof ASTImportDeclaration) {
         //      ASTImportDeclaration is = (ASTImportDeclaration)child;
         //      System.out.println("is="+is.getImage());
         //      Token token = is.getSpecial("import");
         //      while (token!=null) {
         //         System.out.println("  token="+token);
         //         token = token.specialToken;
         //      }
         //   }
         //}


         //  Sort it
         node.sort(printData.getTopOrder(node));

         //System.out.println("SORTED");
         //for (int i=0; i<node.jjtGetNumChildren(); i++) {
         //   SimpleNode child = (SimpleNode)node.jjtGetChild(i);
         //   if (child instanceof ASTImportDeclaration) {
         //      ASTImportDeclaration is = (ASTImportDeclaration)child;
         //      System.out.println("is="+is.getImage());
         //      Token token = is.getSpecial("import");
         //      while (token!=null) {
         //         System.out.println("  token="+token);
         //         token = token.specialToken;
         //      }
         //   }
         //}


         for (int i=0; i<node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode)node.jjtGetChild(i);
            if (child instanceof ASTImportDeclaration) {
               Token token = child.getSpecial("import");
               while (token!=null) {
                  if (token.specialToken==null) {
                     token.specialToken=firstToken;
                     break;
                  }
                  token = token.specialToken;
               }
               break;
            }
         }


         //System.out.println("DEMUNGING");
         previs = null;
         for (int i=0; i<node.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode)node.jjtGetChild(i);
            if (child instanceof ASTImportDeclaration) {
               ASTImportDeclaration is = (ASTImportDeclaration)child;
               Token tok = (Token)isMap.get( ((previs == null) ? firstComment : previs) );
               if (tok != null) {
                  tok.specialToken = null;
                  //System.out.println("  replacing "+tok);
                  
                  Token token = is.getSpecial("import");
                  if (token==null) {
                     replaceNamedToken(is, "import", new NamedToken("import", tok));
                  } else {
                     while (token!=null) {
                        //System.out.println("  token="+token);
                        if (token.specialToken==null) {
                           token.specialToken=tok;
                           break;
                        }
                        token = token.specialToken;
                     }
                  }
               }

               previs = is;
            } else if (previs != null && child instanceof ASTTypeDeclaration) {
               Token tok = (Token)isMap.get( ((previs == null) ? firstComment : previs) );
               if (tok != null) {
                  tok.specialToken = null;
                  //System.out.println("  replacing "+tok);
                  Token token = lastToReplace.getSpecial(lastToReplaceNamed.getID());
                  if (token==null) {
                     replaceNamedToken(lastToReplace, lastToReplaceNamed.getID(), new NamedToken(lastToReplaceNamed.getID(), tok));
                  } else {
                     while (token!=null) {
                        //System.out.println("  token="+token);
                        if (token.specialToken==null) {
                           token.specialToken=tok;
                           break;
                        }
                        token = token.specialToken;
                     }
                  }
               }
               
               previs=null;
               lastToReplace = null;
            }
         }

         //System.out.println("AFTER");
         //for (int i=0; i<node.jjtGetNumChildren(); i++) {
         //   SimpleNode child = (SimpleNode)node.jjtGetChild(i);
         //   System.out.println("child="+child);
         //   if (child instanceof ASTImportDeclaration) {
         //      ASTImportDeclaration is = (ASTImportDeclaration)child;
         //      System.out.println("is="+is.getImage());
         //      Token token = is.getSpecial("import");
         //      while (token!=null) {
         //         System.out.println("  token="+token);
         //         token = token.specialToken;
         //      }
         //   }
         //}
         //System.out.println("COMPLETE");
      }


      //  Check if we have a header
      loadHeader(node, printData);

      //  Accept the children
      node.childrenAccept(this, data);

      //  Visit EOF special token
      if (!loadFooter(printData)) {
         jjtAcceptSpecial(node, printData, "EOF");
      }

      //  Flush the buffer
      printData.flush();

      //  Return the data
      return data;
   }

   /**
    *  Gets the special associated with a particular key
    *
    * @param  key  the key
    * @return      the value
    */
   private void replaceNamedToken(SimpleNode node, String key, NamedToken newToken) {
      if ((node.specials == null) || (key == null)) {
         return;
      }

      //System.out.println("replaceNamedToken("+node+", "+key+", "+newToken+")");
      int last = node.specials.size();
      for (int ndx = 0; ndx < last; ndx++) {
         NamedToken named = (NamedToken)node.specials.elementAt(ndx);
         if (named.check(key)) {
            System.out.println("  - setting");
            node.specials.setElementAt(newToken, ndx);
         }
      }

      return;
   }


   private void removeLastToken(java.util.Map isMap, SimpleNode node, SimpleNode prevNode, String specialName, Object firstComment) {
      Token token = node.getSpecial(specialName);
      //System.out.println("  token="+token);
      Token prevToken = null;
      Token lastToken = null;
      while (token!=null) {
         prevToken = lastToken;
         lastToken = token;
         //System.out.println("  lastToken="+lastToken);
         token = token.specialToken;
      }
      if (lastToken.kind==net.sourceforge.jrefactory.parser.JavaParserConstants.SINGLE_LINE_COMMENT) {
         //System.out.println("removing single line comment "+lastToken);
         token = node.getSpecial(specialName);
         if (prevNode==null) {
            isMap.put(firstComment, lastToken);
         } else {
            isMap.put(prevNode, lastToken);
         }
         if (token==lastToken) {
            replaceNamedToken(node, specialName, new NamedToken(specialName, null));
         } else {
            prevToken.specialToken=null;
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPackageDeclaration node, Object data) {
      PrintData printData = (PrintData)data;
      PackageDeclaration packageJDI = new PackageDeclaration(node);

      //  Print any tokens
      //jjtAcceptSpecial(node, printData, "package");
      node.jjtAccept(specialTokenVisitor, new SpecialTokenData(packageJDI, node.getSpecial("package"), printData));
      printData.appendKeyword("package");
      printData.space();

      //  Traverse the children
      node.childrenAccept(this, data);

      //  Print any final tokens
      //jjtAcceptSpecial(node, printData, "semicolon");
      node.jjtAccept(specialTokenVisitor, new SpecialTokenData(packageJDI, node.getSpecial("semicolon"), printData));
      printData.appendText(";");

      for (int ndx = 0; ndx <= printData.getLinesAfterPackage(); ndx++) {
         printData.newline();
      }

      return data;   //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTImportDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print any tokens
      jjtAcceptSpecial(node, printData, "import", printData.isMaintainNewlinesAroundImports());
      printData.appendKeyword("import");

      // handle static imports for JDK 1.5
      if (node.isStaticImport()) {
         jjtAcceptSpecial(node, printData, "static");
         printData.appendKeyword(" static");
      }

      printData.space();

      //  Traverse the children
      node.childrenAccept(this, data);

      //  Print any final tokens
      if (node.isImportingPackage()) {
         jjtAcceptSpecial(node, printData, "period");
         printData.appendText(".");
         jjtAcceptSpecial(node, printData, "star");
         printData.appendText("*");
         jjtAcceptSpecial(node, printData, "semicolon");
         printData.appendText(";");
         printData.newline();
      } else {
         jjtAcceptSpecial(node, printData, "semicolon");
         printData.appendText(";");
         printData.newline();
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTTypeDeclaration node, Object data) {
      if (node.hasAnyChildren()) {
         node.childrenAccept(this, data);
      } else {
         //  Get the data
         PrintData printData = (PrintData)data;
         jjtAcceptSpecial(node, printData, "semicolon");
         printData.appendText(";");
         printData.newline();
      }
      return data;
   }



   /**
    *  Visit a class declaration
    *
    * @param  node  the class declaration
    * @param  data  the print data
    * @return       the result of visiting this node
    */
   public Object visit(ASTClassDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;
      ClassDeclaration classJDI = new ClassDeclaration(node);

      //  Add blank lines before the class
      for (int ndx = 0; ndx < printData.getLinesBeforeClass(); ndx++) {
         printData.newline();
      }

      //  Print any tokens
      //jjtAcceptSpecials(classJDI, node, printData, CLASS_DECLARATION_MODIFIERS);

      //  Print the annotation and class special tokens
      int childNo=0;
      SimpleNode child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         if (childNo==0) {
            printData.indent();
            node.jjtAccept(specialTokenVisitor, new SpecialTokenData(classJDI, node.getSpecial("@.0"), printData));
         }
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo==0) {
         jjtAcceptSpecials(classJDI, node, printData, CLASS_DECLARATION_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(classJDI, child.getSpecial("class"), printData));
      }
      //child = (SimpleNode)node.jjtGetChild(childNo);
      //child.jjtAccept(specialTokenVisitor, new SpecialTokenData(classJDI, child.getSpecial("class"), printData));

      //  Make sure that the javadoc stuff is there
      if (classJDI.isRequired()) {
         classJDI.finish();
         classJDI.printJavaDocComponents(printData);
      }

      //  Indent and include the modifiers
      childNo=0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         printData.indent();
         if (childNo>0) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
         }
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo>0) {
         printData.indent();
         jjtAcceptSpecials(node, printData, CLASS_DECLARATION_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(classJDI, child.getSpecial("class"), printData));
      }

      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      //  Traverse the children
      for (int i=childNo; i<node.jjtGetNumChildren(); i++) {
         node.jjtGetChild(i).jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTUnmodifiedClassDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print any tokens
      printData.appendKeyword("class");
      printData.space();
      jjtAcceptSpecial(node, printData, "id");
      printData.appendText(node.getName());
      printData.pushCurrentClassName(node.getName());

      //  Traverse the children
      int lastIndex = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < lastIndex; ndx++) {
         Node next = node.jjtGetChild(ndx);
         //if (next instanceof ASTTypeParameterList) {
         //    jjtAcceptSpecial(node, printData, "extends");
         //    printData.space();
         //    next.jjtAccept(this, data);
         //}
         //else
         if (next instanceof ASTName) {
            // ASTClassOrInterfaceType
            jjtAcceptSpecial(node, printData, "extends");
            //  Add blank line before extends
            if (printData.isLineBeforeExtends()) {
               int indentation = printData.getExtendsIndentation();
               printData.incrIndent(indentation);
               printData.indent();
               printData.incrIndent(-indentation);
            } else {
               printData.space();
            }
            printData.appendKeyword("extends");
            printData.space();
            next.jjtAccept(this, data);
         } else if (next instanceof ASTNameList) {
            jjtAcceptSpecial(node, printData, "implements");
            //  Add blank line before implements
            if (printData.isLineBeforeImplements()) {
               int indentation = printData.getImplementsIndentation();
               printData.incrIndent(indentation);
               printData.indent();
               printData.incrIndent(-indentation);
            } else {
               printData.space();
            }
            printData.appendKeyword("implements");
            printData.space();
            printData.sortImplements( (ASTNameList)next );  //  Sort the thrown exceptions
            next.jjtAccept(this, data);
         } else {
            next.jjtAccept(this, data);
         }
      }

      //  Finish the class
      printData.popCurrentClassName();

      //  Return the data
      return data;
   }


   /**
    *  Visits a class body node
    *
    * @param  node  The node we are visiting
    * @param  data  the print data
    * @return       the print data
    */
   public Object visit(ASTClassBody node, Object data) {
      return visit(node, data, true);
   }


   /**
    *  Visits a class body node
    *
    * @param  node     The node we are visiting
    * @param  data     The print data
    * @param  newline  Whether there should be a new line
    * @return          the print data
    */
   public Object visit(ASTClassBody node, Object data, boolean newline) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Sort it
      node.sort(printData.getOrder(), new FixupFinalStaticOrder());

      FieldSizeLookAhead fsla = new FieldSizeLookAhead(printData.getFieldSpaceCode());
      FieldSize size = fsla.run(node);
      size.update(printData.getDynamicFieldSpaces());
      printData.pushFieldSize(size);

      //  Print any tokens
      printData.classBrace();
      printData.beginBlock();
      jjtAcceptSpecial(node, printData, "begin", false);

      //  Traverse the children
      node.childrenAccept(this, data);

      //  Print any tokens
      jjtAcceptSpecial(node, printData, "end");

      printData.classBrace();
      printData.endBlock(newline, true);

      printData.popFieldSize();

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTNestedClassDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;
      NestedClassDeclaration clazzJDI = new NestedClassDeclaration(node);

      //  Print any tokens
      printData.beginClass();
      int childNo=0;
      SimpleNode child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         if (childNo==0) {
            printData.indent();
            node.jjtAccept(specialTokenVisitor, new SpecialTokenData(clazzJDI, node.getSpecial("@.0"), printData));
         }
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo==0) {
         jjtAcceptSpecials(clazzJDI, node, printData, NESTED_CLASS_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(clazzJDI, child.getSpecial("class"), printData));
      }

      //  Make sure the java doc is there
      if (isJavadocRequired(clazzJDI, node, printData)) {
         clazzJDI.finish();
         clazzJDI.printJavaDocComponents(printData);
      }

      //  Indent and include the modifiers
      childNo=0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         printData.indent();
         if (childNo>0) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
         }
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo>0) {
         printData.indent();
         jjtAcceptSpecials(node, printData, NESTED_CLASS_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(clazzJDI, child.getSpecial("class"), printData));
      }


      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      //  Traverse the children
      for (int i=childNo; i<node.jjtGetNumChildren(); i++) {
         node.jjtGetChild(i).jjtAccept(this, data);
      }
      printData.endClass();

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTClassBodyDeclaration node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTInterfaceDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;
      InterfaceDeclaration intfJDI = new InterfaceDeclaration(node);

      //  Add blank lines before the interface
      for (int ndx = 0; ndx < printData.getLinesBeforeClass(); ndx++) {
         printData.newline();
      }

      //  Print any special tokens
      //jjtAcceptSpecials(intfJDI, node, printData, INTERFACE_MODIFIERS);

      //  Print the annotation and interface special tokens
      int childNo=0;
      SimpleNode child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         if (childNo==0) {
            printData.indent();
            node.jjtAccept(specialTokenVisitor, new SpecialTokenData(intfJDI, node.getSpecial("@.0"), printData));
         }
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo==0) {
         jjtAcceptSpecials(intfJDI, node, printData, INTERFACE_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(intfJDI, child.getSpecial("interface"), printData));
      }

      //  Include the java doc comments
      if (intfJDI.isRequired()) {
         intfJDI.finish();
         intfJDI.printJavaDocComponents(printData);
      }

      //  Print the Annotations
      childNo=0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         printData.indent();
         if (childNo>0) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
         }
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo>0) {
         printData.indent();
         jjtAcceptSpecials(node, printData, INTERFACE_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(child.getSpecial("interface"), printData));
      }

      //  Indent and add the modifiers
      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      //  Traverse the non-annotation children (there is only one)
      child =(SimpleNode)node.jjtGetChild(childNo);
      child.jjtAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTNestedInterfaceDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;
      NestedInterfaceDeclaration intfJDI = new NestedInterfaceDeclaration(node);

      //  Print any tokens
      printData.beginInterface();
      //jjtAcceptSpecials(intfJDI, node, printData, NESTED_INTERFACE_MODIFIERS);

      //  Print the annotation and interface special tokens
      int childNo=0;
      SimpleNode child  = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         if (childNo==0) {
            printData.indent();
            node.jjtAccept(specialTokenVisitor, new SpecialTokenData(intfJDI, node.getSpecial("@.0"), printData));
         }
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo==0) {
         jjtAcceptSpecials(intfJDI, node, printData, NESTED_INTERFACE_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(intfJDI, child.getSpecial("interface"), printData));
      }

      //  Force the Javadoc to be included
      if (isJavadocRequired(intfJDI, node, printData)) {
         intfJDI.finish();
         intfJDI.printJavaDocComponents(printData);
      }

      //  Print the Annotations
      childNo=0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         printData.indent();
         if (childNo>0) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
         }
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo>0) {
         printData.indent();
         jjtAcceptSpecials(node, printData, NESTED_INTERFACE_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(intfJDI, child.getSpecial("interface"), printData));
      }

      //  Indent and include the modifiers
      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      //  Traverse the children (except the Annotations) there is only one
      child = (SimpleNode)node.jjtGetChild(childNo);
      child.jjtAccept(this, data);

      //  Finish this interface
      printData.endInterface();

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTUnmodifiedInterfaceDeclaration node, Object data) {
      //  Local Variables
      boolean first = true;

      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print any tokens
      printData.appendKeyword("interface");
      printData.space();
      jjtAcceptSpecial(node, printData, "id");
      printData.appendText(node.getName());
      printData.pushCurrentClassName(node.getName());

      //  Traverse the children
      int child=0;
      Node next = node.jjtGetChild(child++);
      if (next instanceof ASTNameList) {
         jjtAcceptSpecial(node, printData, "extends");
         //  Add blank line before extends
         if (printData.isLineBeforeExtends()) {
            int indentation = printData.getExtendsIndentation();
            printData.incrIndent(indentation);
            printData.indent();
            printData.incrIndent(-indentation);
         } else {
            printData.space();
         }
         printData.appendKeyword("extends");
         printData.space();
         printData.sortExtends( (ASTNameList)next );  //  Sort the thrown exceptions
         next.jjtAccept(this, data);

         //  Get the next node
         next = node.jjtGetChild(child++);
      }

      //  Handle the interface body
      next.jjtAccept(this, data);

      //  Finish
      printData.popCurrentClassName();

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTInterfaceBody node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      FieldSizeLookAhead fsla = new FieldSizeLookAhead(printData.getFieldSpaceCode());
      FieldSize size = fsla.run(node);
      size.update(printData.getDynamicFieldSpaces());
      printData.pushFieldSize(size);

      //  Begin the block
      printData.classBrace();
      printData.beginBlock();
      jjtAcceptSpecial(node, printData, "begin", false);

      //  Travers the children
      node.childrenAccept(this, data);

      //  End the block
      jjtAcceptSpecial(node, printData, "end");

      printData.classBrace();
      printData.endBlock();

      printData.popFieldSize();

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTInterfaceMemberDeclaration node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTFieldDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;
      FieldDeclaration fieldJDI = new FieldDeclaration(node);

      //  Print any special tokens
      printData.beginField();
      //jjtAcceptSpecials(fieldJDI, node, printData, FIELD_MODIFIERS);

      //  Print the annotation and field special tokens
      int childNo=0;
      SimpleNode child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         if (childNo==0) {
            printData.indent();
            node.jjtAccept(specialTokenVisitor, new SpecialTokenData(fieldJDI, node.getSpecial("@.0"), printData));
         }
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo==0) {
         jjtAcceptSpecials(fieldJDI, node, printData, FIELD_MODIFIERS);
         ASTType type = (ASTType)child;
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(fieldJDI, getInitialToken(type), printData));
      }

      // Include the JavaDoc comments
      if (isJavadocRequired(fieldJDI, node, printData)) {
         fieldJDI.finish();
         fieldJDI.printJavaDocComponents(printData);
      }

      //  Print the Annotations
      childNo=0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         printData.indent();
         if (childNo>0) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
         }
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo>0) {
         printData.indent();
         jjtAcceptSpecials(node, printData, FIELD_MODIFIERS);
         ASTType type = (ASTType)child;
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(fieldJDI, getInitialToken(type), printData));
      }

      //  Indent and include the modifiers
      printData.indent();
      String modifiers = node.getModifiersString(printData.getModifierOrder());
      printData.appendKeyword(modifiers);
      if (printData.isDynamicFieldSpacing(fieldJDI.isJavadocPrinted())) {
         FieldSize size = printData.topFieldSize();
         int maxModifier = size.getModifierLength();
         for (int ndx = modifiers.length(); ndx < maxModifier; ndx++) {
            printData.space();
         }
      }

      //  Handle the first two/three children (which are required)
      Node next = node.jjtGetChild(childNo);
      next.jjtAccept(this, data);
      if (printData.isDynamicFieldSpacing(fieldJDI.isJavadocPrinted())) {
         int current = fsla.computeTypeLength(node);

         FieldSize size = printData.topFieldSize();
         int maxType = size.getTypeLength();
         for (int ndx = current; ndx < maxType; ndx++) {
            printData.space();
         }
      }

      if (printData.getFieldSpaceCode() == PrintData.DFS_ALIGN_EQUALS) {
         printData.setTempEqualsLength(fsla.computeEqualsLength(node));
      }

      standardFieldIndent(printData);
      next = node.jjtGetChild(childNo + 1);
      printData.setStoreJavadocPrinted(fieldJDI.isJavadocPrinted());
      next.jjtAccept(this, data);
      printData.setStoreJavadocPrinted(false);

      //  Traverse the rest of the children
      int lastIndex = node.jjtGetNumChildren();
      for (int ndx = childNo + 2; ndx < lastIndex; ndx++) {
         jjtAcceptSpecial(node, printData, "comma." + (ndx - (childNo + 2)));
         printData.appendText(", ");
         next = node.jjtGetChild(ndx);
         next.jjtAccept(this, data);
      }

      //  Finish the entry
      jjtAcceptSpecial(node, printData, "semicolon");
      printData.appendText(";");
      printData.newline();
      printData.endField();

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTVariableDeclarator node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Handle the first child (which is required)
      Node next = node.jjtGetFirstChild();
      next.jjtAccept(this, data);

      if ((printData.isDynamicFieldSpacing(printData.isStoreJavadocPrinted())) &&
            (node.jjtGetNumChildren() > 1)) {
         int current = ((ASTVariableDeclaratorId)next).getName().length();
         current += ((ASTVariableDeclaratorId)next).getArrayCount()*2;

         FieldSize size = printData.topFieldSize();
         int maxName = size.getNameLength();
         for (int ndx = current; ndx < maxName; ndx++) {
            printData.space();
         }
      }
      if ((printData.getFieldSpaceCode() == PrintData.DFS_ALIGN_EQUALS) &&
            (node.jjtGetNumChildren() > 1) &&
            !printData.getSkipNameSpacing()) {
         int current = printData.getTempEqualsLength();

         FieldSize size = printData.topFieldSize();
         int maxName = size.getEqualsLength();
         for (int ndx = current; ndx < maxName; ndx++) {
            printData.space();
         }
      }
      printData.setStoreJavadocPrinted(false);

      //  Traverse the rest of the children
      if (node.jjtGetNumChildren() > 1) {
         jjtAcceptSpecial(node, printData, "equals");
         if (printData.isSpaceAroundOperators()) {
            printData.space();
         }
         printData.appendText("=");
         if (printData.isSpaceAroundOperators()) {
            printData.space();
         }
         next = node.jjtGetChild(1);
         next.jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTVariableDeclaratorId node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Handle the first child (which is required)
      jjtAcceptSpecial(node, printData, "id");
      printData.appendText(node.getName());
      int last = node.getArrayCount();
      for (int ndx = 0; ndx < last; ndx++) {
         jjtAcceptSpecial(node, printData, "[." + ndx);
         printData.appendText("[");
         jjtAcceptSpecial(node, printData, "]." + ndx);
         printData.appendText("]");
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTVariableInitializer node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTArrayInitializer node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      int incrBy = 3;
      //  Handle the first child (which is required)
      if (!printData.isArrayInitializerIndented()) {
         incrBy = (printData.getIndent() <= 1) ? 1 : 0;
         if (incrBy == 1) {
            printData.decrIndent();
         } else if (printData.getSurpriseReturn() == PrintData.SINGLE_INDENT) {
            printData.decrIndent();
         } else if (printData.getSurpriseReturn() == PrintData.DOUBLE_INDENT) {
            printData.decrIndent();
            printData.decrIndent();
         } else if (printData.getSurpriseReturn() == printData.PARAM_INDENT) {
            printData.decrIndent();
         }
      }
      jjtAcceptSpecial(node, printData, "begin");
      printData.appendText("{");
      if (printData.isIndentInInitailzer()) {
         printData.incrIndent();
      }

      int last = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < last; ndx++) {
         if (ndx > 0) {
            jjtAcceptSpecial(node, printData, "comma." + (ndx - 1));
            printData.appendText(", ");
         }
         Node child = node.jjtGetChild(ndx);
         child.jjtAccept(this, data);
      }
      if (node.isFinalComma()) {
         jjtAcceptSpecial(node, printData, "comma." + (last - 1));
         printData.appendText(",");
      }

      if (printData.isIndentInInitailzer()) {
         printData.decrIndent();
      }

      jjtAcceptSpecial(node, printData, "end");
      printData.appendText("}");
      if (!printData.isArrayInitializerIndented()) {
         if (incrBy == 1) {
            printData.incrIndent();
         } else if (printData.getSurpriseReturn() == PrintData.SINGLE_INDENT) {
            printData.incrIndent();
         } else if (printData.getSurpriseReturn() == PrintData.DOUBLE_INDENT) {
            printData.incrIndent();
            printData.incrIndent();
         } else if (printData.getSurpriseReturn() == printData.PARAM_INDENT) {
            printData.incrIndent();
         }
      }

      //  Return the data
      return data;
   }


   /**
    *  Pretty prints the method declaration
    *
    * @param  node  the node in the parse tree
    * @param  data  the print data
    * @return       the print data
    */
   public Object visit(ASTMethodDeclaration node, Object data) {
      PrintData printData = (PrintData)data;
      MethodDeclaration methodJDI = new MethodDeclaration(node);

      //  Print any tokens
      printData.beginMethod();

      //  Print the annotation and method special tokens
      int childNo = 0;
      SimpleNode child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         if (childNo==0) {
            printData.indent();
            node.jjtAccept(specialTokenVisitor, new SpecialTokenData(methodJDI, node.getSpecial("@.0"), printData));
         }
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo==0) {
         jjtAcceptSpecials(methodJDI, node, printData, METHOD_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(methodJDI, child.getSpecial("interface"), printData));
      }
      if (child instanceof ASTTypeParameters) {
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      node.jjtAccept(specialTokenVisitor, new SpecialTokenData(methodJDI, getInitialToken((ASTResultType)child), printData));

      //  Include the java doc comments
      if (isJavadocRequired(methodJDI, node, printData)) {
         try {
            methodJDI.finish(printData.getCurrentClassName());
         } catch (Exception e) {
            methodJDI.finish("<none>");
         }
         methodJDI.printJavaDocComponents(printData);
      }

      //  Print the Annotations
      childNo=0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         printData.indent();
         if (childNo>0) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
         }
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      if (childNo>0) {
         printData.indent();
         jjtAcceptSpecials(node, printData, METHOD_MODIFIERS);
         child.jjtAccept(specialTokenVisitor, new SpecialTokenData(child.getSpecial("class"), printData));
      }


      //  Indent and add the modifiers
      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));

      //  Handle the TypeParameter declaration (if present)
      while (child instanceof ASTTypeParameters) {
         child.jjtAccept(this, data);
         printData.space();
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }

      // Handle return param
      child.jjtAccept(this, data);
      printData.space();
      // Handle the method name
      child = (SimpleNode)node.jjtGetChild(++childNo);
      child.jjtAccept(this, data);

      //  Traverse the rest of the children
      int lastIndex = node.jjtGetNumChildren();
      boolean foundBlock = false;
      for (int ndx = childNo+1; ndx < lastIndex; ndx++) {
         Node next = node.jjtGetChild(ndx);
         if (next instanceof ASTNameList) {
            jjtAcceptSpecial(node, printData, "throws");
            printData.space();
            if (printData.isThrowsOnNewline()) {
               printData.incrIndent();
               printData.indent();
               printData.decrIndent();
            }
            printData.appendKeyword("throws");
            printData.space();
            printData.sortThrows( (ASTNameList)next );  //  Sort the thrown exceptions
            next.jjtAccept(this, data);
         } else if (next instanceof ASTBlock) {
            foundBlock = true;
            if ((next.jjtGetNumChildren() == 0) &&
                  printData.isEmptyBlockOnSingleLine() &&
                  !isCommentsPresent((ASTBlock)next)) {
               printData.appendText(" { }");
               printData.newline();
            } else {
               printData.methodBrace();
               next.jjtAccept(this, data);
            }
         }
      }

      //  Finish if it is abstract
      if (!foundBlock) {
         jjtAcceptSpecial(node, printData, "semicolon");
         printData.appendText(";");
         printData.newline();
      }

      //  Note the end of the method
      printData.endMethod();

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTMethodDeclarator node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Handle the first child (which is required)
      jjtAcceptSpecial(node, printData, "id");
      printData.appendText(node.getName());
      node.childrenAccept(this, data);

      //  Add the array
      int count = node.getArrayCount();
      for (int ndx = 0; ndx < count; ndx++) {
         jjtAcceptSpecial(node, printData, "[." + ndx);
         printData.appendText("[");
         jjtAcceptSpecial(node, printData, "]." + ndx);
         printData.appendText("]");
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTFormalParameters node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Print any tokens
      jjtAcceptSpecial(node, printData, "begin");
      if (printData.isSpaceAfterMethod() && node.hasAnyChildren()) {
         printData.space();
      }
      printData.beginExpression(node.hasAnyChildren());
      printData.enterMethodDecl();
      printData.setParamIndent();

      //  Traverse the children
      Node next;
      int lastIndex = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < lastIndex; ndx++) {
         if (ndx > 0) {
            jjtAcceptSpecial(node, printData, "comma." + (ndx - 1));
            printData.appendText(", ");
         }
         next = node.jjtGetChild(ndx);
         next.jjtAccept(this, data);
      }

      //  Finish it
      jjtAcceptSpecial(node, printData, "end");
      printData.endExpression(node.hasAnyChildren());

      printData.exitMethodDecl();

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTFormalParameter node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Print the annotation and method special tokens
      int childNo = 0;
      SimpleNode child = (SimpleNode)node.jjtGetFirstChild();
      while (child instanceof ASTAnnotation) {
         jjtAcceptSpecial(node, printData, "@."+childNo);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      //  Print any tokens
      if (node.isUsingFinal()) {
         jjtAcceptSpecial(node, printData, "final");
         printData.appendKeyword("final");
         printData.space();
      }

      printData.setParamIndent();

      //  Traverse the children
      childNo = 0;
      child = (SimpleNode)node.jjtGetChild(childNo);
      while (child instanceof ASTAnnotation) {
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      child.jjtAccept(this, data);
      printData.space();
      child = (SimpleNode)node.jjtGetChild(++childNo);
      child.jjtAccept(this, data);

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTConstructorDeclaration node, Object data) {
      PrintData printData = (PrintData)data;
      ConstructorDeclaration constructorJDI = new ConstructorDeclaration(node);

      printData.beginMethod();

      //  Print the annotation and method special tokens
      int childNo = 0;
      SimpleNode child = (SimpleNode)node.jjtGetFirstChild();
      while (child instanceof ASTAnnotation) {
         jjtAcceptSpecial(node, printData, "@."+childNo);
         child = (SimpleNode)node.jjtGetChild(++childNo);
      }
      jjtAcceptSpecials(constructorJDI, node, printData, CONSTRUCTOR_MODIFIERS);

      if (constructorJDI.isRequired()) {
         constructorJDI.finish();
         constructorJDI.printJavaDocComponents(printData);
      }

      printData.indent();
      printData.appendKeyword(node.getModifiersString(printData.getModifierOrder()));
      for (int i=0; i<childNo; i++) {
         Node next = node.jjtGetChild(i);
         next.jjtAccept(this, data);
      }

      printData.appendText(node.getName());

      //  Handle the return type (which is required)
      Node next = node.jjtGetChild(childNo);
      next.jjtAccept(this, data);

      //  Get the last index
      int lastIndex = node.jjtGetNumChildren();
      childNo++;

      //  Handle the name list if it is present
      if (childNo < lastIndex) {
         next = node.jjtGetChild(childNo);
         if (next instanceof ASTNameList) {
            jjtAcceptSpecial(node, printData, "throws");
            printData.space();
            if (printData.isThrowsOnNewline()) {
               printData.incrIndent();
               printData.indent();
               printData.decrIndent();
            }
            printData.appendKeyword("throws");
            printData.space();
            printData.sortThrows( (ASTNameList)next );  //  Sort the thrown exceptions
            next.jjtAccept(this, data);
            childNo++;
         }
      }

      //  Print the starting block
      if ((lastIndex == childNo) && printData.isEmptyBlockOnSingleLine()) {
         printData.appendText(" { }");
         printData.newline();
      } else {
         LocalVariableLookAhead lvla = new LocalVariableLookAhead();
         FieldSize size = lvla.run(node);
         size.update(printData.getDynamicFieldSpaces());
         printData.pushFieldSize(size);

         printData.methodBrace();
         printData.beginBlock();
         jjtAcceptSpecial(node, printData, "begin", false);

         if (printData.isLineBeforeMultistatementMethodBody() &&
               (lastIndex - childNo > 1) &&
               (printData.getMethodBlockStyle() == PrintData.BLOCK_STYLE_C)) {
            printData.newline();
         }

         //  Traverse the rest of the children
         boolean foundBlock = false;
         for (int ndx = childNo; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
         }

         //  Print the end block
         jjtAcceptSpecial(node, printData, "end");

         printData.methodBrace();
         printData.endBlock();
      }
      printData.endMethod();

      return data;  //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTExplicitConstructorInvocation node, Object data) {
      PrintData printData = (PrintData)data;  //  Get the data

      //  Print the name of the node
      int startWith = 0;
      printData.indent();
      if (node.jjtGetFirstChild() instanceof ASTPrimaryExpression) {
         node.jjtGetFirstChild().jjtAccept(this, data);
         startWith++;
         jjtAcceptSpecial(node, printData, ".");
         printData.appendText(".");
      }
      if (node.jjtGetChild(startWith) instanceof ASTIdentifier) {
         node.jjtGetChild(startWith).jjtAccept(this, data);
         startWith++;
         jjtAcceptSpecial(node, printData, ".2");
         printData.appendText(".");
      }
      jjtAcceptSpecial(node, printData, "explicit");
      printData.appendText(node.getName());

      //  Traverse the children
      int last = node.jjtGetNumChildren();
      for (int ndx = startWith; ndx < last; ndx++) {
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      //  End of the line
      printData.appendText(";");
      jjtAcceptSpecial(node, printData, "semicolon");
      printData.newline();

      return data; //  Return the data
   }


   /**
    *  Formats the initializer
    *
    * @param  node  The initializer node
    * @param  data  The print data
    * @return       Nothing interesting
    */
   public Object visit(ASTInitializer node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the name of the node
      printData.indent();
      if (node.isUsingStatic()) {
         jjtAcceptSpecial(node, printData, "static");
         printData.appendKeyword("static");
      }

      //  Traverse the children
      ASTBlock block = (ASTBlock)node.jjtGetFirstChild();
      blockProcess(block, printData, true, node.isUsingStatic());

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTType node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      node.childrenAccept(this, data);
      //if (node.jjtGetFirstChild() != null) {
      //    node.jjtGetFirstChild().jjtAccept(this, data);
      //}

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPrimitiveType node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the name of the node
      jjtAcceptSpecial(node, printData, "primitive");
      printData.appendKeyword(node.getName());

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTResultType node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      if (node.hasAnyChildren()) {
         node.childrenAccept(this, data);
      } else {
         jjtAcceptSpecial(node, printData, "primitive");
         printData.appendKeyword("void");
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTName node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the name of the node
      int size = node.getNameSize();
      for (int ndx = 0; ndx < size; ndx++) {
         jjtAcceptSpecial(node, printData, "id" + ndx);
         jjtAcceptSpecial(node, printData, "period" + ndx);
      }
      printData.appendText(node.getName());

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTNameList node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      int countChildren = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < countChildren; ndx++) {
         if (ndx > 0) {
            jjtAcceptSpecial(node, printData, "comma." + (ndx - 1));
            printData.appendText(", ");
         }
         Node child = node.jjtGetChild(ndx);
         child.jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTExpression node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTAssignmentOperator node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the name of the node
      if (printData.isSpaceAroundOperators()) {
         printData.space();
      }
      jjtAcceptSpecial(node, printData, "operator");
      printData.appendText(node.getName());
      if (printData.isSpaceAroundOperators()) {
         printData.space();
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTConditionalExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      int childCount = node.jjtGetNumChildren();
      if (childCount == 1) {
         node.jjtGetFirstChild().jjtAccept(this, data);
      } else {
         node.jjtGetFirstChild().jjtAccept(this, data);
         if (printData.isSpaceAroundOperators()) {
            printData.space();
         }
         jjtAcceptSpecial(node, printData, "?");
         printData.appendText("?");
         if (printData.isSpaceAroundOperators()) {
            printData.space();
         }
         node.jjtGetChild(1).jjtAccept(this, data);
         if (printData.isSpaceAroundOperators()) {
            printData.space();
         }
         jjtAcceptSpecial(node, printData, ":");
         printData.appendText(":");
         if (printData.isSpaceAroundOperators()) {
            printData.space();
         }
         node.jjtGetChild(2).jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTConditionalOrExpression node, Object data) {
      return binaryExpression(node, "||", data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTConditionalAndExpression node, Object data) {
      return binaryExpression(node, "&&", data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTInclusiveOrExpression node, Object data) {
      return binaryExpression(node, "|", data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTExclusiveOrExpression node, Object data) {
      return binaryExpression(node, "^", data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTAndExpression node, Object data) {
      return binaryExpression(node, "&", data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTEqualityExpression node, Object data) {
      return binaryExpression(node, node.getNames(), data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTInstanceOfExpression node, Object data) {
      return binaryExpression(node, "instanceof", data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTRelationalExpression node, Object data) {
      return binaryExpression(node, node.getNames(), data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTShiftExpression node, Object data) {
      return binaryExpression(node, node.getNames(), data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTAdditiveExpression node, Object data) {
      return binaryExpression(node, node.getNames(), data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTMultiplicativeExpression node, Object data) {
      return binaryExpression(node, node.getNames(), data);
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTUnaryExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      Node child = node.jjtGetFirstChild();
      if (child instanceof ASTUnaryExpression) {
         jjtAcceptSpecial(node, printData, "operator");
         printData.appendText(node.getName());
      }
      child.jjtAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPreIncrementExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Include the preincrement
      jjtAcceptSpecial(node, printData, "operator");
      printData.appendText("++");

      //  Traverse the children
      node.childrenAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPreDecrementExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Include the preincrement
      jjtAcceptSpecial(node, printData, "operator");
      printData.appendText("--");

      //  Traverse the children
      node.childrenAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      Node child = node.jjtGetFirstChild();
      if (child instanceof ASTUnaryExpression) {
         jjtAcceptSpecial(node, printData, "operator");
         printData.appendText(node.getName());
         if (node.getName().equals("!") && printData.isBangSpace()) {
            printData.space();
         }
      }
      child.jjtAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPostfixExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      node.childrenAccept(this, data);

      //  Include the increment
      jjtAcceptSpecial(node, printData, "operator");
      printData.appendText(node.getName());

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTCastExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Cast portion
      jjtAcceptSpecial(node, printData, "begin");
      printData.beginExpression(printData.isCastSpace());
      if (printData.isSpaceInsideCast()) {
         printData.space();
      }
      node.jjtGetFirstChild().jjtAccept(this, data);
      if (printData.isSpaceInsideCast()) {
         printData.space();
      }
      jjtAcceptSpecial(node, printData, "end");
      printData.endExpression(printData.isCastSpace());
      if (printData.isSpaceAfterCast()) {
         printData.space();
      }

      //  Expression
      node.jjtGetChild(1).jjtAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPrimaryExpression node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPrimaryPrefix node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      for (int ndx = 0; ndx < node.getCount(); ndx++) {
         jjtAcceptSpecial(node, printData, "this." + ndx);
      }

      //  Traverse the children
      if (node.jjtGetNumChildren() == 0) {
         jjtAcceptSpecial(node, printData, "this");
         jjtAcceptSpecial(node, printData, "id");
         printData.appendText(node.getName());
      } else {
         Node child = node.jjtGetFirstChild();
         if ((child instanceof ASTLiteral) ||
               (child instanceof ASTName) ||
               (child instanceof ASTAllocationExpression)) {
            java.util.List anonInnerClasses = node.findChildrenOfType(ASTClassBody.class);
            boolean indent = withinArguments && (anonInnerClasses.size()>0);
            if (indent) {
               printData.incrIndent();
               printData.incrIndent();
            }
            child.jjtAccept(this, data);
            if (indent) {
               printData.decrIndent();
               printData.decrIndent();
            }
         } else if (child instanceof ASTExpression) {
            jjtAcceptSpecial(node, printData, "begin");
            if (printData.isSpaceAfterMethod() && node.hasAnyChildren() && printData.isSpaceAroundOperators()) {
               printData.space();
            }
            printData.beginExpression(node.hasAnyChildren());
            child.jjtAccept(this, data);
            jjtAcceptSpecial(node, printData, "end");
            printData.endExpression(node.hasAnyChildren());
         } else if (child instanceof ASTResultType) {
            child.jjtAccept(this, data);
            printData.appendText(".class");
         }
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTPrimarySuffix node, Object data) {
      PrintData printData = (PrintData)data;
      //  Get the data

      //  Traverse the children
      if (node.jjtGetNumChildren() == 0) {
         jjtAcceptSpecial(node, printData, "dot");
         printData.appendText(".");
         jjtAcceptSpecial(node, printData, "id");
         printData.appendText(node.getName());
      } else {
         Node child = node.jjtGetFirstChild();
         if (child instanceof ASTArguments) {
            child.jjtAccept(this, data);
         } else if (child instanceof ASTExpression) {
            jjtAcceptSpecial(node, printData, "[");
            printData.appendText("[");
            child.jjtAccept(this, data);
            jjtAcceptSpecial(node, printData, "]");
            printData.appendText("]");
         } else if (child instanceof ASTAllocationExpression) {
            jjtAcceptSpecial(node, printData, "dot");
            printData.appendText(".");
            child.jjtAccept(this, data);
         } else if (child instanceof ASTReferenceTypeList) {
            jjtAcceptSpecial(node, printData, "dot");
            printData.appendText(".");
            printData.appendText("<");
            child.jjtAccept(this, data);
            printData.appendText(">");
            jjtAcceptSpecial(node, printData, "id");
            printData.appendText(node.getName());
         }
      }

      return data;
      //  Return the data
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTLiteral node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      if (node.hasAnyChildren()) {
         node.childrenAccept(this, data);
      } else {
         jjtAcceptSpecial(node, printData, "id");
         printData.appendConstant(node.getName());
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTBooleanLiteral node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the data
      jjtAcceptSpecial(node, printData, "id");
      printData.appendConstant(node.getName());

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTNullLiteral node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the data
      jjtAcceptSpecial(node, printData, "id");
      printData.appendConstant("null");

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTArguments node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Start the parens
      jjtAcceptSpecial(node, printData, "begin");
      if (printData.isSpaceAfterMethod() && node.hasAnyChildren()) {
         printData.space();
      }
      printData.beginExpression(node.hasAnyChildren());

      withinArguments = true;
      //  Traverse the children
      node.childrenAccept(this, data);
      withinArguments = false;

      //  Finish the parens
      jjtAcceptSpecial(node, printData, "end");
      printData.endExpression(node.hasAnyChildren());

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTArgumentList node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      int count = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < count; ndx++) {
         if (ndx > 0) {
            jjtAcceptSpecial(node, printData, "comma." + (ndx - 1));
            printData.appendText(", ");
         }
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTAllocationExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      if (node.jjtGetChild(node.jjtGetNumChildren() - 1) instanceof ASTClassBody) {
         //  Indenting
         int lastType = printData.getState();
         printData.setState(PrintData.EMPTY);
         printData.incrIndent();

         //  Print the name of the node
         jjtAcceptSpecial(node, printData, "id");
         printData.indent();
         printData.appendKeyword("new");
         printData.space();

         //  Traverse the children
         SimpleNode nameNode = (SimpleNode)node.jjtGetFirstChild();
         nameNode.jjtAccept(this, data);
         int x = 0;
         if (node.jjtGetChild(1) instanceof ASTTypeArguments) {
            x++;
            ASTTypeArguments typeArguments = (ASTTypeArguments)node.jjtGetChild(1);
            typeArguments.jjtAccept(this, data);
         }

         SimpleNode arguments = (SimpleNode)node.jjtGetChild(1 + x);
         arguments.jjtAccept(this, data);

         ASTClassBody classBody = (ASTClassBody)node.jjtGetChild(2 + x);
         visit(classBody, data, false);

         //  Cleanup
         printData.decrIndent();
         //printData.indent();
         printData.setState(lastType);
      } else {
         //  Print the name of the node
         jjtAcceptSpecial(node, printData, "id");
         printData.appendKeyword("new");
         printData.space();

         //  Traverse the children
         node.childrenAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTArrayDimsAndInits node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      boolean foundInitializer = false;
      int last = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < last; ndx++) {
         if (node.jjtGetChild(ndx) instanceof ASTExpression) {
            jjtAcceptSpecial(node, printData, "[." + ndx);
            printData.appendText("[");
            node.jjtGetChild(ndx).jjtAccept(this, data);
            jjtAcceptSpecial(node, printData, "]." + ndx);
            printData.appendText("]");
         } else if (node.jjtGetChild(ndx) instanceof ASTArrayInitializer) {
            foundInitializer = true;
         }
      }
      int looping = node.getArrayCount();
      if (foundInitializer) {
         looping++;
      }

      for (int ndx = last; ndx < looping; ndx++) {
         jjtAcceptSpecial(node, printData, "[." + ndx);
         printData.appendText("[");
         jjtAcceptSpecial(node, printData, "]." + ndx);
         printData.appendText("]");

      }
      if (foundInitializer) {
         node.jjtGetChild(last - 1).jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      printData.indent();
      node.childrenAccept(this, data);
      if (node.jjtGetFirstChild() instanceof ASTStatementExpression) {
         //  Finish off the statement expression
         jjtAcceptSpecial(node, printData, "semicolon");
         printData.appendText(";");
         printData.newline();
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTLabeledStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the data
      jjtAcceptSpecial(node, printData, "id");
      printData.appendText(node.getName());
      jjtAcceptSpecial(node, printData, "colon");
      printData.appendText(": ");

      //  Traverse the children
      node.childrenAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTBlock node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      blockProcess(node, printData, true);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTBlockStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Include the stuff before the class/interface declaration
      if (node.hasAnyChildren()) {
         int childNo=0;
         SimpleNode child = (SimpleNode)node.jjtGetFirstChild();
         while (child instanceof ASTAnnotation) {
            jjtAcceptSpecial(node, printData, "@."+childNo);
            child = (SimpleNode)node.jjtGetChild(++childNo);
         }

         if (child instanceof ASTUnmodifiedClassDeclaration) {
            printData.beginClass();
            if (node.isFinal()) {
               jjtAcceptSpecial(node, printData, "final");
            }
            jjtAcceptSpecial(node, printData, "class");
            childNo=0;
            child = (SimpleNode)node.jjtGetFirstChild();
            while (child instanceof ASTAnnotation) {
               child.jjtAccept(this, data);
               child = (SimpleNode)node.jjtGetChild(++childNo);
            }
            printData.indent();
            if (node.isFinal()) {
               printData.appendKeyword("final");
               printData.space();
            }
            child.jjtAccept(this, data);
            printData.endClass();
         } else if (child instanceof ASTUnmodifiedInterfaceDeclaration) {
            printData.beginClass();
            if (node.isFinal()) {
               jjtAcceptSpecial(node, printData, "final");
            }
            jjtAcceptSpecial(node, printData, "interface");
            childNo=0;
            child = (SimpleNode)node.jjtGetFirstChild();
            while (child instanceof ASTAnnotation) {
               child.jjtAccept(this, data);
               child = (SimpleNode)node.jjtGetChild(++childNo);
            }
            printData.indent();
            if (node.isFinal()) {
               printData.appendKeyword("final");
               printData.space();
            }
            child.jjtAccept(this, data);
            printData.endClass();
         } else if (printData.isInsertSpaceLocalVariables() && (child instanceof ASTLocalVariableDeclaration) ) {
            if (!isLastLocalVariable(node)) {
               printData.newline();
            }
            //  Traverse the children
            node.childrenAccept(this, data);
            if (!isNextLocalVariable(node)) {
               printData.newline();
               printData.newline();
            }
         } else {
            //  Traverse the children
            node.childrenAccept(this, data);
         }
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTLocalVariableDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      int last = node.jjtGetNumChildren();
      int childNo = 0;
      SimpleNode child = (SimpleNode)node.jjtGetFirstChild();
      while (child instanceof ASTAnnotation) {
         jjtAcceptSpecial(node, printData, "@."+childNo);
         child.jjtAccept(this, data);
         child = (SimpleNode)node.jjtGetChild(++childNo);
         printData.indent();
      }
      Node typeNode = child;
      for (int ndx = childNo+1; ndx < last; ndx++) {
         jjtAcceptSpecial(node, printData, "comma." + (ndx - 1));
         //  Indent this type of statement
         if (printData.isVariablesAlignWithBlock()) {
            printData.decrIndent();
            printData.indent();
         } else {
            printData.indent();
         }

         //  Print the final token
         int current = 0;
         if (node.isUsingFinal()) {
            jjtAcceptSpecial(node, printData, "final");
            printData.appendKeyword("final");
            printData.space();
            current = 6;
         }

         if (printData.isDynamicFieldSpacing(false)) {
            FieldSize size = printData.topFieldSize();
            int maxModifier = size.getModifierLength();
            for (int modifierIndex = current; modifierIndex < maxModifier; modifierIndex++) {
               printData.space();
            }
         }

         //  Print the annotation (if any) type
         //childNo = 0;
         //child = (SimpleNode)node.jjtGetChild(childNo);
         //while (child instanceof ASTAnnotation) {
         //   child.jjtAccept(this, data);
         //   childNo++;
         //   child = (SimpleNode)node.jjtGetChild(childNo);
         //}
         typeNode.jjtAccept(this, data);
         printData.space();
         if (printData.isVariablesAlignWithBlock()) {
            printData.incrIndent();
         }

         if (printData.isDynamicFieldSpacing(false)) {
            current = lvla.computeTypeLength(node);

            FieldSize size = printData.topFieldSize();
            int maxType = size.getTypeLength();
            for (int typeIndex = current; typeIndex < maxType; typeIndex++) {
               printData.space();
            }
         }

         if (printData.getFieldSpaceCode() == PrintData.DFS_ALIGN_EQUALS) {
            printData.setTempEqualsLength(lvla.computeEqualsLength(node));
         }

         //  Visit the child
         node.jjtGetChild(ndx).jjtAccept(this, data);

         //  End the variable declaration
         printData.appendText(";");
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTEmptyStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the name of the node
      jjtAcceptSpecial(node, printData, "semicolon");
      printData.appendText(";");
      printData.newline();

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTStatementExpression node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      if (node.jjtGetFirstChild() instanceof ASTPrimaryExpression) {
         int last = node.jjtGetNumChildren();
         node.jjtGetFirstChild().jjtAccept(this, data);
         jjtAcceptSpecial(node, printData, "id");
         printData.appendText(node.getName());
         for (int ndx = 1; ndx < last; ndx++) {
            node.jjtGetChild(ndx).jjtAccept(this, data);
         }
      } else {
         node.childrenAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTSwitchStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Switch
      jjtAcceptSpecial(node, printData, "switch");
      printData.appendKeyword("switch");
      jjtAcceptSpecial(node, printData, "beginExpr");
      if (printData.isSpaceAfterKeyword()) {
         printData.space();
      }
      printData.beginExpression(true);
      node.jjtGetFirstChild().jjtAccept(this, data);
      jjtAcceptSpecial(node, printData, "endExpr");
      printData.endExpression(true);

      //  Start the block
      printData.beginBlock();
      printData.decrIndent();
      printData.incrCaseIndent();
      jjtAcceptSpecial(node, printData, "beginBlock", false);

      //  Traverse the children
      int last = node.jjtGetNumChildren();
      for (int ndx = 1; ndx < last; ndx++) {
         Node next = node.jjtGetChild(ndx);
         if (next instanceof ASTBlockStatement) {
            boolean indent = shouldIndentSwitchBody(next);
            if (indent) {
               printData.incrIndent();
               next.jjtAccept(this, data);
               printData.decrIndent();
            } else {
               Node child = next.jjtGetFirstChild();
               Node grandchild = child.jjtGetFirstChild();
               printData.indent();
               blockProcess((ASTBlock)grandchild,
                     printData,
                     true,
                     false);
            }
         } else {
            next.jjtAccept(this, data);
         }
      }

      //  End the block
      jjtAcceptSpecial(node, printData, "endBlock");
      printData.decrCaseIndent();
      printData.incrIndent();
      printData.endBlock();

      //  Return the data
      return data;
   }


   /**
    *  Reformats the case XXX: portion of a switch statement
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTSwitchLabel node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Determine if the node has children
      if (node.hasAnyChildren()) {
         jjtAcceptSpecial(node, printData, "id");
         printData.indent();
         printData.appendKeyword("case");
         printData.space();
         node.childrenAccept(this, data);
         jjtAcceptSpecial(node, printData, "colon");
         printData.appendText(":");
         printData.newline();
      } else {
         jjtAcceptSpecial(node, printData, "id");
         jjtAcceptSpecial(node, printData, "colon");
         printData.indent();
         printData.appendKeyword("default");
         printData.appendText(":");
         printData.newline();
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTIfStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Determine if the node has children
      jjtAcceptSpecial(node, printData, "if");
      printData.appendKeyword("if");
      jjtAcceptSpecial(node, printData, "beginExpr");
      if (printData.isSpaceAfterKeyword()) {
         printData.space();
      }
      printData.beginExpression(true);
      node.jjtGetFirstChild().jjtAccept(this, data);
      jjtAcceptSpecial(node, printData, "endExpr");
      printData.endExpression(true);

      //  Determine if the then contains a block
      boolean hasElse = (node.jjtGetNumChildren() == 3);
      if (node.jjtGetNumChildren() >= 2) {
         boolean oldEnclosingIfStatement = enclosingIfStatement;
         enclosingIfStatement = true;
         forceBlock(node.jjtGetChild(1), printData,
               !hasElse && printData.isElseOnNewLine(),
               printData.isRemoveExcessBlocks() &&
               !isIfStatement(node.jjtGetChild(1)));
         enclosingIfStatement = oldEnclosingIfStatement;
      }

      //  Determine if the else part
      if (hasElse) {
         boolean shouldIndent = isShouldIndentBeforeElse(printData, node);
         if (shouldIndent) {
            printData.indent();
         } else {
            printData.space();
         }
         jjtAcceptSpecial(node, printData, "else", shouldIndent);
         printData.appendKeyword("else");
         //  Determine if the next item is a statement
         ASTStatement child = (ASTStatement)node.jjtGetChild(2);
         Node next = child.jjtGetFirstChild();
         if (next instanceof ASTIfStatement) {
            printData.space();
            // this rather complex code removes unwanted new lines
            // from before "if" statements so that when they are moved
            // the comments are in the correct place.
            Token special = ((ASTIfStatement)next).getSpecial("if");
            Token prev = null;
            Token first = null;
            while (special != null) {
               if (special.kind >= 4 && special.kind <= 6) {
                  special.kind = 1;
                  // make it a space!
                  special.image = " ";
               } else if (first == null) {
                  first = special;
                  prev = first;
               } else {
                  prev.specialToken = special;
                  prev = special;
               }
               special = special.specialToken;
            }
            if (first != null) {
               prev.specialToken = null;
               special = ((ASTIfStatement)next).getSpecial("if");
               special.kind = first.kind;
               special.image = first.image;
               special.specialToken = first.specialToken;
            } else {
               ((ASTIfStatement)next).removeSpecial("if");
            }
            next.jjtAccept(this, data);
         } else {
            forceBlock(child, printData, true, printData.isRemoveExcessBlocks());
         }
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTWhileStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Determine if the node has children
      jjtAcceptSpecial(node, printData, "while");
      printData.appendKeyword("while");
      jjtAcceptSpecial(node, printData, "beginExpr");
      if (printData.isSpaceAfterKeyword()) {
         printData.space();
      }
      printData.beginExpression(true);
      node.jjtGetFirstChild().jjtAccept(this, data);
      jjtAcceptSpecial(node, printData, "endExpr");
      printData.endExpression(true);

      //  Process the block
      Node next = node.jjtGetChild(1);
      // get the Statement
      forceBlock(next,
            printData,
            true,
            printData.isRemoveExcessBlocks() && isIfStatementWithElse(next));

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTDoStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Begin the do block
      jjtAcceptSpecial(node, printData, "do");
      printData.appendKeyword("do");

      //  Process the block
      forceBlock(node.jjtGetFirstChild(), printData, false, false);

      //  Process the while block
      jjtAcceptSpecial(node, printData, "while", false);
      if (printData.isSpaceAfterKeyword()) {
         printData.space();
      }
      printData.appendKeyword("while");
      jjtAcceptSpecial(node, printData, "beginExpr", false);
      printData.space();
      printData.beginExpression(true);
      node.jjtGetChild(1).jjtAccept(this, data);
      jjtAcceptSpecial(node, printData, "endExpr");
      printData.endExpression(true);
      jjtAcceptSpecial(node, printData, "semicolon");
      printData.appendText(";");

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTForStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Start the for loop
      jjtAcceptSpecial(node, printData, "for");
      printData.appendKeyword("for");
      jjtAcceptSpecial(node, printData, "beginExpr");
      if (printData.isSpaceAfterKeyword()) {
         printData.space();
      }
      printData.beginExpression(node.hasAnyChildren());

      //  Traverse the children
      Node next = node.jjtGetFirstChild();
      int index = 1;

      if (next instanceof ASTLocalVariableDeclaration) {
         // new for loop structure for JDK 1.5
         printData.setSkipNameSpacing(true);
         forInit((ASTLocalVariableDeclaration)next, data);
         printData.setSkipNameSpacing(false);
         next = node.jjtGetChild(index);
         index++;
         jjtAcceptSpecial(node, printData, "loopover");
         printData.appendText(" : ");
         if (next instanceof ASTExpression) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
         }
      } else {
         if (next instanceof ASTForInit) {
            printData.setSkipNameSpacing(true);
            next.jjtAccept(this, data);
            printData.setSkipNameSpacing(false);
            next = node.jjtGetChild(index);
            index++;
         }
         jjtAcceptSpecial(node, printData, "init");
         printData.appendText("; ");
         if (next instanceof ASTExpression) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
         }
         jjtAcceptSpecial(node, printData, "test");
         printData.appendText("; ");
         if (next instanceof ASTForUpdate) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
         }
      }
      jjtAcceptSpecial(node, printData, "endExpr");
      printData.endExpression(node.hasAnyChildren());
      forceBlock(next,
            printData,
            true,
            printData.isRemoveExcessBlocks() && (!enclosingIfStatement || isIfStatementWithElse(next)));

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTForInit node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      Node next = node.jjtGetFirstChild();
      if (next instanceof ASTLocalVariableDeclaration) {
         forInit((ASTLocalVariableDeclaration)next, data);
      } else {
         node.childrenAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTStatementExpressionList node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      int last = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < last; ndx++) {
         if (ndx > 0) {
            jjtAcceptSpecial(node, printData, "comma." + (ndx - 1));
            printData.appendText(", ");
         }
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTForUpdate node, Object data) {
      //  Traverse the children
      node.childrenAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTBreakStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the break statement
      jjtAcceptSpecial(node, printData, "break");
      printData.appendKeyword("break");
      String name = node.getName();
      if (!((name == null) || (name.length() == 0))) {
         jjtAcceptSpecial(node, printData, "id");
         printData.appendText(" " + node.getName());
      }
      jjtAcceptSpecial(node, printData, "semicolon");
      printData.appendText(";");

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTContinueStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the continue statement
      jjtAcceptSpecial(node, printData, "continue");
      printData.appendKeyword("continue");
      String name = node.getName();
      if (!((name == null) || (name.length() == 0))) {
         jjtAcceptSpecial(node, printData, "id");
         printData.appendText(" " + node.getName());
      }
      jjtAcceptSpecial(node, printData, "semicolon");
      printData.appendText(";");

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTReturnStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      if (node.hasAnyChildren()) {
         jjtAcceptSpecial(node, printData, "return");
         printData.appendKeyword("return");
         printData.space();
         node.childrenAccept(this, data);
         jjtAcceptSpecial(node, printData, "semicolon");
         printData.appendText(";");
      } else {
         jjtAcceptSpecial(node, printData, "return");
         printData.appendKeyword("return");
         jjtAcceptSpecial(node, printData, "semicolon");
         printData.appendText(";");
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTThrowStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      jjtAcceptSpecial(node, printData, "throw");
      printData.appendKeyword("throw");
      printData.space();
      node.childrenAccept(this, data);
      jjtAcceptSpecial(node, printData, "semicolon");
      printData.appendText(";");

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTSynchronizedStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      jjtAcceptSpecial(node, printData, "synchronized");
      printData.appendKeyword("synchronized");
      jjtAcceptSpecial(node, printData, "beginExpr");
      if (printData.isSpaceAfterKeyword()) {
         printData.space();
      }
      printData.beginExpression(true);
      node.jjtGetFirstChild().jjtAccept(this, data);
      jjtAcceptSpecial(node, printData, "endExpr");
      printData.endExpression(true);
      node.jjtGetChild(1).jjtAccept(this, data);

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   public Object visit(ASTTryStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      jjtAcceptSpecial(node, printData, "try");
      printData.appendKeyword("try");
      blockProcess((ASTBlock)node.jjtGetFirstChild(), printData, printData.isCatchOnNewLine());

      //  Now work with the pairs
      int last = node.jjtGetNumChildren();
      boolean paired = false;
      int catchCount = 0;

      for (int ndx = 1; ndx < last; ndx++) {
         Node next = node.jjtGetChild(ndx);
         if (next instanceof ASTFormalParameter) {
            if (printData.isCatchOnNewLine()) {
               printData.indent();
            } else {
               printData.space();
            }
            jjtAcceptSpecial(node, printData, "catch" + catchCount, printData.isCatchOnNewLine());
            printData.appendKeyword("catch");
            jjtAcceptSpecial(node, printData, "beginExpr" + catchCount);
            if (printData.isSpaceAfterKeyword()) {
               printData.space();
            }
            printData.beginExpression(true);
            next.jjtAccept(this, data);
            jjtAcceptSpecial(node, printData, "endExpr" + catchCount);
            printData.endExpression(true);
            paired = true;
            catchCount++;
         } else {
            if (!paired) {
               jjtAcceptSpecial(node, printData, "finally", printData.isCatchOnNewLine());
               if (printData.isCatchOnNewLine()) {
                  printData.indent();
               } else {
                  printData.space();
               }
               printData.appendKeyword("finally");
            }
            blockProcess((ASTBlock)next, printData, printData.isCatchOnNewLine());
            paired = false;
         }
      }

      if (!printData.isCatchOnNewLine()) {
         printData.newline();
      }

      //  Return the data
      return data;
   }


   /**
    *  Visit the assertion node
    *
    * @param  node  the node
    * @param  data  the data needed to perform the visit
    * @return       the result of visiting the node
    */
   public Object visit(ASTAssertionStatement node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      jjtAcceptSpecial(node, printData, "assert");
      printData.appendKeyword("assert");
      printData.space();
      node.jjtGetFirstChild().jjtAccept(this, data);

      if (node.jjtGetNumChildren() > 1) {
         printData.space();
         jjtAcceptSpecial(node, printData, "colon");
         printData.appendText(":");
         printData.space();
         node.jjtGetChild(1).jjtAccept(this, data);
      }

      jjtAcceptSpecial(node, printData, "semicolon");
      printData.appendText(";");

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  name  Description of Parameter
    * @param  data  Description of Parameter
    * @return       Description of the Returned Value
    */
   protected Object binaryExpression(SimpleNode node, String name, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      int childCount = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < childCount; ndx++) {
         if (ndx > 0) {
            if (printData.isSpaceAroundOperators() || name.equals("instanceof")) {
               printData.space();
            }
            jjtAcceptSpecial(node, printData, "operator." + (ndx - 1));
            printData.appendText(name);
            if (printData.isSpaceAroundOperators() || name.equals("instanceof")) {
               printData.space();
            }
         }
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node   Description of Parameter
    * @param  names  Description of Parameter
    * @param  data   Description of Parameter
    * @return        Description of the Returned Value
    */
   protected Object binaryExpression(SimpleNode node, Enumeration names, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Traverse the children
      int childCount = node.jjtGetNumChildren();
      for (int ndx = 0; ndx < childCount; ndx++) {
         if (ndx > 0) {
            if (printData.isSpaceAroundOperators()) {
               printData.space();
            }
            jjtAcceptSpecial(node, printData, "operator." + (ndx - 1));
            printData.appendText(names.nextElement().toString());
            if (printData.isSpaceAroundOperators()) {
               printData.space();
            }
         }
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }

      //  Return the data
      return data;
   }


   /**
    *  Description of the Method
    *
    * @param  node  Description of Parameter
    * @param  data  Description of Parameter
    */
   protected void forInit(ASTLocalVariableDeclaration node, Object data) {
      //  Get the data
      PrintData printData = (PrintData)data;

      //  Print the final token
      if (node.isUsingFinal()) {
         jjtAcceptSpecial(node, printData, "final");
         printData.appendKeyword("final");
         printData.space();
      }

      //  Traverse the children
      int last = node.jjtGetNumChildren();
      Node typeNode = node.jjtGetFirstChild();
      typeNode.jjtAccept(this, data);
      printData.space();

      for (int ndx = 1; ndx < last; ndx++) {
         if (ndx > 1) {
            //  Add a comma between entries
            jjtAcceptSpecial(node, printData, "comma." + (ndx - 1));
            printData.appendText(", ");
         }

         //  Visit the child
         node.jjtGetChild(ndx).jjtAccept(this, data);
      }
   }


   /**
    *  Forces a block around this node, if it is not already a block
    *
    * @param  node            the node
    * @param  printData       the print data
    * @param  newline         do we have a newline at the end of the block
    * @param  canRemoveBlock  Description of the Parameter
    */
   protected void forceBlock(Node node, PrintData printData,
         boolean newline, boolean canRemoveBlock) {
      if ((node.jjtGetNumChildren() > 0) &&
            (node.jjtGetFirstChild() instanceof ASTBlock)) {
         //  We know we have a block
         ASTBlock child = (ASTBlock)node.jjtGetFirstChild();

         LocalVariableLookAhead lvla = new LocalVariableLookAhead();
         FieldSize size = lvla.run(child);
         size.update(printData.getDynamicFieldSpaces());
         printData.pushFieldSize(size);

         //  Start the block
         if (isThisBlockRequired(canRemoveBlock, node)) {
            printData.beginBlock();
         } else {
            printData.incrIndent();
         }
         jjtAcceptSpecial(child, printData, "begin", false);

         if (child.jjtGetNumChildren()>0) {
            //  Accept the children
            child.childrenAccept(this, printData);
         }

         //  End the block
         jjtAcceptSpecial(child, printData, "end");
         if (isThisBlockRequired(canRemoveBlock, node)) {
            printData.endBlock(newline, true);
         } else {
            if (child.jjtGetNumChildren()==0) {
               printData.indent();
               printData.appendText(";");
            }
            printData.decrIndent();
         }

         printData.popFieldSize();
      } else {
         if (printData.isForcingBlock()) {
            printData.beginBlock();
         } else {
            printData.incrIndent();
         }

         printData.indent();
         ((SimpleNode)node).childrenAccept(this, printData);
         if (node.jjtGetNumChildren() == 0 || node.jjtGetFirstChild() instanceof ASTStatementExpression) {
            //  Finish off the statement expression
            printData.appendText(";");
            printData.newline();
         }

         if (printData.isForcingBlock()) {
            printData.endBlock(newline, true);
         } else {
            printData.decrIndent();
         }
      }
   }


   /**
    *  Check the initial token, and removes it from the object.
    *
    * @param  top  the result type
    * @return      the initial token
    */
   private Token getInitialToken(ASTResultType top) {
      //  Check to see if we need to go farther down
      if (top.hasAnyChildren()) {
         ASTType type = (ASTType)top.jjtGetFirstChild();
         if (type.jjtGetFirstChild() instanceof ASTPrimitiveType) {
            ASTPrimitiveType primitiveType = (ASTPrimitiveType)type.jjtGetFirstChild();
            Token tok = primitiveType.getSpecial("primitive");
            primitiveType.removeSpecial("primitive");
            return tok;
         } else if (type.jjtGetFirstChild() instanceof ASTReferenceType) {
            ASTReferenceType referenceType = (ASTReferenceType)type.jjtGetFirstChild();
            if (referenceType.jjtGetFirstChild() instanceof ASTClassOrInterfaceType) {
               ASTClassOrInterfaceType name = (ASTClassOrInterfaceType)referenceType.jjtGetFirstChild();
               ASTIdentifier ident = (ASTIdentifier)name.jjtGetFirstChild();
               Token tok = ident.getSpecial("id");
               ident.removeSpecial("id");
               return tok;
            } else if (referenceType.jjtGetFirstChild() instanceof ASTPrimitiveType) {
               ASTPrimitiveType primitiveType = (ASTPrimitiveType)referenceType.jjtGetFirstChild();
               Token tok = primitiveType.getSpecial("primitive");
               primitiveType.removeSpecial("primitive");
               return tok;
            } else {
               // FIXME: we get a CompilationUnit which is suprising as a child of a ASTReferenceType
               //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: top="+top.getName());
               //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: type="+type.getName());
               //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: referenceType="+referenceType.getName());
               //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: referenceType.jjtGetFirstChild()="+referenceType.jjtGetFirstChild());
               //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: referenceType.jjtGetFirstChild()="+((SimpleNode)referenceType.jjtGetFirstChild()).getName());
               return null;
            }
         } else {
            ASTName name = (ASTName)type.jjtGetFirstChild();
            Token tok = name.getSpecial("id0");
            name.removeSpecial("id0");
            return tok;
         }
      } else {
         //  No farther to go - return the token
         Token tok = top.getSpecial("primitive");
         top.removeSpecial("primitive");
         return tok;
      }
   }


   /**
    *  Check the initial token, and removes it from the object.
    *
    * @param  top  the type
    * @return      the initial token
    */
   private Token getInitialToken(ASTType top) {
      if (top.jjtGetFirstChild() instanceof ASTPrimitiveType) {
         ASTPrimitiveType primitiveType = (ASTPrimitiveType)top.jjtGetFirstChild();
         Token tok = primitiveType.getSpecial("primitive");
         primitiveType.removeSpecial("primitive");
         return tok;
      } else if (top.jjtGetFirstChild() instanceof ASTReferenceType) {
         ASTReferenceType reference = (ASTReferenceType)top.jjtGetFirstChild();
         if (reference.jjtGetFirstChild() instanceof ASTPrimitiveType) {
            ASTPrimitiveType primitiveType = (ASTPrimitiveType)reference.jjtGetFirstChild();
            Token tok = primitiveType.getSpecial("primitive");
            primitiveType.removeSpecial("primitive");
            return tok;
         } else if (reference.jjtGetFirstChild() instanceof ASTClassOrInterfaceType) {
            ASTClassOrInterfaceType name = (ASTClassOrInterfaceType)reference.jjtGetFirstChild();
            ASTIdentifier ident = (ASTIdentifier)name.jjtGetFirstChild();
            Token tok = ident.getSpecial("id");
            ident.removeSpecial("id");
            return tok;
         } else {
            // FIXME: we get a CompilationUnit which is suprising as a child of a ASTReferenceType
            //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: top="+top.getName());
            //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: reference="+reference.getName());
            //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: reference.jjtGetFirstChild()="+reference.jjtGetFirstChild());
            //System.out.println("PrettyPrintVisitor.getInitialToken: ERROR: reference.jjtGetFirstChild()="+((SimpleNode)reference.jjtGetFirstChild()).getName());
            return null;
         }
      } else {
         ASTName name = (ASTName)top.jjtGetFirstChild();
         Token tok = name.getSpecial("id0");
         name.removeSpecial("id0");
         return tok;
      }
   }


   /**
    *  Gets the commentsPresent attribute of the PrettyPrintVisitor object
    *
    * @param  node  Description of the Parameter
    * @return       The commentsPresent value
    */
   private boolean isCommentsPresent(ASTBlock node) {
      Token tok = node.getSpecial("end");
      if (tok == null) {
         return false;
      }

      //  Find the first token
      Token current = tok;
      Token previous = tok.specialToken;
      int type;
      while (previous != null) {
         type = current.kind;
         if ((type < 5) || (type > 7)) {
            return true;
         }
         current = previous;
         previous = current.specialToken;
      }

      //  Return the first
      type = current.kind;
      return ((type < 5) || (type > 7));
   }


   /**
    *  Gets the ifStatement attribute of the PrettyPrintVisitor object
    *
    * @param  node  Description of the Parameter
    * @return       The ifStatement value
    */
   private boolean isIfStatement(Node node) {
      if (node.jjtGetFirstChild() instanceof ASTBlock) {
         ASTBlock block = (ASTBlock)node.jjtGetFirstChild();
         if (block.jjtGetNumChildren() < 1) {
            return false;
         }
         ASTBlockStatement blockStatement = (ASTBlockStatement)block.jjtGetFirstChild();
         if (blockStatement.jjtGetFirstChild() instanceof ASTStatement) {
            ASTStatement statement = (ASTStatement)blockStatement.jjtGetFirstChild();
            return (statement.jjtGetFirstChild() instanceof ASTIfStatement);
         }
      }

      return false;
   }


   /**
    *  Gets the ifStatement attribute of the PrettyPrintVisitor object
    *
    * @param  node  Description of the Parameter
    * @return       The ifStatement value
    * @since        JRefactory 2.7.00
    */
   private boolean isIfStatementWithElse(Node node) {
      if (node.jjtGetFirstChild() instanceof ASTBlock) {
         ASTBlock block = (ASTBlock)node.jjtGetFirstChild();
         ASTBlockStatement blockStatement = (ASTBlockStatement)block.jjtGetFirstChild();
         if (blockStatement.jjtGetFirstChild() instanceof ASTStatement) {
            ASTStatement statement = (ASTStatement)blockStatement.jjtGetFirstChild();
            if (statement.jjtGetFirstChild() instanceof ASTIfStatement) {
               ASTIfStatement ifstatement = (ASTIfStatement)statement.jjtGetFirstChild();
               return (ifstatement.jjtGetNumChildren() == 3);
            }
         }
      }

      return true;
   }


   /**
    *  Determine if the node is a field or a method in an anonymous class
    *
    * @param  node  The node in question
    * @return       true if the node is in an anonymous class
    */
   private boolean isInAnonymousClass(Node node) {
      return (node.jjtGetParent().jjtGetParent().jjtGetParent()
            instanceof ASTAllocationExpression);
   }


   /**
    *  Determine if the node is a field or a method in an anonymous class
    *
    * @param  node  The node in question
    * @return       true if the node is in an anonymous class
    */
   private boolean isInInnerClass(Node node) {
      Node greatGreatGrandparent = node.jjtGetParent().jjtGetParent().jjtGetParent().jjtGetParent();

      boolean topLevelClass = (greatGreatGrandparent instanceof ASTClassDeclaration);
      boolean topLevelInterface = (greatGreatGrandparent instanceof ASTInterfaceDeclaration);

      return !topLevelClass && !topLevelInterface;
   }


   /**
    *  Determine if the class is an inner class
    *
    * @param  node  Description of Parameter
    * @return       The InnerClass value
    */
   private boolean isInnerClass(Node node) {
      return (node instanceof ASTNestedClassDeclaration) ||
            (node instanceof ASTNestedInterfaceDeclaration);
   }


   /**
    *  Gets the JavadocRequired attribute of the PrettyPrintVisitor object
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @return            The JavadocRequired value
    */
   private boolean isJavadocRequired(SimpleNode node, PrintData printData) {
      if (printData.isNestedClassDocumented()) {
         return node.isRequired() && !isInAnonymousClass(node);
      } else {
         return node.isRequired() && !isInAnonymousClass(node)
               && !isInInnerClass(node) && !isInnerClass(node);
      }
   }


   /**
    *  Gets the JavadocRequired attribute of the PrettyPrintVisitor object
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @return            The JavadocRequired value
    */
   private boolean isJavadocRequired(JavaDocable jdi, SimpleNode node, PrintData printData) {
      if (printData.isNestedClassDocumented()) {
         return jdi.isRequired() && !isInAnonymousClass(node);
      } else {
         return jdi.isRequired() && !isInAnonymousClass(node)
               && !isInInnerClass(node) && !isInnerClass(node);
      }
   }


   /**
    *  Gets the lastLocalVariable attribute of the PrettyPrintVisitor object
    *
    * @param  node  Description of the Parameter
    * @return       The lastLocalVariable value
    */
   private boolean isLastLocalVariable(ASTBlockStatement node) {
      Node parent = node.jjtGetParent();
      int last = parent.jjtGetNumChildren();
      return isNeighborLV(parent, node, 1, last, -1);
   }


   /**
    *  Gets the neighborLV attribute of the PrettyPrintVisitor object
    *
    * @param  parent     Description of the Parameter
    * @param  node       Description of the Parameter
    * @param  first      Description of the Parameter
    * @param  last       Description of the Parameter
    * @param  direction  Description of the Parameter
    * @return            The neighborLV value
    */
   private boolean isNeighborLV(Node parent, Node node, int first, int last, int direction) {
      for (int ndx = first; ndx < last; ndx++) {
         if (parent.jjtGetChild(ndx) == node) {
            Node next = parent.jjtGetChild(ndx + direction);
            if (next instanceof ASTBlockStatement) {
               return (next.jjtGetFirstChild() instanceof ASTLocalVariableDeclaration);
            }
            return true;
         }
      }
      return true;
   }


   /**
    *  Gets the nextLocalVariable attribute of the PrettyPrintVisitor object
    *
    * @param  node  Description of the Parameter
    * @return       The nextLocalVariable value
    */
   private boolean isNextLocalVariable(ASTBlockStatement node) {
      Node parent = node.jjtGetParent();
      int last = parent.jjtGetNumChildren() - 1;
      return isNeighborLV(parent, node, 0, last, 1);
   }


   /**
    *  The rules to determine if we should indent before the block are a little more complex if we are allowed not to
    *  force a block statement around the body of the then part.
    *
    * @param  printData  Description of the Parameter
    * @param  node       Description of the Parameter
    * @return            The shouldIndentBeforeElse value
    */
   private boolean isShouldIndentBeforeElse(PrintData printData, ASTIfStatement node) {
      if (printData.isElseOnNewLine()) {
         return true;
      }

      if (printData.isForcingBlock() || (node.jjtGetChild(1) instanceof ASTBlock)) {
         return false;
      }

      return true;
   }


   /**
    *  Gets the thisBlockRequired attribute of the PrettyPrintVisitor object
    *
    * @param  canRemoveBlock  Description of the Parameter
    * @param  node            Description of the Parameter
    * @return                 The thisBlockRequired value
    */
   private boolean isThisBlockRequired(boolean canRemoveBlock, Node node) {
      if (!canRemoveBlock) {
         return true;
      }

      return (node.jjtGetFirstChild().jjtGetNumChildren() > 1);
   }


   /**
    *  Processes a block object
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @param  newline    Description of Parameter
    */
   private void blockProcess(ASTBlock node, PrintData printData, boolean newline) {
      blockProcess(node, printData, newline, true);
   }


   /**
    *  Processes a block object
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @param  newline    Description of Parameter
    * @param  space      Description of Parameter
    */
   private void blockProcess(ASTBlock node, PrintData printData, boolean newline, boolean space) {
      boolean onSingleLine = (node.jjtGetNumChildren() > 0) ||
      // REVISIT: should be == 0
            !printData.isEmptyBlockOnSingleLine() ||
            (node.getSpecial("end") != null) ||
            (node.getSpecial("begin") != null);

      LocalVariableLookAhead lvla = new LocalVariableLookAhead();
      FieldSize size = lvla.run(node);
      size.update(printData.getDynamicFieldSpaces());
      printData.pushFieldSize(size);

      //  Start the block
      printData.beginBlock(space, onSingleLine);
      jjtAcceptSpecial(node, printData, "begin", false);

      // If this is a multistatement method body, then check whether to print a beginning line
      if (printData.isLineBeforeMultistatementMethodBody() &&
            (node.jjtGetParent() instanceof ASTMethodDeclaration) &&
            (node.jjtGetNumChildren() > 1) &&
            (printData.getMethodBlockStyle() == PrintData.BLOCK_STYLE_C)) {
         printData.newline();
      }

      //  Traverse the children
      node.childrenAccept(this, printData);

      //  Finish the block
      jjtAcceptSpecial(node, printData, "end");

      if (node.jjtGetParent() instanceof ASTMethodDeclaration) {
         printData.methodBrace();
      }
      printData.endBlock(newline, onSingleLine);

      printData.popFieldSize();
   }


   /**
    *  Loads the footer
    *
    * @param  printData  where we are printing to
    * @return            Description of the Return Value
    */
   private boolean loadFooter(PrintData printData) {
      boolean foundSomething = false;
      try {
         FileSettings settings = FileSettings.getRefactoryPrettySettings();
         String temp = settings.getString("footer.1");

         foundSomething = true;

         int ndx = 1;
         while (true) {
            String nextLine = settings.getString("footer." + ndx);
            printData.appendComment(nextLine, PrintData.C_STYLE_COMMENT);
            printData.newline();
            ndx++;
         }
      } catch (MissingSettingsException mse) {
      }

      return foundSomething;
   }


   /**
    *  Loads the header
    *
    * @param  node       the compilation unit
    * @param  printData  where we are printing to
    */
   private void loadHeader(ASTCompilationUnit node, PrintData printData) {
      if (node == null) {
         return;
      }

      try {
         FileSettings settings = FileSettings.getRefactoryPrettySettings();
         String temp = settings.getString("header.1");

         /*
             *  That would throw an exception if there wasn't a header.
             *  So now we are free to delete things out of the child node
             */
         SimpleNode child = (SimpleNode)node.jjtGetFirstChild();
         if (child == null) {
            return;
         } else if (child instanceof ASTPackageDeclaration) {
            child.removeSpecial("package");
         } else if (child instanceof ASTImportDeclaration) {
            child.removeSpecial("import");
         } else {
            //  The first thing is a type declaration - you're on your own
            return;
         }

         int ndx = 1;
         while (true) {
            String nextLine = settings.getString("header." + ndx);
            printData.appendComment(nextLine, PrintData.C_STYLE_COMMENT);
            printData.newline();
            ndx++;
         }
      } catch (MissingSettingsException mse) {
      }
   }


   /**
    *  Determines if we should indent the statement that is contained in the switch statement.
    *
    * @param  next  the next node
    * @return       true if we should use the indent
    */
   private boolean shouldIndentSwitchBody(Node next) {
      Node child = next.jjtGetFirstChild();
      if (child instanceof ASTStatement) {
         Node grandchild = child.jjtGetFirstChild();
         if (grandchild instanceof ASTBlock) {
            return false;
         }
      }
      return true;
   }


   /**
    *  The standard field indent
    *
    * @param  printData  Description of Parameter
    */
   private void standardFieldIndent(PrintData printData) {
      printData.space();

      if (printData.isFieldNameIndented()) {
         int currentLength = printData.getLineLength();
         int desiredLength = printData.getFieldNameIndent();
         for (int ndx = currentLength; ndx < desiredLength; ndx++) {
            printData.space();
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @param  specials   Description of Parameter
    */
   private void jjtAcceptSpecials(SimpleNode node, PrintData printData, String[] specials) {
      for (int i = 0; i < specials.length; i++) {
         node.jjtAccept(specialTokenVisitor, new SpecialTokenData(node.getSpecial(specials[i]), printData));
      }
   }


   /**
    *  Description of the Method
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @param  specials   Description of Parameter
    */
   private void jjtAcceptSpecials(JavaDocable jdi, SimpleNode node, PrintData printData, String[] specials) {
      for (int i = 0; i < specials.length; i++) {
         node.jjtAccept(specialTokenVisitor, new SpecialTokenData(jdi, node.getSpecial(specials[i]), printData));
      }
   }


   /**
    *  Description of the Method
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @param  special    Description of Parameter
    */
   private void jjtAcceptSpecial(SimpleNode node, PrintData printData, String special) {
      node.jjtAccept(specialTokenVisitor, new SpecialTokenData(node.getSpecial(special), printData));
   }


   /**
    *  Description of the Method
    *
    * @param  node       Description of Parameter
    * @param  printData  Description of Parameter
    * @param  special    Description of Parameter
    * @param  newline    Description of Parameter
    */
   private void jjtAcceptSpecial(SimpleNode node, PrintData printData, String special, boolean newline) {
      node.jjtAccept(specialTokenVisitor, new SpecialTokenData(node.getSpecial(special), printData, newline));
   }
}

