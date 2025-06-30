package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;

public abstract class ASTValidationRule {
    protected Checker checker;

    public ASTValidationRule(Checker checker) {
        this.checker = checker;
    }

    public abstract void validate(ASTNode node);
}
