package cycling;

import java.io.Serializable;

public class Rider implements Serializable{
    private String name;
    private int id;
    private int score;
    private static int RiderIDCounter = 0;
    public int yearOfBirth;
    // public Rider(){}
    // public Rider(String name){this.name = name;}

    // public Rider(String name, int id){this.name = name; this.id = id;}

    public Rider(String name, int yearOfBirth){
        //shouldve made id like this instead of so many hashmaps...
        this.name = name;
        this.id = ++RiderIDCounter;
        this.yearOfBirth = yearOfBirth;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void incrementScore(int addedScore){
        this.score += addedScore;
    }
    // @Override
	// public int createRider(int teamID, String name, int yearOfBirth)
	// 		throws IDNotRecognisedException, IllegalArgumentException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public void removeRider(int riderId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub
    // also removes the results of the rider
    

	// }
}
