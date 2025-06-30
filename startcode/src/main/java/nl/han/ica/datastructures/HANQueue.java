package nl.han.ica.datastructures;

public class HANQueue<T> implements IHANQueue<T>{
    IHANLinkedList<T> queue;

    public HANQueue() {
        this.queue = new HANLinkedList<>();
    }

    @Override
    public void enqueue(T value) {
        this.queue.insert(queue.getSize(), value);
    }

    @Override
    public T dequeue() {
        T currentValue = queue.getFirst();
        queue.removeFirst();
        return currentValue;
    }

    @Override
    public T peek() {
        return this.queue.getFirst();
    }

    @Override
    public int getSize() {
        return this.queue.getSize();
    }

    @Override
    public void clear() {
        this.queue.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.getSize() == 0;
    }
}
