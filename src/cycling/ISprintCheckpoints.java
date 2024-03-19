package cycling;
// CHECK IF THIS WORKS

import java.io.Serializable;

public class ISprintCheckpoints extends Checkpoints implements Serializable {

    public ISprintCheckpoints(Double location){
		super(location);
	}

}
