package test.net.sourceforge.pmd.rules.strictexception;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.strictexception.AvoidCatchingThrowable;
import test.net.sourceforge.pmd.rules.SimpleAggregatorTst;
import test.net.sourceforge.pmd.rules.TestDescriptor;

public class AvoidCatchingThrowableRuleTest extends SimpleAggregatorTst {

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "simple failure case", 1, new AvoidCatchingThrowable()),
           new TestDescriptor(TEST2, "ok", 0, new AvoidCatchingThrowable()),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  try {} catch (Throwable t) {}   " + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  try {} catch (RuntimeException t) {}   " + PMD.EOL +
    " }" + PMD.EOL +
    "}";
}
