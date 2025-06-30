package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.types.ExpressionType;

public class IfConditionTypeCheck extends ASTValidationRule {

    public IfConditionTypeCheck(Checker checker) {
        super(checker);
    }

    @Override
    public void validate(ASTNode node) {
        if (!(node instanceof IfClause ifClause)) {
            return;
        }

        ExpressionType conditionType = checker.resolveExpressionType(ifClause.getConditionalExpression());

        if (conditionType != ExpressionType.BOOL) {
            ifClause.setError("The condition of an if-statement must be a boolean expression.");
        }
    }
}
