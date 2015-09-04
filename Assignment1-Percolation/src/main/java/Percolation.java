import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by presnakovr on 7/2/2015.
 */
public class Percolation {

    private int n;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;

    private boolean[] area;
    private int topIndex;
    private int bottomIndex;

    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw  new IllegalArgumentException();
        }

        this.n = n;

        uf = new WeightedQuickUnionUF(n * n + 2);
        area = new boolean[n * n];
        for (int i = 0; i < n*n; i++) {
            area[i] = false;
        }

        topIndex = n * n;
        bottomIndex = n * n + 1;

        uf2 = new WeightedQuickUnionUF(n * n + 1);
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }

        area[getIndex(i, j)] = true;
        //check if side in the top line
        if (i == 1) {
            uf.union(getIndex(i, j), topIndex);
            uf2.union(getIndex(i, j), topIndex);
        }

        if (i == n) {
            uf.union(getIndex(i, j), bottomIndex);
        }

        checkAndConnectNeighbours(i, j, i, j - 1);
        checkAndConnectNeighbours(i, j, i, j + 1);
        checkAndConnectNeighbours(i, j, i - 1, j);
        checkAndConnectNeighbours(i, j, i + 1, j);
    }

    private void checkAndConnectNeighbours(int i, int j, int ii, int jj) {
        if (ii >= 1 && ii <= n && jj >= 1 && jj <= n && isOpen(ii, jj)) {
            uf.union(getIndex(i, j), getIndex(ii, jj));
            uf2.union(getIndex(i, j), getIndex(ii, jj));
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }

        return area[getIndex(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(i, j)) {
            return false;
        }

        return uf.connected(getIndex(i, j), topIndex) && uf2.connected(getIndex(i, j), topIndex);
    }

    // does the system percolate?
    public boolean percolates()  {
        return uf.connected(topIndex, bottomIndex);
    }

    private int getIndex(int i, int j) {
        return n * (i - 1) + (j  -1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation perc = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            perc.open(p, q);
            StdOut.println(perc.isFull(p, q));
            StdOut.println(perc.percolates());

            if (p == 5 && q == 4) {
                break;
            }
        }

        StdOut.println(perc.isFull(1, 1));
    }
}
