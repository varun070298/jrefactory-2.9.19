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

import java.io.File;
import java.io.StringReader;
import java.util.Iterator;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.TypeSummary;

/**
 *  For IDEs where the user can select a number of files in the project pane,
 *  this class handles the translation of those selected files into
 *  TypeSummaries and a determination of whether the selected files are .java
 *  files.
 *
 *@author     Chris Seguin
 *@author     <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 *@version    $Id: SelectedFileSet.java,v 1.3 2003/07/29 20:51:52 mikeatkinson Exp $ 
 *@created    October 18, 2001
 */
public abstract class SelectedFileSet {

    /**
     *  Returns the type summary that has the same name as the file summary
     *
     *@param  summary  the file summary
     *@return          the type summary
     */
    protected TypeSummary getTypeSummary(FileSummary summary) {
        Iterator iter = summary.getTypes();
        while (iter.hasNext()) {
            TypeSummary next = (TypeSummary) iter.next();
            String temp = next.getName() + ".java";
            if (temp.equals(summary.getName())) {
                return next;
            }
        }

        return null;
    }


    /**
     *  Gets the TypeSummaryArray attribute of the SelectedFileSet object
     *
     *@return    The TypeSummaryArray value
     */
    public abstract TypeSummary[] getTypeSummaryArray();


    /**
     *  Gets the AllJava attribute of the SelectedFileSet object
     *
     *@return    The AllJava value
     */
    public abstract boolean isAllJava();


    /**
     *  Gets the SingleJavaFile attribute of the SelectedFileSet object
     *
     *@return    The SingleJavaFile value
     */
    public abstract boolean isSingleJavaFile();


    /**
     *  Reloads the file summary
     *
     *@param  file   Description of Parameter
     *@param  input  Description of Parameter
     *@return        Description of the Returned Value
     */
    protected FileSummary reloadFile(File file, StringReader input) {
        return FileSummary.reloadFromBuffer(file, input);
    }
}
//  EOF