/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

import net.sourceforge.jrefactory.ast.Node;

/**
 *  Consume unknonwn special tokens 
 *
 *@author     Chris Seguin 
 *@created    October 14, 1999 
 *@date       April 10, 1999 
 */
public abstract class PrintSpecial {
	/**
	 *  Determines if this print special can handle the current object 
	 *
	 *@param  spec  Description of Parameter 
	 *@return       true if this one should process the input 
	 */
	public abstract boolean isAcceptable(SpecialTokenData spec);


	/**
	 *  Processes the special token 
	 *
	 *@param  node  the type of node this special is being processed for 
	 *@param  spec  the special token data 
	 *@return       Description of the Returned Value 
	 */
	public abstract boolean process(Node node, SpecialTokenData spec);
}
