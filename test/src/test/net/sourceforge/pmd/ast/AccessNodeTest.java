package test.net.sourceforge.pmd.ast;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.AccessNode;

public class AccessNodeTest extends TestCase {
    public void testStatic() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not static.", !node.isStatic());

        node.setStatic();
        assertTrue("Node set to static, not static.", node.isStatic());
    }

    public void testPublic() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not public.", !node.isPublic());

        node.setPublic();
        assertTrue("Node set to public, not public.", node.isPublic());
    }

    public void testProtected() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not protected.", !node.isProtected());

        node.setProtected();
        assertTrue("Node set to protected, not protected.", node.isProtected());
    }

    public void testPrivate() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not private.", !node.isPrivate());

        node.setPrivate();
        assertTrue("Node set to private, not private.", node.isPrivate());
    }

    public void testFinal() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not final.", !node.isFinal());

        node.setFinal();
        assertTrue("Node set to final, not final.", node.isFinal());
    }

    public void testSynchronized() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not synchronized.", !node.isSynchronized());

        node.setSynchronized();
        assertTrue("Node set to synchronized, not synchronized.", node.isSynchronized());
    }

    public void testVolatile() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not volatile.", !node.isVolatile());

        node.setVolatile();
        assertTrue("Node set to volatile, not volatile.", node.isVolatile());
    }

    public void testTransient() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not transient.", !node.isTransient());

        node.setTransient();
        assertTrue("Node set to transient, not transient.", node.isTransient());
    }

    public void testNative() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not native.", !node.isNative());

        node.setNative();
        assertTrue("Node set to native, not native.", node.isNative());
    }

    public void testInterface() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not interface.", !node.isInterface());

        node.setInterface();
        assertTrue("Node set to interface, not interface.", node.isInterface());
    }

    public void testAbstract() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not abstract.", !node.isAbstract());

        node.setAbstract();
        assertTrue("Node set to abstract, not abstract.", node.isAbstract());
    }

    public void testStrict() {
        AccessNode node = new AccessNode(1);

        assertTrue("Node should default to not strict.", !node.isStrictFP());

        node.setStrict();
        assertTrue("Node set to strict, not strict.", node.isStrictFP());
    }

    //FIXME? MRA public void testSuper() {
    //FIXME? MRA     AccessNode node = new AccessNode(1);
    //FIXME? MRA 
    //FIXME? MRA     assertTrue("Node should default to not super.", !node.isSuper());
    //FIXME? MRA 
    //FIXME? MRA     node.setSuper();
    //FIXME? MRA     assertTrue("Node set to super, not super.", node.isSuper());
    //FIXME? MRA }
}
