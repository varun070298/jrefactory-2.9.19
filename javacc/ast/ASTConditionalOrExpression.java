/* Generated By:JJTree: Do not edit this line. ASTConditionalOrExpression.java */

public class ASTConditionalOrExpression extends SimpleNode {
  public ASTConditionalOrExpression(int id) {
    super(id);
  }

  public ASTConditionalOrExpression(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
