package cycling;
//import java.time.LocalDateTime;
//import java.util.Date;

// TODO

import java.io.Serializable;

public class Race implements Serializable {
	private String name;
	private int id;
	private String description;
	private static int RaceIDCounter = 0;
	

	public Race(String name, String description, int id) {
		this.name = name;
		this.description = description;
		this.id = ++RaceIDCounter;
	}
	public Race(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	public int getId(){
		return id;
	}

	


	// @Override
	// public int[] getRaceIds() {
	// 	// TODO Auto-generated method stub
	// 	return new int[] {};
	// }

	// @Override
	// public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub
	// 	return null;
	// }

	// @Override
	// public void removeRaceById(int raceId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub

	// }

	// @Override
	// public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
	// 		StageType type)
	// 		throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub
	// 	return null;
	// }
}

