package nl.han.ica.datastructures;

public class ListNode<T> {
    private ListNode<T> next;
    private T value;

    public ListNode(ListNode<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public ListNode(T value) {
        this.value = value;
    }

    public ListNode<T> getNext() {
        return this.next;
    }

    public T getValue() {
        return this.value;
    }

    public void setNext(ListNode<T> next) {
        this.next = next;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
