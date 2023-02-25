package test.net.sourceforge.pmd.cpd;

import junit.framework.TestCase;
import org.acm.seguin.pmd.cpd.Mark;

public class MarkTest extends TestCase {

    public void testSimple() {
        Mark mark = new Mark(0, "/var/Foo.java", 10, 1);
        assertEquals(mark.getIndexIntoFile(), 10);
        assertEquals(1, mark.getBeginLine());
        assertEquals("/var/Foo.java", mark.getTokenSrcID());
        assertEquals(0, mark.getIndexIntoTokenArray());
    }
}
