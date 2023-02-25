package test.net.sourceforge.pmd.rules.design;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.design.NullAssignmentRule;
import test.net.sourceforge.pmd.rules.RuleTst;

public class NullAssignmentRuleTest extends RuleTst {

    private static final String TEST1 =
    "public class NullAssignment1 {" + PMD.EOL +
    "       public Object foo() {" + PMD.EOL +
    "               Object x = null; // OK" + PMD.EOL +
    "               return x;" + PMD.EOL +
    "       }       " + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class NullAssignment2 {" + PMD.EOL +
    "       public void foo() {" + PMD.EOL +
    "               Object x;" + PMD.EOL +
    "               x = new Object();" + PMD.EOL +
    "               for (int y = 0; y < 10; y++) {" + PMD.EOL +
    "                       System.err.println(y);  " + PMD.EOL +
    "               }" + PMD.EOL +
    "               x = null; // This is bad" + PMD.EOL +
    "       }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class NullAssignment3 {" + PMD.EOL +
    "       public void foo() {" + PMD.EOL +
    "               Object x;" + PMD.EOL +
    "               if (x == null) { // This is OK" + PMD.EOL +
    "                       return;" + PMD.EOL +
    "               }" + PMD.EOL +
    "       }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class NullAssignment4 {" + PMD.EOL +
    " public void foo() {" + PMD.EOL +
    "  String x = null;" + PMD.EOL +
    "  x = new String(null);" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    public void testInitAssignment() throws Throwable {
        runTestFromString(TEST1, 0, new NullAssignmentRule());
    }

    public void testBadAssignment() throws Throwable {
        runTestFromString(TEST2, 1, new NullAssignmentRule());
    }

    public void testCheckTest() throws Throwable {
        runTestFromString(TEST3, 0, new NullAssignmentRule());
    }

    public void testNullParamOnRHS() throws Throwable {
        runTestFromString(TEST4, 0, new NullAssignmentRule());
    }
}
