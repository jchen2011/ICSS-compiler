package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.literals.NumberLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;

import java.util.ArrayList;

public abstract class Operation extends Expression {

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

    public NumberLiteral evaluateOperation(NumberLiteral value1, NumberLiteral value2) {
        NumberLiteral result;

        if (value1 instanceof PixelLiteral | value2 instanceof PixelLiteral) {
            result = new PixelLiteral(calculate(value1.getNumber(), value2.getNumber()));
        } else if (value1 instanceof PercentageLiteral | value2 instanceof PercentageLiteral) {
            result = new PercentageLiteral(calculate(value1.getNumber(), value2.getNumber()));
        } else {
            result = new ScalarLiteral(calculate(value1.getNumber(), value2.getNumber()));
        }

        return result;
    }

    abstract public int calculate(int value1, int value2);
}
