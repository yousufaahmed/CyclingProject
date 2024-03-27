package cycling;

public class SprintPoints {

    /**
     *  Gives the Points for all the Sprints:
     *  
     *  Flat, Hilly Mountain, High Mountain, Individual Time Trial, and Intermediate Sprint
     *  
     * @author Yousuf Ahmed, Sri Guhanathan
     */
    
    /**
   * Arrays of points for their particular category
   */       
    static int[] flatPoints = {50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};

    static int[] hillyMPoints = {30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};

    static int[] highMPoints = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    static int[] individualTTPoints = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    static int[] intermediateSPoints = {20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    /**
   * Gets the points in the flat category
   * 
   * @return points in flat Category
   */   
    public static int[] getFlatPoints(){
        return flatPoints;
    }

    /**
   * Gets the points in the Hilly Mountain category
   * 
   * @return points in Hilly Mountain Category
   */   
    public static int[] getHillyMPoints(){
        return hillyMPoints;
    }

    /**
   * Gets the points in the High Mountain category
   * 
   * @return points in High Mountain Category
   */   
    public static int[] getHighMPoints(){
        return highMPoints;
    }

    /**
   * Gets the points in the Individual Time Trial category
   * 
   * @return points in Individual Time Trial Category
   */   
    public static int[] getIndividualTTPoints(){
        return individualTTPoints;
    }

    /**
   * Gets the points in the Intermediate Sprint category
   * 
   * @return points in Intermediate Sprint Category
   */   
    public static int[] getIntermediateSPoints(){
        return intermediateSPoints;
    }
}
