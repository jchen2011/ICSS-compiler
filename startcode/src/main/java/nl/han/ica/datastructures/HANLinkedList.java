package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T>{
    private ListNode<T> head;
    private int length;

    public HANLinkedList() {
        clear();
    }

    public HANLinkedList(ListNode<T> head, int length) {
        this.head = head;
        this.length = length;
    }

    @Override
    public void clear() {
        this.length = 0;
        this.head = null;
    }

    @Override
    public void addFirst(T value) {
        length++;
        if (head == null) {
            head = new ListNode<>(value);
        } else {
            head = new ListNode<>(head, value);
        }
    }

    @Override
    public void delete(int pos) {
        validateOutOfBounds(pos);
        if (pos == 0) {
            removeFirst();
        } else {
            for (ListNode<T> current = head; current != null; current = current.getNext()) {
                pos--;
                if (pos == 0) {
                    deleteNode(current, current.getNext());
                    length--;
                    return;
                }
            }
        }
    }

    private void deleteNode(ListNode<T> previous, ListNode<T> next) {
        if (next == null) {
            throw new IllegalStateException("No node to delete");
        }
        previous.setNext(next.getNext());
    }

    @Override
    public void insert(int index, T value) {
        if (head == null || index == 0) {
            addFirst(value);
        } else {
            validateOutOfBounds(index - 1);
            for (ListNode<T> current = head; current != null; current = current.getNext()) {
                index--;
                if (index == 0) {
                    insertNode(current, current.getNext(), value);
                    length++;
                    return;
                }
            }
        }
    }

    private void insertNode(ListNode<T> previous, ListNode<T> next, T value) {
        if (next == null) {
            ListNode<T> insert = new ListNode<>(value);
            previous.setNext(insert);
        } else {
            ListNode<T> insert = new ListNode<>(next, value);
            previous.setNext(insert);
        }
    }

    @Override
    public T get(int pos) {
        validateOutOfBounds(pos);
        ListNode<T> currentNode = head;

        for(int i = 0; i < pos; i++) {
            currentNode = currentNode.getNext();
        }

        return currentNode.getValue();
    }

    @Override
    public void removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        head = head.getNext();
        length--;
    }

    @Override
    public T getFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        return head.getValue();
    }

    @Override
    public int getSize() {
        return this.length;
    }

    private void validateOutOfBounds(int index) {
        if (index > length - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + length);
        }
    }
}