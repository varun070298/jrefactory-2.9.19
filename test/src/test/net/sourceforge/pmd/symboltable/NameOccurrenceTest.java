package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.pmd.symboltable.LocalScope;
import org.acm.seguin.pmd.symboltable.NameOccurrence;

public class NameOccurrenceTest extends TestCase {

    public void testConstructor() {
        SimpleNode node = new ASTPrimaryExpression(1);
        node.testingOnly__setBeginLine(10);
        LocalScope lclScope = new LocalScope();
        node.setScope(lclScope);
        NameOccurrence occ = new NameOccurrence(node, "foo");
        assertEquals("foo", occ.getImage());
        assertTrue(!occ.isThisOrSuper());
        assertEquals(new NameOccurrence(null, "foo"), occ);
        assertEquals(10, occ.getBeginLine());
    }
}
