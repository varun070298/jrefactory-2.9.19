/* Generated By:JJTree: Do not edit this line. ASTPreIncrementExpression.java */

public class ASTPreIncrementExpression extends SimpleNode {
  public ASTPreIncrementExpression(int id) {
    super(id);
  }

  public ASTPreIncrementExpression(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}