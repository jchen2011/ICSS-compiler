package nl.han.ica.icss.ast.booloperations;

public class LessThanOperation extends ComparisonOperation {

    /**
     * Compares two Comparable values and returns true if the first value is less than the second.
     * This method uses the compareTo() function, which returns: a negative number if value1 is less than value2, zero if they are equal and a positive number if value1 is greater than value2.
     *
     * @param value1 The first value to compare.
     * @param value2 The second value to compare.
     * @return True if value1 is less than value2, otherwise false.
     */
    @Override
    public boolean compare(Comparable value1, Comparable value2) {
        return value1.compareTo(value2) < 0;
    }

    @Override
    public String getNodeLabel() {
        return "Less_Than";
    }
}
