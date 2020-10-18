import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Devoir {

    private int I_MAX = 17;
    private int J_MAX = 27;

    private double TOLERANCE = 0.001; // Tolerance en mV
    private int MAX_ITERATIONS = 10000; // Donner dans l'enonce

    private int F = 0;
    private double D = -200; // Potentiel en mV
    private double S = 200;
    private int G;
    private int O = 0;
    private int Z;
    // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
    private double[][] domain = { { F, S, S, S, S, S, S, S, F, F, F, F, G, G, G, F, F, F, F, D, D, D, D, D, D, D, F }, // 0
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 1
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 2
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 3
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 4
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 5
            { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F }, // 6
            { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F }, // 7
            { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F }, // 8
            { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F }, // 9
            { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F }, // 10
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 11
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 12
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 13
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 14
            { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F }, // 15
            { F, F, F, F, F, F, F, F, F, Z, Z, Z, G, G, G, Z, Z, Z, F, F, F, F, F, F, F, F, F } // 16
    };

    // this function return the cell on top of the actual cell
    private double getTop(int i, int j) {
        return domain[i - 1][j];
    }

    private double getBottom(int i, int j) {
        return domain[i + 1][j];
    }

    private double getLeft(int i, int j) {
        return domain[i][j - 1];
    }

    private double getRight(int i, int j) {
        return domain[i][j + 1];
    }

    // This function evaluate the precision of the domain during an iteration
    private boolean precisionReached(double[][] oldDomain) {

        for (int i = 1; i < I_MAX - 1; i++) {
            for (int j = 1; j < J_MAX - 1; j++) {
                if (Math.abs(domain[i][j] - oldDomain[i][j]) > TOLERANCE) {
                    return false;
                }
            }
        }
        System.out.println("Precision reached");
        return true;
    }

    // This function create a deep copy of the domain so we can evaluate the
    // precision fo the next iteration
    public static double[][] deepCopy(double[][] original) {
        if (original == null) {
            return null;
        }

        final double[][] result = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    // This function calculate the potential under the source and the drain
    public void updateDomainUnderSourceAndDrain() {

        for (int i = 1; i < 16; i++) {
            // For the left side
            for (int j = 2; j < 8; j++) {
                domain[i][j] = (getTop(i, j) + getBottom(i, j) + getLeft(i, j) + getRight(i, j)) / 4;
            }

            // For the right side
            for (int j = 19; j < 25; j++) {
                domain[i][j] = (getTop(i, j) + getBottom(i, j) + getLeft(i, j) + getRight(i, j)) / 4;
            }
        }
    }

    // This function calculate the potential for the 1 columns after the source and
    // the 1 collumns before the drain
    public void updateDomainEmptyColumns() {
        for (int i = 2; i < 15; i++) {
            int j = 8;
            // For the left side
            domain[i][j] = (getTop(i, j) + getBottom(i, j) + 2 * getLeft(i, j)) / 4;

            j = 18;
            // For the right side
            domain[i][j] = (getTop(i, j) + getBottom(i, j) + 2 * getRight(i, j)) / 4;

            if (i == 5) {
                i = 10;
            }
        }
    }

    // This function calculate the potential bettween the 2 area of depletion but
    // not the row just after the top depletion and the row just before the bottom
    // depletion
    public void updateDomainCenter() {
        for (int i = 7; i < 10; i++) {
            for (int j = 8; j < 19; j++) {
                domain[i][j] = (getTop(i, j) + getBottom(i, j) + getLeft(i, j) + getRight(i, j)) / 4;
            }
        }
    }

    // This function calculate the potential for the 4 internal corners
    public void updateDomainInteriorCorners() {

        int i = 6;
        int j = 8;
        domain[i][j] = (getTop(i, j) + 2 * getBottom(i, j) + 2 * getLeft(i, j) + getRight(i, j)) / 6;

        i = 6;
        j = 18;
        domain[i][j] = (getTop(i, j) + 2 * getBottom(i, j) + 2 * getLeft(i, j) + getRight(i, j)) / 6;

        i = 10;
        j = 8;
        domain[i][j] = (2 * getTop(i, j) + getBottom(i, j) + getLeft(i, j) + 2 * getRight(i, j)) / 6;

        i = 10;
        j = 18;
        domain[i][j] = (2 * getTop(i, j) + getBottom(i, j) + getLeft(i, j) + 2 * getRight(i, j)) / 6;

    }

    // This function calculate the potential around the depletion area without the
    // corners
    public void updateDeplationBorder() {
        for (int j = 9; j < 18; j++) {
            int i = 6;
            // For the top side
            domain[i][j] = (getLeft(i, j) + getRight(i, j) + 2 * getBottom(i, j)) / 4;

            i = 10;
            // For the bottom side
            domain[i][j] = (getLeft(i, j) + getRight(i, j) + 2 * getTop(i, j)) / 4;
        }
    }

    // This function calculate the potential for the 4 corners
    public void updateDomainCorners() {
        int i = 1;
        int j = 1;
        domain[i][j] = (getBottom(i, j) + getRight(i, j)) / 2;

        // 4 cornes near the outside
        i = 15;
        j = 1;
        domain[i][j] = (getTop(i, j) + getRight(i, j)) / 2;

        i = 15;
        j = 25;
        domain[i][j] = (getTop(i, j) + getLeft(i, j)) / 2;

        i = 1;
        j = 25;
        domain[i][j] = (getBottom(i, j) + getLeft(i, j)) / 2;

        // 4 cornes near the depletion zone
        i = 15;
        j = 8;
        domain[i][j] = (getTop(i, j) + getLeft(i, j)) / 2;

        i = 15;
        j = 18;
        domain[i][j] = (getTop(i, j) + getRight(i, j)) / 2;

        i = 1;
        j = 18;
        domain[i][j] = (getBottom(i, j) + getLeft(i, j)) / 2;

        i = 1;
        j = 8;
        domain[i][j] = (getBottom(i, j) + getRight(i, j)) / 2;

    }

    // This function calculate the potential for the border of the domain without
    // the corners
    public void updateDomainBorders() {
        for (int i = 1; i < 15; i++) {
            // For the left side
            int j = 1;
            domain[i][j] = (getTop(i, j) + getBottom(i, j) + 2 * getRight(i, j)) / 4;

            // For the right side
            j = 25;
            domain[i][j] = (getTop(i, j) + getBottom(i, j) + 2 * getLeft(i, j)) / 4;
        }

        for (int j = 2; j < 25; j++) {

            int i = 15;
            domain[i][j] = (getTop(i, j) + getBottom(i, j) + 2 * getLeft(i, j)) / 4;
            if (j == 7) {
                j = 18;
            }
        }

    }

    // This function call all other function that implement the algorithm
    public double[][] updateDomain() {
        // first step is to create a deep copy of the current array
        double[][] oldDomain = deepCopy(domain);
        updateDomainUnderSourceAndDrain();
        updateDomainCorners();
        updateDomainBorders();
        updateDomainInteriorCorners();
        updateDomainEmptyColumns();
        updateDeplationBorder();
        updateDomainCenter();

        return oldDomain;
    }

    // Just to print the current state of the domain
    private void printDomain() {
        String domainString = "";
        for (int i = 0; i < I_MAX - 1; i++) {
            domainString += "| ";
            for (int j = 1; j < J_MAX - 1; j++) {
                String result = (Math.floor(Math.abs(domain[i][j]) * 1000) / 1000 + ",     ").substring(0, 8) + " ";
                if (domain[i][j] < 0) {
                    result = "-" + result;
                }
                domainString += result;
            }
            domainString += " |\n";
        }

        try {
            File output  = new File("output.txt");
            if (!output.exists()) {
                output.mkdir();
            }
            PrintWriter printer = new PrintWriter(output);
            printer.write(domainString);
            printer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Devoir dev = new Devoir();
        for (int i = 0; i < dev.MAX_ITERATIONS; i++) {
            double[][] oldDomain = dev.updateDomain();
            if (dev.precisionReached(oldDomain))
                break;
        }
        
        dev.printDomain();
    }
}