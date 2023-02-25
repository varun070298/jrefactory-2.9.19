/*
 *  JRefactory.java - a Java pretty printer and refactoring plugin for Netbeans
 *  Copyright (c) 1999 Andreas "Mad" Schaefer (andreas.schaefer@madplanet.ch)
 *  Copyright (c) 2000,2001 Dirk Moebius (dmoebius@gmx.net)
 *
 *  jEdit buffer options:
 *  :tabSize=4:indentSize=4:noTabs=false:maxLineLen=0:
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.acm.seguin.ide.netbeans;

import java.awt.BorderLayout;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.text.StyledDocument;
import javax.swing.tree.TreeNode;

import org.openide.ErrorManager;
import org.openide.cookies.*;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.*;
import org.openide.util.Lookup;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.MultiDataObject;
import org.openide.filesystems.FileUtil;
import org.openide.windows.*;
import org.openide.text.NbDocument;
import org.openide.text.Annotation;

import org.netbeans.modules.java.JavaDataObject;
import org.netbeans.modules.java.JavaEditor;
import org.netbeans.modules.java.JavaDataLoader;

import edu.umd.cs.findbugs.DetectorFactoryCollection;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;
import org.acm.seguin.ide.command.CommandLineSourceBrowser;
import org.acm.seguin.ide.common.ASTViewerPane;
import org.acm.seguin.ide.common.CPDDuplicateCodeViewer;
import org.acm.seguin.ide.common.CodingStandardsViewer;
import org.acm.seguin.ide.common.ExitOnCloseAdapter;
import org.acm.seguin.ide.common.IDEInterface;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.ide.common.PackageSelectorPanel;
import org.acm.seguin.ide.common.SourceBrowser;
import org.acm.seguin.ide.common.options.PropertiesFile;
import org.acm.seguin.util.FileSettings;
import org.acm.seguin.io.AllFileFilter;
import org.acm.seguin.pmd.RuleContext;
import org.acm.seguin.pmd.RuleViolation;
import org.acm.seguin.pmd.cpd.CPD;
import org.acm.seguin.pmd.cpd.FileFinder;
import org.acm.seguin.pmd.cpd.JavaLanguage;
import org.acm.seguin.summary.*;
import org.acm.seguin.tools.RefactoryInstaller;

/**
 *@author     Mike Atkinson (<a href="mailto:javastyle@ladyshot.demon.co.uk">
 *      Mike@ladyshot.demon.co.uk</a> )
 *@created    30 September 2003
 *@version    $Version: $
 *@since      1.0
 */

public class JRefactory extends TopComponent implements IDEInterface {
// Possible that you may break serialization of this class accidentally:
	private MySerObject state;
	private transient Properties ideProperties = new Properties();
	private transient JTabbedPane mainstage;
	private transient JPanel jRefactoryPanel;
	private transient CPDDuplicateCodeViewer cpdViewer;
	private transient CodingStandardsViewer csViewer;
	private transient ASTViewerPane astv;
	private transient Frame aView;
	private transient JRootPane findBugs = null;


	private transient Annotation prevAnnotation = null;
	private transient int prevLine = -1;
	/*
	 *  // The above assumes that the SomeType is safely serializable, e.g. String or Date.
	 *  // If it is some class of your own that might change incompatibly, use e.g.:
	 *  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	 *  super.readExternal(in);
	 *  NbMarshalledObject read = (NbMarshalledObject)in.readObject();
	 *  if (read != null) {
	 *  try {
	 *  setSomeState((SomeType)read.get());
	 *  } catch (Exception e) {
	 *  ErrorManager.getDefault().notify(e);
	 *  // If the problem would make this component inconsistent, use:
	 *  // throw new SafeException(e);
	 *  }
	 *  }
	 *  }
	 *  public void writeExternal(ObjectOutput out) throws IOException {
	 *  super.writeExternal(out);
	 *  Object toWrite;
	 *  try {
	 *  toWrite = new NbMarshalledObject(getSomeState());
	 *  } catch (Exception e) {
	 *  ErrorManager.getDefault().notify(e);
	 *  toWrite = null;
	 *  // Again you may prefer to use:
	 *  // throw new SafeException(e);
	 *  }
	 *  out.writeObject(toWrite);
	 *  }
	 */
	/*
	 *  // Use this to discard the component after restarts (make it nonpersistent):
	 *  private Object readResolve() throws ObjectStreamException {
	 *  return null;
	 *  // If you wish to conditionally discard it, make readExternal set
	 *  // or clear some flag acc. to the condition, then use:
	 *  // return discardFlag ? null : this;
	 *  // Singleton component using private static JRefactory theInstance:
	 *  // if (theInstance == null) theInstance = this;
	 *  // return theInstance;
	 *  }
	 */
	// ACTIONS

	/*
	 *  // If you wish to have extra actions appear in the window's
	 *  // popup menu, they can go here:
	 *  public SystemAction[] getSystemActions() {
	 *  SystemAction[] supe = super.getSystemActions();
	 *  SystemAction[] mine = new SystemAction[supe.length + 1];
	 *  System.arraycopy(supe, 0, mine, 0, supe.length);
	 *  mine[supe.length] = SystemAction.get(SomeActionOfMine.class);
	 *  return mine;
	 *  }
	 */
	/*
	 *  // Associate implementations with copying, searching, etc.:
	 *  protected void componentActivated() {
	 *  ((CallbackSystemAction)SystemAction.get(FindAction.class)).setActionPerformer(new ActionPerformer() {
	 *  public void performAction(SystemAction action) {
	 *  // search this component somehow
	 *  }
	 *  });
	 *  // similar for CopyAction, CutAction, DeleteAction, GotoAction, ReplaceAction, etc.
	 *  // for PasteAction, use:
	 *  // ((PasteAction)SystemAction.get(PasteAction.class)).setPasteTypes(new PasteType[] {...});
	 *  }
	 *  protected void componentDeactivated() {
	 *  // FindAction will be turned off by itself
	 *  // ((PasteAction)SystemAction.get(PasteAction.class)).setPasteTypes(null);
	 *  }
	 */
	/*
	 *  // If you want UndoAction and RedoAction to be enabled on this component:
	 *  public UndoRedo getUndoRedo() {
	 *  return new MyUndoRedo(this);
	 *  }
	 */
	// Printing, saving, compiling, etc.: use cookies on some appropriate node and
	// use this node as the node selection.



	/**
	 *  Description of the Field
	 */
	public static final String OPTION_RULES_PREFIX = "options.pmd.rules.";
	/**
	 *  Description of the Field
	 */
	public static final String OPTION_UI_DIRECTORY_POPUP = "pmd.ui.directorypopup";
	/**
	 *  Description of the Field
	 */
	public static final String DEFAULT_TILE_MINSIZE_PROPERTY = "pmd.cpd.defMinTileSize";
	/**
	 *  Description of the Field
	 */
	public static final String NAME = "javastyle";

	/**
	 *  Description of the Field
	 */
	public static File PRETTY_SETTINGS_FILE;
	/**
	 *  Description of the Field
	 */
	public static String JAVASTYLE_DIR = "";
	/*
	 *  public HelpCtx getHelpCtx() {
	 *  return new HelpCtx(JRefactory.class);
	 *  }
	 */
	/*
	 *  // If you are using CloneableTopComponent, probably you should override:
	 *  protected CloneableTopComponent createClonedObject() {
	 *  return new JRefactory();
	 *  }
	 *  protected boolean closeLast() {
	 *  // You might want to prompt the user first and maybe return false:
	 *  return true;
	 *  }
	 */
	// APPEARANCE

	/**
	 *  This method is called from within the constructor to initialize the form.
	 *  WARNING: Do NOT modify this code. The content of this method is always
	 *  regenerated by the FormEditor.
	 */

	// Variables declaration - do not modify
	// End of variables declaration

	// MODES AND WORKSPACES

	/*
	 *  // If you want it to open in a specific mode:
	 *  public static final String JRefactory_MODE = "JRefactory";
	 *  public void open(Workspace ws) {
	 *  super.open(ws);
	 *  if (ws == null) ws = WindowManager.getDefault().getCurrentWorkspace();
	 *  Mode m = ws.findMode(JRefactory_MODE);
	 *  if (m == null) {
	 *  try {
	 *  m = ws.createMode(JRefactory_MODE, // code name
	 *  NbBundle.getMessage(JRefactory.class, "LBL_mode_name"), // display name
	 *  new URL("nbresloc:/src/JRefactoryIcon.gif"));
	 *  } catch (MalformedURLException mfue) {
	 *  ErrorManager.getDefault().notify(mfue);
	 *  return;
	 *  }
	 *  // If you want it in a specific position:
	 *  // m.setBounds(...ws.getBounds()...);
	 *  }
	 *  m.dockInto(this);
	 *  }
	 */
	/*
	 *  // If you are not specifying a mode you may wish to use:
	 *  public Dimension getPreferredSize() {
	 *  return ...WindowManager.getDefault().getCurrentWorkspace().getBounds()...;
	 *  }
	 */
	/*
	 *  // If you want it to open on a specific workspace:
	 *  public static final String JRefactory_WORKSPACE = NbBundle.getMessage(JRefactory.class, "LBL_workspace_name");
	 *  public void open() {
	 *  WindowManager wm = WindowManager.getDefault();
	 *  Workspace ws = wm.findWorkspace(JRefactory_WORKSPACE);
	 *  if (ws == null)
	 *  ws = wm.createWorkspace(JRefactory_WORKSPACE);
	 *  open(ws);
	 *  ws.activate();
	 *  }
	 */
	// PERSISTENCE

	private static final long serialVersionUID = 5L;
	/**
	 *  Description of the Field
	 */

	/**
	 *  Description of the Field
	 */
	private static JRefactory jsPlugin = null;
	private static PropertiesFile properties = null;
	private static final File userDir = new File(System.getProperty("user.dir"));
	//private JPanel panel = null;
	private static Map propertiesMap = new HashMap();


	/**
	 *  Constructor for the JRefactory object
	 */
	public JRefactory() {
		//log("JRefactory()");
		initThisComponent();
	}


	// REMEMBER: You should have a public default constructor!
	// This is for externalization. If you have a nondefault
	// constructor for normal creation of the component, leave
	// in a default constructor that will put the component into
	// a consistent but unconfigured state, and make sure readExternal
	// initializes it properly. Or, be creative with writeReplace().
	/**
	 *  Constructor for the JRefactory object
	 *
	 *@param  view  Description of Parameter
	 */
	public JRefactory(Frame view) {
		//log("JRefactory(" + view + ")");
		aView = view;
		initThisComponent();
	}


	/**
	 *  Sets the View attribute of the JRefactory object
	 *
	 *@param  view  The new View value
	 */
	public void setView(Frame view) {
		cpdViewer.setView(view);
		csViewer.setView(view);
		astv.setView(view);
		findBugs = org.acm.seguin.findbugs.FindBugsFrame.createFindBugsPanel(view);
	}


	/**
	 *  Sets the line number
	 *
	 *@param  view        The new LineNumber value
	 *@param  buffer      The new LineNumber value
	 *@param  lineNumber  The new lineNumber value
	 */
	public void setLineNumber(Frame view, Object buffer, int lineNumber) {
		JavaDataObject jdo = (JavaDataObject) buffer;
		EditorCookie editor = (EditorCookie) jdo.getCookie(EditorCookie.class);
		editor.open();
		try {
			StyledDocument doc = editor.openDocument();
			JEditorPane pane = getEditorPane(buffer);
			int targetOffset = NbDocument.findLineOffset(doc, lineNumber);
			pane.setCaretPosition(targetOffset);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Sets the string in the IDE
	 *
	 *@param  view    The frame containing the IDE.
	 *@param  buffer  Description of Parameter
	 *@param  value   The new file contained in a string
	 */
	public void setText(Frame view, Object buffer, String value) {
		JEditorPane pane = getEditorPane(buffer);
		if (pane != null) {
			pane.setText(value);
		}
	}



	/**
	 *  Description of the Method
	 *
	 *@param  view    Description of Parameter
	 *@param  buffer  The new Selection value
	 *@param  start   The new Selection value
	 *@param  end     The new Selection value
	 */
	public void setSelection(Frame view, Object buffer, int start, int end) {
		log("setSelection(view, " + start + "," + end + ")");
		javax.swing.text.Caret caret = getCaret();
		caret.setDot(start);
		caret.moveDot(end);
		caret.setVisible(true);
		caret.setSelectionVisible(true);
		//for (int lineNo=start; lineNo<=end; lineNo++) {
                //    log("  line="+lineNo);
                //    addAnnotation(view, buffer, IDEInterface.CUT_AND_PASTE_DETECTOR, lineNo, "duplicate code");
		//}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view      Description of Parameter
	 *@param  fileName  The new Buffer value
	 */
	public void setBuffer(Frame view, Object fileName) {
		try {
			if (fileName instanceof JavaDataObject) {
				JavaDataObject jdo = (JavaDataObject) fileName;
				EditorCookie editor = (EditorCookie) jdo.getCookie(EditorCookie.class);
				//log("ec="+ec);
				editor.open();
				return;
			}
			DataObject dataObj = null;
			if (fileName instanceof String) {
				File file = new File((String) fileName);
				FileObject fileObj = FileUtil.fromFile(file)[0];
				//log("fileObj="+fileObj);
				if (fileObj != null) {
					dataObj = DataObject.find(fileObj);
				}
				if (dataObj == null) {
					JavaDataLoader dataLoader = new JavaDataLoader();
					//log("dataLoader="+dataLoader);
					dataObj = new JavaDataObject(fileObj, dataLoader);
				}
			}
			else if (fileName instanceof MultiDataObject) {
				dataObj = ((MultiDataObject) fileName);
			}
			//log("dataObj="+dataObj);
			if (dataObj != null) {
				JavaEditor editor = (JavaEditor) dataObj.getCookie(JavaEditor.class);
				//log("editor="+editor);
				editor.open();
			}
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(view, e.getMessage(), "JRefactory", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	/**
	 *  Gets the PersistenceType attribute of the JRefactory object
	 *
	 *@return    The PersistenceType value
	 */
	public int getPersistenceType() {
		return PERSISTENCE_ALWAYS;
	}


	/**
	 *  Gets the Frame attribute of the JRefactory object
	 *
	 *@return    The Frame value
	 */
	public Frame getFrame() {
		return aView;
	}


	/**
	 *  Gets the CodingStandardsViewer attribute of the JRefactory object
	 *
	 *@return    The CodingStandardsViewer value
	 */
	public CodingStandardsViewer getCodingStandardsViewer() {
		return csViewer;
	}


	/**
	 *  Gets the IDEProperty attribute of the IDEInterface object
	 *
	 *@param  prop  Description of Parameter
	 *@return       The IDEProperty value
	 */
	public String getIDEProperty(String prop) {
		//log("getIDEProperty(" + prop + ")");
		return ideProperties.getProperty(prop);
	}


	/**
	 *  Gets the IDEProperty attribute of the IDEInterface object
	 *
	 *@param  prop   Description of Parameter
	 *@param  deflt  Description of Parameter
	 *@return        The IDEProperty value
	 */
	public String getIDEProperty(String prop, String deflt) {
		//log("getIDEProperty(" + prop + "," + deflt + ")");
		return ideProperties.getProperty(prop, deflt);
	}


	/**
	 *  Gets the IDEProjects attribute of the IDEInterface object
	 *
	 *@param  parent  Description of Parameter
	 *@return         The IDEProjects value
	 */
	public String[] getIDEProjects(Frame parent) {
		//log("getIDEProjects(" + parent + ")");
                org.netbeans.modules.projects.CurrentProjectNode  currentNode = org.netbeans.modules.projects.CurrentProjectNode.getDefault();
                System.err.println("CurrentProjectNode="+currentNode);
                if (currentNode == null) {
                       return new String[]{"default"};
                }
                System.err.println("CurrentProjectNode="+currentNode.getName());
                
                org.openide.loaders.DataFolder f = org.netbeans.modules.projects.PSupport.getProjectsFolder();
                System.err.println("DataFolder="+f);
                org.openide.loaders.DataObject[] dos = f.getChildren ();
                System.err.println("f.getChildren ()="+dos+", dos.length="+dos.length);
                if (dos!=null) {
                    int n=1;
                    for (int i=0; i<dos.length; i++) {
                        if (dos[i].getCookie (org.openide.cookies.ProjectCookie.class) != null) {
                            n++;
                        }
                    }
                    String[] projects = new String[n];
                    projects[0] = "default";
                    n=1;
                    for (int i=0; i<dos.length; i++) {
                        if (dos[i].getCookie (org.openide.cookies.ProjectCookie.class) != null) {
                            System.err.println(" dos[i]="+dos[i]);
                            projects[n++] = dos[i].getName();
                        }
                    }
                    return projects;
                }
                return new String[]{"default", currentNode.getName()};
		// FIXME: only gets "default" project at present.
	}
 
	/**
	 *  Description of the Method
	 *
	 *@param  view    Description of Parameter
	 *@param  buffer  Description of Parameter
	 *@return         The ProjectName value
	 */
	public String getProjectName(Frame view, Object buffer) {
                org.netbeans.modules.projects.CurrentProjectNode  currentNode = org.netbeans.modules.projects.CurrentProjectNode.getDefault();
                System.err.println("CurrentProjectNode="+currentNode);
                if (currentNode == null) {
                       return "default";
                }
                System.err.println("CurrentProjectNode="+currentNode.getName());
		return currentNode.getName();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  buffer  Description of Parameter
	 *@return         The File path for this buffer
	 */
	public String getFilePathForBuffer(Object buffer) {
		JavaDataObject jdo = (JavaDataObject) buffer;
		FileObject primary = jdo.getPrimaryEntry().getFile();
		String path = primary.getPath();
		try {
			String fsPath = primary.getFileSystem().toString();
			fsPath = fsPath.substring(0, fsPath.indexOf('['));
			File fullPath = new File(new File(fsPath), primary.getPath());
			path = fullPath.getPath();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}



	/**
	 *  Returns the frame that contains the editor. If this is not available or
	 *  you want dialog boxes to be centered on the screen return null from this
	 *  operation.
	 *
	 *@return    the frame
	 */
	public Frame getEditorFrame() {
		return aView;
	}


	/**
	 *  Get the current (atcive) buffer.
	 *
	 *@param  view  The frame containing the IDE.
	 *@return       The active buffer or null if no active buffer.
	 */
	public Object getCurrentBuffer(Frame view) {
		JavaDataObject jdo = null;
		Mode mode = WindowManager.getDefault().findMode("editor");
		TopComponent[] topc = mode.getTopComponents();
		for (int n = 0; n < topc.length; n++) {
			if (topc[n].isVisible() && topc[n] instanceof JavaEditor.JavaEditorComponent) {
				Lookup lookup = topc[n].getLookup();
				jdo = (JavaDataObject) lookup.lookup(JavaDataObject.class);
				break;
			}
		}
		return jdo;
	}


	/**
	 *  Get the line number of the cursor within the current buffer.
	 *
	 *@param  x:String  Description of Parameter
	 *@param  buffer    Description of Parameter
	 *@return           The line number of the cursor in the current buffer, or
	 *      -1 if no current buffer.
	 */
	public int getLineNumber(Frame view, Object buffer) {
		JavaDataObject jdo = (JavaDataObject) buffer;
		EditorCookie editor = (EditorCookie) jdo.getCookie(EditorCookie.class);
		editor.open();
		try {
			StyledDocument doc = editor.openDocument();
			JEditorPane pane = getEditorPane(buffer);
			int pos = pane.getCaretPosition();
			return NbDocument.findLineNumber(doc, pos);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 *  Gets the file that is being edited
	 *
	 *@param  view    Description of Parameter
	 *@param  buffer  Description of Parameter
	 *@return         The File value
	 */
	public File getFile(Frame view, Object buffer) {
		try {
			if (buffer != null && buffer instanceof JavaDataObject) {
				JavaDataObject jdo = (JavaDataObject) buffer;
				FileObject primary = jdo.getPrimaryFile();
				String fsPath = primary.getFileSystem().toString();
				fsPath = fsPath.substring(0, fsPath.indexOf('['));
				return new File(new File(fsPath), primary.getPath());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		log("getFile(view" + ", " + buffer + ") FAILED");
		return null;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view    Description of Parameter
	 *@param  buffer  Description of Parameter
	 *@return         The Text value
	 */
	public String getText(Frame view, Object buffer) {
		//log("getText(view)->"+ (((JEditorPane)buffer).getText()));
		JEditorPane pane = getEditorPane(buffer);
		if (pane != null) {
			return pane.getText();
		}
		log("ERROR: can't get text for:");
		log("view=" + view);
		log("buffer=" + buffer);
		return "";
	}



	/**
	 *  Description of the Method
	 *
	 *@param  buffer  Description of Parameter
	 *@param  begin   Description of Parameter
	 *@return         The BeginLine value
	 */
	public int getLineStartOffset(Object buffer, int begin) {
		//log("getLineStartOffset(" + buffer + "," + begin + ")");
		JavaDataObject jdo = (JavaDataObject) buffer;
		EditorCookie editor = (EditorCookie) jdo.getCookie(EditorCookie.class);
		editor.open();
		try {
			StyledDocument doc = editor.openDocument();
			return NbDocument.findLineOffset(doc, begin);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		String text = getText(null, buffer);
		int index = 0;
		int pos = begin;
		while (pos > 0 && index < text.length()) {
			if (text.charAt(index++) == '\n') {
				pos--;
			}
		}
		return index;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  buffer  Description of Parameter
	 *@param  end     Description of Parameter
	 *@return         The LineEndOffset value
	 */
	public int getLineEndOffset(Object buffer, int end) {
		//log("getLineEndOffset(" + buffer + "," + end + ")");
		JavaDataObject jdo = (JavaDataObject) buffer;
		EditorCookie editor = (EditorCookie) jdo.getCookie(EditorCookie.class);
		editor.open();
		try {
			StyledDocument doc = editor.openDocument();
			return NbDocument.findLineOffset(doc, end);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		String text = getText(null, buffer);
		int index = 0;
		int pos = end;
		while (pos > 0 && index < text.length()) {
			if (text.charAt(index++) == '\n') {
				pos--;
			}
		}
		return index;
	}


	/**
	 *  Gets the Properties attribute of the JavaStylePlugin class
	 *
	 *@param  type     Description of Parameter
	 *@param  project  Description of Parameter
	 *@return          The Properties value
	 */
	public PropertiesFile getProperties(String type, String project) {
		//log("getProperties(" + type+","+project + ")");

		String key = type + "::" + (("default".equals(project)) ? null : project);
		PropertiesFile propertiesFile = (PropertiesFile) propertiesMap.get(key);

		//log("  key="+key+" ->propertiesFile="+propertiesFile);
		if (propertiesFile == null) {
			//log("  getting Properties(FileSettings.getSettings("+project+", \"Refactory\", "+type+")");
			propertiesFile = new PropertiesFile(org.acm.seguin.util.FileSettings.getSettings(project, "Refactory", type));
			propertiesMap.put(key, propertiesFile);
		}
		return propertiesFile;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  buffer  Description of Parameter
	 *@return         The LineEndOffset value
	 */
	public int getLineCount(Object buffer) {
		//log("getLineCount(" + buffer + ")");
		String text = getText(null, buffer);
		int lineCount = 1;
		int index = 0;
		while (index < text.length()) {
			if (text.charAt(index++) == '\n') {
				lineCount++;
			}
		}
		return lineCount;
	}


	/**
	 *  Gets the userSelection attribute of the JRefactory object
	 */
	public void getUserSelection() {
		JFileChooser chooser = new JFileChooser();

		//  Add other file filters - All
		AllFileFilter allFilter = new AllFileFilter();
		chooser.addChoosableFileFilter(allFilter);

		//  Set it so that files and directories can be selected
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		//  Set the directory to the current directory
		chooser.setCurrentDirectory(userDir);

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			PackageSelectorPanel panel = PackageSelectorPanel.getMainPanel(chooser.getSelectedFile().getAbsolutePath());
			ReloaderSingleton.register(panel);
			mainstage.setComponentAt(0, panel.getPanel());
		}
		else {
			mainstage.setComponentAt(0, new ReloadChooserPanel());
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String preferredID() {
		return "JRefactory";
	}

// ...

	/**
	 *  Description of the Method
	 *
	 *@param  oo               Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	public void writeExternal(java.io.ObjectOutput oo) throws IOException {
		super.writeExternal(oo);
		state = new MySerObject();
		state.xxx = 10;
		Object toWrite;
		try {
			toWrite = new org.openide.util.io.NbMarshalledObject(state);
		}
		catch (Exception e) {
			ErrorManager.getDefault().notify(ErrorManager.WARNING, e);
			toWrite = null;
		}
		oo.writeObject(toWrite);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  oi                          Description of Parameter
	 *@exception  IOException             Description of Exception
	 *@exception  ClassNotFoundException  Description of Exception
	 */
	public void readExternal(java.io.ObjectInput oi) throws ClassNotFoundException, IOException {
		super.readExternal(oi);
		state = new MySerObject();
		org.openide.util.io.NbMarshalledObject read = (org.openide.util.io.NbMarshalledObject) oi.readObject();
		if (read != null) {
			try {
				state = (MySerObject) read.get();
			}
			catch (Exception e) {
				ErrorManager.getDefault().notify(ErrorManager.WARNING, e);
			}
		}
		//log("state.xxx=" + state.xxx);
	}



	/**
	 *  Create a new <code>JRefactory</code>.
	 *
	 */
	public void initComponents() {
		initThisComponent();
	}


	/**
	 *  Description of the Method
	 */
	public void showCSTab() {
		mainstage.setSelectedComponent(csViewer);
	}


	/**
	 *  Description of the Method
	 */
	public void showCPDTab() {
		mainstage.setSelectedComponent(cpdViewer);
	}


	/**
	 *  Description of the Method
	 */
	public void showJRefactoryTab() {
		mainstage.setSelectedComponent(jRefactoryPanel);
	}


	/**
	 *  Load an icon from the IDE
	 *
	 *@param  name  The name of the icon.
	 *@return       An icon (or null if the icon cannot be found).
	 */
	public Icon loadIcon(String name) {
		ClassLoader classLoader = this.getClass().getClassLoader();
		Icon icon = null;
		try {
			icon = new ImageIcon(classLoader.getResource(name));
		}
		catch (Exception e) {
		}
		if (icon == null) {
			try {
				icon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/" + name));
			}
			catch (Exception e) {
			}
		}
		return icon;
	}


	/**
	 *  Does the buffer contain Java source code.
	 *
	 *@param  view    The frame containing the IDE.
	 *@param  buffer  Description of Parameter
	 *@return         <code>true</code> if the buffer contains Java source code,
	 *      <code>false</code> otherwise.
	 */
	public boolean bufferContainsJavaSource(Frame view, Object buffer) {
		//log("bufferContainsJavaSource(view" + ", " + buffer + ")");
		return (buffer != null && buffer instanceof JavaDataObject);
	}


	/**
	 *  Indicates that a buffer has been parsed and that an Abstract Syntax Tree
	 *  is available.
	 *
	 *@param  view             The frame containing the IDE.
	 *@param  buffer           The buffer (containing Java Source) that has been
	 *      parsed.
	 *@param  compilationUnit  The root node of the AST.
	 */
	public void bufferParsed(Frame view, Object buffer, net.sourceforge.jrefactory.ast.Node compilationUnit) {
		log("NOT IMPLEMENTED: bufferParsed()");
	}


	/**
	 *  Indicates that a buffer has been parsed and that a navigator tree of the
	 *  source is available.
	 *
	 *@param  view    The frame containing the IDE.
	 *@param  buffer  The buffer (containing Java Source) that has been parsed.
	 *@param  node    The root node of the tree.
	 */
	public void bufferNavigatorTree(Frame view, Object buffer, TreeNode node) {
		log("NOT IMPLEMENTED: bufferNavigatorTree()");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view    Description of Parameter
	 *@param  buffer  Description of Parameter
	 */
	public void goToBuffer(Frame view, Object buffer) {
		//log("goToBuffer(view" + ", " + buffer + ")");
                JEditorPane editor = getEditorPane(buffer);
		java.awt.Container pane = editor;
		//log("  pane=" + pane);
		while (pane != null && !(pane instanceof javax.swing.JTabbedPane) ) {
                    pane = pane.getParent();
   		    //log("  pane=" + pane);
                    if (pane instanceof javax.swing.JTabbedPane) {
                        ((javax.swing.JTabbedPane) pane).setSelectedComponent(editor);
                    }
                }
	}

        private transient java.awt.Cursor oldCursor = null;

	/**
	 *  Description of the Method
	 *
	 *@param  parent  Description of Parameter
	 */
	public void showWaitCursor(Frame parent) {
		//log("showWaitCursor(" + parent + ")");
                oldCursor = parent.getCursor();
                java.awt.Cursor cursor = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR);
                parent.setCursor(cursor);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  parent  Description of Parameter
	 */
	public void hideWaitCursor(Frame parent) {
		//log("hideWaitCursor(" + parent + ")");
                if (oldCursor==null) {
                    parent.setCursor(java.awt.Cursor.getDefaultCursor());
                } else {
                    parent.setCursor(oldCursor);
                }
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view    Description of Parameter
	 *@param  buffer  Description of Parameter
	 */
	public void checkBuffer(Frame view, Object buffer) {
		log("check(view," + buffer + ")");
		showCSTab();
		csViewer.check(view, ((buffer == null) ? getCurrentBuffer(view) : buffer), false);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view  Description of Parameter
	 */
	public void checkAllOpenBuffers(Frame view) {
		log("checkAllOpenBuffers(view)");
		showCSTab();
		Mode mode = WindowManager.getDefault().findMode("editor");
		TopComponent[] topc = mode.getTopComponents();
		for (int n = 0; n < topc.length; n++) {
			if (topc[n] instanceof JavaEditor.JavaEditorComponent) {
				Lookup lookup = topc[n].getLookup();
				JavaDataObject jdo = (JavaDataObject) lookup.lookup(JavaDataObject.class);
				checkBuffer(view, jdo);
			}
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view       Description of Parameter
	 *@param  recursive  Description of Parameter
	 */
	public void checkDirectory(Frame view, boolean recursive) {
		//log("checkDirectory(view" + recursive + ")");
		showCSTab();
		JFileChooser chooser = new JFileChooser();

		//  Add other file filters - All
		AllFileFilter allFilter = new AllFileFilter();
		chooser.addChoosableFileFilter(allFilter);

		//  Set it so that files and directories can be selected
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		//  Set the directory to the current directory
		chooser.setCurrentDirectory(userDir);

		//  Get the user's selection
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			process(findFiles(chooser.getSelectedFile().getAbsolutePath(), recursive), view);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view             Description of Parameter
	 *@param  fileName         Description of Parameter
	 *@return                  Description of the Returned Value
	 *@exception  IOException  Description of Exception
	 */
	public Object openFile(Frame view, String fileName) throws IOException {
		//log("openFile(view, " + fileName + ")");
		File file = new File(fileName);
		if (file.exists()) {
			//log("file exists");
			try {
				FileObject fileObj = FileUtil.fromFile(file)[0];
				DataObject dataObj = DataObject.find(fileObj);
				if (dataObj != null) {
					JavaEditor editor = (JavaEditor) dataObj.getCookie(JavaEditor.class);
					editor.open();
					return dataObj;
				}
				else {
					JavaDataLoader jdl = new JavaDataLoader();
					JavaDataObject jdo = new JavaDataObject(fileObj, jdl);
					EditorCookie editor = (EditorCookie) jdo.getCookie(EditorCookie.class);
					editor.open();
					return jdo;
				}
			}
			catch (IOException e) {
				JOptionPane.showMessageDialog(view, e.getMessage(), "JRefactory", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return null;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  runnable  Description of Parameter
	 */
	public void runInAWTThread(Runnable runnable) {
		//log("runInAWTThread(" + runnable + ")");
                javax.swing.SwingUtilities.invokeLater(runnable);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view    Description of Parameter
	 *@param  buffer  Description of Parameter
	 *@param  start   Description of Parameter
	 */
	public void moveCaretPosition(Frame view, Object buffer, int start) {
		//log("moveCaretPosition(view, " + start + ")");
		javax.swing.text.Caret caret = getCaret();
		caret.moveDot(start);
		caret.setVisible(true);
	}


	/**
	 *  write new settings
	 */
	public void saveProperties() {
		for (java.util.Iterator i = propertiesMap.keySet().iterator(); i.hasNext(); ) {
			PropertiesFile projectProps = (PropertiesFile) propertiesMap.get(i.next());
			projectProps.save();
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view             Description of Parameter
	 *@param  buffer           Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	public void cpdBuffer(Frame view, Object buffer) throws IOException {
		log("cpdBuffer(view," + buffer + ")");
		showCPDTab();
		if (buffer != null) {
			JavaEditor editor = ((JavaDataObject) buffer).getJavaEditor();
			JEditorPane[] panes = (JEditorPane[]) editor.getOpenedPanes();
			if (panes != null && panes.length > 0) {
				//String str = panes[0].getText();

				CPD cpd = new CPD(100, new JavaLanguage());
				//cpd.add(new java.io.File(new java.io.StringReader(str)));

				cpd.go();
				if (cpdViewer != null) {
					cpdViewer.processDuplicates(cpd, view);
				}
			}

		}

	}


	/**
	 *  Description of the Method
	 *
	 *@param  view             Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	public void cpdAllOpenBuffers(Frame view) throws IOException {
		log("cpdAllOpenBuffers");
		showCPDTab();
		Mode mode = WindowManager.getDefault().findMode("editor");
		TopComponent[] topc = mode.getTopComponents();
		for (int n = 0; n < topc.length; n++) {
			if (topc[n] instanceof JavaEditor.JavaEditorComponent) {
				Lookup lookup = topc[n].getLookup();
				JavaDataObject jdo = (JavaDataObject) lookup.lookup(JavaDataObject.class);
				cpdBuffer(view, jdo);
			}
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  view             Description of Parameter
	 *@param  recursive        Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	public void cpdDir(Frame view, boolean recursive) throws IOException {
		//log("cpdDir(view)");
		showCPDTab();
		JFileChooser chooser = new JFileChooser(getIDEProperty("pmd.cpd.lastDirectory"));

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		JPanel pnlAccessory = new JPanel();

		pnlAccessory.add(new JLabel("Minimum Tile size :"));

		JTextField txttilesize = new JTextField("100");

		pnlAccessory.add(txttilesize);
		chooser.setAccessory(pnlAccessory);

		int returnVal = chooser.showOpenDialog(view);
		File selectedFile = null;

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedFile = chooser.getSelectedFile();
			if (!selectedFile.isDirectory()) {
				JOptionPane.showMessageDialog(view, "Selection not a directory.", "JRefactory", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else {
			return;
		}
		// In case the user presses cancel or escape.

		getIDEProperty("pmd.cpd.lastDirectory", selectedFile.getCanonicalPath());

		int tilesize = 100;

		try {
			tilesize = Integer.parseInt(txttilesize.getText());
		}
		catch (NumberFormatException e) {
			//use the default.
			tilesize = 100;
		}

		CPD cpd = new CPD(tilesize, new JavaLanguage());

		if (recursive) {
			cpd.addRecursively(selectedFile.getCanonicalPath());
		}
		else {
			cpd.addAllInDirectory(selectedFile.getCanonicalPath());
		}

		cpd.go();
		if (cpdViewer != null) {
			cpdViewer.processDuplicates(cpd, view);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  urgency  Description of Parameter
	 *@param  source   Description of Parameter
	 *@param  message  Description of Parameter
	 */
	public void log(int urgency, Object source, Object message) {
		int type = ErrorManager.INFORMATIONAL;
		switch (urgency) {
			case IDEInterface.DEBUG:
				type = ErrorManager.USER;
			case IDEInterface.ERROR:
				type = ErrorManager.ERROR;
			case IDEInterface.MESSAGE:
				type = ErrorManager.INFORMATIONAL;
			case IDEInterface.NOTICE:
				type = ErrorManager.INFORMATIONAL;
			case IDEInterface.WARNING:
				type = ErrorManager.WARNING;
			default:
				type = ErrorManager.INFORMATIONAL;
		}
		System.err.println(source + ": " + message);
		ErrorManager.getDefault().log(type, source + ": " + message);
	}


	/**
	 *  Adds an annotation to an ide buffer.
	 *
	 *@param  view         The frame containing the IDE.
	 *@param  buffer       The buffer (containing Java Source) that has been
	 *      parsed.
	 *@param  type         either CODING_STANDARDS or CUT_AND_PASTE_DETECTION
	 *@param  lineNo       The line number of the annotation.
	 *@param  description  The annotation
	 */
	public void addAnnotation(Frame view, Object buffer, int type, int lineNo, String description) {
		if (buffer != null) {
			JavaDataObject jdo = null;
			if (buffer instanceof String) {
				// find the buffer for the annotation but don't load it
				File file = new File((String) buffer);
				if (file.exists()) {
					try {
						FileObject fileObj = FileUtil.fromFile(file)[0];
						jdo = (JavaDataObject) DataObject.find(fileObj);
					}
					catch (Exception e) {
					}
				}
			}
			else {
				jdo = (JavaDataObject) buffer;
			}
			if (jdo == null) {
				return;
			}
			JavaEditor editor = jdo.getJavaEditor();
			if (editor == null) {
				return;
			}
			LineCookie cookie = (LineCookie) jdo.getCookie(LineCookie.class);
			org.openide.text.Line.Set lineset = cookie.getLineSet();
			org.openide.text.Line line = lineset.getOriginal(lineNo - 1);
			if (type == IDEInterface.CODING_STANDARDS) {
				if (prevLine == lineNo && prevAnnotation instanceof CSAnnotation) {
					CSAnnotation annotation = (CSAnnotation) prevAnnotation;
					annotation.setErrorMessage(annotation.getShortDescription() + "\n" + description);
				}
				else {
					CSAnnotation annotation = CSAnnotation.getNewInstance();
					annotation.setErrorMessage(description);
					annotation.attach(line);
					line.addPropertyChangeListener(annotation);
				}
			}
			else if (type == IDEInterface.CUT_AND_PASTE_DETECTOR) {
				CPDAnnotation annotation = CPDAnnotation.getNewInstance();
				annotation.setErrorMessage(description);
				annotation.attach(line);
				line.addPropertyChangeListener(annotation);
			}
			line.show(org.openide.text.Line.SHOW_GOTO);
			org.openide.awt.StatusDisplayer.getDefault().setStatusText(description);
		}
	}


	/**
	 *  Clears all annotation for an ide buffer.
	 *
	 *@param  view    The frame containing the IDE.
	 *@param  buffer  The buffer (containing Java Source) that has been parsed.
	 *@param  type    either CODING_STANDARDS or CUT_AND_PASTE_DETECTION
	 */
	public void clearAnnotations(Frame view, Object buffer, int type) {
		if (type == IDEInterface.CODING_STANDARDS) {
			CSAnnotation.clearAll();
		}
		else if (type == IDEInterface.CUT_AND_PASTE_DETECTOR) {
			CPDAnnotation.clearAll();
		}
	}


	/**
	 *  Gets the EditorPane attribute of the JRefactory object
	 *
	 *@param  buffer  Description of Parameter
	 *@return         The EditorPane value
	 */
	private JEditorPane getEditorPane(Object buffer) {
		if (buffer != null && buffer instanceof JavaDataObject) {
			JavaDataObject jdo = (JavaDataObject) buffer;
			JavaEditor editor = jdo.getJavaEditor();
			JEditorPane[] panes = (JEditorPane[]) editor.getOpenedPanes();
			if (panes != null && panes.length > 0) {
				return panes[0];
			}
		}

		if (buffer != null && buffer instanceof JEditorPane) {
			return (JEditorPane) buffer;
		}
		else {
			//log("buffer="+buffer);
			return getCurrentEditorPane(null);
			//log("getText(view)->"+ (((JEditorPane)buffer).getText()));
		}
	}


	/**
	 *  Gets the CurrentEditorPane attribute of the NetBeansExtractMethodDialog
	 *  object
	 *
	 *@param  cookie  Description of Parameter
	 *@return         The CurrentEditorPane value
	 */
	private JEditorPane getCurrentEditorPane(EditorCookie cookie) {
		WindowManager windowManager = (WindowManager) Lookup.getDefault().lookup(WindowManager.class);
		TopComponent comp = windowManager.getRegistry().getActivated();
		Node[] nodes = comp.getRegistry().getActivatedNodes();
		//log("nodes.length=" + nodes.length);
		//log("cookie=" + cookie);

		//(NOTE) This is a hack fix
		EditorCookie editorCookie = null;
		for (int i = 0; i < nodes.length; i++) {
			editorCookie = (EditorCookie) nodes[i].getCookie(EditorCookie.class);
			if (editorCookie != null) {
				JEditorPane[] panes = editorCookie.getOpenedPanes();
				//log("panes.length="+panes.length);
				if (panes != null && panes.length == 1) {
					return panes[0];
				}
				break;
			}
		}

		return null;
	}


	/**
	 *  Gets the Caret attribute of the JRefactory object
	 *
	 *@return    The Caret value
	 */
	private javax.swing.text.Caret getCaret() {
		JEditorPane pane = getCurrentEditorPane(null);
		return (pane == null) ? null : pane.getCaret();
	}


	/**
	 *  Create a new <code>JRefactory</code>.
	 *
	 */
	private void initThisComponent() {
		//log("initComponents() jsPlugin=" + jsPlugin);
		if (jsPlugin != null) {
			return;
		}
		jsPlugin = this;
		IDEPlugin.setPlugin(jsPlugin);
		if (aView==null) {
			aView = WindowManager.getDefault().getMainWindow();
		}

		//log("initComponents()");
		setLayout(new java.awt.BorderLayout());
		Properties props = System.getProperties();
		props.list(System.out);

		JAVASTYLE_DIR = new File(props.getProperty("user.home") + File.separator + ".netbeans" + File.separator + "javastyle").getAbsolutePath();
		PRETTY_SETTINGS_FILE = new File(JAVASTYLE_DIR + File.separator + ".Refactory", "pretty.settings");

		// plug into JRefactory some classes that adapt it to jEdit.
		ExitOnCloseAdapter.setExitOnWindowClose(false);

		//  Make sure everything is installed properly
		FileSettings.setSettingsRoot(JAVASTYLE_DIR);
		log("running RefactoryInstaller");
		(new RefactoryInstaller(true)).run();
		SourceBrowser.set(new CommandLineSourceBrowser());
		net.sourceforge.jrefactory.action.CurrentSummary.register(new NetbeansCurrentSummary(aView));

		log("creating cpdViewer");
		cpdViewer = new CPDDuplicateCodeViewer(aView);
		PackageSelectorPanel panel = PackageSelectorPanel.getMainPanel(null);
		jRefactoryPanel = panel.getPanel();
		astv = new ASTViewerPane(aView);

		try {
         ClassLoader classLoader = this.getClass().getClassLoader();
         java.net.URL url = classLoader.getResource("org/acm/seguin/ide/netbeans/JRefactory.class");
         //System.out.println("url="+url);
         java.util.List plugins = new java.util.ArrayList();
         if (url != null) {
            String urlStr = url.toString();
            if (urlStr.indexOf('!')>0) {
               String xStr = urlStr.substring("jar:file:/".length(), urlStr.indexOf('!'));
               //System.out.println("xStr="+xStr);
               xStr = replace(xStr, "%20", " ");
               //System.out.println("xStr="+xStr);
               File file = new File(xStr);
               //System.out.println("file="+file);
               if (file.exists()) {
                  System.out.println(" file exists");
                  plugins.add(file);
               }
            }
         }
         File findBugsDir = new File(JAVASTYLE_DIR,"findbugs");
         //System.out.println("findBugsDir="+findBugsDir);
         if (findBugsDir.exists() && findBugsDir.isDirectory()) {
            File[] files = findBugsDir.listFiles();
            for (int i=0; i<files.length; i++) {
               if (files[i].getName().endsWith(".jar")) {
                  plugins.add(files[i]);
               }
            }
         }
         
         File corePluginFile = new File(userDir, "coreplugin.jar");
         if (corePluginFile.exists()) {
            //System.out.println(" file " + corePluginFile + " exists");
            plugins.add(corePluginFile);
         }

			log("creating findBugs");
			corePluginFile = new File("./Modules/JRefactoryModule.jar");
			log("corePluginFile=" + corePluginFile.getCanonicalPath());
			if (!corePluginFile.exists()) {
				corePluginFile = new File("./Modules/autoload/JRefactoryModule.jar");
			}
			if (!corePluginFile.exists()) {
				corePluginFile = new File("./Modules/eager/JRefactoryModule.jar");
			}
                        if (!corePluginFile.exists()){
                            plugins.add(corePluginFile);
                        }
         File[] pluginList = (File[])plugins.toArray(new File[plugins.size()]);
         
         //File[] pluginList = (corePluginFile.exists()) ? new File[]{corePluginFile} : new File[0];
         for (int i=0; i<pluginList.length; i++) {
            System.out.println("pluginList["+i+"]="+pluginList[i]);
         }
			//File[] pluginList = (corePluginDir.exists()) ? new File[]{corePluginDir} : new File[0];
			DetectorFactoryCollection.setPluginList(pluginList);
			findBugs = org.acm.seguin.findbugs.FindBugsFrame.createFindBugsPanel(aView);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}

		log("running csViewer");
		csViewer = new CodingStandardsViewer(aView);
		mainstage = new JTabbedPane(JTabbedPane.TOP);
		mainstage.addTab("JRefactory", jRefactoryPanel);
		mainstage.addTab("Cut & paste detector", cpdViewer);
		mainstage.addTab("Coding standards", csViewer);
		if (findBugs != null) {
			mainstage.addTab("Find Bugs", findBugs);
		}
		mainstage.addTab("Abstract Syntax Tree", astv);
		add(mainstage, BorderLayout.CENTER);
		try {
			log("loading properties");
			ideProperties.load(getClass().getResourceAsStream("/ui/JavaStyle.props"));
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}

		log("docking into explorer");
		java.util.Set modes = WindowManager.getDefault().getModes();
		//log("== modes ==");
		//for (java.util.Iterator i = modes.iterator(); i.hasNext(); ) {
		//	log("  " + i.next());
		//}
		Mode mode = WindowManager.getDefault().findMode("explorer");
		//log("mode=" + mode);
		if (mode != null) {
			log("   opening in explorer");
			mode.dockInto(this);
			log("opening JRefactory TopComponent pane");
			open();
			requestVisible();
		}

		setCloseOperation(CLOSE_LAST);
		// or CLOSE_EACH
		// Display name of this window (not needed if you use the DataObject constructor):
		setName(NbBundle.getMessage(JRefactory.class, "LBL_jrefactory"));
		// You may set the icon, but often it is better to set the icon for an associated mode instead:
		// setIcon(Utilities.loadImage("src/JRefactoryIcon.gif", true));
		// Use the Component Inspector to set tool-tip text. This will be saved
		// automatically. Other JComponent properties you may need to save yuorself.
		// At any time you can affect the node selection:
		// setActivatedNodes(new Node[] {...});
	}

   private static String replace(String from, String oldStr, String newStr) {
      StringBuffer sb = new StringBuffer();
      int y = 0;
      int x = from.indexOf(oldStr);
      while (x>=0) {
         System.out.println("part="+from.substring(y,x));
         sb.append(from.substring(y,x)).append(newStr);
         y = x+oldStr.length();
         x = from.indexOf(oldStr,y);
      }
      System.out.println("lastpart="+from.substring(y));
      sb.append(from.substring(y));
      return sb.toString();
   }

	/**
	 *  Description of the Method
	 *
	 *@param  dir      Description of Parameter
	 *@param  recurse  Description of Parameter
	 *@return          Description of the Returned Value
	 */
	private List findFiles(String dir, boolean recurse) {
		FileFinder finder = new FileFinder();
		return finder.findFilesFrom(dir, new JavaLanguage.JavaFileOrDirectoryFilter(), recurse);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  files  Description of Parameter
	 *@param  view   Description of Parameter
	 */
	private void process(final List files, final Frame view) {
		new Thread(
					new Runnable() {
						public void run() {
							processFiles(files, view);
						}
					}).start();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  files  Description of Parameter
	 *@param  view   Description of Parameter
	 */
	private void processFiles(List files, Frame view) {
		List contexts = csViewer.checkFiles(files, view, null);
		org.openide.windows.IOProvider iop = org.openide.windows.IOProvider.getDefault();
		org.openide.windows.InputOutput inOut = iop.getIO("JRefactory", true);
		java.io.PrintWriter out = new java.io.PrintWriter(inOut.getErr());

		boolean foundProblems = false;
		for (Iterator i = contexts.iterator(); i.hasNext(); ) {
			RuleContext ctx = (RuleContext) i.next();
			for (Iterator j = ctx.getReport().iterator(); j.hasNext(); ) {
				foundProblems = true;
				RuleViolation ruleViolation = (RuleViolation) j.next();
				//errorSource.addError(ErrorSource.WARNING, ctx.getSourceCodeFilename(), ruleViolation.getLine() - 1, 0, 0, ruleViolation.getDescription());
				out.println(ctx.getSourceCodeFilename() + " [" + (ruleViolation.getLine() - 1) + "] " + ruleViolation.getDescription());
			}
		}
		if (!foundProblems) {
			//JOptionPane.showMessageDialog(jEdit.getFirstView(), "No problems found", NAME, JOptionPane.INFORMATION_MESSAGE);
			//errorSource.clear();
			inOut.closeInputOutput();
		}

	}



	/**
	 *  Sets the Property attribute of the JavaStylePlugin class
	 *
	 *@param  key    The new Property value
	 *@param  value  The new Property value
	 */
	public static void setProperty(String key, String value) {
		properties.setString(key, value);
	}


	/**
	 *  Gets the JRefactoryFrame attribute of the JRefactory class
	 *
	 *@return    The JRefactoryFrame value
	 */
	public static Frame getJRefactoryFrame() {
		ensureVisible();
		return WindowManager.getDefault().getMainWindow();
	}


	/**
	 *  Gets the SettingsDirectory attribute of the JRefactory class
	 *
	 *@return    The SettingsDirectory value
	 */
	public static String getSettingsDirectory() {
		return System.getProperty("user.dir") + File.separator + ".JRefactory";
	}


	/**
	 *  Sets the projectData attribute of the JEditPrettyPrinter object
	 *
	 *@param  view  Description of Parameter
	 *@return       The projectName value
	 */
	public static String getProjectName(Frame view) {
		return "";
	}


	/**
	 *  Ensure that the JRefactory plugin is visible
	 */
	public static void ensureVisible() {
            //log("ensureVisible()");
            if (jsPlugin == null) {
                jsPlugin = new JRefactory();
            }
            if (!jsPlugin.isVisible()) {
                Mode mode = WindowManager.getDefault().findMode("explorer");
                if (mode != null) {
                    mode.dockInto(jsPlugin);
                }
                JRefactory.jsPlugin.open();
                JRefactory.jsPlugin.requestVisible();
            }
	}


	/**
	 *  Ensure that the JRefactory plugin is loaded
	 */
	public static void ensureLoaded() {
            if (jsPlugin == null) {
                jsPlugin = new JRefactory();
            }
        }


	/**
	 *  Description of the Method
	 *
	 *@param  key  Description of Parameter
	 */
	public static void deleteProperty(String key) {
		properties.deleteKey(key);
	}



	/**
	 *  Description of the Method
	 *
	 *@param  message  Description of Parameter
	 */
	public static void log(Object message) {
		System.err.println("JavaStyle: " + message);
		ErrorManager.getDefault().log(ErrorManager.INFORMATIONAL, "JavaStyle: " + message);
	}


	/**
	 *  Description of the Class
	 *
	 *@author     <a href="mailto:JRefactoryPlugin@ladyshot.demon.co.uk">Mike
	 *      Atkinson</a>
	 *@created    23 July 2003
	 *@version    $Id: JRefactory.java,v 1.19 2004/05/10 16:19:18 mikeatkinson Exp $
	 *@since      0.1.0
	 */
	private final class ReloadChooserPanel extends JPanel {
		/**
		 *  Constructor for the ReloadChooserPanel object
		 */
		public ReloadChooserPanel() {
			//log("new ReloadChooserPanel()");
			JButton load = new JButton("load JRefactory UML viewer");
			load.addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								JRefactory.this.getUserSelection();
							}
						});
			add(load);
		}
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Mike Atkinson
	 */
	public static class MySerObject implements java.io.Serializable {
		int xxx = 1;
		private static final long serialVersionUID = -5300379359373857991L;
	}
}

