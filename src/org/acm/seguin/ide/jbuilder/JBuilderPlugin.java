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

import com.borland.primetime.PrimeTime;
import com.borland.primetime.actions.ActionGroup;
import com.borland.primetime.actions.UpdateAction;
import com.borland.primetime.editor.EditorManager;
import com.borland.primetime.ide.Context;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.ide.ProjectView;
import com.borland.primetime.node.DuplicateNodeException;
import com.borland.primetime.node.FileNode;
import com.borland.primetime.node.FileType;
import com.borland.primetime.node.Node;
import com.borland.primetime.node.Project;
import com.borland.primetime.vfs.InvalidUrlException;
import com.borland.primetime.vfs.Url;
import java.io.File;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JComponent;
import org.acm.seguin.ide.common.action.GenericAction;
import org.acm.seguin.ide.common.MultipleDirClassDiagramReloader;
import org.acm.seguin.ide.common.PackageNameLoader;
import org.acm.seguin.ide.common.SourceBrowser;
import org.acm.seguin.ide.common.UMLIcon;
import org.acm.seguin.ide.common.action.CurrentSummary;
import org.acm.seguin.ide.common.action.ExtractMethodAction;
import net.sourceforge.jrefactory.action.PrettyPrinterAction;
import org.acm.seguin.ide.jbuilder.refactor.JBuilderCurrentSummary;
import org.acm.seguin.ide.jbuilder.refactor.JBuilderRefactoringFactory;
import org.acm.seguin.ide.jbuilder.refactor.MenuBuilder;
//import org.acm.seguin.tools.RefactoryInstaller;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.util.FileSettings;
import org.acm.seguin.ide.command.ExitMenuSelection;
import org.acm.seguin.ide.common.IDEPlugin;

/**
 *  File node representing the JBuilder Plugin
 *
 *@author     Mike Atkinson
 *@created    October 06, 2003
 */
public class JBuilderPlugin extends com.borland.primetime.viewer.AbstractNodeViewer {
    private static JFrame frame;
    private static JRefactory refactory;


    public static String JAVASTYLE_DIR = "";
    private UMLPackage packageDiagram;
    private String packageName;


    public JComponent createViewerComponent() {
       return refactory;
    }
    
    
    public JComponent createStructureComponent() {
       return refactory;
    }


    public JBuilderPlugin(Context context) {
       super(context);

       JRefactory.log("JBuilderPlugin("+context+")");
       Node top = context.getNode();
       Node parent = top;
       while (parent != null) {
          top = parent;
          parent = parent.getParent();
       }
        
       printchild(top, 0);
       JRefactory.log("JBuilderPlugin() - OK");
    }


    public String getViewerTitle() {
       return "JRefactory";
    }


    /**
     *  Constructor for the JBuilderPlugin object
     *
     *@param  project                     Description of Parameter
     *@param  parent                      Description of Parameter
     *@param  url                         Description of Parameter
     *@exception  DuplicateNodeException  Description of Exception
     */
/*
    public JBuilderPlugin(Project project,
            Node parent,
            Url url)
             throws DuplicateNodeException {
        super(project, parent, url);

        JRefactory.log("JBuilderPlugin("+project+", "+parent+", "+url+")");

        Node top = parent;
        while (parent != null) {
           top = parent;
           parent = parent.getParent();
        }
        
        printchild(top, 0);

        MultipleDirClassDiagramReloader reloader = UMLNodeViewerFactory.getFactory().getReloader();
        if (!reloader.isNecessary()) {
            reloader.setNecessary(true);
            reloader.reload();
        }

        PackageNameLoader loader = new PackageNameLoader();
        packageName = loader.load(url.getFile());
        JRefactory.log("JBuilderPlugin() - OK");
    }
*/

    private void printchild(Node node, int x) {
       //for (int i=0; i<x; i++) {
       //   System.out.print(" ");
       //}
       JRefactory.log("node="+node);
       Node[] children = node.getChildren();
       for (int i=0; i<children.length; i++) {
           printchild(children[i], x+3);
       }
    }


    /**
     *  Sets the Diagram attribute of the JBuilderPlugin object
     *
     *@param  diagram  The new Diagram value
     */
    public void setDiagram(UMLPackage diagram) {
        packageDiagram = diagram;
    }


    /**
     *  Setup the key maps
     *
     *@param  prettyPrint    the pretty print action
     *@param  extractMethod  the extract method action
     */
    private static void setupKeys(Action prettyPrint, Action extractMethod) {
        ModifyKeyBinding m = new ModifyKeyBinding(prettyPrint, extractMethod);
        EditorManager.addPropertyChangeListener(m);
    }


    /**
     *  Gets the Diagram attribute of the JBuilderPlugin object
     *
     *@return    The Diagram value
     */
    public UMLPackage getDiagram() {
        return packageDiagram;
    }


    /**
     *  Gets the DisplayIcon attribute of the JBuilderPlugin object
     *
     *@return    The DisplayIcon value
     */
    public Icon getDisplayIcon() {
        return new UMLIcon();
    }


    /**
     *  Gets the DisplayName attribute of the JBuilderPlugin object
     *
     *@return    The DisplayName value
     */
    public String getDisplayName() {
        if ((PrimeTime.CURRENT_MAJOR_VERSION >= 4) &&
                (PrimeTime.CURRENT_MINOR_VERSION >= 1)) {
            return packageName + ".uml";
        }
        return packageName;
    }


    /**
     *  Determines if the diagram has been modified
     *
     *@return    true if it has
     */
    public boolean isModified() {
        if (packageDiagram == null) {
            return false;
        }
        return packageDiagram.isDirty();
    }


    /**
     *  Gets the Persistant attribute of the JBuilderPlugin object
     *
     *@return    The Persistant value
     */
    public boolean isPersistant() {
        return false;
    }


    /**
     *  Description of the Method
     */
    private static void cleanJBuilderSetting() {
        File file = new File(FileSettings.getRefactorySettingsRoot(), "jbuilder.settings");
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     *  Initialize the open tools
     *
     *@param  majorVersion  the version number
     *@param  minorVersion  the version number
     */
    public static void initOpenTool(byte majorVersion, byte minorVersion) {
        JRefactory.log("initOpenTool("+majorVersion+", "+ minorVersion+")");
        if (majorVersion != PrimeTime.CURRENT_MAJOR_VERSION) {
            return;
        }
        JRefactory.log("Version:  " + majorVersion + "." + minorVersion +
                "     (Primetime:  " + PrimeTime.CURRENT_MAJOR_VERSION + "." +
                PrimeTime.CURRENT_MINOR_VERSION + ")");

        java.util.Properties props = System.getProperties();
        JAVASTYLE_DIR = new File(props.getProperty("user.home") + File.separator + ".jbuilder" + File.separator + "javastyle").getAbsolutePath();
        FileSettings.setSettingsRoot(JAVASTYLE_DIR);

        //  Create the property files
        initJRefactory1();
        cleanJBuilderSetting();

        //  Register the source browser
        JBuilderBrowser sourceBrowser = new JBuilderBrowser();
        SourceBrowser.set(sourceBrowser);
        CurrentSummary.register(new JBuilderCurrentSummary());

        // Initialize OpenTool here...
        FileType.registerFileType("uml", new FileType("Class Diagram",
                JBuilderPlugin.class,
                new TestObject(),
                new UMLIcon()));

        FileNode.registerFileNodeClass("uml",
                "Class Diagram",
                JBuilderPlugin.class,
                new UMLIcon());

        Browser.registerNodeViewerFactory(UMLNodeViewerFactory.getFactory(), true);
        Browser.addStaticBrowserListener(new NewProjectAdapter());

        //  Adds a menu item
        ActionGroup group = new ActionGroup("JRefactory");
        Action prettyPrintAction = new PrettyPrinterAction();
        prettyPrintAction.putValue(UpdateAction.ACCELERATOR, prettyPrintAction.getValue(GenericAction.ACCELERATOR));
        group.add(prettyPrintAction);
        
        Action jrefactoryAction = new JRefactoryAction();
        group.add(jrefactoryAction);
        
        ActionGroup csGroup = new ActionGroup("Coding Standards");
        csGroup.setPopup(true);
        csGroup.add(new CSCheckBufferAction());
        csGroup.add(new CSCheckAllBuffersAction());
        csGroup.add(new CSCheckDirAction());
        csGroup.add(new CSCheckDirRecurseAction());
        group.add(csGroup);
        
        ActionGroup cpdGroup = new ActionGroup("Cut&Paste detector");
        cpdGroup.setPopup(true);
        cpdGroup.add(new CPDCheckBufferAction());
        cpdGroup.add(new CPDCheckAllBuffersAction());
        cpdGroup.add(new CPDCheckDirAction());
        cpdGroup.add(new CPDCheckDirRecurseAction());
        group.add(cpdGroup);
        
        ActionGroup refactorGroup = new ActionGroup("Refactor");
        Action extractMethodAction = new ExtractMethodAction();
        extractMethodAction.putValue(UpdateAction.ACCELERATOR, extractMethodAction.getValue(GenericAction.ACCELERATOR));
        refactorGroup.add(extractMethodAction);
        group.add(refactorGroup);
        
        ActionGroup umlGroup = new ActionGroup("UML");
        umlGroup.setPopup(true);
        umlGroup.add(new ReloadAction());
        umlGroup.add(new NewClassDiagramAction());
        umlGroup.add(MenuBuilder.build());
        umlGroup.add(new UndoAction());
        umlGroup.add(new PrintAction());
        umlGroup.add(new JPGFileAction());
        umlGroup.add(umlGroup);
        ActionGroup zoomGroup = new ActionGroup("Zoom");
        zoomGroup.setPopup(true);
        zoomGroup.add(new ZoomAction(0.1));
        zoomGroup.add(new ZoomAction(0.25));
        zoomGroup.add(new ZoomAction(0.5));
        zoomGroup.add(new ZoomAction(1.0));
        group.add(zoomGroup);
        group.add(new PrettyPrinterConfigAction());
        group.add(new AboutAction());
        Browser.addMenuGroup(8, group);

        ProjectView.registerContextActionProvider(new ProjectViewRefactorings());
        JBuilderRefactoringFactory.register();

        setupKeys(prettyPrintAction, extractMethodAction);
        initJRefactory2();
        refactory.setSourceBrowser(sourceBrowser);
    }


    public static void initJRefactory1() {
        frame = new JFrame();
        frame.setTitle("JRefactory");
        refactory = new JRefactory(frame);
    }
    public static void initJRefactory2() {
        IDEPlugin.setPlugin(refactory);
        frame.getContentPane().add(refactory);

        frame.addWindowListener(new ExitMenuSelection());
        frame.pack();
        refactory.astv.initDividers();
    }


    public static void showJRefactory() {
        //initJRefactory1();
        refactory.astv.initDividers();
        frame.show();
    }


    /**
     *  Saves the diagram to the disk
     *
     *@exception  IOException          Description of Exception
     *@exception  InvalidUrlException  Description of Exception
     */
    public void save() throws IOException, InvalidUrlException {
        if (packageDiagram != null) {
            packageDiagram.save();
        }
    }


    /**
     *  Description of the Method
     *
     *@param  url                         Description of Parameter
     *@exception  IOException             Description of Exception
     *@exception  InvalidUrlException     Description of Exception
     *@exception  DuplicateNodeException  Description of Exception
     */
    public void saveAs(Url url) throws IOException,
            InvalidUrlException, DuplicateNodeException {
        save();
    }
}

