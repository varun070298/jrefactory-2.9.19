package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.ConstructorCallsOverridableMethodRule;

public class ConstructorCallsOverridableMethodRuleTest extends SimpleAggregatorTst {

    public void testAll() {
       runTests(new TestDescriptor[] {
           //new TestDescriptor(TEST1, "", 1, new ConstructorCallsOverridableMethodRule()),
           //new TestDescriptor(TEST2, "", 1, new ConstructorCallsOverridableMethodRule()),
           //new TestDescriptor(TEST3, "", 1, new ConstructorCallsOverridableMethodRule()),
           //new TestDescriptor(TEST4, "", 0, new ConstructorCallsOverridableMethodRule()),
           new TestDescriptor(TEST5, "", 1, new ConstructorCallsOverridableMethodRule()),
           //new TestDescriptor(TEST6, "calling method on literal bug", 0, new ConstructorCallsOverridableMethodRule()),
           //new TestDescriptor(TEST7, "method in anonymous inner class is ok", 0, new ConstructorCallsOverridableMethodRule()),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " public void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " protected void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " private void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST5 =
    "public class Foo {" + PMD.EOL +
    " public Foo() {" + PMD.EOL +
    "  this(\"Bar\");" + PMD.EOL +
    " }" + PMD.EOL +
    " private Foo(String bar) {" + PMD.EOL +
    "  bar();" + PMD.EOL +
    " }" + PMD.EOL +
    " public void bar() {}" + PMD.EOL +
    "}";

    private static final String TEST6 =
    "public class Foo {" + PMD.EOL +
    " public Foo(String s) {" + PMD.EOL +
    "  \"foo\".equals(s);" + PMD.EOL +
    " }" + PMD.EOL +
    " public void equals(String bar) {}" + PMD.EOL +
    "}";

    private static final String TEST7 =
    "public class Foo {" + PMD.EOL +
    " public Foo(String s) {" + PMD.EOL +
    "  addActionListener(new ActionListener() {" + PMD.EOL +
    "   public void actionPerformed(ActionEvent e) {bar();}" + PMD.EOL +
    "  });" + PMD.EOL +
    " }" + PMD.EOL +
    " public void bar() {}" + PMD.EOL +
    "}";
}
