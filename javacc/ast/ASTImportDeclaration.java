/* Generated By:JJTree: Do not edit this line. ASTImportDeclaration.java */

public class ASTImportDeclaration extends SimpleNode {
  public ASTImportDeclaration(int id) {
    super(id);
  }

  public ASTImportDeclaration(JavaParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JavaParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
