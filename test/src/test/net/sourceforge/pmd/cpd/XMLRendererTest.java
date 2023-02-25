package test.net.sourceforge.pmd.cpd;

import junit.framework.TestCase;
import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.cpd.Mark;
import org.acm.seguin.pmd.cpd.Match;
import org.acm.seguin.pmd.cpd.Renderer;
import org.acm.seguin.pmd.cpd.XMLRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Philippe T'Seyen
 */
public class XMLRendererTest extends TestCase
{
  public void testRender_NoMatches()
  {
    Renderer renderer = new XMLRenderer();
    List list = new ArrayList();
    String report = renderer.render(list.iterator());
    assertEquals("<?xml version=\"1.0\"?><pmd-cpd></pmd-cpd>", report);
  }

  public void testRender_Match()
  {
    Renderer renderer = new XMLRenderer();
    List list = new ArrayList();
    Match match = new Match(75, new Mark(1, "/var/Foo.java", 1, 48), new Mark(2, "/var/Foo.java", 2, 73));
    match.setLineCount(6);
    match.setSourceCodeSlice("code fragment");
    list.add(match);
    String report = renderer.render(list.iterator());
    assertEquals("<?xml version=\"1.0\"?><pmd-cpd><duplication lines=\"6\" tokens=\"75\"><file line=\"48\" path=\"/var/Foo.java\"/><file line=\"73\" path=\"/var/Foo.java\"/><codefragment><![CDATA[" + PMD.EOL + "code fragment" + PMD.EOL + "]]></codefragment></duplication></pmd-cpd>", report);
  }

  public void testRender_MultipleMatch()
  {
    Renderer renderer = new XMLRenderer();
    List list = new ArrayList();
    Match match1 = new Match(75, new Mark(1, "/var/Foo.java", 1, 48), new Mark(2, "/var/Foo.java", 2, 73));
    match1.setLineCount(6);
    match1.setSourceCodeSlice("code fragment");
    Match match2 = new Match(76, new Mark(2, "/var/Foo2.java", 2, 49), new Mark(3, "/var/Foo2.java", 3, 74));
    match2.setLineCount(7);
    match2.setSourceCodeSlice("code fragment 2");
    list.add(match1);
    list.add(match2);
    String report = renderer.render(list.iterator());
    assertEquals("<?xml version=\"1.0\"?><pmd-cpd><duplication lines=\"6\" tokens=\"75\"><file line=\"48\" path=\"/var/Foo.java\"/><file line=\"73\" path=\"/var/Foo.java\"/><codefragment><![CDATA[" + PMD.EOL + "code fragment" + PMD.EOL + "]]></codefragment></duplication><duplication lines=\"7\" tokens=\"76\"><file line=\"49\" path=\"/var/Foo2.java\"/><file line=\"74\" path=\"/var/Foo2.java\"/><codefragment><![CDATA[" + PMD.EOL + "code fragment 2" + PMD.EOL + "]]></codefragment></duplication></pmd-cpd>", report);
  }
}

