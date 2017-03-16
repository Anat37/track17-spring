package track.lessons.lesson3;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Stack, Queue {

    @Override
    public void enqueue(int value) {
        add(value);
    }

    @Override
    public int dequeue() {
        return remove(0);
    }

    @Override
    public void push(int value) {
        add(value);
    }

    @Override
    public int pop() {
        return remove(size - 1);
    }

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    Node tail = new Node(null, null, 0);

    @Override
    public void add(int item) {
        tail.val = item;
        tail.next = new Node(tail, null, 0);
        tail = tail.next;
        size++;
    }

    private Node getNode(int idx) {
        Node currNode = tail;
        for (int i = 0; i < size - idx; ++i) {
            currNode = currNode.prev;
        }
        return currNode;
    }

    @Override
    public int remove(int idx) throws NoSuchElementException {
        indexOkOrThrow(idx);
        Node currNode = getNode(idx);
        currNode.next.prev = currNode.prev;
        if (currNode.prev != null) {
            currNode.prev.next = currNode.next;
        }
        size--;
        return currNode.val;
    }

    @Override
    public int get(int idx) throws NoSuchElementException {
        indexOkOrThrow(idx);
        return getNode(idx).val;
    }
}
