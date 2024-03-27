package cycling;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Main class for CyclingPortal
 * 
 * @author Yousuf Ahmed, Sri Guhanathan
 */

// TODO:

// ADD EXCEPTION HANDLING
// ADD CLASS DOCUMENTATION USING /** */
// ADD POLYMORPHISM
// FIX THE ADJUSTED TIME TO MAKE IT CONCURRENT

// create array lists to store all riders, teams, stages, checkpoints etc

public class CyclingPortalImpl implements CyclingPortal {

	// not the most efficient...

	// Variables, Arrays, Arraylists, and Hashmaps to store all the data from  the methods
	private static int CPIDCounter = 0;
	private static int raceIDCounter = 0;
	private static int stageIDCounter = 0;
	private static int teamIDCounter = 0;
	private static int riderIDCounter = 0;

	public List<Integer> raceIDs = new ArrayList<>();
	public List<Integer> stageIDs = new ArrayList<>();
	public Map<Integer, Race> races = new HashMap<>(); // raceid - race
	public Map<String, Integer> racenameid = new HashMap<>();
	public Map<Integer, Stage> stages = new HashMap<>(); // stageid - stage
	public Map<Integer, int[]> stagesRace = new HashMap<>(); // raceid - [stageids]

	public List<Integer> teamIDs = new ArrayList<>();
	public Map<Integer, Team> teams = new HashMap<>(); // teamid - team  
	public Map<Integer, int[]> teamRiders = new HashMap<>(); // team - [riderids]
	public List<Integer> riderIDs = new ArrayList<>();
	public Map<Integer, Rider> riders = new HashMap<>(); // riderid - rider
	public List<String> teamName = new ArrayList<>();

	public List<Integer> cpIDs = new ArrayList<>(); // checkpointids
	public Map<Integer, ClimbCheckpoints> climbCheckpoints = new HashMap<>(); // checkpointid - climbcheckpoint
	public Map<Integer, ISprintCheckpoints> ISprintCheckpoints = new HashMap<>(); // checkpointid - isprintcheckpoint
	public Map<Integer, int[]> checkpointsStages = new HashMap<>(); // stageid - [checkpointids]

/////////////////////////////////////////////////////

	// Methods to check if the race, Stage or team name exist in the hashmaps
	private boolean raceNameExists(String name) {
		for (Race existingRace : races.values()) {
			if (existingRace.getName() == name) {
				return true;
			}
		}
		return false;
	}

	private boolean stageNameExists(String name) {
		for (Stage existingStage : stages.values()) {
			if (existingStage.getName() == name) {
				return true;
			}
		}
		return false;
	}

	private boolean teamNameExists(String name) {
		for (Team existingTeam : teams.values()) {
			if (existingTeam.getName() == name) {
				return true;
			}
		}
		return false;
	}

	// Checks if 2 riders times are 'close' together for the adjusted time
	private boolean isCloseTogether(LocalTime time1, LocalTime time2) {
        return Math.abs(time1.toNanoOfDay() - time2.toNanoOfDay()) <= 1_000_000_000L;
    }

	// private void addToMapArr(Map<Integer, int[]> hasher, int id){
	// 	if (hasher.size() != 0){
	// 		int[] Originalarray = hasher.get(id);
	// 		int[] newArray = Arrays.copyOf(Originalarray,Originalarray.length + 1);
	// 		newArray[newArray.length - 1] = id;
	// 		hasher.replace(id, newArray);   
	// 	}else{
	// 		int[] newArray = {id};
	// 		hasher.put(id, newArray);
	// 	}
	// }

	private void removeFromMapArr(Map<Integer, int[]> hasher, int id){
		for (Map.Entry<Integer, int[]> entry : hasher.entrySet()) {
			int[] array = entry.getValue();
			int indexToRemove = -1;
		
			// Find the index of the target value in the array
			for (int i = 0; i < array.length; i++) {
				if (array[i] == id) {
					indexToRemove = i;
					break;
				}
			}
		
			if (indexToRemove != -1) {
				// Creates a new array with size - 1
				int[] newArray = new int[array.length - 1];
		
				// Copies the elements before the target value
				System.arraycopy(array, 0, newArray, 0, indexToRemove);
		
				// Copies the elements after the target value
				System.arraycopy(array, indexToRemove + 1, newArray, indexToRemove, array.length - indexToRemove - 1);
				hasher.put(entry.getKey(), newArray);
		
				break;
			}
		}
	}

///////////////////////////////////////////////////////////////////

	@Override
	public int[] getRaceIds() {
		int[] raceIDsArray = raceIDs.stream().mapToInt(Integer::intValue).toArray();
		return raceIDsArray;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {

		// Check for invalid name
        if (name == null || name.trim().isEmpty() || name.length() > 30 || name.contains(" ")) {
            throw new InvalidNameException("name not valid");
        }

        // Check if the name already exists
        if (raceNameExists(name)) {
            throw new IllegalNameException("name already exists");
        }

        // Create a new raceID and add it to the list
		int raceId = ++raceIDCounter;
        raceIDs.add(raceId);

		// Map a new Race to a raceID
		Race race = new Race(name, description);
		races.put(raceId, race);
		racenameid.put(name,raceId);

        return raceId;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

		if (!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
        }

		// Fetches information from the corresponding race
        Race race = races.get(raceId);
        String name = race.getName();
        String description = race.getDescription();

        return "Race ID: " + raceId + "\nName: " + name + "\nDescription: " + description;
    }


	@Override
	// NEED TO UPDATE IT LATER
	public void removeRaceById(int raceId) throws IDNotRecognisedException {

		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
		}
	
		races.remove(raceId);

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		if (stagesRace.isEmpty()){
			return 0;
		}
		else if(!stagesRace.containsKey(raceId)) {
			throw new IDNotRecognisedException("Stage with ID " + raceId + " not found.");
		}
		
		int[] stageArray = stagesRace.get(raceId);
		int nStages = stageArray.length;
		return nStages;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race ID not recognized: " + raceId);
		}

		// Check for invalid name
		if (stageName == null || stageName.trim().isEmpty() || stageName.length() > 30 || stageName.contains(" ")) {
			throw new InvalidNameException("Invalid stage name: " + stageName);
		}

		// Check if the name already exists
		if (stageNameExists(stageName)) {
			throw new IllegalNameException("Stage name already exists: " + stageName);
		}

		// Check for invalid length
		if (length < 5.0) {
			throw new InvalidLengthException("Invalid stage length: " + length);
		}

        // Create a new stageID and add it to the list
		int stageId = ++stageIDCounter;
		stageIDs.add(stageId);
		
		// Map the stage to the race
		Stage stage = new Stage(stageName, description, length, startTime, type);
		stages.put(stageId, stage);
		
		// Makes a new array of length + 1 and replaces it in the hashmap
		// which maps the Race to the [stages]
			if (getNumberOfStages(raceId) != 0){
				int[] Originalarray = stagesRace.get(raceId);
				int[] newArray = Arrays.copyOf(Originalarray,Originalarray.length + 1);
				newArray[newArray.length - 1] = stageId;
				stagesRace.replace(raceId, newArray);   
			}else{
				int[] newArray = {stageId};
				stagesRace.put(raceId, newArray);
		}

		return stageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

		if (!stagesRace.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race ID not recognized: " + raceId);
		}
		
		// Gets the array of stages from the hashmap
		int[] stagesArr = stagesRace.get(raceId);

		return stagesArr;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}

		// First gets the specific stage, then gets its information
		Stage stage = stages.get(stageId);
		double length = stage.getlength();
		return length;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage with ID " + stageId + " not found.");
		}
	
		stages.remove(stageId);

		for (Map.Entry<Integer, int[]> entry : stagesRace.entrySet()) {
			int[] array = entry.getValue();
			int indexToRemove = -1;
		
			// Find the index of the target value in the array
			for (int i = 0; i < array.length; i++) {
				if (array[i] == stageId) {
					indexToRemove = i;
					break;
				}
			}
		
			if (indexToRemove != -1) {
				// Creates a new array with size - 1
				int[] newArray = new int[array.length - 1];
		
				// Copies the elements before the target value
				System.arraycopy(array, 0, newArray, 0, indexToRemove);
		
				// Copies the elements after the target value
				System.arraycopy(array, indexToRemove + 1, newArray, indexToRemove, array.length - indexToRemove - 1);
				stagesRace.put(entry.getKey(), newArray);
		
				break;
			}
		}
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {

		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}

		Stage stage = stages.get(stageId);

		if (stage.getState() == StageState.READY) {
			throw new InvalidStageStateException("Stage is in 'waiting for results' state and cannot be modified.");
		}            

		if (stage.getType() == StageType.TT) {
			throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoint.");
		}

		// Check if the location is within the bounds of the stage length
		if (location < 0 || location > stage.getlength()) {
			throw new InvalidLocationException("Location is out of bounds of the stage length.");
		}

		// Create a new climb checkpoint
		int checkpointId = ++CPIDCounter;
		cpIDs.add(checkpointId);
		ClimbCheckpoints checkpoint = new ClimbCheckpoints(location, type, averageGradient, length);
		climbCheckpoints.put(checkpointId, checkpoint);

		// Add the checkpoint to the stage by creating a new array and
		// replacing the old array
		int[] stageCheckpoints = getStageCheckpoints(stageId);
		if (stageCheckpoints.length != 0){
			int[] Originalarray = checkpointsStages.get(stageId);
			int[] newArray = Arrays.copyOf(Originalarray,Originalarray.length + 1);
			newArray[newArray.length - 1] = stageId;
			checkpointsStages.replace(stageId, newArray);   
		}else{
			int[] newArray = {stageId};  
			checkpointsStages.put(stageId, newArray);
		}

		return checkpointId;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}

		Stage stage = stages.get(stageId);

		if (stage.getState() == StageState.READY) {
			throw new InvalidStageStateException("Stage is in 'waiting for results' state and cannot be modified.");
		}            

		if (stage.getType() == StageType.TT) {
			throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoint.");
		}

		if (location < 0 || location > stage.getlength()) {
			throw new InvalidLocationException("Location is out of bounds of the stage length.");
		}

		// Create a new climb checkpoint
		int checkpointId = ++CPIDCounter;
		cpIDs.add(checkpointId);
		ISprintCheckpoints checkpoint = new ISprintCheckpoints(location);
		ISprintCheckpoints.put(checkpointId, checkpoint);

		// Add the checkpoint to the stage
		int[] stageCheckpoints = getStageCheckpoints(stageId);
		if (stageCheckpoints.length != 0){
			int[] Originalarray = checkpointsStages.get(stageId);
			int[] newArray = Arrays.copyOf(Originalarray,Originalarray.length + 1);
			newArray[newArray.length - 1] = stageId;
			checkpointsStages.replace(stageId, newArray);   
		}else{
			int[] newArray = {stageId};
			checkpointsStages.put(stageId, newArray);
		}

		return checkpointId;
	}

	@Override
	// GETTING A RANDOM LOGIC ERROR, CHECK LATER TO FIX
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		if (!ISprintCheckpoints.containsKey(checkpointId) && !climbCheckpoints.containsKey(checkpointId)) {
			throw new IDNotRecognisedException("Checkpoint with ID " + checkpointId + " not found.");
		}

	    cpIDs.remove(checkpointId);
		climbCheckpoints.remove(checkpointId);
		ISprintCheckpoints.remove(checkpointId);

		// Removes the specific value from the hashmap value array
		removeFromMapArr(checkpointsStages, checkpointId);
	}


	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}
	
		Stage stage = stages.get(stageId);
	
		if (stage.getState() == StageState.WAITING_FOR_RESULTS) {
			throw new InvalidStageStateException("Stage is already in 'waiting for results' state.");
		}
	
		// Updates the state of the stage to "waiting for results"
		stage.setState();
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		
		if (checkpointsStages.isEmpty()){
			return new int[0];
		}
		else if (!checkpointsStages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID was not recognized: " + stageId);
		}
		
		int[] checkpointsArr = checkpointsStages.get(stageId);

		return checkpointsArr;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		if (name == null || name.trim().isEmpty() || name.length() > 30 || name.contains(" ")) {
            throw new InvalidNameException("name not valid");
        }

        // Check if the name already exists
        if (teamNameExists(name)) {
            throw new IllegalNameException("name already exists");
        }

        // Creates teamID and adds to list
		int teamID = ++teamIDCounter;
        teamIDs.add(teamID);

		// Create a Team object and associate it with the teamID
		Team team = new Team(name, description);
		teams.put(teamID, team);

        return teamID;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		
		if (!teams.containsKey(teamId)) {
			throw new IDNotRecognisedException("Team with ID " + teamId + " not found.");
		}
	    teamIDs.remove(teamId);
		teams.remove(teamId);
	}

	@Override
	public int[] getTeams() {
		int[] teamIDsArray = teamIDs.stream().mapToInt(Integer::intValue).toArray();
		return teamIDsArray;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		if (teamRiders.isEmpty()){
			return new int[0];
		}
		else if (!teamRiders.containsKey(teamId)) {
			throw new IDNotRecognisedException("Team ID was not recognized: " + teamId);
		}
		
		int[] ridersArr = teamRiders.get(teamId);

		return ridersArr;
	}
	
	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		
		if (!teams.containsKey(teamID)) {
			throw new IDNotRecognisedException("Team ID not recognized: " + teamID);
		}

		if (name == null || name == "" || yearOfBirth < 1900) {
			throw new IllegalArgumentException("Invalid name or invalid year of birth, please try again.");
		}

		// Create raceID and add to list
		int riderId = ++riderIDCounter;
		riderIDs.add(riderId);
  
		// Create a Race object and map it to the raceId
		Rider rider = new Rider(name, yearOfBirth);
		riders.put(riderId, rider);

		return riderId;   
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider with ID " + riderId + " not found.");
		}
	
		riders.remove(riderId);

		removeFromMapArr(teamRiders, riderId);
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,InvalidStageStateException {
    
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider ID not recognized: " + stageId);
		}

		Stage stage = stages.get(stageId);
		
		if (stage.getState() != StageState.WAITING_FOR_RESULTS) {
			throw new InvalidStageStateException("Stage is not in 'waiting for results' state.");
		}
		
		if (stage.hasResultForRider(riderId)) {
			throw new DuplicatedResultException("Rider already has a result for the stage.");
		}
		
		int expectedCheckpointCount = getStageCheckpoints(stageId).length + 2; // Add 2 for start and finish
		if (checkpoints.length != expectedCheckpointCount) {
			throw new InvalidCheckpointTimesException("Invalid number of checkpoint times."+ checkpoints.length + "!=" + expectedCheckpointCount);
		}
		
		stage.recordRiderResults(riderId, checkpoints);

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

		if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider ID not recognized: " + riderId);
		}

		Stage stage = stages.get(stageId);

		LocalTime[] riderResults = stage.getRiderResult(riderId);

		if (riderResults.length == 0) {
			return new LocalTime[0];
		}

		// Takes the first and last checkpoint times to get the elapsed time
		LocalTime firstResult = riderResults[0];
        LocalTime lastResult = riderResults[riderResults.length - 1];
		int elapsedHours = lastResult.getHour() - firstResult.getHour();
		int elapsedMinutes = lastResult.getMinute() - firstResult.getMinute();
		int elapsedSeconds = lastResult.getSecond() - firstResult.getSecond();

		// Prevents time from being negative
		if (elapsedSeconds < 0) {
			elapsedMinutes--;
			elapsedSeconds += 60;
		}
		if (elapsedMinutes < 0) {
			elapsedHours--;
			elapsedMinutes += 60;
		}

		// Combines all the individual hours minutes seconds into one LocalTime variable, 
		// and adds the elapsed time to the last index
        LocalTime elapsedtime =  LocalTime.of(elapsedHours, elapsedMinutes, elapsedSeconds);
		LocalTime[] resultsWithElapsed = new LocalTime[riderResults.length + 1];
		System.arraycopy(riderResults, 0, resultsWithElapsed, 0, riderResults.length);

		resultsWithElapsed[resultsWithElapsed.length - 1] = elapsedtime;

        return resultsWithElapsed;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {

		
		if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }

        LocalTime[] riderTimes = getRiderResultsInStage(stageId, riderId);
		LocalTime riderElapsed = riderTimes[riderTimes.length - 1];
		if (riderTimes == null || riderTimes.length == 0) {
			return null;
        }

        LocalTime adjustedTime = LocalTime.of(0, 0, 0);
        LocalTime lowestTime = riderElapsed;

        for (int i = 0; i < riderIDs.size(); i++){
            int rider = riderIDs.get(i);
            LocalTime[] checkpointTimes = getRiderResultsInStage(stageId, rider);

            if (checkpointTimes[checkpointTimes.length - 1] == riderElapsed){
                continue;
            } else {
                if (isCloseTogether(checkpointTimes[checkpointTimes.length - 1], riderElapsed)){
                    adjustedTime = checkpointTimes[checkpointTimes.length - 1].isBefore(riderElapsed) ? 
					checkpointTimes[checkpointTimes.length - 1]: riderElapsed;

                    lowestTime =  adjustedTime.isBefore(lowestTime) ? adjustedTime: lowestTime;
                }
            }
        }

        return lowestTime;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

        if (!riders.containsKey(riderId)) {
            throw new IDNotRecognisedException("Rider ID not recognized: " + riderId);
        }

		if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }

		Stage stage = stages.get(stageId);

        stage.removeRiderResultsInStage(riderId);

	}

	@Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {

        if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }

        Map<Integer, LocalTime> riderTimes = new HashMap<>();

		// Gets the riders elapsed time and puts it into a hashmap with their riderID
        for (int i = 0; i < riderIDCounter; i++){
			int a = riderIDs.get(i);

            LocalTime[] riderTimelist = getRiderResultsInStage(stageId, a);
			if (riderTimelist.length == 0 || riderTimelist == null){
                // LocalTime riderTime = null;
            }else{
				LocalTime riderTime = riderTimelist[riderTimelist.length - 1];
			    riderTimes.put(riderIDs.get(i), riderTime);
			}
            // riderTimes.put(riderIDs.get(i), riderTime);
        }

		// Sorts the riderIDs based on their times
        List<Integer> sortedRiderIds = new ArrayList<>(riderTimes.keySet());
        sortedRiderIds.sort(Comparator.comparing(riderTimes::get));

		// Puts the List contents into an array
        int[] riderRanks = new int[sortedRiderIds.size()];
        for (int i = 0; i < sortedRiderIds.size(); i++) {
            riderRanks[i] = sortedRiderIds.get(i);
        }

        return riderRanks;
    }

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {

		if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }

		// SORT IT BY FINISH TIME INSTEAD 
		// WE DID THE ADJUSTED TIME WRONG IT SHOULD BE CONSECUTIVE
		int[] riderRankList = getRidersRankInStage(stageId);
		LocalTime[] riderTimeArr = new LocalTime[riderRankList.length];

		for (int i = 0; i < riderRankList.length; i++){
			int riderId = riderRankList[i];
			LocalTime adjtime = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
			riderTimeArr[i] = adjtime;
		}

		return riderTimeArr;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {

		if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }

		int[] ridersRank = getRidersRankInStage(stageId);

		Stage stage = stages.get(stageId);

		StageType stageType = stage.getType();

		if (ridersRank == null || ridersRank.length == 0 || !(stageType == StageType.FLAT || stageType == StageType.TT)){
			return new int[0];
		}

		int[] points;

		// Sets the points to be the corresponding stage types points
		switch (stageType){
			case FLAT:
				points = SprintPoints.getFlatPoints();
				break;
			case TT:
				points = SprintPoints.getIndividualTTPoints();
				break;
			default:
				points = new int[0];
				break;
		}

		// Replaces the ridersID with their relative points
		int y = 0;
		for (int i = 0; i < ridersRank.length; i++){
			ridersRank[i] = points[i];
			y++;
		}
		
		// If the number of riders is more than the maximum that can get points, the rest get 0 points.
		if (!(ridersRank.length == points.length)){
			for (int i = y; y < ridersRank.length - points.length - 1; i++){
				ridersRank[i] = 0;
			}
		}

		return ridersRank;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {

		if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }

		LocalTime[] ridersRank = getRankedAdjustedElapsedTimesInStage(stageId);
		int nRiders = ridersRank.length;

		Stage stage = stages.get(stageId);

		StageType stageType = stage.getType();

		if (ridersRank == null || ridersRank.length == 0 || !(stageType == StageType.MEDIUM_MOUNTAIN || stageType == StageType.HIGH_MOUNTAIN)){
			return new int[0];
		}

		int[] points;
		int[] riderPoints = new int[nRiders];

		// Sets the points to be the corresponding stage types points
		switch (stageType){
			case MEDIUM_MOUNTAIN:
				points = SprintPoints.getHillyMPoints();
				break;
			case HIGH_MOUNTAIN:
				points = SprintPoints.getHighMPoints();
				break;
			default:
				points = new int[0];
				break;
		}

		int y = 0;
		for (int i = 0; i < ridersRank.length; i++){
			riderPoints[i] = points[i];
			y++;
		}
		
		// If the number of riders is more than the maximum that can get points, the rest get 0 points.
		if (!(nRiders == points.length)){
			for (int i = y; y < nRiders - points.length - 1; i++){
				riderPoints[i] = 0;
			}
		}

		return riderPoints;
	}

	@Override
	public void eraseCyclingPortal() {
		raceIDs.clear();
		races.clear();
		stageIDs.clear();
		stages.clear();
		stagesRace.clear();
		teamIDs.clear();
		teams.clear();
		teamRiders.clear();
		riderIDs.clear();
		riders.clear();
		cpIDs.clear();
		climbCheckpoints.clear();
		ISprintCheckpoints.clear();
		checkpointsStages.clear();

		// Reset counters
		raceIDCounter = 0;
		stageIDCounter = 0;
		teamIDCounter = 0;
		riderIDCounter = 0;
		CPIDCounter = 0;
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (IOException e) {
            throw new IOException("Error occurred while saving the CyclingPortal to file: " + e.getMessage());
        }

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            in.readObject();

        } catch (IOException e) {
            throw new IOException("Error occurred while loading the CyclingPortal from file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new IOException("Error, class not found: " + e.getMessage());
        }
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		if (!racenameid.containsKey(name)) {
			throw new NameNotRecognisedException("Race with name '" + name + "' not found.");
		}

		int raceId = racenameid.get(name);
		
		racenameid.remove(name);
		races.remove(raceId);
		raceIDs.remove(raceId);
		
		if (stagesRace.containsKey(raceId)) {
			int[] stageIdslist = stagesRace.get(raceId); // Retrieve the array of stage IDs
			List<Integer> itemsToRemoveList = new ArrayList<>();
            for (int item : stageIdslist) {
				itemsToRemoveList.add(item);// put stage IDs to array list
            }
			stageIDs.removeAll(itemsToRemoveList); // remove the array list of stageids from stageIDs

			// Iterate over the array of stage IDs and remove each stage
			for (int stageId : stageIdslist) {
				stages.remove(stageId); // Remove the stage from the stages map
			}
			
			// Remove the entry corresponding to the race ID from the stagesRace map
			stagesRace.remove(raceId);
		}

		// Remove checkpoints associated with the race
		if (stagesRace.containsKey(raceId)) {
			int[] stageIdslist = stagesRace.get(raceId);
			for (int stageId : stageIdslist){
				if (checkpointsStages.containsKey(stageId)) {
					int[] cpIdslist = checkpointsStages.get(stageId); // Retrieve the array of stage IDs
					List<Integer> itemsToRemoveList = new ArrayList<>();
					for (int item : cpIdslist) {
						itemsToRemoveList.add(item);// put stage IDs to array list
					}
					cpIDs.removeAll(itemsToRemoveList); // remove the array list of stageids from stageIDs

					// Iterate over the array of stage IDs and remove each stage
					for (int cpId : cpIdslist) {
						climbCheckpoints.remove(cpId); // Remove the stage from the stages map
						ISprintCheckpoints.remove(cpId);
					}
					
				}
			}
	    }

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
        }
        int[] raceStageIds = stagesRace.get(raceId);

        // Makes a map to store rider IDs and their total adjusted elapsed times
        Map<Integer, LocalTime> riderTotalTimes = new HashMap<>();

        // Iterate for each stage in the race
        for (int stageId : raceStageIds) {
            int[] stageRiderIds = getRidersRankInStage(stageId);

            for (int riderId : stageRiderIds) {
                LocalTime elapsedTime = getRiderAdjustedElapsedTimeInStage(stageId, riderId);

                LocalTime currentTotalTime = riderTotalTimes.getOrDefault(riderId, LocalTime.MIN);
                LocalTime newTotalTime = currentTotalTime.plusHours(elapsedTime.getHour())
                        .plusMinutes(elapsedTime.getMinute())
                        .plusSeconds(elapsedTime.getSecond());
                riderTotalTimes.put(riderId, newTotalTime);
            }
        }

        // Create an array of LocalTime for general classification times
        LocalTime[] generalClassificationTimes = new LocalTime[riderTotalTimes.size()];

        // Populate the array with total times of riders
        int i = 0;
        for (LocalTime time : riderTotalTimes.values()) {
            generalClassificationTimes[i++] = time;
        }

        return generalClassificationTimes;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {

		if (!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
        }

		int[] raceStages = stagesRace.get(raceId);

		int[] riderPointsRace = new int[riderIDs.size()];

		// Iterates for every stage in a race, gets their points and appends it to a points array
		for (int i = 0; i < raceStages.length; i++){
			int[] currentPoints = getRidersPointsInStage(raceStages[i]);
			for (int j = 0; j < currentPoints.length; j++){
				riderPointsRace[j] += currentPoints[j];
			}
		}

		return riderPointsRace;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {

		if (!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
        }

		int[] raceStages = stagesRace.get(raceId);

		int[] riderPointsRace = new int[riderIDs.size()];

		// Iterates for every stage in a race, gets their points and appends it to a points array
		for (int i = 0; i < raceStages.length; i++){
			int[] currentPoints = getRidersMountainPointsInStage(raceStages[i]);
			for (int j = 0; j < currentPoints.length; j++){
				riderPointsRace[j] += currentPoints[j];
			}
		}

		return riderPointsRace;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
        throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
		}
		int[] raceStageIds = stagesRace.get(raceId);

		// Initialize a map to store rider IDs and their total adjusted elapsed times
		Map<Integer, LocalTime> riderTotalTimes = new HashMap<>();

		// Iterate over each stage in the race
		for (int stageId : raceStageIds) {
			int[] stageRiderIds = getRidersRankInStage(stageId);

			// Calculate and update the total adjusted elapsed time for each rider
			for (int riderId : stageRiderIds) {
				LocalTime elapsedTime = getRiderAdjustedElapsedTimeInStage(stageId, riderId);

				LocalTime currentTotalTime = riderTotalTimes.getOrDefault(riderId, LocalTime.MIN);
				LocalTime newTotalTime = currentTotalTime.plusHours(elapsedTime.getHour())
														.plusMinutes(elapsedTime.getMinute())
														.plusSeconds(elapsedTime.getSecond());
				riderTotalTimes.put(riderId, newTotalTime);
			}
		}

		// Creates a list of rider IDs sorted by their total adjusted elapsed times
		List<Integer> sortedRiderIds = new ArrayList<>(riderTotalTimes.keySet());
		Collections.sort(sortedRiderIds, Comparator.comparing(riderTotalTimes::get, Comparator.nullsFirst(Comparator.naturalOrder())));

		// Convert the sorted rider IDs to an array
		int[] rankedRiderIds = sortedRiderIds.stream().mapToInt(Integer::intValue).toArray();

		return rankedRiderIds;
	}
	

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
		}
	
		int[] raceStages = stagesRace.get(raceId);
		int [] riderIds = getRidersGeneralClassificationRank(raceId);

		int[] riderPointsRace = new int[riderIDs.size()];

		Map<Integer, Integer> riderTotalPoints= new HashMap<>();


		for (int i = 0; i < raceStages.length; i++){
			int[] currentPoints = getRidersPointsInStage(raceStages[i]);
			for (int j = 0; j < currentPoints.length; j++){
				riderPointsRace[j] += currentPoints[j];
			}
		}
		
		for (int i = 0; i < riderIds.length; i++) {
			if (riderPointsRace[i] != 0) {
				riderTotalPoints.put(riderIds[i], riderPointsRace[i]);
			}
		}
		Set<Integer> riderIdsSet = riderTotalPoints.keySet();
		Integer[] riderIdsArray = riderIdsSet.toArray(new Integer[0]);

		// Convert Integer[] to int[]
		int[] riderIdsIntArray = Arrays.stream(riderIdsArray).mapToInt(Integer::intValue).toArray();

		return riderIdsIntArray;
	}


	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
		}
	
		int[] raceStages = stagesRace.get(raceId);
		int [] riderIds = getRidersGeneralClassificationRank(raceId);

		int[] riderPointsRace = new int[riderIDs.size()];

		Map<Integer, Integer> riderTotalPoints= new HashMap<>();


		for (int i = 0; i < raceStages.length; i++){
			int[] currentPoints = getRidersMountainPointsInStage(raceStages[i]);
			for (int j = 0; j < currentPoints.length; j++){
				riderPointsRace[j] += currentPoints[j];
			}
		}
		
		for (int i = 0; i < riderIds.length; i++) {
			if (riderPointsRace[i] != 0) {
				riderTotalPoints.put(riderIds[i], riderPointsRace[i]);
			}
		}
		Set<Integer> riderIdsSet = riderTotalPoints.keySet();
		Integer[] riderIdsArray = riderIdsSet.toArray(new Integer[0]);

		// Convert Integer[] to int[]
		int[] riderIdsIntArray = Arrays.stream(riderIdsArray).mapToInt(Integer::intValue).toArray();

		return riderIdsIntArray;
	}

}
