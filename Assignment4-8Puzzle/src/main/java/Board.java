import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

/**
 * Created by Roman on 8/23/2015.
 */
public class Board {

    private int[][] blocks;
    private int N;

    private class Coordinate {
        private int i;
        private int j;
    }

    public Board(int[][] blocks) {
        this.blocks = copyArray(blocks);
        N = blocks.length;
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int count = 0;

        // check that all numbers are in ascending order in place
        for (int k = 1; k < N*N; k++) {
            int i = (k - 1) / N;
            int j = k - i * N - 1;
            if (blocks[i][j] != k) {
                count++;
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = blocks[i][j];
                if (k == 0) {
                    continue;
                }

                int ni = (k - 1) / N;
                int nj = k - ni * N - 1;

                int di = ni - i;
                if (di < 0) {
                    di *= -1;
                }
                int dj = nj - j;
                if (dj < 0) {
                    dj *= -1;
                }

                count = count + di + dj;
            }
        }

        return count;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        if (N < 2) {
            return new Board(this.blocks);
        }

        int[][] newBlocks = copyArray(this.blocks);

        int i = 0;
        while (true) {
            if (newBlocks[i][0] != 0 && newBlocks[i][1] != 0) {
                int tmp = newBlocks[i][0];
                newBlocks[i][0] = newBlocks[i][1];
                newBlocks[i][1] = tmp;
                break;
            }
            i++;
        }

        return new Board(newBlocks);
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        if (this.toString().equals(y.toString())) {
            return true;
        }

        return false;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        Coordinate zeroElement = findZero(this.blocks);
        Coordinate nonZeroElement = new Coordinate();

        // left
        nonZeroElement.i = zeroElement.i;
        nonZeroElement.j = zeroElement.j - 1;
        addNeighbor(neighbors, this.blocks, zeroElement, nonZeroElement);
        // right
        nonZeroElement.i = zeroElement.i;
        nonZeroElement.j = zeroElement.j + 1;
        addNeighbor(neighbors, this.blocks, zeroElement, nonZeroElement);
        // up
        nonZeroElement.i = zeroElement.i - 1;
        nonZeroElement.j = zeroElement.j;
        addNeighbor(neighbors, this.blocks, zeroElement, nonZeroElement);
        // down
        nonZeroElement.i = zeroElement.i + 1;
        nonZeroElement.j = zeroElement.j;
        addNeighbor(neighbors, this.blocks, zeroElement, nonZeroElement);

        return neighbors;
    }

    /**
     * Find zero element.
     * @param originalBlocks
     * @return
     */
    private Coordinate findZero(final int[][] originalBlocks) {
        Coordinate zero = new Coordinate();

        int i = 0;
        int j = 0;

        // find zero element
        while (originalBlocks[i][j] != 0 && i < N) {
            if (j == N - 1) {
                i++;
                j = 0;
            } else {
                j++;
            }
        }

        zero.i = i;
        zero.j = j;

        return zero;
    }

    /**
     * Build neighbor boar with exchanging zero and nonzero elements.
     * @param original
     * @param zeroElement
     * @param nonZeroElement
     * @return
     */
    private void addNeighbor(
            final Stack<Board> neighbors, final int[][] original, final Coordinate zeroElement, final Coordinate nonZeroElement) {
        if (nonZeroElement.i < 0 || nonZeroElement.j < 0
                || nonZeroElement.i >= N || nonZeroElement.j >= N) {
            return;
        }

        // copying array
        int[][] result = copyArray(original);

        // exchanging elements;
        result[zeroElement.i][zeroElement.j] = result[nonZeroElement.i][nonZeroElement.j];
        result[nonZeroElement.i][nonZeroElement.j] = 0;

        neighbors.push(new Board(result));
    }

    private int[][] copyArray(int[][] original) {
        // copying array
        int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original.length);
        }

        return result;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                str.append(blocks[i][j]);
                if (j != (N - 1)) {
                    str.append(" ");
                } else {
                    str.append("\n");
                }
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        Board initial1 = new Board(blocks);

        StdOut.println(initial1.equals(initial));
        StdOut.println(initial.isGoal());
        StdOut.println(initial);

        for (Board neighbor : initial.neighbors()) {
            StdOut.println(neighbor);
        }

        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.twin());
    }
}
