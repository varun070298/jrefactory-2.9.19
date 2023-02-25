package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.UnnecessaryConversionTemporaryRule;

public class UnnecessaryTemporariesRuleTest extends RuleTst {

    private static final String TEST1 =
    " public class UnnecessaryTemporary1 {" + PMD.EOL +
    "     void method (int x) {" + PMD.EOL +
    "        new Integer(x).toString(); " + PMD.EOL +
    "        new Long(x).toString(); " + PMD.EOL +
    "        new Float(x).toString(); " + PMD.EOL +
    "        new Byte((byte)x).toString(); " + PMD.EOL +
    "        new Double(x).toString(); " + PMD.EOL +
    "        new Short((short)x).toString(); " + PMD.EOL +
    "     }" + PMD.EOL +
    " }";

    public void testSimple() throws Throwable {
        runTestFromString(TEST1, 6, new UnnecessaryConversionTemporaryRule());
    }
}
