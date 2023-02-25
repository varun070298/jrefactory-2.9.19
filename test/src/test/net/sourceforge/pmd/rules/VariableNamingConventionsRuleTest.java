package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.VariableNamingConventionsRule;

public class VariableNamingConventionsRuleTest extends SimpleAggregatorTst {

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "finals should be all caps", 1, new VariableNamingConventionsRule()),
           new TestDescriptor(TEST2, "non-finals shouldn't have underscores", 1, new VariableNamingConventionsRule()),
           new TestDescriptor(TEST3, "variables names should start with lowercase character", 1, new VariableNamingConventionsRule()),
           new TestDescriptor(TEST4, "all is well", 0, new VariableNamingConventionsRule()),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " private static final int foo = 2;" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " private int foo_bar = 2;" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " private int Ubar = 2;" + PMD.EOL +
    "}";

    private static final String TEST4 =
    "public class Foo {" + PMD.EOL +
    " private int bar = 2;" + PMD.EOL +
    " private static final int FOO_BAR = 2;" + PMD.EOL +
    "}";
}
