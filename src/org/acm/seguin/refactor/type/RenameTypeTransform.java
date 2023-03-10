package org.acm.seguin.refactor.type;

import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.factory.NameFactory;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.refactor.TransformAST;

/**
 *  This object traverses the syntax tree and renames the types. An old and a 
 *  new value are provided. 
 *
 *@author     Chris Seguin 
 *@created    October 23, 1999 
 */
public class RenameTypeTransform extends TransformAST {
	private ASTName oldName;
	private ASTName newName;
	private TypeSummary summary;


	/**
	 *  Constructor for the RenameTypeTransform object 
	 *
	 *@param  oldValue  the old name 
	 *@param  newValue  the new name 
	 *@param  init      Description of Parameter 
	 */
	public RenameTypeTransform(ASTName oldValue, ASTName newValue, TypeSummary init) {
		oldName = oldValue;
		newName = newValue;
		summary = init;
	}


	/**
	 *  Constructor for the RenameTypeTransform object 
	 *
	 *@param  oldPackageName  the old package 
	 *@param  newPackageName  Description of Parameter 
	 *@param  className       Description of Parameter 
	 */
	public RenameTypeTransform(String oldPackageName, String newPackageName, String className) {
		newName = NameFactory.getName(newPackageName, className);
		oldName = NameFactory.getName(oldPackageName, className);
		summary = GetTypeSummary.query(oldPackageName, className);
	}


	/**
	 *  Constructor for the RenameTypeTransform object 
	 *
	 *@param  oldPackageName  the old package 
	 *@param  newValue        the new name 
	 *@param  init            Description of Parameter 
	 */
	public RenameTypeTransform(String oldPackageName, ASTName newValue, TypeSummary init) {
		newName = newValue;
		oldName = new ASTName();
		oldName.fromString(oldPackageName);
		oldName.addNamePart(newName.getNamePart(newName.getNameSize() - 1));
		summary = init;
	}


	/**
	 *  Update the syntax tree 
	 *
	 *@param  root  the root of the syntax tree 
	 */
	public void update(SimpleNode root) {
		RenameTypeVisitor rtv = new RenameTypeVisitor();
		root.jjtAccept(rtv, new RenameTypeData(oldName, newName, summary));
	}
}
