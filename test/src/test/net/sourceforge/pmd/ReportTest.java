package test.net.sourceforge.pmd;

import junit.framework.TestCase;
import org.acm.seguin.pmd.Report;
import org.acm.seguin.pmd.ReportListener;
import org.acm.seguin.pmd.RuleContext;
import org.acm.seguin.pmd.RuleViolation;
import org.acm.seguin.pmd.renderers.Renderer;
import org.acm.seguin.pmd.renderers.XMLRenderer;
import org.acm.seguin.pmd.stat.Metric;

import java.util.Iterator;

public class ReportTest extends TestCase implements ReportListener {

    private boolean violationSemaphore;
    private boolean metricSemaphore;

    public void testBasic() {
        Report r = new Report();
        RuleContext ctx = new RuleContext();
        ctx.setSourceCodeFilename("foo");
        r.addRuleViolation(new RuleViolation(new MockRule("name", "desc", "msg"), 5, ctx));
        assertTrue(!r.isEmpty());
    }

    public void testMetric0() {
        Report r = new Report();
        assertTrue("Default report shouldn't contain metrics", !r.hasMetrics());
    }

    public void testMetric1() {
        Report r = new Report();
        assertTrue("Default report shouldn't contain metrics", !r.hasMetrics());

        r.addMetric(new Metric("m1", 0, 0.0, 1.0, 2.0, 3.0, 4.0));
        assertTrue("Expected metrics weren't there", r.hasMetrics());

        Iterator ms = r.metrics();
        assertTrue("Should have some metrics in there now", ms.hasNext());

        Object o = ms.next();
        assertTrue("Expected Metric, got " + o.getClass(), o instanceof Metric);

        Metric m = (Metric) o;
        assertEquals("metric name mismatch", "m1", m.getMetricName());
        assertEquals("wrong low value", 1.0, m.getLowValue(), 0.05);
        assertEquals("wrong high value", 2.0, m.getHighValue(), 0.05);
        assertEquals("wrong avg value", 3.0, m.getAverage(), 0.05);
        assertEquals("wrong std dev value", 4.0, m.getStandardDeviation(), 0.05);
    }


    // Files are grouped together now.
    public void testSortedReport_File() {
        Report r = new Report();
        RuleContext ctx = new RuleContext();
        ctx.setSourceCodeFilename("foo");
        r.addRuleViolation(new RuleViolation(new MockRule("name", "desc", "msg"), 10, ctx));
        ctx.setSourceCodeFilename("bar");
        r.addRuleViolation(new RuleViolation(new MockRule("name", "desc", "msg"), 20, ctx));
        Renderer rend = new XMLRenderer();
        String result = rend.render(r);
        assertTrue("sort order wrong", result.indexOf("bar") < result.indexOf("foo"));
    }

    public void testSortedReport_Line() {
        Report r = new Report();
        RuleContext ctx = new RuleContext();
        ctx.setSourceCodeFilename("foo1");
        r.addRuleViolation(new RuleViolation(new MockRule("rule2", "rule2", "msg"), 10, ctx));
        ctx.setSourceCodeFilename("foo2");
        r.addRuleViolation(new RuleViolation(new MockRule("rule1", "rule1", "msg"), 20, ctx));
        Renderer rend = new XMLRenderer();
        String result = rend.render(r);
        assertTrue("sort order wrong", result.indexOf("rule2") < result.indexOf("rule1"));
    }

    public void testListener() {
        Report rpt = new Report();
        rpt.addListener(this);
        violationSemaphore = false;
        RuleContext ctx = new RuleContext();
        ctx.setSourceCodeFilename("file");
        rpt.addRuleViolation(new RuleViolation(new MockRule("name", "desc", "msg"), 5, ctx));
        assertTrue(violationSemaphore);

        metricSemaphore = false;
        rpt.addMetric(new Metric("test", 0, 0.0, 0.0, 0.0, 0.0, 0.0));

        assertTrue("no metric", metricSemaphore);
    }

    public void ruleViolationAdded(RuleViolation ruleViolation) {
        violationSemaphore = true;
    }

    public void metricAdded(Metric metric) {
        metricSemaphore = true;
    }

}
