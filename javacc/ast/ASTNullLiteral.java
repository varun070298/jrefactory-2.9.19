/* Generated By:JJTree: Do not edit this line. ASTNullLiteral.java */

public class ASTNullLiteral extends SimpleNode {
  public ASTNullLiteral(int id) {
    super(id);
  }

  public ASTNullLiteral(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
