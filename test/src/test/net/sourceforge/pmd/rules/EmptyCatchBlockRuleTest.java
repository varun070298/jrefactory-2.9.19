package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.rules.XPathRule;

public class EmptyCatchBlockRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        rule.addProperty(
            "xpath",
            "//TryStatement[@Catch='true'][FormalParameter/Type/ReferenceType/ClassOrInterfaceType[@Image != 'InterruptedException']][FormalParameter/Type/ReferenceType/ClassOrInterfaceType[@Image != 'CloneNotSupportedException']]"
                + "/Block"
                + "[position() > 1]"
                + "[count(*) = 0]"
                + "[../@Finally='false' or following-sibling::Block]");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "simple failure", 1, rule),
           new TestDescriptor(TEST2, "ok", 0, rule),
           new TestDescriptor(TEST3, "no catch with nested catch in finally", 1, rule),
           new TestDescriptor(TEST4, "multiple catch blocks", 2, rule),
           new TestDescriptor(TEST5, "empty try with finally", 0, rule),
           new TestDescriptor(TEST6, "InterruptedException is OK", 0, rule),
           new TestDescriptor(TEST7, "CloneNotSupportedException is OK", 0, rule),
       });
    }

    public static final String TEST1 =
    "import java.io.*;" + PMD.EOL +
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  try {" + PMD.EOL +
    "   FileReader fr = new FileReader(\"/dev/null\");" + PMD.EOL +
    "  } catch (Exception e) {}" + PMD.EOL +
    "  try {" + PMD.EOL +
    "   FileReader fr = new FileReader(\"/dev/null\");" + PMD.EOL +
    "  } catch (Exception e) {" + PMD.EOL +
    "   e.printStackTrace();" + PMD.EOL +
    "   // this shouldn't show up on the report" + PMD.EOL +
    "  }" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    "    public Foo() {" + PMD.EOL +
    "       try {" + PMD.EOL +
    "       } catch (RuntimeException e) {e.getMessage();}" + PMD.EOL +
    "    }" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " private void foo() {" + PMD.EOL +
    "  try {" + PMD.EOL +
    "  } finally {" + PMD.EOL +
    "   try {" + PMD.EOL +
    "    int x =2;" + PMD.EOL +
    "   } catch (Exception e) {}" + PMD.EOL +
    "  }" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " private void foo() {" + PMD.EOL +
    "  try {" + PMD.EOL +
    "  } catch (Exception e) {" + PMD.EOL +
    "  } catch (Throwable t) {" + PMD.EOL +
    "  }" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class Foo {" + PMD.EOL +
    " public void foo() {" + PMD.EOL +
    "  try {" + PMD.EOL +
    "  } catch (Exception e) {" + PMD.EOL +
    "   blah.blah();" + PMD.EOL +
    "  } finally {}" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST6 =
    "public class Foo {" + PMD.EOL +
    " public void foo() {" + PMD.EOL +
    "  try {" + PMD.EOL +
    "  } catch (InterruptedException e) {}" + PMD.EOL +
    " }" + PMD.EOL +
    "}";

    private static final String TEST7 =
    "public class Foo {" + PMD.EOL +
    " public void foo() {" + PMD.EOL +
    "  try {" + PMD.EOL +
    "  } catch (CloneNotSupportedException e) {}" + PMD.EOL +
    " }" + PMD.EOL +
    "}";
}

