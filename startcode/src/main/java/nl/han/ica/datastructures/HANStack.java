package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T>{
    IHANLinkedList<T> stack;

    public HANStack(){
        this.stack = new HANLinkedList<>();
    }

    @Override
    public T peek() {
        return this.stack.getFirst();
    }

    @Override
    public void push(T value) {
        this.stack.addFirst(value);
    }

    @Override
    public T pop() {
        T currentValue = stack.getFirst();
        this.stack.removeFirst();
        return currentValue;
    }
}
