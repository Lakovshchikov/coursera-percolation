import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Percolation {
    private boolean[][] grid;
    private int length;
    private WeightedQuickUnionUF uF;
    private WeightedQuickUnionUF uF2;
    private int openSites = 0;

    public Percolation(int n) {
        try {
            if (n <= 0) {
                throw new IllegalArgumentException("Something wrong!");
            }
            length = n + 1;
            grid = new boolean[length][length];
            int ufLength = (length) * (length) + 2;
            uF = new WeightedQuickUnionUF(ufLength);
            uF2 = new WeightedQuickUnionUF(ufLength - 1);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Something wrong!");
        }
    }

    public void open(int row, int col) {
        try{
            if (checkSite(row, col)) {
                if (!myOpen(row, col)) {
                    grid[row][col] = true;
                    openSites++;
                    int current = convert(row, col);
                    if (row == 1) {
                        uF.union(0, current);
                        uF2.union(0, current);
                    }
                    if (row == length - 1) {
                        uF.union((length)*(length) + 1, current);
                    }
                    if (myOpen(row-1, col)) {
                        int top = current - length;
                        uF.union(top, current);
                        uF2.union(top, current);
                    }
                    if (myOpen(row+1, col) && (current + length) <= (length * length)) {
                        int bottom = current + length;
                        uF.union(current, bottom);
                        uF2.union(current, bottom);
                    }
                    if (myOpen(row, col-1) && (current - 1) >= (row * length)) {
                        int left = current - 1;
                        uF.union(left, current);
                        uF2.union(left, current);
                    }
                    if (myOpen(row, col+1) && (current + 1) <= ((row+1) * length)) {
                        int right = current + 1;
                        uF.union(right, current);
                        uF2.union(right, current);
                    }
                }
            }
            else{
                throw new IllegalArgumentException("Something wrong!");
            }
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Something wrong!");
        }

    }

    private int convert(int row, int col) {
        return row * (length) + col + 1;
    }

    private boolean myOpen(int row, int col){
        try {
            return grid[row][col];
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public boolean isOpen(int row, int col) {
        try {
            if (checkSite(row, col)){
                return grid[row][col];
            }
            else{
                throw new IllegalArgumentException("Something wrong!");
            }
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Something wrong!");
        }
    }

    public boolean isFull(int row, int col) {
        int q = convert(row, col);
        try {
            if(checkSite(row, col)){
                return (uF2.connected(0, q));
            }
            else{
                throw new IllegalArgumentException("Something wrong!");
            }
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Something wrong!");
        }
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uF.connected(0, (length)*(length) + 1);
    }

    private boolean checkSite(int row, int col){
        if(row < 1 || row > length || col < 1 || col >= length){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        int row = 0;
        int col = 0;
        while (true) {
            row = StdIn.readInt();
            col = StdIn.readInt();
            perc.open(row, col);
            StdOut.println(perc.isFull(row, col));
            StdOut.println(perc.percolates());
        }
    }
}
