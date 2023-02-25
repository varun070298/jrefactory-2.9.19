package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.rules.ExcessiveImportsRule;

public class ExcessiveImportsRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() {
        rule = new ExcessiveImportsRule();
        rule.addProperty("minimum", "5");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "bad", 1, rule),
           new TestDescriptor(TEST2, "ok", 0, rule),
       });
    }

    private static final String TEST1 =
    "import java.util.Vector;" + PMD.EOL +
    "import java.util.Vector;" + PMD.EOL +
    "import java.util.Vector;" + PMD.EOL +
    "import java.util.Vector;" + PMD.EOL +
    "import java.util.Vector;" + PMD.EOL +
    "import java.util.Vector;" + PMD.EOL +
    "public class Foo{}";

    private static final String TEST2 =
    "import java.util.Vector;" + PMD.EOL +
    "public class Foo{}";


}
