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
package org.acm.seguin.ide.netbeans;

import java.io.StringReader;
import java.io.IOException;

import java.awt.Frame;

import net.sourceforge.jrefactory.action.SelectedFileSet;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.TypeSummary;
import org.netbeans.modules.java.JavaDataObject;

import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.ide.common.options.SelectedRules;

/**
 *  The concrete implementation of this class for Netbeans
 *
 *@author     <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 *@version    $Id: NetbeansSelectedFileSet.java,v 1.1 2004/05/05 17:28:52 mikeatkinson Exp $ 
 *@created    May 05, 2004
 */
public class NetbeansSelectedFileSet extends SelectedFileSet {
    private Frame view;
    private JavaDataObject[] buffers;


    /**
     *  Constructor for the JBuilderSelectedFileSet object
     *
     *@param  init  Description of Parameter
     */
    public NetbeansSelectedFileSet(Frame view, JavaDataObject buffer) {
        this.view = view;
        this.buffers = new JavaDataObject[] { buffer };
    }


    /**
     *  Constructor for the JBuilderSelectedFileSet object
     *
     *@param  init  Description of Parameter
     */
    public NetbeansSelectedFileSet(Frame view, JavaDataObject[] buffers) {
        this.view = view;
        this.buffers = buffers;
    }


    /**
     *  Gets the TypeSummaryArray attribute of the SelectedFileSet object
     *
     *@return    The TypeSummaryArray value
     */
    public TypeSummary[] getTypeSummaryArray() {
        TypeSummary[] typeSummaryArray = new TypeSummary[buffers.length];

        for (int ndx = 0; ndx < buffers.length; ndx++) {
            TypeSummary typeSummary = getTypeSummaryFromBuffer(buffers[ndx]);
            if (typeSummary == null) {
                return null;
            }
            typeSummaryArray[ndx] = typeSummary;
        }

        return typeSummaryArray;
    }


    /**
     *  Gets the AllJava attribute of the SelectedFileSet object
     *
     *@return    The AllJava value
     */
    public boolean isAllJava() {
      System.out.println("JEditSelectedFileSet.isAllJava()");
       for (int i=0; i<buffers.length; i++) {
          if (!javaBuffer(buffers[i])) {
             return false;
          }
       }
       return true;
    }


    private boolean javaBuffer(JavaDataObject buffer) {
        //return buffer.getPrimaryEntry().getFile().getExt().toLowerCase().equals("java");
        return true;
    }

    
    /**
     *  Gets the SingleJavaFile attribute of the SelectedFileSet object
     *
     *@return    The SingleJavaFile value
     */
    public boolean isSingleJavaFile() {
      System.out.println("JEditSelectedFileSet.isSingleJavaFile()");
       return (buffers.length==1 && javaBuffer(buffers[0]));
    }


    /**
     *  Gets the TypeSummaryFromNode attribute of the AddParentClassAction
     *  object
     *
     *@param  node  Description of Parameter
     *@return       The TypeSummaryFromNode value
     */
    private TypeSummary getTypeSummaryFromBuffer(JavaDataObject buffer) {
      System.out.println("JEditSelectedFileSet.getTypeSummaryFromBuffer("+ buffer+")");
        FileSummary fileSummary = reloadBuffer(buffer);
        if (fileSummary == null) {
            return null;
        }

        return getTypeSummary(fileSummary);
    }


    /**
    *  Description of the Method
    *
    *@param  node  Description of Parameter
    *@return       Description of the Returned Value
    */
   private FileSummary reloadBuffer(JavaDataObject buffer) {
      System.out.println("JEditSelectedFileSet.reloadBuffer("+ buffer+")");
      StringReader reader = new StringReader(IDEPlugin.getText(view, buffer));
      return reloadFile(new java.io.File(IDEPlugin.getFilePathForBuffer(buffer)), reader);
   }


}

