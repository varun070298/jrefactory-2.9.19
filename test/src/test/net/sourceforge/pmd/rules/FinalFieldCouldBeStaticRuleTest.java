package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.rules.XPathRule;

public class FinalFieldCouldBeStaticRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        rule.addProperty("xpath", "//FieldDeclaration[@Final='true' and @Static='false']/VariableDeclarator/VariableInitializer/Expression//PrimaryPrefix/Literal");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "simple failure case", 1, rule),
           new TestDescriptor(TEST2, "already static, OK", 0, rule),
           new TestDescriptor(TEST3, "non-final, OK", 0, rule),
           new TestDescriptor(TEST4, "non-primitive failure case - only works for String", 1, rule),
           new TestDescriptor(TEST5, "final field that's a thread, OK", 0, rule)
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " public final int BAR = 42;" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " public static final int BAR = 42;" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " public int BAR = 42;" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " public final String BAR = \"42\";" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class Foo {" + PMD.EOL +
    " public final Thread BAR = new Thread();" + PMD.EOL +
    "}";

}
