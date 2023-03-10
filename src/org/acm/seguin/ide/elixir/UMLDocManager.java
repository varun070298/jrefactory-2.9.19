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
package org.acm.seguin.ide.elixir;

import com.elixirtech.fx.DocManager;
import com.elixirtech.fx.ViewManager;
import com.elixirtech.tree.TNode;
import com.elixirtech.tree.TLeaf;
import java.io.File;
import org.acm.seguin.ide.common.SingleDirClassDiagramReloader;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;

/**
 *  Document manager for UML files
 *
 *@author     Chris Seguin
 *@created    October 18, 2001
 */
public class UMLDocManager implements DocManager {
    private SingleDirClassDiagramReloader reloader;


    /**
     *  Constructor for the UMLDocManager object
     */
    public UMLDocManager() {
        reloader = new SingleDirClassDiagramReloader();
        ElixirClassDiagramLoader.register(reloader);
    }


    /**
     *  Get the default extension associated with this document type
     *
     *@return    The Extension value
     */
    public String getExtension() {
        return "uml";
    }


    /**
     *  Get the name of the document type.
     *
     *@return    The Name value
     */
    public String getName() {
        return "UML Class Diagrams";
    }


    /**
     *  Gets the reloader object
     *
     *@return    The reloader value
     */
    public SingleDirClassDiagramReloader getReloader() {
        return reloader;
    }


    /**
     *  Create a new TNode which can represent the given file within the project
     *  tree.
     *
     *@param  parent  Description of Parameter
     *@param  file    Description of Parameter
     *@return         The TreeNode value
     */
    public TNode getTreeNode(TNode parent, File file) {
        return new UMLLeaf(parent, file, this);
    }


    /**
     *  Create a new ViewManager which can show the given filename
     *
     *@param  filename  Description of Parameter
     *@return           The ViewManager value
     */
    public ViewManager getViewManager(String filename) {
        return new UMLViewManager(this, filename, RefactoryExtension.base);
    }


    /**
     *  Determine whether this document type can be created by the user through
     *  the "New File" dialog.
     *
     *@return    The UserCreated value
     */
    public boolean isUserCreated() {
        return true;
    }


    /**
     *  Test whether a filename is valid for a particular kind of document
     *
     *@param  filename  Description of Parameter
     *@return           The ValidFilename value
     */
    public boolean isValidFilename(String filename) {
        return filename.endsWith(".uml");
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     */
    public String toString() {
        return "UML Class Diagram";
    }
}
//  EOF
