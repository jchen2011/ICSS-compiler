package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.List;


public class Checker {

    private List<ASTValidationRule> validationRules;
    protected SymbolTable<String, ExpressionType> symbolTable;

    public Checker() {
        this.validationRules = List.of(
                new ArithmeticOperationCheck(this),
                new BooleanExpressionCheck(this),
                new DeclarationTypeCheck(this),
                new IfConditionTypeCheck(this),
                new VariableAssignmentCheck(this),
                new VariableReferenceCheck(this)
        );
    }

    /**
     * Starts the validation process for the given AST.
     *
     * It initializes the symbol table and recursively validates all nodes
     * in the tree by applying a set of validation rules.
     *
     * @param ast The abstract syntax tree to be checked.
     */
    public void check(AST ast) {
        this.symbolTable = new SymbolTable<>();
        validateNodes(ast.root);
    }


    private void validateNodes(ASTNode node) {
        for (ASTNode child : node.getChildren()) {
            handleScopeEntry(child);

            validateNode(child);
            validateNodes(child);

            handleScopeExit(child);
        }
    }

    private void validateNode(ASTNode node) {
        for (ASTValidationRule rule : validationRules) {
            rule.validate(node);
        }
    }

    private void handleScopeEntry(ASTNode node) {
        if (node instanceof IfClause || node instanceof Stylerule) {
            symbolTable.newScope();
        }

        if (node instanceof ElseClause) {
            symbolTable.removeScope();
            symbolTable.newScope();
        }
    }
    private void handleScopeExit(ASTNode node) {
        if (node instanceof VariableAssignment variableAssignment) {
            this.symbolTable.assignSymbol(variableAssignment.name.name, resolveExpressionType(variableAssignment.expression));
        }

        if (node instanceof IfClause || node instanceof Stylerule) {
            symbolTable.removeScope();
        }
    }

    /**
     * Determines the type of an expression.
     *
     * - For literals, returns their literal type (e.g., SCALAR, PIXEL, etc.).
     * - For operations, returns the type based on the right-hand side.
     * - For variable references, returns the type from the symbol table.
     * - For boolean expressions, always returns BOOL.
     * - Returns UNDEFINED if the expression type is unknown.
     *
     * @param expression The expression to analyze.
     * @return The detected ExpressionType.
     */
    public ExpressionType resolveExpressionType(Expression expression) {
        if (expression instanceof Literal literal) {
            return literal.getType();
        }

        if (expression instanceof Operation op) {
            ExpressionType left = resolveExpressionType(op.lhs);
            ExpressionType right = resolveExpressionType(op.rhs);

            if (right == ExpressionType.PERCENTAGE) return ExpressionType.PERCENTAGE;
            if (right == ExpressionType.PIXEL) return ExpressionType.PIXEL;
            return left;
        }

        if (expression instanceof VariableReference ref) {
            return symbolTable.getValue(ref.name);
        }

        if (expression instanceof BooleanExpression) {
            return ExpressionType.BOOL;
        }

        return ExpressionType.UNDEFINED;
    }
}
