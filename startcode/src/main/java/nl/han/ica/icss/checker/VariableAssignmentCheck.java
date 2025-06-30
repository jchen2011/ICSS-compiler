package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.VariableAssignment;
import nl.han.ica.icss.ast.types.ExpressionType;

public class VariableAssignmentCheck extends ASTValidationRule {

    public VariableAssignmentCheck(Checker checker) {
        super(checker);
    }

    @Override
    public void validate(ASTNode node) {
        if (!(node instanceof VariableAssignment assignment)) {
            return;
        }

        ExpressionType assignedType = checker.resolveExpressionType(assignment.expression);
        ExpressionType existingType = checker.symbolTable.getValue(assignment.name.name);

        if (existingType != null && assignedType != existingType) {
            assignment.setError("A variable must always be assigned the same type.");
        }
    }

}
