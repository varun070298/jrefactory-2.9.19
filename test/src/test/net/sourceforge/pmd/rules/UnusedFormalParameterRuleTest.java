package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.UnusedFormalParameterRule;

public class UnusedFormalParameterRuleTest extends SimpleAggregatorTst {

    private UnusedFormalParameterRule rule;

    public void setUp() {
        rule = new UnusedFormalParameterRule();
        rule.setMessage("Avoid this stuff -> ''{0}''");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "one parameter", 1, rule),
           new TestDescriptor(TEST2, "fully qualified parameter", 0, rule),
           new TestDescriptor(TEST3, "one parameter with a method call", 0, rule),
           new TestDescriptor(TEST4, "interface", 0, rule)
       });
    }

    private static final String TEST1 =
    "class UnusedFormalParam1 {" + PMD.EOL +
    "    private void testMethod(String param1) {" + PMD.EOL +
    "        //System.out.println(param1);" + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "class UnusedFormalParam2 {" + PMD.EOL +
    "    private void foo (String s) " + PMD.EOL +
    "    {String str = s.toString();}" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "class UnusedFormalParam3 {" + PMD.EOL +
    "    private void t1(String s) {" + PMD.EOL +
    "        s.toString();" + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public interface Foo {" + PMD.EOL +
    " void foo(String bar);" + PMD.EOL +
    "}";
}
