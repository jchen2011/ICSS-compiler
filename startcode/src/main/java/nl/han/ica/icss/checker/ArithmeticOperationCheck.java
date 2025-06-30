package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class ArithmeticOperationCheck extends ASTValidationRule {

    public ArithmeticOperationCheck(Checker checker) {
        super(checker);
    }

    /**
     * Checks if an arithmetic operation uses the right types of values.
     *
     * - Colors and booleans are not allowed in any arithmetic operation.
     * - Add and subtract only work if both sides have the same type (like both are pixels).
     * - Multiply needs at least one side to be a scalar (a plain number).
     * - Other operations must have scalars on both sides.
     *
     *
     * @param node The AST node that is an operation
     */
    @Override
    public void validate(ASTNode node) {
        if (!(node instanceof Operation operation)) {
            return;
        }

        ExpressionType lhs = checker.resolveExpressionType(operation.lhs);
        ExpressionType rhs = checker.resolveExpressionType(operation.rhs);

        if (lhs == ExpressionType.COLOR || rhs == ExpressionType.COLOR) {
            operation.setError("Color values cannot be used in arithmetic operations.");
            return;
        }

        if (lhs == ExpressionType.BOOL || rhs == ExpressionType.BOOL) {
            operation.setError("Boolean values cannot be used in arithmetic operations.");
            return;
        }

        if (operation instanceof AddOperation || operation instanceof SubtractOperation) {
            if (lhs != rhs) {
                operation.setError("Operands of add and subtract operations must be of the same type.");
            }
        } else if (operation instanceof MultiplyOperation) {
            if (lhs != ExpressionType.SCALAR && rhs != ExpressionType.SCALAR) {
                operation.setError("One operand of a multiply operation must be a scalar.");
            }
        } else {
            if (lhs != ExpressionType.SCALAR || rhs != ExpressionType.SCALAR) {
                operation.setError("This operation requires both operands to be scalar.");
            }
        }
    }

}
