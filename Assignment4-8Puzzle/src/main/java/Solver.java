import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Roman on 8/23/2015.
 */
public class Solver {

    private class Node implements Comparable<Node> {

        private Board board;
        private int moves;
        private Node previous;

        Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(Node o) {
            int thisM = this.board.manhattan();
            int thatM = o.board.manhattan();

            return (thisM + moves) - (thatM + o.moves);
        }
    }

    private MinPQ<Node> prQueue = new MinPQ<>();
    private MinPQ<Node> prTwinQueue = new MinPQ<>();
    private boolean solvable = false;
    private Node goalNode = null;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }

        solve(initial);
    }

    private void solve(Board initial) {
        Node searchNode = new Node(initial, 0, null);
        Node searchTwinNode = new Node(initial.twin(), 0, null);
        prQueue.insert(searchNode);
        prTwinQueue.insert(searchTwinNode);

        while (!prQueue.min().board.isGoal() && !prTwinQueue.min().board.isGoal()) {
            searchNode = prQueue.delMin();
            for (Board neighbor : searchNode.board.neighbors()) {
                if (searchNode.previous == null ||
                        !neighbor.equals(searchNode.previous.board)) {
                    prQueue.insert(new Node(neighbor, searchNode.moves + 1, searchNode));
                }
            }
            searchTwinNode = prTwinQueue.delMin();
            for (Board neighbor : searchTwinNode.board.neighbors()) {
                if (searchTwinNode.previous == null ||
                        !neighbor.equals(searchTwinNode.previous.board)) {
                    prTwinQueue.insert(new Node(neighbor, searchTwinNode.moves + 1, searchTwinNode));
                }
            }
        }

        if (prTwinQueue.min().board.isGoal()) {
            solvable = false;
        } else {
            solvable = true;
            goalNode = prQueue.delMin();
        }

        prQueue = null;
        prTwinQueue = null;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        } else {
            return goalNode.moves;
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> solution = new Stack<>();
        solution.push(goalNode.board);
        Node previousNode = goalNode.previous;
        while (previousNode != null) {
            solution.push(previousNode.board);
            previousNode = previousNode.previous;
        }

        return solution;
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

        //solve the puzzle
        Solver solver = new Solver(initial);

        //print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
