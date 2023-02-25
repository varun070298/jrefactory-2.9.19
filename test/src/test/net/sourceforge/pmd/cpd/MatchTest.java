package test.net.sourceforge.pmd.cpd;

import junit.framework.TestCase;
import org.acm.seguin.pmd.cpd.Mark;
import org.acm.seguin.pmd.cpd.Match;

import java.util.Iterator;

public class MatchTest extends TestCase  {

    public void testSimple() {
        Mark mark1 = new Mark(1, "/var/Foo.java", 1, 1);
        Mark mark2 = new Mark(2, "/var/Foo.java", 2, 1);
        Match match = new Match(1, mark1, mark2);
        match.setSourceCodeSlice("public class Foo {}");
        assertEquals("public class Foo {}", match.getSourceCodeSlice());
        match.setLineCount(10);
        assertEquals(10, match.getLineCount());
        assertEquals(1, match.getTokenCount());
        Iterator i = match.iterator();
        assertEquals(mark1, i.next());
        assertEquals(mark2, i.next());
        assertFalse(i.hasNext());
    }

    public void testCompareTo() {
        Match m1 = new Match(1, new Mark(1, "/var/Foo.java", 1, 1), new Mark(2, "/var/Foo.java", 2, 1));
        Match m2 = new Match(2, new Mark(4, "/var/Foo.java", 4, 1), new Mark(5, "/var/Foo.java", 5, 1));
        assertTrue(m2.compareTo(m1) < 0);
    }

    public void testAddMark() {
        Match m1 = new Match(1, new Mark(1, "/var/Foo.java", 1, 1), new Mark(2, "/var/Foo.java", 2, 1));
        assertEquals(2, m1.getMarkCount());
        m1.add(new Mark(3, "/var/Foo.java", 3, 3));
        assertEquals(3, m1.getMarkCount());
    }
}
