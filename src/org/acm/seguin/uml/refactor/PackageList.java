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
package org.acm.seguin.uml.refactor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.acm.seguin.summary.PackageSummary;

/**
 *  Creates a label and a jcombo box and adds it into the JDialog. The combo box
 *  contains a list of all the packages that have been created so far. This
 *  assumes that the dialog box has used a GridBagLayout, and makes the label
 *  fill one column and the combo box fill 2 columns. <P>
 *
 *  The usage: <BR>
 *  <TT><BR>
 *  PackageList pl = new PackageList(); <BR>
 *  JComboBox save = pl.add(this); <BR>
 *  </TT>
 *
 *@author     Chris Seguin
 *@created    September 12, 2001
 */
public class PackageList {
    /**
     *  Adds a label and the combo box to the designated dialog
     *
     *@param  dialog  the dialog window
     *@return         the combo box that was added
     */
    public JComboBox add(JDialog dialog) {
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel packageLabel = new JLabel("Package:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        dialog.getContentPane().add(packageLabel, gbc);

        JComboBox packageName = new JComboBox();
        packageName.setEditable(true);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        dialog.getContentPane().add(packageName, gbc);

        addPackages(packageName);

        JPanel blank = new JPanel();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        Dimension blankDim = new Dimension(50, packageName.getPreferredSize().height * 5);
        blank.setMinimumSize(blankDim);
        blank.setPreferredSize(blankDim);
        dialog.getContentPane().add(blank, gbc);

        return packageName;
    }


    /**
     *  Fills in the combo box with the names of the packages
     *
     *@param  comboBox  the combo box to fill in
     */
    private void addPackages(JComboBox comboBox) {
        //  Add the package names
        Iterator iter = PackageSummary.getAllPackages();
        TreeSet set = new TreeSet();
        while (iter.hasNext()) {
            PackageSummary next = (PackageSummary) iter.next();
            set.add(next.toString());
        }

        iter = set.iterator();
        while (iter.hasNext()) {
            comboBox.addItem(iter.next().toString());
        }
    }
}
