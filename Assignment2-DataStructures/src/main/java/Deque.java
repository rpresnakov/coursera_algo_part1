import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by presnakovr on 7/6/2015.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item);
        Node<Item> newNode = new Node<Item>(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else if (first != null) {
            first.next = newNode;
            newNode.previous = first;
            first = newNode;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkNull(item);
        Node<Item> newNode = new Node<Item>(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else if (last != null) {
            last.previous = newNode;
            newNode.next = last;
            last = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkEmptyDeque();

        Node<Item> node = first;

        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.previous;
            first.next = null;
        }
        size--;
        return node.value;
    }

    // remove and return the item from the end
    public Item removeLast() {
        checkEmptyDeque();

        Node<Item> node = last;

        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.next;
            last.previous = null;
        }
        size--;
        return node.value;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }
    private void checkEmptyDeque() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }

    private class DequeIterator<Item> implements Iterator<Item> {

        private Node<Item> node;

        public DequeIterator(Node<Item> first) {
            node = first;
        }

        public boolean hasNext() {
            return node != null;
        }

        public Item next() {
            if (node == null) {
                throw new NoSuchElementException();
            }

            Node<Item> show = node;
            node = node.previous;
            return show.value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node<Item> {
        private Node<Item> next = null;
        private Node<Item> previous = null;

        private Item value;

        Node(Item value) {
            this.value = value;
        }
    }

    // unit testing
    public static void main(String[] args) {
//        Deque<Integer> deque = new Deque<Integer>();
//        System.out.println(deque.size);
//
//        deque.addFirst(1);
//        deque.addFirst(2);
//        deque.addFirst(3);
//
//        deque.addLast(4);
//        deque.addLast(5);
//        deque.addLast(6);
//
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeLast());
//        System.out.println(deque.removeLast());
//        System.out.println(deque.removeLast());
//        System.out.println(deque.removeLast());
//        System.out.println(deque.isEmpty());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.isEmpty());
//
//        System.out.println("===============");
//        for (Integer value : deque) {
//            System.out.println(value);
//        }

    }

}