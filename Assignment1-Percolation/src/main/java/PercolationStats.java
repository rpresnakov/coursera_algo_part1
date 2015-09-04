import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by presnakovr on 7/2/2015.
 */
public class PercolationStats {

    private double[] experiment;

    private int n;
    private int t;

    private double mean = 0;
    private double stddev = 0;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0) {
            throw  new IllegalArgumentException();
        }
        if (t <= 0) {
            throw  new IllegalArgumentException();
        }

        this.n = n;
        this.t = t;

        experiment = new double[t];

        for (int ind = 0; ind < t; ind++) {
            doExperiment(ind);
        }
    }

    private void doExperiment(int index) {
        Percolation percolation = new Percolation(n);

        int count = 0;

        while (!percolation.percolates() && count < n * n) {
            count++;

            int i = StdRandom.uniform(1, n + 1);
            int j = StdRandom.uniform(1, n + 1);

            while (percolation.isOpen(i, j)) {
                i = StdRandom.uniform(1, n + 1);
                j = StdRandom.uniform(1, n + 1);
            }
            percolation.open(i, j);
        }

        experiment[index] = (double) count / (n * n);

        //draw percolation
        //PercolationVisualizer.draw(percolation, n);
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(experiment);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(experiment);
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        checkMeanAndStddev();

        return mean - 1.96 * stddev / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        checkMeanAndStddev();

        return mean + 1.96 * stddev / Math.sqrt(t);
    }

    private void checkMeanAndStddev() {
        if (mean == 0) {
            mean = mean();
        }

        if (stddev == 0) {
            stddev = stddev();
        }
    }

    // test client (described below)
    public static void main(String[] args) {
//        args[0] = "320";
//        args[1] = "100";
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));

        StdOut.printf("%-23s = ", "mean");
        StdOut.println(stats.mean());
        StdOut.printf("%-23s = ", "stddev");
        StdOut.println(stats.stddev());
        StdOut.printf("%-23s = ", "95% confidence interval");
        StdOut.println(stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
