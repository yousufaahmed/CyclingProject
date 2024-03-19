package cycling;

import java.io.Serializable;

public class Team implements Serializable{
    private String name;
    private String description;
    private int id;
    private int score;
    private int[] riders;

    public Team(){}

    public Team(String name, String description){this.name = name; this.description = description;}
    public Team(String name, int id){this.name = name; this.id = id;}
    public Team(String name, int id, int[] riders){
        this.name = name;
        this.id = id;
        this.riders = riders;
    }

    public int[] getRiders() {
        return riders;
    }

	public String getDescription(){
		return description;
	}

    public void setRiders(int[] riders) {
        this.riders = riders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    // @Override
	// public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public void removeTeam(int teamId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub

	// }

	// @Override
	// public int[] getTeams() {
	// 	// TODO Auto-generated method stub
	// 	return null;
	// }

	// @Override
	// public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub
	// 	return null;
	// }

}
