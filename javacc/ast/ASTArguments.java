/* Generated By:JJTree: Do not edit this line. ASTArguments.java */

public class ASTArguments extends SimpleNode {
  public ASTArguments(int id) {
    super(id);
  }

  public ASTArguments(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}