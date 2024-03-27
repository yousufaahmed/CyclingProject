package cycling;
public class Cycling {

    /**
     * Parent class for Rider, Stage, Team and Race
     * 
     * @author Yousuf Ahmed, Sri Guhanathan
     */
    
    private String name;
    private String description;


    /**
     * Cycling Constructor class
     */
    public Cycling(String name, String description){
        this.name = name;
        this.description = description;
    }
    public Cycling(String name){
        this.name = name;
    }

    /**
     * Gets the name
     * 
     * @return name of the corresponding Rider,
     *          Stage, Team, or Race
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the description
     * 
     * @return description of the corresponding Rider,
     *          Stage, Team, or Race
     */
    public String getDescription() {
		return description;
	}

    /**
     * Sets the name
     * 
     * @param name      name of the corresponding Rider,
     *                  Stage, Team, or Race
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description
     * 
     * @param description       description of the corresponding Rider,
     *                          Stage, Team, or Race
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
