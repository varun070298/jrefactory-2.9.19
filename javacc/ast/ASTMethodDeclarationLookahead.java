/* Generated By:JJTree: Do not edit this line. ASTMethodDeclarationLookahead.java */

public class ASTMethodDeclarationLookahead extends SimpleNode {
  public ASTMethodDeclarationLookahead(int id) {
    super(id);
  }

  public ASTMethodDeclarationLookahead(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
