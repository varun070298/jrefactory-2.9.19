package org.acm.seguin.pmd.rules;

import org.acm.seguin.pmd.AbstractRule;
import org.acm.seguin.pmd.RuleContext;
import net.sourceforge.jrefactory.ast.ASTLocalVariableDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import org.acm.seguin.pmd.symboltable.Scope;
import org.acm.seguin.pmd.symboltable.VariableNameDeclaration;

import java.text.MessageFormat;
import java.util.Iterator;

public class UnusedLocalVariableRule extends AbstractRule {
    public Object visit(ASTVariableDeclaratorId node, Object data) {
        if (node.jjtGetParent().jjtGetParent() instanceof ASTLocalVariableDeclaration) {
            RuleContext ctx = (RuleContext) data;
            for (Iterator i = ((Scope)node.getScope()).getVariableDeclarations(false).keySet().iterator(); i.hasNext();) {
                VariableNameDeclaration decl = (VariableNameDeclaration) i.next();
                ctx.getReport().addRuleViolation(createRuleViolation(ctx, decl.getLine(), MessageFormat.format(getMessage(), new Object[]{decl.getImage()})));
            }
        }
        return data;
    }
}
