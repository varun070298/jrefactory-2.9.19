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
package org.acm.seguin.ide.common.action;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import org.acm.seguin.ide.common.SummaryLoaderThread;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.TypeSummary;


/**
 *  Shares the commonality between actions that perform refactorings from
 *  JBuilder's IDE
 *
 *@author     Chris Seguin
 *@created    October 18, 2001
 */
abstract class RefactoringAction extends GenericAction {
    private SelectedFileSet selectedFileSet;


    /**
     *  Constructor for the JBuilderRefactoringAction object
     *
     *@param  init  Description of Parameter
     */
    public RefactoringAction(SelectedFileSet init) {
        super();
        System.out.println("RefactoringAction()");

        selectedFileSet = init;
    }


    /**
     *  Gets the TypeSummaryArray attribute of the JBuilderRefactoringAction
     *  object
     *
     *@return    The TypeSummaryArray value
     */
    private TypeSummary[] getTypeSummaryArray() {
        System.out.println("RefactoringAction.getTypeSummaryArray()");
        return selectedFileSet.getTypeSummaryArray();
    }


    /**
     *  Gets the AllJava attribute of the AddParentClassAction object
     *
     *@return    The AllJava value
     */
    protected boolean isAllJava() {
        System.out.println("RefactoringAction.isAllJava()");
        return selectedFileSet.isAllJava();
    }


    /**
     *  Gets the SingleJavaFile attribute of the AddChildClassAction object
     *
     *@return    The SingleJavaFile value
     */
    protected boolean isSingleJavaFile() {
        System.out.println("RefactoringAction.isSingleJavaFile()");
        return selectedFileSet.isSingleJavaFile();
    }


    /**
     *  The action to be performed
     *
     *@param  evt  the triggering event
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("RefactoringAction.actionPerformed()");
        updateMetaData();
        System.out.println("RefactoringAction.actionPerformed() - 2");
        TypeSummary[] typeSummaryArray = getTypeSummaryArray();
        if (typeSummaryArray==null) {
           JOptionPane.showMessageDialog(null, "A summary of the Java source cannot be found. Load the java class into a JRefactory viewer",
                    "Refactoring Failed", JOptionPane.ERROR_MESSAGE);

        }
        System.out.println("RefactoringAction.actionPerformed() - 3");
        activateListener(typeSummaryArray, evt);
        System.out.println("RefactoringAction.actionPerformed() - 4");

        CurrentSummary.get().reset();
        System.out.println("RefactoringAction.actionPerformed() - 5");
    }


    /**
     *  The listener to activate with the specified types
     *
     *@param  typeSummaryArray  Description of Parameter
     *@param  evt               Description of Parameter
     */
    protected abstract void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt);


    /**
     *  Reloads all the metadata before attempting to perform a refactoring.
     */
    protected void updateMetaData() {
        System.out.println("RefactoringAction.updateMetaData()");
        System.out.flush();
        CurrentSummary.get().updateMetaData();
    }
}

