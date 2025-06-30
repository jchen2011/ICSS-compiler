package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;

import java.util.List;

/**
 * Abstract class for applying transformations to AST nodes.
 * Used to traverse and modify parts of the tree based on specific rules.
 */
public abstract class ASTNodeTransform {
    protected final Evaluator evaluator;

    public ASTNodeTransform(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * Replaces a single child node of a parent with a new node.
     *
     * @param parent   The parent node whose child should be replaced.
     * @param oldChild The child node to be replaced.
     * @param newChild The new node that will replace the old one.
     */
    protected void replaceChild (ASTNode parent, ASTNode oldChild, ASTNode newChild) {
        parent.removeChild(oldChild);
        parent.addChild(newChild);
    }

    /**
     * Replaces a single child node with multiple new nodes.
     *
     * @param parent    The parent node whose child should be replaced.
     * @param oldChild  The child node to be replaced.
     * @param newChild  A list of new child nodes to add instead.
     */
    protected void replaceChildWithChildren(ASTNode parent, ASTNode oldChild, List<ASTNode> newChild) {
        parent.removeChild(oldChild);
        if (newChild != null) {
            for (ASTNode child : newChild) {
                parent.addChild(child);
            }
        }
    }

    /**
     * Abstract method to be implemented by subclasses to perform a transformation
     * on a specific AST node and its parent.
     *
     * @param node   The node to transform.
     * @param parent The parent of the node.
     */
    public abstract void transformNode(ASTNode node, ASTNode parent);
}
