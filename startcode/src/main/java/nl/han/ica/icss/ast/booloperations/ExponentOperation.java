package nl.han.ica.icss.ast.booloperations;

import nl.han.ica.icss.ast.Operation;

public class ExponentOperation extends Operation {
    @Override
    public String getNodeLabel() {
        return "Exponent";
    }

    @Override
    public int calculate(int value1, int value2) {
        return (int) Math.pow(value1, value2);
    }
}
