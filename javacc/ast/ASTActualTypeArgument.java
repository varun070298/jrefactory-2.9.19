/* Generated By:JJTree: Do not edit this line. ASTActualTypeArgument.java */

public class ASTActualTypeArgument extends SimpleNode {
  public ASTActualTypeArgument(int id) {
    super(id);
  }

  public ASTActualTypeArgument(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
