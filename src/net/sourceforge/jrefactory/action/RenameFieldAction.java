/* ====================================================================
 * The JRefactory License, Version 1.0
 *
 * Copyright (c) 2001 JRefactory.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        JRefactory (http://www.sourceforge.org/projects/jrefactory)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "JRefactory" must not be used to endorse or promote
 *    products derived from this software without prior written
 *    permission. For written permission, please contact seguin@acm.org.
 *
 * 5. Products derived from this software may not be called "JRefactory",
 *    nor may "JRefactory" appear in their name, without prior written
 *    permission of Chris Seguin.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of JRefactory.  For more information on
 * JRefactory, please see
 * <http://www.sourceforge.org/projects/jrefactory>.
 */
package net.sourceforge.jrefactory.action;

import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringFactory;
import org.acm.seguin.refactor.field.RenameFieldRefactoring;
import org.acm.seguin.summary.FieldSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;


/**
 *  Pushes a field into the child classes
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: RenameFieldAction.java,v 1.3 2004/04/30 17:11:45 mikeatkinson Exp $
 */
public class RenameFieldAction extends RefactoringAction {

   /**
    *  Constructor for the RenameFieldAction object
    *
    * @since    2.9.12
    */
   public RenameFieldAction() {
      super(new EmptySelectedFileSet());
      initNames();
   }


   /**
    *  Constructor for the RenameFieldAction object
    *
    * @param  initPackage  Description of Parameter
    * @param  initField    Description of Parameter
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since               2.9.12
    */
   public RenameFieldAction(UMLPackage initPackage, FieldSummary initField, JPopupMenu initMenu, JMenuItem initItem) {
      this();
      currentPackage = initPackage;
      fieldSummary = initField;
      setPopupMenuListener(new RenameFieldListener(initMenu, initItem));
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.12
    */
   public boolean isEnabled() {
      Summary summary = CurrentSummary.get().getCurrentSummary();
      return (summary != null) && (summary instanceof FieldSummary);
   }


   /**
    *  Description of the Method
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt) {
      System.err.println("RenameFieldAction: activateListener() line=" + evt.getID());
      if (typeSummaryArray != null) {
         for (int i = 0; i < typeSummaryArray.length; i++) {
            System.err.println("RenameFieldAction:    " + typeSummaryArray[i]);
         }
      }
      CurrentSummary.get().lineNo = evt.getID();
      Summary summary = CurrentSummary.get().getCurrentSummary();
      System.err.println("RenameFieldAction: summary=" + summary);
      if (summary instanceof FieldSummary) {
         System.err.println("RenameFieldListener: field");
         fieldSummary = (FieldSummary)summary;
         RenameFieldListener pdfl = new RenameFieldListener(null, null);
         pdfl.actionPerformed(null);
      }
      CurrentSummary.get().lineNo = -1;
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Rename Field");
      putValue(SHORT_DESCRIPTION, "Rename Field (fails Unit tests)");
      putValue(LONG_DESCRIPTION, "Rename a field (this should not be used as it fails Unit tests)");
   }


   /**
    *  Adds a rename class listener
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public class RenameFieldListener extends DialogViewListener {
      /**
       *  Constructor for the RenameFieldListener object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public RenameFieldListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  As this refactoring fails its unit tests, confirm that the user wants to continue.
       *
       * @param  evt  the action event
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         System.err.println("RenameFieldListener: actionPerformed(" + evt + ")");
         if (failsUnitTests(currentPackage, "Rename Field")) {
            super.actionPerformed(evt);
         }
      }


      /**
       *  Creates an appropriate dialog to prompt the user for additional input
       *
       * @return    the dialog box
       * @since     2.9.12
       */
      protected JDialog createDialog() {
         return new RenameFieldDialog();
      }
   }


   /**
    *  Creates a dialog box to prompt for the new package name
    *
    * @author     Chris Seguin
    * @since      empty
    * @created    September 12, 2001
    */
   public class RenameFieldDialog extends ClassNameDialog {
      /**
       *  Constructor for RenameFieldDialog
       *
       * @since    empty
       */
      public RenameFieldDialog() {
         super(1);
         setTitle(getWindowTitle());
         setDefaultName(fieldSummary);
      }


      /**
       *  Gets the label for the text
       *
       * @return    the text for the label
       * @since     empty
       */
      public String getLabelText() {
         return "New Name:";
      }


      /**
       *  Creates a refactoring to be performed
       *
       * @return    the refactoring
       * @since     empty
       */
      protected Refactoring createRefactoring() {
         RenameFieldRefactoring rfr = RefactoringFactory.get().renameField();
         rfr.setClass((TypeSummary)fieldSummary.getParent());
         rfr.setField(fieldSummary.getName());
         rfr.setNewName(getClassName());

         return rfr;
      }


      /**
       *  Rename the type summary that has been influenced
       *
       * @since    empty
       */
      protected void updateSummaries() {
         fieldSummary.setName(getClassName());
      }


      /**
       *  Sets the suggested name of this parameter
       *
       * @param  initVariable  The new defaultName value
       * @since                empty
       */
      private void setDefaultName(FieldSummary initVariable) {
         try {
            HungarianNamer namer = new HungarianNamer();
            setClassName(namer.getDefaultName(initVariable, "m_"));
         } catch (Exception exc) {
            exc.printStackTrace();
            setClassName(initVariable.getName());
         }
      }


      /**
       *  Returns the window title
       *
       * @return    the title
       * @since     empty
       */
      private String getWindowTitle() {
         return (fieldSummary == null) ? "Rename field" : "Rename field: " + fieldSummary.getName();
      }
   }
}

