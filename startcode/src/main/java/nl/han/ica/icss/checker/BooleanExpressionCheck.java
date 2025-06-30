package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.BooleanExpression;
import nl.han.ica.icss.ast.booloperations.AndOperation;
import nl.han.ica.icss.ast.booloperations.LessThanOperation;
import nl.han.ica.icss.ast.booloperations.NotOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class BooleanExpressionCheck extends ASTValidationRule {

    public BooleanExpressionCheck(Checker checker) {
        super(checker);
    }

    /**
     * Checks if a boolean expression uses the correct value types.
     *
     * - The 'not' operator (!) can only be used on boolean values.
     * - The 'and' operator (&&) must have boolean values on both sides.
     * - The 'less than' operator (<) must compare values of the same type,
     *   and only works with numeric types (scalar, pixel, percentage).
     *
     * @param node The AST node that is a boolean expression
     */
    @Override
    public void validate(ASTNode node) {
        if (!(node instanceof BooleanExpression)) return;

        if (node instanceof NotOperation notOp) {
            if (checker.resolveExpressionType(notOp.value) != ExpressionType.BOOL) {
                notOp.setError("The 'not' operator can only be used with boolean values.");
            }

        } else if (node instanceof AndOperation andOp) {
            ExpressionType lhs = checker.resolveExpressionType(andOp.lhs);
            ExpressionType rhs = checker.resolveExpressionType(andOp.rhs);
            if (lhs != ExpressionType.BOOL || rhs != ExpressionType.BOOL) {
                andOp.setError("Both sides of '&&' must be boolean expressions.");
            }

        } else if (node instanceof LessThanOperation ltOp) {
            ExpressionType lhs = checker.resolveExpressionType(ltOp.lhs);
            ExpressionType rhs = checker.resolveExpressionType(ltOp.rhs);

            if (lhs != rhs) {
                ltOp.setError("Both sides of '<' must be of the same type.");
            } else if (!(isNumericType(lhs) && isNumericType(rhs))) {
                ltOp.setError("'<'' can only be used with numeric types.");
            }
        }
    }

    private boolean isNumericType(ExpressionType type) {
        return type == ExpressionType.SCALAR
                || type == ExpressionType.PIXEL
                || type == ExpressionType.PERCENTAGE;
    }

}
