package test.net.sourceforge.pmd;

import junit.framework.TestCase;
import org.acm.seguin.pmd.Report;
import org.acm.seguin.pmd.RuleContext;
import org.acm.seguin.pmd.RuleSet;
import org.acm.seguin.pmd.RuleViolation;
import net.sourceforge.jrefactory.parser.JavaParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RuleSetTest extends TestCase {
    private String javaCode = "public class Test { }";

    public void testConstructor() {
        new RuleSet();
    }

    public void testAccessors() {
        RuleSet rs = new RuleSet();
        rs.setName("foo");
        assertEquals("name mismatch", "foo", rs.getName());
        rs.setDescription("bar");
        assertEquals("description mismatch", "bar", rs.getDescription());
    }

    public void testGetRuleByName() {
        RuleSet rs = new RuleSet();
        MockRule mock = new MockRule("name", "desc", "msg");
        rs.addRule(mock);
        assertEquals("unable to fetch rule by name", mock, rs.getRuleByName("name"));
    }

    public void testRuleList() {
        RuleSet IUT = new RuleSet();

        assertEquals("Size of RuleSet isn't zero.", 0, IUT.size());

        MockRule rule = new MockRule("name", "desc", "msg");
        IUT.addRule(rule);

        assertEquals("Size of RuleSet isn't one.", 1, IUT.size());

        Set rules = IUT.getRules();

        Iterator i = rules.iterator();
        assertTrue("Empty Set", i.hasNext());
        assertEquals("Returned set of wrong size.", 1, rules.size());
        assertEquals("Rule isn't in ruleset.", rule, i.next());
    }

    public void testAddRuleSet() {
        RuleSet set1 = new RuleSet();
        set1.addRule(new MockRule("name", "desc", "msg"));
        RuleSet set2 = new RuleSet();
        set2.addRule(new MockRule("name", "desc", "msg"));
        set1.addRuleSet(set2);
        assertEquals("ruleset size wrong", 2, set1.size());
    }

    public void testApply0Rules() throws Throwable {
        RuleSet IUT = new RuleSet();
        verifyRuleSet(IUT, 0, new HashSet());
    }

    public void testApply1Rule() throws Throwable {
        RuleSet IUT = new RuleSet();

        MockRule rule = new MockRule("name", "desc", "msg");
        RuleContext ctx = new RuleContext();
        ctx.setSourceCodeFilename("filename");
        RuleViolation violation = new RuleViolation(rule, 1, ctx);
        rule.addViolation(violation);

        IUT.addRule(rule);

        verifyRuleSet(IUT, 1, Collections.singleton(violation));
    }

    public void testApplyNRule() throws Throwable {
        RuleSet IUT = new RuleSet();

        Random rand = new Random();
        int numRules = rand.nextInt(10) + 1;
        Set ruleViolations = new HashSet();

        for (int i = 0; i < numRules; i++) {
            MockRule rule = new MockRule("name", "desc", "msg");
            RuleContext ctx = new RuleContext();
            ctx.setSourceCodeFilename("filename");
            RuleViolation violation = new RuleViolation(rule, i, ctx);

            ruleViolations.add(violation);
            rule.addViolation(violation);

            IUT.addRule(rule);
        }

        verifyRuleSet(IUT, numRules, ruleViolations);
    }

    protected void verifyRuleSet(RuleSet IUT, int size, Set values) throws Throwable {

        RuleContext context = new RuleContext();
        Set reportedValues = new HashSet();
        context.setReport(new Report());
        IUT.apply(makeCompilationUnits(), context);

        assertEquals("Invalid number of Violations Reported", size, context.getReport().size());

        Iterator violations = context.getReport().iterator();
        while (violations.hasNext()) {
            RuleViolation violation = (RuleViolation) violations.next();

            reportedValues.add(violation);
            assertTrue("Unexpected Violation Returned: " + violation, values.contains(violation));
        }

        Iterator expected = values.iterator();
        while (expected.hasNext()) {
            RuleViolation violation = (RuleViolation) expected.next();
            assertTrue("Expected Violation not Returned: " + violation, reportedValues.contains(violation));
        }
    }


    protected List makeCompilationUnits() throws Throwable {
        List RC = new ArrayList();
        JavaParser parser = new JavaParser(new StringReader(javaCode));
        RC.add(parser.CompilationUnit());
        return RC;
    }
}
