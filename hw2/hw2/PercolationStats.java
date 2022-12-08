package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] openSite;
    private double m;
    private double s;
    private double t;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (T<0 || N<0) {
            throw new java.lang.IllegalArgumentException();
        }

        t = (double)T;
        openSite = new double[T];

        for (int i=0; i<T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                if (p.isOpen(row, col)) {
                    continue;
                }
                p.open(row, col);
            }
            openSite[i] = (double)p.numberOfOpenSites() / N / N;
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(openSite);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(openSite);
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - (1.96*stddev()/Math.sqrt(t));
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return mean() + (1.96*stddev()/Math.sqrt(t));
    }

    public static void main (String[] args) {
        PercolationFactory pf = new PercolationFactory();
        Stopwatch sw = new Stopwatch();

        PercolationStats ps1 = new PercolationStats(50, 100, pf);
        ps1.confidenceHigh();
        double timeInSeconds1 = sw.elapsedTime();
        System.out.println("Time for using original N and T is: " + timeInSeconds1 + " seconds.");

        PercolationStats ps2 = new PercolationStats(50, 200, pf);
        ps2.confidenceHigh();
        double timeInSeconds2 = sw.elapsedTime() - timeInSeconds1;
        System.out.println("Time for using original N and double T is: " + timeInSeconds2 + " seconds.");

        PercolationStats ps3 = new PercolationStats(100, 100, pf);
        ps3.confidenceHigh();
        double timeInSeconds3 = sw.elapsedTime() - timeInSeconds1 - timeInSeconds2;
        System.out.println("Time for using original double N and T is: " + timeInSeconds3 + " seconds.");

    }
}
