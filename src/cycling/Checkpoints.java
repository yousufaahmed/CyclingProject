package cycling;

import java.io.Serializable;

public class Checkpoints implements Serializable{

	/**
	 * Parent class for the types of checkpoints 
	 * Intermediate Sprint and Categorised climb checkpoints
	 * @author Yousuf Ahmed, Sri Guhanathan
	 */

    private Double location;

	/**
	 * Checkpoints constructor class
	 */

    public Checkpoints(Double location){
		this.location = location;
	}

	/**
	 * Gets the location
	 * 
	 * @return location of the checkpoints
	 */
    public Double getLocation() {
		return location;
	}

	/**
	 * Sets the location
	 * 
	 * @param location		Location of the checkpoints
	 */
	public void setLocation(double location) {
		this.location = location;
	}
}