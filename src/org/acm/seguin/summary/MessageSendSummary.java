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
package org.acm.seguin.summary;

import net.sourceforge.jrefactory.ast.ASTName;
import org.acm.seguin.summary.query.GetTypeSummary;

/**
 *  Summarize a message send summary.
 *
 *@author     Chris Seguin
 *@created    June 23, 1999
 */
public class MessageSendSummary extends Summary {
    //  Instance Variables
    private String objectName;
    private String packageName;
    private String messageName;
    private String first;


    /**
     *  Creates a message send summary from an ASTName object.
     *
     *@param  parentSummary  the parent summary
     *@param  nameNode       the ASTName object
     */
    public MessageSendSummary(Summary parentSummary, ASTName nameNode) {
        //  Initialize the parent class
        super(parentSummary);

        //  Initialize the variables
        messageName = null;
        objectName = null;
        packageName = null;

        //  Local Variables
        int numChildren = nameNode.getNameSize();

        //  Determine the name of the message
        messageName = nameNode.getNamePart(numChildren - 1).intern();

        //  Determine the name of the object (or class)
        if (numChildren > 1) {
            first = nameNode.getNamePart(0);
            objectName = nameNode.getNamePart(numChildren - 2).intern();

            //  Extract the package
            if (numChildren > 2) {
                StringBuffer buffer = new StringBuffer(nameNode.getNamePart(0));
                for (int ndx = 1; ndx < numChildren - 2; ndx++) {
                    buffer.append(".");
                    buffer.append(nameNode.getNamePart(ndx));
                }
                packageName = buffer.toString().intern();
            }
        } else {
            first = null;
        }
    }


    /**
     *  Get the package name
     *
     *@return    a string containing the name of the package
     */
    public String getPackageName() {
        return packageName;
    }


    /**
     *  Get the name of the type
     *
     *@return    a string containing the name of the type
     */
    public String getObjectName() {
        return objectName;
    }


    /**
     *  Get the name of the type
     *
     *@return    a string containing the name of the type
     */
    public String getMessageName() {
        return messageName;
    }


    /**
     *  Gets a type declaration if this reference is to a package and type pair
     *
     *@return    the summary
     */
    public TypeDeclSummary getTypeDecl() {
        if (packageName == null) {
            if (objectName != null) {
                TypeDeclSummary result = new TypeDeclSummary(this, packageName, objectName);
                TypeSummary test = GetTypeSummary.query(result);
                if (test != null) {
                    return result;
                }
            }
            return null;
        }

        return new TypeDeclSummary(this, packageName, objectName);
    }


    /**
     *  Gets the Name attribute of the MessageSendSummary object
     *
     *@return    The Name value
     */
    public String getName() {
        return toString();
    }


    /**
     *  Convert this into a string
     *
     *@return    a string representation of the type
     */
    public String toString() {
        //  Start with the long name
        StringBuffer buffer = new StringBuffer();

        if (packageName != null) {
            buffer.append(packageName);
            buffer.append(".");
        }

        if (objectName != null) {
            buffer.append(objectName);
            buffer.append(".");
        }

        buffer.append(messageName);
        buffer.append("()");

        return buffer.toString();
    }


    /**
     *  Provide method to visit a node
     *
     *@param  visitor  the visitor
     *@param  data     the data for the visit
     *@return          some new data
     */
    public Object accept(SummaryVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }


    /**
     *  Check to see if it is equal
     *
     *@param  other  the other item
     *@return        true if they are equal
     */
    public boolean equals(Object other) {
        if (other instanceof MessageSendSummary) {
            MessageSendSummary mss = (MessageSendSummary) other;

            boolean sameObject = ((objectName == null) && (mss.objectName == null)) ||
                    ((objectName != null) && objectName.equals(mss.objectName));

            boolean samePackage = ((packageName == null) && (mss.packageName == null)) ||
                    ((packageName != null) && packageName.equals(mss.packageName));

            boolean sameMessage = ((messageName == null) && (mss.messageName == null)) ||
                    ((messageName != null) && messageName.equals(mss.messageName));

            return sameObject && samePackage && sameMessage;
        }
        return super.equals(other);
    }


    /**
     *  Gets the firstObject attribute of the MessageSendSummary object
     *
     *@return    The firstObject value
     */
    public String getFirstObject() {
        return first;
    }
}
