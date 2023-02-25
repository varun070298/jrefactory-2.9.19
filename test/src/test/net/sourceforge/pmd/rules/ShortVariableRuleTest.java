package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.rules.XPathRule;

public class ShortVariableRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        rule.addProperty("xpath", "//VariableDeclaratorId[string-length(@Image) < 3][not(ancestor::ForInit)][not((ancestor::FormalParameter) and (ancestor::TryStatement))]");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "param", 1, rule),
           new TestDescriptor(TEST2, "none", 0, rule),
           new TestDescriptor(TEST3, "local", 1, rule),
           new TestDescriptor(TEST4, "for", 0, rule),
           new TestDescriptor(TEST5, "field", 1, rule),
           new TestDescriptor(TEST6, "catch(Exception e) is OK", 0, rule),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    "    public static void main(String a[]) { // a should trigger it." + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    "    public static void main(String args[]) {" + PMD.EOL +
    "       int bugleDeWump = -1;" + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    "" + PMD.EOL +
    "    public static void main(String args[]) {" + PMD.EOL +
    "       int ab = -1; " + PMD.EOL +
    "       // Should trigger ShortVariable rule." + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    "    public static void main(String args[]) {" + PMD.EOL +
    "       for (int i = 0; i < 10; i++) { } // Should NOT!! trigger." + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class Foo {" + PMD.EOL +
    "    private int qx; // Should cause a problem." + PMD.EOL +
    "}";

    private static final String TEST6 =
    "public class Foo {" + PMD.EOL +
    "    private void bar() {" + PMD.EOL +
    "     try {} catch (Exception e) {}" + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

}
