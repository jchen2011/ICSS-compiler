package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;

public class AddOperation extends Operation {

    @Override
    public int calculate(int value1, int value2) {
        return value1 + value2;
    }

    @Override
    public String getNodeLabel() {
        return "Add";
    }
}
