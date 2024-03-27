package cycling;

import java.io.Serializable;

public class Team extends Cycling implements Serializable{

    /**
     * Class for the Teams
     * 
     * @author Yousuf Ahmed, Sri Guhanathan
     */

	/**
	 * Team Constructor Class
	 */
    public Team(String name, String description){
        super(name, description);
    }
}
