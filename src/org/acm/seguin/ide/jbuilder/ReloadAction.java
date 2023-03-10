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
package org.acm.seguin.ide.jbuilder;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import javax.swing.Action;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;
import org.acm.seguin.ide.common.MultipleDirClassDiagramReloader;

/**
 *  Reloads class diagrams
 *
 *@author     Chris Seguin
 *@created    October 18, 2001
 */
public class ReloadAction extends JBuilderAction {
    /**
     *  Constructor for the PrintAction object
     */
    public ReloadAction() {
        putValue(NAME, "Load Refactoring Metadata");
        putValue(SHORT_DESCRIPTION, "Load Refactoring Metadata");
        putValue(LONG_DESCRIPTION, "Reloads the metadata for the class diagrams");
    }


    /**
     *  Gets the Enabled attribute of the ReloadAction object
     *
     *@return    The Enabled value
     */
    public boolean isEnabled() {
        return enabled;
    }


    /**
     *  The pretty printer action
     *
     *@param  evt  the action that occurred
     */
    public void actionPerformed(ActionEvent evt) {
        MultipleDirClassDiagramReloader reloader =
                UMLNodeViewerFactory.getFactory().getReloader();

        reloader.setNecessary(true);
        reloader.reload();

        putValue(NAME, "Reload Refactoring Metadata");
        putValue(SHORT_DESCRIPTION, "Reload Refactoring Metadata");
    }
}
//  EOF
