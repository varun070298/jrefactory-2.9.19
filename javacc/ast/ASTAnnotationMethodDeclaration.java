/* Generated By:JJTree: Do not edit this line. ASTAnnotationMethodDeclaration.java */

public class ASTAnnotationMethodDeclaration extends SimpleNode {
  public ASTAnnotationMethodDeclaration(int id) {
    super(id);
  }

  public ASTAnnotationMethodDeclaration(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}