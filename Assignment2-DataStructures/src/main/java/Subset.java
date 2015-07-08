/**
 * Created by presnakovr on 7/6/2015.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        int count = 0;
        while (!StdIn.isEmpty()) {
            if (k == 0) {
                StdIn.readString();
                continue;
            }
            if (count >= k) {
                int odds = StdRandom.uniform(count + 1);
                if (odds < k) {
                    queue.dequeue();
                    queue.enqueue(StdIn.readString());
                } else {
                    StdIn.readString();
                }
            } else {
                queue.enqueue(StdIn.readString());
            }
            count++;
        }
        for (int i = 1; i <= k; i++) {
            StdOut.println(queue.dequeue());
        }

    }
}
