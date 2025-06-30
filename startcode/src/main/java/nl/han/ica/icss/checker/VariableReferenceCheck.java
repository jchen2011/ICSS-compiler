package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

public class VariableReferenceCheck extends ASTValidationRule {

    public VariableReferenceCheck(Checker checker) {
        super(checker);
    }

    @Override
    public void validate(ASTNode node) {
        if (node instanceof VariableAssignment assignment) {
            validateReference(assignment.expression);
        }

        if (node instanceof Declaration declaration) {
            validateReference(declaration.expression);
        }

        if (node instanceof Operation operation) {
            validateReference(operation.lhs);
            validateReference(operation.rhs);
        }

        if (node instanceof IfClause ifClause) {
            validateReference(ifClause.conditionalExpression);
        }
    }

    private void validateReference(Expression expression) {
        if (expression instanceof VariableReference reference) {
            ExpressionType type = checker.resolveExpressionType(reference);
            if (type == null) {
                reference.setError("The variable has not been defined in the current scope.");
            }
        }
    }

}
