/* Generated By:JJTree: Do not edit this line. ASTAnnotationTypeMemberDeclaration.java */

public class ASTAnnotationTypeMemberDeclaration extends SimpleNode {
  public ASTAnnotationTypeMemberDeclaration(int id) {
    super(id);
  }

  public ASTAnnotationTypeMemberDeclaration(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
