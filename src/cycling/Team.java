package cycling;

import java.io.Serializable;

// TODO

public class Team implements Serializable{

    /*
     * Class for the Teams
     */

    private String name;
    private String description;

    public Team(){}

    public Team(String name, String description){
        this.name = name; 
        this.description = description;
    }

	public String getDescription(){
		return description;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
