package cycling;

import java.io.Serializable;

// TODO

public class Checkpoints implements Serializable{

	/*
	 * Parent class for the types of checkpoints 
	 * Intermediate Sprint and Categorised climb
	 */

    private Double location;

    public Checkpoints(Double location){
		this.location = location;
	}

    public Double getLocation() {
		return location;
	}
}