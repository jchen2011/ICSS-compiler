package nl.han.ica.icss.ast.booloperations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.BooleanExpression;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.NumberLiteral;

import java.util.ArrayList;

public abstract class ComparisonOperation extends BooleanExpression {
    public Expression lhs;
    public Expression rhs;

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if(lhs != null)
            children.add(lhs);
        if(rhs != null)
            children.add(rhs);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(lhs == null) {
            lhs = (Expression) child;
        } else if(rhs == null) {
            rhs = (Expression) child;
        }
        return this;
    }

    public <T extends Literal> BoolLiteral evaluateOperation(T value1, T value2) {
        boolean result = false;

        if (value1 instanceof ColorLiteral) {
            result = compare(((ColorLiteral) value1).value, ((ColorLiteral) value2).value);
        } else if (value1 instanceof BoolLiteral) {
            result = compare(((BoolLiteral) value1).value, ((BoolLiteral) value2).value);
        } else if (value1 instanceof NumberLiteral) {
            result = compare(((NumberLiteral) value1).getNumber(), ((NumberLiteral) value2).getNumber());
        }

        return new BoolLiteral(result);
    }

    abstract public boolean compare(Comparable value1, Comparable value2);
}
