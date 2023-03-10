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
package org.acm.seguin.summary.load;

import java.awt.Frame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import org.acm.seguin.tools.RefactoryStorage;

/**
 *  Reports to the user the status of the loading using stdout
 *
 *@author     Chris Seguin
 *@created    September 12, 2001
 */
public class SwingLoadStatus extends JDialog implements LoadStatus {
    private JLabel label;
    private JProgressBar progress;
    private int count;
    private int max;
    private int fivePercent;
    private String oldName;
    private RefactoryStorage lengths;
    private boolean inPanel = false;


    /**
     *  Constructor for the SwingLoadStatus object
     */
    public SwingLoadStatus(Frame owner) {
        super(owner, "Loading source files", false);
        initialise();
    }
    /**
     *  Constructor for the SwingLoadStatus object
     */
    public SwingLoadStatus() {
        super(new JFrame(), "Loading source files", false);
        initialise();
    }

    private void initialise() {
        getContentPane().setLayout(new GridLayout(2, 1));

        label = new JLabel("Loading:  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        Dimension size = label.getPreferredSize();
        label.setSize(size);
        getContentPane().add(label);

        progress = new JProgressBar();
        progress.setSize(size);
        getContentPane().add(progress);

        //setSize(230, 70);
        pack();
        setVisible(true);

        oldName = null;
        lengths = new RefactoryStorage();
        try {
           setAlwaysOnTop(true);
        } catch (Error e) {
        } catch (Exception e) {
        }
    }


    public java.awt.Container getPanel() {
       return getContentPane();
    }
    
       
    /**
     *  Sets the Root attribute of the LoadStatus object
     *
     *@param  name  The new Root value
     */
    public void setLength(String name, int count) {
        lengths.addKey(name + ".count", count);
        oldName = name;
    }
    
    
    /**
     *  Sets the Root attribute of the LoadStatus object
     *
     *@param  name  The new Root value
     */
    public void setRoot(String name) {
        if (oldName != null) {
            lengths.addKey(oldName + ".count", count);
        }

        if (name.endsWith(".stub")) {
            name = name.substring(0, name.length() - 5);
            progress.setForeground(Color.red);
        } else if (name.toLowerCase().indexOf("j2sdk")>=0) {
            progress.setForeground(Color.green);
        } else {
            progress.setForeground(Color.blue);
        }
        label.setText("Loading:  " + name);
        label.setSize(label.getPreferredSize());
        count = 0;
        progress.setValue(count);
        max = lengths.getValue(name + ".count");
        progress.setMaximum(max);
        fivePercent = max / 20;

        oldName = name;
    }

    public void completedLoading() {
        progress.setValue(0);
        label.setText("Loaded");
    }
    
    public void setLabel(String name) {
        label.setText(name);
    }
       
    
    /**
     *  Sets the CurrentFile attribute of the LoadStatus object
     *
     *@param  name  The new CurrentFile value
     */
    public void setCurrentFile(String name) {
        count++;
        if (fivePercent < 1) {
            progress.setValue(count);
        } else if (count % fivePercent == 0) {
            progress.setValue(count);
        }
    }


    /**
     *  Completed the loading
     */
    public void done() {
        completedLoading();
        dispose();
        if (oldName != null) {
            lengths.addKey(oldName + ".count", count);
        }
        lengths.store();
    }
}
