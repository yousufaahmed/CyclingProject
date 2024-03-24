package cycling;

public class SprintPoints {

    /*
     *  Gives the Points for all the Sprints:
     *  
     *  Flat, Hilly Mountain, High Mountain, Individual Time Trial, and Intermediate Sprint
     */
    
    static int[] flatPoints = {50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};

    static int[] hillyMPoints = {30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};

    static int[] highMPoints = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    static int[] individualTTPoints = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    static int[] intermediateSPoints = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    // Get Methods for each array of points

    public static int[] getFlatPoints(){
        return flatPoints;
    }

    public static int[] getHillyMPoints(){
        return hillyMPoints;
    }

    public static int[] getHighMPoints(){
        return highMPoints;
    }

    public static int[] getIndividualTTPoints(){
        return individualTTPoints;
    }

    public static int[] getIntermediateSPoints(){
        return intermediateSPoints;
    }
}
