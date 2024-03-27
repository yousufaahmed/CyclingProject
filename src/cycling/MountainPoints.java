package cycling;

public class MountainPoints {

    /**
     * Gives the points for the mountains:
     * 
     * Fourth, Third, Second, First and Hors Categories
     * 
     * @author Yousuf Ahmed, Sri Guhanathan
     */

    static int[] horsC = {20, 15, 12, 10, 8, 6, 4, 2};

    static int[] firstC = {10, 8, 6, 4, 2, 1};

    static int[] secondC = {5, 3, 2, 1};

    static int[] thirdC = {2, 1};

    static int[] fourthC = {1};

    
    /**
   * Gets the points in the Hors category
   * 
   * @return points in Hors Category
   */   
    public static int[] getHorsCPoints(){
        return horsC;
    }

    /**
   * Gets the points in the First category
   * 
   * @return points in First Category
   */   
    public static int[] getFirstCPoints(){
        return firstC;
    }

    /**
   * Gets the points in the Second category
   * 
   * @return points in Second Category
   */   
    public static int[] getSecondCMoints(){
        return secondC;
    }

    /**
   * Gets the points in the Third category
   * 
   * @return points in Third Category
   */   
    public static int[] getThirdCPoints(){
        return thirdC;
    }

    /**
   * Gets the points in the Fourth category
   * 
   * @return points in Fourth Category
   */   
    public static int[] getFourthCPoints(){
        return fourthC;
    }

}
