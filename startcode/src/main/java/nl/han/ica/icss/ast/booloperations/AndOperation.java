package nl.han.ica.icss.ast.booloperations;

public class AndOperation extends ComparisonOperation {

    @Override
    public boolean compare(Comparable value1, Comparable value2) {
        return (boolean) value1 && (boolean) value2;
    }

    @Override
    public String getNodeLabel() {
        return "And";
    }
}
