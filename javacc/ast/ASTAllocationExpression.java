/* Generated By:JJTree: Do not edit this line. ASTAllocationExpression.java */

public class ASTAllocationExpression extends SimpleNode {
  public ASTAllocationExpression(int id) {
    super(id);
  }

  public ASTAllocationExpression(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
