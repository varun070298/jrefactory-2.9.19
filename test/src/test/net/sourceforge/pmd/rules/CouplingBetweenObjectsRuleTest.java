package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.rules.CouplingBetweenObjectsRule;

public class CouplingBetweenObjectsRuleTest extends RuleTst {

    private static final String TEST1 =
    "import java.util.*;" + PMD.EOL +
    "" + PMD.EOL +
    "public class CouplingBetweenObjects1 {" + PMD.EOL +
    " public List foo() {return null;}" + PMD.EOL +
    " public ArrayList foo() {return null;}" + PMD.EOL +
    " public Vector foo() {return null;}" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class CouplingBetweenObjects2 {" + PMD.EOL +
    "}";


    private Rule rule;

    public void setUp() {
        rule = new CouplingBetweenObjectsRule();
        rule.addProperty("threshold", "2");
    }

    public void testSimpleBad() throws Throwable {
        runTestFromString(TEST1, 1, rule);
    }

    public void testSimpleOK() throws Throwable {
        runTestFromString(TEST2, 0, rule);
    }
}
