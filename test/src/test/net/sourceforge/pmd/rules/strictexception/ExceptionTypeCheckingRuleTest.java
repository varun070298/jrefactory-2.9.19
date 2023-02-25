package test.net.sourceforge.pmd.rules.strictexception;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.strictexception.ExceptionTypeChecking;
import test.net.sourceforge.pmd.rules.SimpleAggregatorTst;
import test.net.sourceforge.pmd.rules.TestDescriptor;

public class ExceptionTypeCheckingRuleTest extends SimpleAggregatorTst  {

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "checks for NPE", 1, new ExceptionTypeChecking()),
           new TestDescriptor(TEST2, "ok", 0, new ExceptionTypeChecking()),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  try {} catch (Exception e) {" + PMD.EOL +
    "   if (e instanceof NullPointerException) {}" + PMD.EOL +
    "  }" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " void foo() {" + PMD.EOL +
    "  try {} catch (Exception e) {}" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

}
