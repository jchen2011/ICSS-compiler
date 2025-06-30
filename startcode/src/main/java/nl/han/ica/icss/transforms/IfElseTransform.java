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

    @Override
    public void transformNode(ASTNode node, ASTNode parent) {
        if (!(node instanceof IfClause ifClause)) {
            return;
        }

        BoolLiteral condition = (BoolLiteral) evaluator.evaluateExpression(ifClause.conditionalExpression);

        if (condition.value) {
            this.evaluator.validateNodes(node);
            replaceChildWithChildren(parent, node, ifClause.body);
        } else {
            Optional<ElseClause> optionalElseClause = Optional.ofNullable(ifClause.elseClause);
            if (optionalElseClause.isPresent()) {
                this.evaluator.validateNodes(node);
                replaceChildWithChildren(parent, node, optionalElseClause.get().body);
            } else {
                replaceChildWithChildren(parent, node, null);
            }
        }
    }
}
