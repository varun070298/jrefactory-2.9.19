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
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.acm.seguin.awt.ExceptionPrinter;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringException;
import net.sourceforge.jrefactory.uml.UMLPackage;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;

/**
 *  Dialog box that runs a refactoring
 *
 *@author     Chris Seguin
 *@created    September 12, 2001
 */
abstract class RefactoringDialog extends JDialog implements ActionListener {
    private UMLPackage currentPackage;


    /**
     *  Constructor for the RefactoringDialog object
     *
     *@param  init  the current package
     */
    public RefactoringDialog(UMLPackage init) {
        currentPackage = init;
    }


    /**
     *  Constructor for the RefactoringDialog object
     *
     *@param  init   the current package
     *@param  frame  Description of the Parameter
     */
    public RefactoringDialog(UMLPackage init, JFrame frame) {
        super(frame);
        currentPackage = init;
    }


    /**
     *  Respond to a button press
     *
     *@param  evt  The action event
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("OK")) {
            dispose();
            runRefactoring();
        } else if (evt.getActionCommand().equals("Cancel")) {
            dispose();
        }

        if (currentPackage != null) {
            currentPackage.repaint();
        }
    }


    /**
     *  Returns the current UML package
     *
     *@return    the package
     */
    protected UMLPackage getUMLPackage() {
        return currentPackage;
    }


    /**
     *  Creates a refactoring to be performed
     *
     *@return    the refactoring
     */
    protected abstract Refactoring createRefactoring();


    /**
     *  Do any necessary updates to the summaries after the refactoring is
     *  complete
     */
    protected void updateSummaries() { }


    /**
     *  Adds an abstract parent class to all specified classes.
     */
    private void runRefactoring() {
        Refactoring refactoring = createRefactoring();

        //  Update the code
        try {
            refactoring.run();
        } catch (RefactoringException re) {
            JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Throwable thrown) {
            ExceptionPrinter.print(thrown, true);
        }

        followup(refactoring);
    }


    /**
     *  Follows up the refactoring by updating the class diagrams
     *
     *@param  refactoring  Description of the Parameter
     */
    protected void followup(Refactoring refactoring) {
        updateSummaries();

        //  Update the GUIs
        ReloaderSingleton.reload();
    }
}
