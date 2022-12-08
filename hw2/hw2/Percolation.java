package hw2;

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
WeightedQuickUnionUF - parent, size(of union), count(of unions)
count(): number of unions
find(p): find the root of p
connected(p,q): isConnected
validate(p): is p legal
union(p,q): connects p and q (small union links to big one)
 */

public class Percolation {
    private QuickFindUF uf;
    //    private WeightedQuickUnionUF uf;
    private boolean[] site;
    private int size;
    private int openNum;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        site = new boolean[N*N];
//        uf = new WeightedQuickUnionUF(N*N+2);
        uf = new QuickFindUF(N*N+2);
        for(int i=0; i<N; i++) {
            uf.union(N*N, i);
            uf.union(N*N+1, N*N-N+i);
        }
        size = N;
        openNum = 0;
    }

    public int xyTo1D(int r, int c) {
        return r*size+c;
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        if (row>=size || col>=size || row<0 || col<0) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        site[xyTo1D(row, col)] = true;
//        System.out.println(row + " " + col);
        openNum++;

        if (row!=0 && site[xyTo1D(row-1,col)]) {
            uf.union(xyTo1D(row, col), xyTo1D(row-1,col));
        }
        if (row!=size-1 && site[xyTo1D(row+1,col)]) {
            uf.union(xyTo1D(row, col), xyTo1D(row+1, col));
        }
        if (col!=0 && site[xyTo1D(row,col-1)]) {
            uf.union(xyTo1D(row, col), xyTo1D(row,col-1));
        }
        if (col!=size-1 && site[xyTo1D(row, col)+1]) {
            uf.union(xyTo1D(row, col), xyTo1D(row,col+1));
        }
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if (row>=size || col>=size || row<0 || col<0) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return site[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        if (row>=size || col>=size || row<0 || col<0) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return uf.connected(xyTo1D(row,col), size*size);
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openNum;
    }

    public boolean percolates() {
        // does the system percolate?
        return uf.connected(size*size, size*size+1);
    }
}
