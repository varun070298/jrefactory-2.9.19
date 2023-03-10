package org.acm.seguin.pmd.symboltable;

import org.acm.seguin.pmd.util.Applier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides behavior common to all Scopes
 */
public abstract class AbstractScope implements Scope {
    private Scope parent;
    protected Map variableNames = new HashMap();
    protected Map methodNames = new HashMap();

    public ClassScope getEnclosingClassScope() {
        return parent.getEnclosingClassScope();
    }

    public void setParent(net.sourceforge.jrefactory.ast.Scope parent) {
        this.parent = (Scope)parent;
    }

    public net.sourceforge.jrefactory.ast.Scope getParent() {
        return parent;
    }

    public void addDeclaration(VariableNameDeclaration variableDecl) {
       //System.err.println("AbstractScope.addDeclaration("+variableDecl+")");
        if (variableNames.containsKey(variableDecl)) {
            throw new RuntimeException("Variable " + variableDecl + " is already in the symbol table");
        }
        variableNames.put(variableDecl, new ArrayList());
    }

    public void addDeclaration(MethodNameDeclaration methodDecl) {
        parent.addDeclaration(methodDecl);
    }

    public boolean contains(NameOccurrence occurrence) {
        return findVariableHere(occurrence) != null;
    }

    public Map getVariableDeclarations(boolean lookingForUsed) {
        VariableUsageFinderFunction f = new VariableUsageFinderFunction(variableNames, lookingForUsed);
        Applier.apply(f, variableNames.keySet().iterator());
        return f.getUsed();
    }

    public NameDeclaration addVariableNameOccurrence(NameOccurrence occurrence) {
       //System.err.println("AbstractScope.addVariableNameOccurrence("+occurrence+")");
        NameDeclaration decl = findVariableHere(occurrence);
        if (decl != null && !occurrence.isThisOrSuper()) {
            List nameOccurrences = (List) variableNames.get(decl);
            nameOccurrences.add(occurrence);
        }
        return decl;
    }

    protected abstract NameDeclaration findVariableHere(NameOccurrence occurrence);

    protected String glomNames() {
        String result = "";
        for (Iterator i = variableNames.keySet().iterator(); i.hasNext();) {
            result += i.next().toString() + ",";
        }
        return result;
    }

}
