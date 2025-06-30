package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.booloperations.ComparisonOperation;
import nl.han.ica.icss.ast.booloperations.NotOperation;
import nl.han.ica.icss.ast.literals.*;

import java.util.LinkedList;
import java.util.List;

public class Evaluator implements Transform {

    private List<ASTNodeTransform> transforms;
    private SymbolTable<String, Literal> table;

    public Evaluator() {
        this.transforms = new LinkedList<>();
        this.transforms = List.of(
                new IfElseTransform(this),
                new DeclarationNodeTransform(this)
        );
    }

    @Override
    public void apply(AST ast) {
        this.table = new SymbolTable<>();
        validateNodes(ast.root);
    }

    protected void validateNodes(ASTNode node) {
        for (ASTNode child : node.getChildren()) {
            handleScopeEntry(child);

            validateNode(child, node);
            validateNodes(child);

            handleScopeExit(child);
        }
    }

    private void validateNode(ASTNode node, ASTNode parent) {
        for (ASTNodeTransform transform : transforms) {
            transform.transformNode(node, parent);
        }
    }

    private void handleScopeEntry(ASTNode node) {
        if (node instanceof Stylerule) {
            table.newScope();
        } else if (node instanceof VariableAssignment assignment) {
            table.assignSymbol(assignment.name.name, evaluateExpression(assignment.expression));
        }
    }

    private void handleScopeExit(ASTNode node) {
        if (node instanceof Stylerule) {
            table.removeScope();
        }
    }

    public Literal evaluateExpression(Expression expression) {
        if (expression instanceof Operation operation) {
            return evaluateOperation(operation);
        }
        if (expression instanceof VariableReference ref) {
            return table.getValue(ref.name);
        }
        if (expression instanceof Literal literal) {
            return literal;
        }
        if (expression instanceof BooleanExpression boolExpr) {
            return evaluateBooleanExpression(boolExpr);
        }
        return null;
    }

    private NumberLiteral evaluateOperation(Operation operation) {
        NumberLiteral left = (NumberLiteral) evaluateExpression(operation.lhs);
        NumberLiteral right = (NumberLiteral) evaluateExpression(operation.rhs);
        return operation.evaluateOperation(left, right);
    }

    private BoolLiteral evaluateBooleanExpression(BooleanExpression booleanExpression) {
        if (booleanExpression instanceof NotOperation notOperation) {
            BoolLiteral value = (BoolLiteral) evaluateExpression(notOperation.value);
            return new BoolLiteral(notOperation.evaluate(value));
        }
        if (booleanExpression instanceof ComparisonOperation comparisonOperation) {
            return comparisonOperation.evaluateOperation(evaluateExpression(comparisonOperation.lhs), evaluateExpression(comparisonOperation.rhs));
        }
        return null;
    }

    public SymbolTable<String, Literal> getSymbolTable() {
        return this.table;
    }
}
