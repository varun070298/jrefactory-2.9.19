/* Generated By:JJTree: Do not edit this line. ASTReferenceType.java */

public class ASTReferenceType extends SimpleNode {
  public ASTReferenceType(int id) {
    super(id);
  }

  public ASTReferenceType(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
