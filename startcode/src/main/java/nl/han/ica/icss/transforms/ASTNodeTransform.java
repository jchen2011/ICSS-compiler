package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;

import java.util.List;

public abstract class ASTNodeTransform {
    protected final Evaluator evaluator;

    public ASTNodeTransform(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    protected void replaceChild (ASTNode parent, ASTNode oldChild, ASTNode newChild) {
        parent.removeChild(oldChild);
        parent.addChild(newChild);
    }

    protected void replaceChildWithChildren(ASTNode parent, ASTNode oldChild, List<ASTNode> newChild) {
        parent.removeChild(oldChild);
        if (newChild != null) {
            for (ASTNode child : newChild) {
                parent.addChild(child);
            }
        }
    }
    public abstract void transformNode(ASTNode node, ASTNode parent);
}
