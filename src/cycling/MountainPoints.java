package cycling;

public class MountainPoints {

    /*
     * Gives the points for the mountains:
     * 
     * Fourth, Third, Second, First and Hors Categories
     */

    static int[] horsC = {20, 15, 12, 10, 8, 6, 4, 2};

    static int[] firstC = {10, 8, 6, 4, 2, 1};

    static int[] secondC = {5, 3, 2, 1};

    static int[] thirdC = {2, 1};

    static int[] fourthC = {1};

    // Get Methods for each array of points

    public static int[] getHorsCPoints(){
        return horsC;
    }

    public static int[] getFirstCPoints(){
        return firstC;
    }

    public static int[] getSecondCMoints(){
        return secondC;
    }

    public static int[] getThirdCPoints(){
        return thirdC;
    }

    public static int[] getFourthCPoints(){
        return fourthC;
    }

}
