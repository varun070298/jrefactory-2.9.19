package org.acm.seguin.pmd.rules.strictexception;

import org.acm.seguin.pmd.AbstractRule;
import org.acm.seguin.pmd.RuleContext;
import net.sourceforge.jrefactory.ast.ASTCastExpression;
import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTInstanceOfExpression;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.ASTTryStatement;
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.Node;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * <p>
 * @author <a mailto:trond.andersen@nordea.com>Trond Andersen</a>
 * @version 1.0
 * @since 1.1?
 */
public class ExceptionTypeChecking extends AbstractRule {

    public Object visit(ASTTryStatement catchStatment, Object ruleContext) {
        if (catchStatment.hasCatch()) {
           int lastIndex = catchStatment.jjtGetNumChildren();
           for (int ndx = 0; ndx < lastIndex; ndx++) {
              Node next = catchStatment.jjtGetChild(ndx);
	      if (next instanceof ASTFormalParameter) {
              Node block = catchStatment.jjtGetChild(ndx+1);
                 evaluateCatchClause((ASTFormalParameter)next, (ASTBlock)block, (RuleContext)ruleContext);
	      }
           }
        }

        return super.visit(catchStatment, ruleContext);
    }

    private void evaluateCatchClause(ASTFormalParameter catchStmt, ASTBlock block, RuleContext ctx) {
        String exceptionParameter = getExceptionParameter(catchStmt);
        // Retrieves all instance of expressions
        List myList = block.findChildrenOfType(ASTInstanceOfExpression.class);

        for (Iterator i = myList.iterator(); i.hasNext();) {
            evaluateInstanceOfExpression((ASTInstanceOfExpression)i.next(), exceptionParameter, ctx);
        }
    }

    private void evaluateInstanceOfExpression(ASTInstanceOfExpression instanceOfExpression,
                                              String exceptionName, RuleContext ctx) {
        if (!hasTypeEvaluation(instanceOfExpression)) {
            return;
        }
        if (exceptionName.equals( getInstanceOfObjectReference(instanceOfExpression)) ) {
            ctx.getReport().addRuleViolation(createRuleViolation(ctx, instanceOfExpression.getBeginLine()));
        }
    }

    private boolean hasTypeEvaluation(ASTInstanceOfExpression instanceOfExpression) {
        List typeList = instanceOfExpression.findChildrenOfType(ASTReferenceType.class);
        if (typeList != null && typeList.size() >= 1) {
            ASTReferenceType theType = (ASTReferenceType)typeList.get(0);
            if (!(theType.jjtGetParent() instanceof ASTCastExpression)) {
                return true;
            }
        }
       return false;
    }

    private String getInstanceOfObjectReference(ASTInstanceOfExpression expression) {
        List primaryList = expression.findChildrenOfType(ASTPrimaryExpression.class);
        String objectReferenceName = null;
        if (primaryList.size() == 1) {
            List someList = ((ASTPrimaryExpression)primaryList.get(0)).findChildrenOfType(ASTName.class);
            if (someList.size() == 1) {
                objectReferenceName = ((ASTName)someList.get(0)).getImage();
            }
        }
        return objectReferenceName;
    }

    private String getExceptionParameter(ASTFormalParameter catchStmt) {
        List declarationList = catchStmt.findChildrenOfType(ASTVariableDeclaratorId.class);
        return ((ASTVariableDeclaratorId)declarationList.get(0)).getImage();
    }

}
