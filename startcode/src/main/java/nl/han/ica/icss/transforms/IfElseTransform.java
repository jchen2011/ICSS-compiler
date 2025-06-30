package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.ElseClause;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.literals.BoolLiteral;

import java.util.Optional;

public class IfElseTransform extends ASTNodeTransform {

    public IfElseTransform(Evaluator evaluator) {
        super(evaluator);
    }

    /**
     * Transforms an IfClause node by checking its condition and replacing it with
     * either the if-body, else-body, or nothing.
     *
     * @param node   The node to transform. Must be an instance of IfClause.
     * @param parent The parent of the node in the AST.
     */
    @Override
    public void transformNode(ASTNode node, ASTNode parent) {
        if (!(node instanceof IfClause ifClause)) {
            return;
        }

        BoolLiteral condition = (BoolLiteral) evaluator.evaluateExpression(ifClause.conditionalExpression);

        if (condition.value) {
            // Condition is true: keep the if-body
            this.evaluator.validateNodes(node);
            replaceChildWithChildren(parent, node, ifClause.body);
        } else {
            // Condition is false: check for else clause
            Optional<ElseClause> optionalElseClause = Optional.ofNullable(ifClause.elseClause);
            if (optionalElseClause.isPresent()) {
                this.evaluator.validateNodes(node);
                replaceChildWithChildren(parent, node, optionalElseClause.get().body);
            } else {
                // No else clause, remove the if-statement completely
                replaceChildWithChildren(parent, node, null);
            }
        }
    }
}
