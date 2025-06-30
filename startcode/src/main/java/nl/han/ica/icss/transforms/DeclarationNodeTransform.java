package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Declaration;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Literal;

public class DeclarationNodeTransform extends ASTNodeTransform {

    public DeclarationNodeTransform(Evaluator evaluator) {
        super(evaluator);
    }

    @Override
    public void transformNode(ASTNode node, ASTNode parent) {
        if (node instanceof Expression expression && parent instanceof Declaration) {
            Literal evaluated = evaluator.evaluateExpression(expression);
            replaceChild(parent, node, evaluated);
        }
    }
}
