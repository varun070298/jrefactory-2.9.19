/* Generated By:JJTree: Do not edit this line. ASTAttribute.java */

public class ASTAttribute extends SimpleNode {
  public ASTAttribute(int id) {
    super(id);
  }

  public ASTAttribute(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
