package cycling;

import java.io.Serializable;

// TODO

public class Checkpoints implements Serializable{

    private Double location;

    public Checkpoints(Double location){
		this.location = location;
	}

    public Double getLocation() {
		return location;
	}
}