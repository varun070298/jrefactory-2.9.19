package org.acm.seguin.pmd;

import net.sourceforge.jrefactory.parser.JavaParser;

import java.io.InputStream;
import java.io.Reader;

public class JLS1_4 implements JLSVersion {
    public JavaParser createParser(InputStream in) {
        return new JavaParser(in);
    }

    public JavaParser createParser(Reader in) {
        return new JavaParser(in);
    }
}
