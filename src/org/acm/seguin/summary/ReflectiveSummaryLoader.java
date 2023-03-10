/*
 *  ====================================================================
 *  The JRefactory License, Version 1.0
 *
 *  Copyright (c) 2001 JRefactory.  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 *  3. The end-user documentation included with the redistribution,
 *  if any, must include the following acknowledgment:
 *  "This product includes software developed by the
 *  JRefactory (http://www.sourceforge.org/projects/jrefactory)."
 *  Alternately, this acknowledgment may appear in the software itself,
 *  if and wherever such third-party acknowledgments normally appear.
 *
 *  4. The names "JRefactory" must not be used to endorse or promote
 *  products derived from this software without prior written
 *  permission. For written permission, please contact seguin@acm.org.
 *
 *  5. Products derived from this software may not be called "JRefactory",
 *  nor may "JRefactory" appear in their name, without prior written
 *  permission of Chris Seguin.
 *
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
 *  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 *  ====================================================================
 *
 *  This software consists of voluntary contributions made by many
 *  individuals on behalf of JRefactory.  For more information on
 *  JRefactory, please see
 *  <http://www.sourceforge.org/projects/jrefactory>.
 */

package org.acm.seguin.summary;

import java.io.File;
import java.lang.reflect.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;

/**
 *  Creates a summary of a class using reflection
 *
 *@author     Mike Atkinson
 *@created    August 28, 2003
 *@version    $Id: ReflectiveSummaryLoader.java,v 1.7 2004/05/04 15:52:26 mikeatkinson Exp $
 *@since      2.8.00
 */
public class ReflectiveSummaryLoader {

    /**
     *  Constructor for the package summary
     *
     *@since               2.8.00
     */
    private ReflectiveSummaryLoader() { }


    /**
     *  Description of the Method
     *
     *@param  className                   Description of the Parameter
     *@return                             Description of the Return Value
     *@exception  ClassNotFoundException  Description of the Exception
     */
    public static TypeSummary loadType(String className, ClassLoader classLoader) throws ClassNotFoundException {
        //System.out.println("ReflectiveSummaryLoader.loadType(" + className + ","+ classLoader+")");
        TypeSummary thisType = null;
        Class clazz = null;
        PackageSummary parentSummary = null;
        try {
           clazz = classLoader.loadClass(className);
           //Class clazz = Class.forName(className);
           //System.out.println("  clazz=" + clazz);
           Class[] subClasees = clazz.getDeclaredClasses();
           Constructor[] constructors = clazz.getDeclaredConstructors();
           Field[] fields = clazz.getDeclaredFields();
           Method[] methods = clazz.getDeclaredMethods();
           Class[] interfaces = clazz.getInterfaces();
           Class superclass = clazz.getSuperclass();
           int modifiers = clazz.getModifiers();
           parentSummary = new PackageSummary(clazz.getPackage().getName());
           thisType = new TypeSummary(parentSummary, null);
           thisType.setName(className);
           thisType.setInterface(clazz.isInterface());
           thisType.setModifiers(modifiers);
           for (int i = 0; i < methods.length; i++) {
               MethodSummary methodSummary = new MethodSummary(thisType);
               methodSummary.setModifiers(methods[i].getModifiers());
               Class rt = methods[i].getReturnType();
               methodSummary.setReturnType(new TypeDeclSummary(methodSummary, rt));
               methodSummary.setName(methods[i].getName());
               Class[] parameters = methods[i].getParameterTypes();
               for (int j = 0; j < parameters.length; j++) {
                   ParameterSummary parameterSummary = new ParameterSummary(methodSummary, new TypeDeclSummary(methodSummary, parameters[j]), "");
                   methodSummary.add(parameterSummary);
               }
               thisType.add(methodSummary);
           }
   
           for (int i = 0; i < fields.length; i++) {
               FieldSummary fieldSummary = new FieldSummary(thisType, new TypeDeclSummary(thisType, fields[i].getClass()), fields[i].getName());
               fieldSummary.setModifiers(fields[i].getModifiers());
               thisType.add(fieldSummary);
           }
           //System.out.println("  => " + thisType);
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println("  className="+className+", clazz=" + clazz);
           if (clazz != null) {
              System.err.println("  clazz.getPackage()="+clazz.getPackage());
           }
           if (parentSummary==null) {
              parentSummary = new PackageSummary("<unknown>");
           }
           thisType = new TypeSummary(parentSummary, null);
           thisType.setName(className);
           thisType.setInterface(false);
        }

        return thisType;
    }

}

