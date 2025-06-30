package nl.han.ica.icss.ast.booloperations;

public class EqualsOperation extends ComparisonOperation {

    @Override
    public boolean compare(Comparable value1, Comparable value2) {
        return value1.equals(value2);
    }

    @Override
    public String getNodeLabel() {
        return "Equals";
    }
}
