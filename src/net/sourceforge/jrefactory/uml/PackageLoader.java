/* ====================================================================
 * The JRefactory License, Version 1.0
 *
 * Copyright (c) 2003 JRefactory.  All rights reserved.
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
package net.sourceforge.jrefactory.uml;

import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.StringTokenizer;
import net.sourceforge.jrefactory.uml.line.AssociationRelationship;
import net.sourceforge.jrefactory.uml.line.ImplementsRelationship;
import net.sourceforge.jrefactory.uml.line.InheretenceRelationship;
import net.sourceforge.jrefactory.uml.line.SegmentedLine;
import org.acm.seguin.awt.ExceptionPrinter;
import org.acm.seguin.ide.common.SummaryLoaderThread;

import org.acm.seguin.project.Project;
import org.acm.seguin.project.ProjectClassLoader;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.ImportSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.ReflectiveSummaryLoader;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeDeclSummary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.util.FileSettings;



/**
 *  Loads a UMLPackage panel from a package summary
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: PackageLoader.java,v 1.3 2004/05/04 15:41:36 mikeatkinson Exp $
 */
public class PackageLoader implements Runnable {
   private UMLPackage packagePanel;
   private int defaultX = 20;
   private int defaultY = 20;
   private boolean loaded = false;

   private PackageSummary loadSummary;
   private File loadFile;
   private Reader loadStream;

   private ProjectClassLoader pcl = null;


   /**
    *  Constructor for the PackageLoader object
    *
    * @param  panel  the panel that we are loading
    * @param  input  Description of Parameter
    * @since         2.9.12
    */
   public PackageLoader(UMLPackage panel, Reader input) {
      packagePanel = panel;
      loadSummary = null;
      loadStream = input;
      loadFile = null;
   }


   /**
    *  Constructor for the PackageLoader object
    *
    * @param  panel     the panel that we are loading
    * @param  filename  Description of Parameter
    * @since            2.9.12
    */
   public PackageLoader(UMLPackage panel, String filename) {
      packagePanel = panel;
      loadSummary = null;
      loadStream = null;
      loadFile = new File(filename);
   }


   /**
    *  Constructor for the PackageLoader object
    *
    * @param  panel    the panel that we are loading
    * @param  summary  Description of Parameter
    * @since           2.9.12
    */
   public PackageLoader(UMLPackage panel, PackageSummary summary) {
      packagePanel = panel;
      loadSummary = summary;
      loadStream = null;
      loadFile = null;
   }


   /**
    *  Main processing method for the PackageLoader object
    *
    * @since    2.9.12
    */
   public void run() {
      /*
         *  Don't run this until we have completed loading the
         *  summaries from disk
         */
      SummaryLoaderThread.waitForLoading();

      synchronized (PackageLoader.class) {
         packagePanel.setLoading(true);
         packagePanel.clear();

         if (loadSummary != null) {
            load(loadSummary);
         }
         if (loadFile != null) {
            load(loadFile);
         }
         if (loadStream != null) {
            load(loadStream);
         }

         packagePanel.updateListeners();
         packagePanel.updateClassListPanel();
         packagePanel.setLoading(false);
         packagePanel.repaint();
      }
   }


   /**
    *  Returns the UMLType panel associated with a summary or null if none is available
    *
    * @param  current  Description of Parameter
    * @param  summary  the type declaration
    * @return          the panel associated with the type
    * @since           2.9.12
    */
   private UMLType getUMLType(TypeSummary current, TypeDeclSummary summary) {
      if (summary != null) {
         TypeSummary typeSummary = GetTypeSummary.query(summary);
         if (typeSummary == null) {
            if (pcl == null) {
               pcl = new ProjectClassLoader(Project.getProject("default"));
            }
            //System.out.println("package="+summary.getPackage());
            //System.out.println("name="+summary.getName());
            //System.out.println("getLongName="+summary.getLongName());
            String fullName = summary.getLongName();
            Summary fs = current;
            while ((fs != null) && !(fs instanceof FileSummary)) {
               fs = fs.getParent();
            }
            FileSummary fileSummary = (FileSummary)fs;

            if (fileSummary != null) {
               Iterator i = fileSummary.getImports();
               if (i != null) {
                  while (i.hasNext()) {
                     ImportSummary is = (ImportSummary)i.next();
                     String type = is.getType();
                     if (summary.getName().equals(type)) {
                        fullName = is.getPackage().getName();
                        break;
                     } else {
                        try {
                           String testName = is.getPackage().getName() + "." + summary.getName();
                           //System.out.println("testName="+testName);
                           //typeSummary = ReflectiveSummaryLoader.loadType(testName);
                           pcl.loadClass(testName);
                           fullName = testName;
                           break;
                        } catch (ClassNotFoundException e) {
                        }
                     }
                  }
               }
            }
            //System.out.println("fullName="+fullName);
            try {
               typeSummary = ReflectiveSummaryLoader.loadType(fullName, pcl);
               //typeSummary = pcl.loadClass(fullName);
            } catch (ClassNotFoundException e) {
               //e.printStackTrace(); // FIXME: don't print stack trace
            }
         }
         if (typeSummary != null) {
            UMLType typePanel = packagePanel.findType(typeSummary);
            if (typePanel == null) {
               typePanel = addType(packagePanel, typeSummary, true);
            }
            return typePanel;
         }
      }

      return null;
   }


   /**
    *  Returns the location of this class
    *
    * @param  umlType  the type panel
    * @param  summary  the type summary
    * @return          the point where this type panel should be located
    * @since           2.9.12
    */
   private Point getLocation(UMLType umlType, TypeSummary summary) {
      Dimension dim = umlType.getPreferredSize();
      Point result = new Point(defaultX, defaultY);
      defaultX += (20 + dim.width);
      return result;
   }


   /**
    *  Reload the summaries
    *
    * @param  summary  the package summary
    * @since           2.9.12
    */
   private void load(PackageSummary summary) {
      if (summary != null) {
         defaultPositions(summary);
      }

      loadPositions(getFile(packagePanel));
   }


   /**
    *  Reload the summaries
    *
    * @param  file  Description of Parameter
    * @since        2.9.12
    */
   private void load(File file) {
      loadPositions(file);
   }


   /**
    *  Reload the summaries
    *
    * @param  input  Description of Parameter
    * @since         2.9.12
    */
   private void load(Reader input) {
      loadPositions(input);
   }


   /**
    *  Loads all the classes into their default positions
    *
    * @param  summary  the package that is being loaded
    * @since           2.9.12
    */
   private void defaultPositions(PackageSummary summary) {
      //  Add all the types
      Iterator iter = summary.getFileSummaries();
      if (iter != null) {
         while (iter.hasNext()) {
            addFile((FileSummary)iter.next());
         }
      }

      loadInheretence();
      loadImplements();

      packagePanel.resetPositions();
      packagePanel.rearragePositions(2000, 50, 1.0);
      packagePanel.reset();

      loaded = true;
   }


   /**
    *  Adds all the types in a file
    *
    * @param  fileSummary  the file summary
    * @since               2.9.12
    */
   private void addFile(FileSummary fileSummary) {
      Iterator iter = fileSummary.getTypes();
      if (iter != null) {
         while (iter.hasNext()) {
            addType(packagePanel, (TypeSummary)iter.next(), false);
         }
      }
   }


   /**
    *  Adds a UML type
    *
    * @param  pack         The feature to be added to the type attribute
    * @param  typeSummary  the type summary being added
    * @param  foreign      whether this type is in this package (true means it is from a different package
    * @return              The panel
    * @since               2.9.12
    */
   private UMLType addType(UMLPackage pack, TypeSummary typeSummary, boolean foreign) {
      UMLType umlType = new UMLType(pack, typeSummary, new UMLSettings(), foreign);
      packagePanel.add(umlType);
      umlType.setLocation(getLocation(umlType, typeSummary));

      return umlType;
   }


   /**
    *  Loads the inheritence relationships
    *
    * @since    2.9.12
    */
   private void loadInheretence() {
      //System.out.println("loadImplements()");
      UMLType[] typeList = packagePanel.getTypes();

      for (int ndx = 0; ndx < typeList.length; ndx++) {
         //System.out.println("type=" + typeList[ndx].getName());
         TypeSummary current = typeList[ndx].getSummary();
         TypeDeclSummary parent = current.getParentClass();
         UMLType typePanel = getUMLType(current, parent);
         if (typePanel != null) {
            //System.out.println("  uinherits from =" + typePanel.getName());
            packagePanel.add(new InheretenceRelationship(typeList[ndx], typePanel));
         }
      }
   }


   /**
    *  Loads the inheritence relationships
    *
    * @since    2.9.12
    */
   private void loadImplements() {
      //System.out.println("loadImplements()");
      UMLType[] typeList = packagePanel.getTypes();

      for (int ndx = 0; ndx < typeList.length; ndx++) {
         //System.out.println("type=" + typeList[ndx].getName());
         if (typeList[ndx].isForeign()) {
            //System.out.println("  - is foreign");
            continue;
         }

         TypeSummary current = typeList[ndx].getSummary();
         Iterator iter = current.getImplementedInterfaces();
         if (iter != null) {
            while (iter.hasNext()) {
               TypeDeclSummary next = (TypeDeclSummary)iter.next();
               //System.out.println("  getImplementedInterface=" + next.getName());
               UMLType typePanel = getUMLType(current, next);
               if (typePanel != null) {
                  SegmentedLine nextLine;
                  if (current.isInterface()) {
                     nextLine = new InheretenceRelationship(typeList[ndx], typePanel);
                  } else {
                     nextLine = new ImplementsRelationship(typeList[ndx], typePanel);
                  }

                  packagePanel.add(nextLine);
               }
            }
         }
      }
   }


   /**
    *  Loads the package from disk
    *
    * @param  inputFile  Description of Parameter
    * @since             2.9.12
    */
   private void loadPositions(File inputFile) {
      //System.err.println("loadPositions("+inputFile+")");
      try {
         FileReader fr = new FileReader(inputFile);
         BufferedReader input = new BufferedReader(fr);
         loadPositions(input);
      } catch (FileNotFoundException fnfe) {
         //  This is a normal and expected condition
      } catch (IOException ioe) {
         ExceptionPrinter.print(ioe, false);
      }
   }


   /**
    *  Loads the package from disk
    *
    * @param  inputStream  Description of Parameter
    * @since               2.9.12
    */
   private void loadPositions(Reader inputStream) {
      try {
         if (!(inputStream instanceof BufferedReader)) {
            inputStream = new BufferedReader(inputStream);
         }
         loadPositions(inputStream);
         inputStream.close();
      } catch (IOException ioe) {
         ExceptionPrinter.print(ioe, false);
      }
   }


   /**
    *  Loads the package from disk
    *
    * @param  input            Description of Parameter
    * @exception  IOException  Description of Exception
    * @since                   2.9.12
    */
   private void loadPositions(BufferedReader input) throws IOException {
      String line = input.readLine();
      while (line != null) {
         //  Decide what to do
         char ch = line.charAt(0);
         if (ch == 'P') {
            positionPanel(line);
         } else if (ch == 'S') {
            positionLine(line);
         } else if (ch == 'A') {
            positionAttribute(line);
         } else if (ch == 'V') {
            loadVersion(line);
         }

         //  Read the next line
         line = input.readLine();
      }
   }


   /**
    *  position the type in the UMLPackage
    *
    * @param  buffer  the line that describes where to position the type
    * @since          2.9.12
    */
   private void positionPanel(String buffer) {
      StringTokenizer tok = new StringTokenizer(buffer, "[]{},\n");
      String code = tok.nextToken();
      String id = tok.nextToken();
      String x = tok.nextToken();
      String y = tok.nextToken();
      //System.out.println(code + "," + id + "," + x + "," + y);
      String visible = "true";
      try {
         visible = tok.nextToken();
         //System.out.println("visible=" + visible);
      } catch (Exception e) {
      }
      String viewPrivate = "true";
      try {
         viewPrivate = tok.nextToken();
         //System.out.println("viewPrivate=" + viewPrivate);
      } catch (Exception e) {
      }
      //System.out.println(code + "," + id + "," + x + "," + y + "," + visible + "," + viewPrivate);

      UMLType type = packagePanel.find(id);
      if (type == null) {
         //System.err.println("  - cant' find "+id);
         return;
      }
      Point pt = type.getLocation();
      int nX = pt.x;
      int nY = pt.y;

      try {
         nX = Integer.parseInt(x);
         nY = Integer.parseInt(y);
      } catch (NumberFormatException nfe) {
      }

      //System.err.println("nX="+nX+", nY="+nY);
      type.setLocation(nX, nY);
      type.setVisible("true".equals(visible));
      type.setViewPrivate("true".equals(viewPrivate));
      type.validate();
   }


   /**
    *  Position the line
    *
    * @param  buffer  the line read from the file
    * @since          2.9.12
    */
   private void positionLine(String buffer) {
      StringTokenizer tok = new StringTokenizer(buffer, "[]{}\n");
      String code = tok.nextToken();
      String pair = tok.nextToken();
      String position = tok.nextToken();

      tok = new StringTokenizer(pair, ",");
      String first = tok.nextToken();
      String second = tok.nextToken();

      SegmentedLine line = packagePanel.find(first, second);
      if (line != null) {
         line.load(position);
      }
   }


   /**
    *  The attribute
    *
    * @param  buffer  the input string
    * @since          2.9.12
    */
   private void positionAttribute(String buffer) {
      StringTokenizer tok = new StringTokenizer(buffer, "[]{}\n");
      String code = tok.nextToken();
      String pair = tok.nextToken();
      String position = tok.nextToken();
      String fieldPosition = tok.nextToken();

      tok = new StringTokenizer(pair, ",");
      String first = tok.nextToken();
      String second = tok.nextToken();

      UMLType type = packagePanel.find(first);
      if (type == null) {
         return;
      }

      UMLField field = type.getField(second);
      if (field == null) {
         return;
      }

      field.setAssociation(true);
      AssociationRelationship ar = type.convertToAssociation(packagePanel, field);
      ar.load(position);

      //  Set the field label position
      tok = new StringTokenizer(fieldPosition, ",");
      String x = tok.nextToken();
      String y = tok.nextToken();
      try {
         field.setLocation(Integer.parseInt(x), Integer.parseInt(y));
      } catch (NumberFormatException nfe) {
      }
   }


   /**
    *  Loads a version line
    *
    * @param  buffer  the input line that contains the version number and package name
    * @since          2.9.12
    */
   private void loadVersion(String buffer) {
      StringTokenizer tok = new StringTokenizer(buffer, "[]:\n");
      String key = tok.nextToken();

      String versionID = tok.nextToken();
      String packageName = "";
      if (tok.hasMoreTokens()) {
         packageName = tok.nextToken();
      }

      //System.out.println("Loading:  " + packageName + " from a file with version " + versionID);
      if (!loaded) {
         PackageSummary summary = PackageSummary.getPackageSummary(packageName);
         packagePanel.setSummary(summary);
         defaultPositions(summary);
      }
   }


   /**
    *  Gets the File attribute of the PackageLoader object
    *
    * @param  panel  Description of Parameter
    * @return        The File value
    * @since         2.9.12
    */
   public static File getFile(UMLPackage panel) {
      return getFile(panel.getSummary());
   }


   /**
    *  Gets the File attribute of the PackageSummary object
    *
    * @param  summary  Description of Parameter
    * @return          The File value
    * @since           2.7.04
    */
   public static File getFile(PackageSummary summary) {
      File dir = summary.getDirectory();
      File inputFile;
      if (dir == null) {
         dir = new File(FileSettings.getRefactorySettingsRoot(), "UML");
         dir.mkdirs();
         inputFile = new File(dir, summary.getName() + ".uml");
      } else {
         inputFile = new File(summary.getDirectory(), "package.uml");
      }

      return inputFile;
   }
}

