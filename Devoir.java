import java.util.Arrays;

public class Devoir {

    private int I_MAX = 18;
    private int J_MAX = 26;

    private double TOLERANCE = 0.001; // Tolerance en mV
    private int MAX_ITERATIONS = 10000; // Donner dans l'enonce

    private int F = -1;
    private int D = -200; // Potentiel en mV
    private int S = 200;
    private int G;
    private int O = 0;
    private int Z;

    private int[][] domain = {  { F, S, S, S, S, S, S, F, F, F, F, F, G, G, F, F, F, F, F, D, D, D, D, D, D, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, O, O, O, O, O, O, O, O, Z, Z, Z, Z, Z, Z, Z, Z, O, O, O, O, O, O, O, O, F },
                                { F, F, F, F, F, F, F, F, F, F, F, F, G, G, F, F, F, F, F, F, F, F, F, F, F, F }
                            };

    //this function return the cell on top of the actual cell
    private int getTop(int i, int j) {
        return domain[i - 1][j];
    }

    private int getBottom(int i, int j) {
        return domain[i + 1][j];
    }

    private int getLeft(int i, int j) {
        return domain[i][j - 1];
    }

    private int getRight(int i, int j) {
        return domain[i][j + 1];
    }

    //This function evaluate the precision of the domain during an iteration
    private boolean precisionReached(int[][] oldDomain) {
    
        for (int i = 0; i < I_MAX; i++) {
            for (int j = 0; j < J_MAX; j++) {
                if (Math.abs(domain[i][j] - oldDomain[i][j]) > TOLERANCE) {
                    return false;
                }
            }
        }

        return true;
    }

    //This function create a deep copy of the domain so we can evaluate the precision fo the next iteration   
    public static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    // This function calculate the potential under the source and the drain
    public void updateDomainUnderSourceAndDrain() {

    }

    // This function calculate the potential for the 2 columns after the source and the 2 collumns before the drain
    public void updateDomainEmptyColumns() {

    } 
    
    // This function calculate the potential bettween the 2 area of depletion but not the row just after the top depletion and the row just before the bottom depletion 
    public void updateDomainCenter() {

    }
    
    // This function calculate the potential for the 4 internal corners
    public void updateDomainInteriorCorners() {

    }

    // This function calculate the potential around the depletion area without the corners
    public void updateDeplationBorder() {

    } 

    // This function calculate the potential for the 4 corners
    public void updateDomainCorners() {

    }

    // This function calculate the potential for the border of the domain without the corners 
    public void updateDomainBorders() {

    }

    // This function call all other function that implement the algorithm
    public void updateDomain() {

    }

    // Just to print the current state of the domain
    private void printDomain() {
        String domainString = "";
        for (int i = 0; i < I_MAX; i++) {
            domainString += "| ";
            for (int j = 0; j < J_MAX; j++) {
                domainString += domain[i][j] + ", ";
            }
            domainString += " |\n";
        }

        System.out.println(domainString);
    }

    public static void main(String[] args) {
        Devoir dev = new Devoir();
        dev.printDomain();
    }
}