package nl.han.ica.icss.ast.booloperations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.BooleanExpression;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.literals.BoolLiteral;

import java.util.ArrayList;

public class NotOperation extends BooleanExpression {
    public Expression value;

    @Override
    public ASTNode addChild(ASTNode child) {
        if(value == null) {
            value = (Expression) child;
        }
        return this;
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> child = new ArrayList<>();
        if(value != null)
            child.add(value);
        return child;
    }

    @Override
    public String getNodeLabel() {
        return "Not";
    }

    /**
     * Evaluates the NOT operation on the given boolean operand.
     * This method returns the negation of the operand's value.
     *
     * @param operand The BoolLiteral to negate.
     * @return The result of the logical NOT operation.
     */
    public boolean evaluate(BoolLiteral operand) {
        return operand.value = !operand.value;
    }
}
