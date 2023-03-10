package org.acm.seguin.refactor.method;

import net.sourceforge.jrefactory.ast.ModifierHolder;
import org.acm.seguin.summary.MethodSummary;

/**
 *  Adds a concrete method to a class
 *
 *@author    Chris Seguin
 */
public class AddConcreteMethod extends AddNewMethod {
	/**
	 *  Constructor for the AddConcreteMethod object
	 *
	 *@param  init  Description of Parameter
	 */
	public AddConcreteMethod(MethodSummary init) {
		super(init);
	}


	/**
	 *  Sets up the modifiers
	 *
	 *@param  source  the source holder
	 *@param  dest    the destination holder
	 */
	protected void copyModifiers(ModifierHolder source, ModifierHolder dest) {
		dest.copyModifiers(source);
		dest.setAbstract(false);
	}


	/**
	 *  Determines if the method is abstract
	 *
	 *@return    true if the method is abstract
	 */
	protected boolean isAbstract() {
		return false;
	}
}
