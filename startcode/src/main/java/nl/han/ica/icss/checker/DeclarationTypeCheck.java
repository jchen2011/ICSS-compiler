package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Declaration;
import nl.han.ica.icss.ast.types.ExpressionType;

public class DeclarationTypeCheck extends ASTValidationRule {

    public DeclarationTypeCheck(Checker checker) {
        super(checker);
    }

    @Override
    public void validate(ASTNode node) {
        if (!(node instanceof Declaration declaration)) {
            return;
        }

        ExpressionType type = checker.resolveExpressionType(declaration.expression);
        String property = declaration.property.name;

        switch (property) {
            case "color", "background-color" -> {
                if (type != ExpressionType.COLOR) {
                    declaration.setError("Colors must be written using hexadecimal values.");
                }
            }

            case "width", "height" -> {
                if (type != ExpressionType.PERCENTAGE && type != ExpressionType.PIXEL) {
                    declaration.setError("Sizes must be given in pixels or percentages.");
                }
            }
        }
    }

}
