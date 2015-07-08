import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by presnakovr on 7/6/2015.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private int max;
    private Item[] arr;

    // construct an empty deque
    public RandomizedQueue() {
        size = 0;
        max = 2;
        arr = (Item[]) new Object[max];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        checkNull(item);

        if (size == max) {
            max = max * 2;
            resizeArray();
        }
        arr[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        checkEmptyDeque();

        int ind = StdRandom.uniform(size);
        Item value = arr[ind];

        arr[ind] = arr[size - 1];
        arr[size - 1] = null;
        size--;

        if (size > 0 && size == max / 4) {
            max = max / 2;
            resizeArray();
        }
        return value;
    }

    private void resizeArray() {
        Item[] backup = arr;
        arr = (Item[]) new Object[max];

        for (int i = 0; i < size; i++) {
            arr[i] = backup[i];
        }
    }

    // return (but do not remove) a random item
    public Item sample() {
        checkEmptyDeque();

        int ind = StdRandom.uniform(size);
        return arr[ind];
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
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

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private int[] iteratorArray;

        private int arrayInd;

        public RandomizedQueueIterator() {
            iteratorArray = new int[size];
            for (int i = 0; i < size; i++) {
                iteratorArray[i] = i;
            }
            StdRandom.shuffle(iteratorArray);

            arrayInd = size;
        }

        public boolean hasNext() {
            return arrayInd > 0;
        }

        public Item next() {
            arrayInd--;

            if (arrayInd < 0) {
                throw new NoSuchElementException();
            }
            return (Item) arr[iteratorArray[arrayInd]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
   }

}