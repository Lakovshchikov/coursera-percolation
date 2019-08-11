import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] results;
    private int t;
    private final double cons = 1.96;

    public PercolationStats(int n, int trails) {
        try {
            if( n <= 0 || trails <= 0) {
                throw new IllegalArgumentException("Something wrong!");
            }

            t = trails;
            results = new double[trails];
            Percolation perc;
            for (int i = 0; i < trails; i++) {
                perc = new Percolation(n);
                while (!perc.percolates()) {
                    int row = StdRandom.uniform(1, n + 1);
                    int col = StdRandom.uniform(1, n + 1);
                    if(!perc.isFull(row, col) && !perc.isOpen(row, col)){
                        perc.open(row, col);
                    }
                }
                results[i] = perc.numberOfOpenSites()/(double) (n * n);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("File not found");
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return (mean() - (cons * stddev()/Math.sqrt(t)));
    }

    public double confidenceHi() {
        return (mean() + (cons * stddev()/Math.sqrt(t)));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats Stats = new PercolationStats(n,t);
        StdOut.println("mean");
        StdOut.println(Stats.mean());
        StdOut.println("stddev");
        StdOut.println(Stats.stddev());
        StdOut.println("confidenceLo");
        StdOut.println(Stats.confidenceLo());
        StdOut.println("confidenceHi");
        StdOut.println(Stats.confidenceHi());
    }
}
