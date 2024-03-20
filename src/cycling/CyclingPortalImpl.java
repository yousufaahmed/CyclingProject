package cycling;

import java.io.FileOutputStream;

import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import javax.swing.text.TextAction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;

// TO DO:

// CREATE A CLASSES UML DIAGRAM
// ADD SOME SORT OF INHERITANCE
// ADD EXCEPTION HANDLING
// ADD CLASS DOCUMENTATION USING /** */
// CREATE THE COVER SHEET WITH WHAT WE HAVE DONE INDIVIDUALLY

// create array lists to store all riders, teams, stages, checkpoints etc

public class CyclingPortalImpl implements CyclingPortal {

	// not the most efficient...
	private static int CPIDCounter = 0;
	private static int raceIDCounter = 0;
	private static int stageIDCounter = 0;
	private static int teamIDCounter = 0;
	private static int riderIDCounter = 0;

	public List<Integer> raceIDs = new ArrayList<>();
	public List<Integer> stageIDs = new ArrayList<>();
	public Map<Integer, Race> races = new HashMap<>(); // raceid - race
	public Map<Integer, Stage> stages = new HashMap<>(); // stageid - stage
	public Map<Integer, int[]> stagesRace = new HashMap<>(); // raceid - [stageids]

	public List<Integer> teamIDs = new ArrayList<>();
	public Map<Integer, Team> teams = new HashMap<>(); // stageid - stageb  
	public Map<Integer, int[]> teamRiders = new HashMap<>(); // team - [riderids]
	public List<Integer> riderIDs = new ArrayList<>();
	public Map<Integer, Rider> riders = new HashMap<>(); // riderid - rider

	public List<Integer> cpIDs = new ArrayList<>(); // checkpointids
	public Map<Integer, ClimbCheckpoints> climbCheckpoints = new HashMap<>(); // checkpointid - climbcheckpoint
	public Map<Integer, ISprintCheckpoints> ISprintCheckpoints = new HashMap<>(); // checkpointid - isprintcheckpoint
	public Map<Integer, int[]> checkpointsStages = new HashMap<>(); // stageid - [checkpointids]

/////////////////////////////////////////////////////

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
			if (existingStage.getstageName() == name) {
				return true;
			}
		}
		return false;
	}

	private boolean teamNameExists(String name) {
		for (Stage existingStage : stages.values()) {
			if (existingStage.getstageName() == name) {
				return true;
			}
		}
		return false;
	}

	private boolean isCloseTogether(LocalTime time1, LocalTime time2) {
        // Assuming "close together" means less than one second difference
        return Math.abs(time1.toNanoOfDay() - time2.toNanoOfDay()) < 1_000_000_000L;
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
        // create raceID and add to list
		int raceId = ++raceIDCounter;
        raceIDs.add(raceId);
		// Create a Race object and associate it with the raceId
		Race race = new Race(name, description);
		races.put(raceId, race);

        return raceId;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
            throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
        }

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

		// Increment the nextStageId for the next stage
		int stageId = ++stageIDCounter;
		stageIDs.add(stageId);
		// Map the stage to the race
		Stage stage = new Stage(stageName, description, length, startTime, type);
		stages.put(stageId, stage);
		
		if (getNumberOfStages(raceId) != 0){
			int[] Originalarray = stagesRace.get(raceId);
			int[] newArray = Arrays.copyOf(Originalarray,Originalarray.length + 1);
			newArray[newArray.length - 1] = stageId;
			stagesRace.replace(raceId, newArray);   
		}else{
			int[] newArray = {stageId};
			stagesRace.put(raceId, newArray);
		}

		// Return the unique ID of the stage
		return stageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

		if (!stagesRace.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race ID not recognized: " + raceId);
		}
		
		int[] stagesArr = stagesRace.get(raceId);

		return stagesArr;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}

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
				// Create a new array with size - 1
				int[] newArray = new int[array.length - 1];
		
				// Copy the elements before the target value
				System.arraycopy(array, 0, newArray, 0, indexToRemove);
		
				// Copy the elements after the target value
				System.arraycopy(array, indexToRemove + 1, newArray, indexToRemove, array.length - indexToRemove - 1);
		
				// Update the array associated with the key
				stagesRace.put(entry.getKey(), newArray);
		
				// Exit the loop after deletion
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

		// Check if the stage is in a valid state
		if (stage.getState() == StageState.READY) {
			throw new InvalidStageStateException("Stage is in 'waiting for results' state and cannot be modified.");
		}            

		// Check if the stage type allows climb checkpoints
		if (stage.gettype() == StageType.TT) {
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

		// Return the ID of the created checkpoint
		return checkpointId;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}

		Stage stage = stages.get(stageId);

		// Check if the stage is in a valid state
		if (stage.getState() == StageState.READY) {
			throw new InvalidStageStateException("Stage is in 'waiting for results' state and cannot be modified.");
		}            

		// Check if the stage type allows climb checkpoints
		if (stage.gettype() == StageType.TT) {
			throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoint.");
		}

		// Check if the location is within the bounds of the stage length
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

		for (Map.Entry<Integer, int[]> entry : checkpointsStages.entrySet()) {
			int[] array = entry.getValue();
			int indexToRemove = -1;
			for (int i = 0; i < array.length; i++) {
				if (array[i] == checkpointId) {
					indexToRemove = i;
					break;
				}
			}
	
			if (indexToRemove != -1) {
				// Create a new array with size - 1
				int[] newArray = new int[array.length - 1];
	
				// Copy the elements before the target value
				System.arraycopy(array, 0, newArray, 0, indexToRemove);
	
				// Copy the elements after the target value
				System.arraycopy(array, indexToRemove + 1, newArray, indexToRemove, array.length - indexToRemove - 1);
	
				// Update the array associated with the key
				checkpointsStages.put(entry.getKey(), newArray);
	
				// Exit the loop after deletion
				break;
			}
		}
	}


	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// Check if the stage ID is recognized
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}
	
		// Retrieve the stage object
		Stage stage = stages.get(stageId);
	
		// Check if the stage is not already in "waiting for results" state
		if (stage.getState() == StageState.WAITING_FOR_RESULTS) {
			throw new InvalidStageStateException("Stage is already in 'waiting for results' state.");
		}
	
		// Update the state of the stage to "waiting for results"
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

        // create raceID and add to list
		int teamId = ++teamIDCounter;
        teamIDs.add(teamId);

		// Create a Race object and associate it with the raceId
		Team team = new Team(name, description);
		teams.put(teamId, team);

        return teamId;
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

		// create raceID and add to list
		int riderId = ++riderIDCounter;
		riderIDs.add(riderId);
  
		// Create a Race object and associate it with the raceId
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

		for (Map.Entry<Integer, int[]> entry : teamRiders.entrySet()) {
			int[] array = entry.getValue();
			int indexToRemove = -1;
		
			// Find the index of the target value in the array
			for (int i = 0; i < array.length; i++) {
				if (array[i] == riderId) {
					indexToRemove = i;
					break;
				}
			}
		
			if (indexToRemove != -1) {
				// Create a new array with size - 1
				int[] newArray = new int[array.length - 1];
		
				// Copy the elements before the target value
				System.arraycopy(array, 0, newArray, 0, indexToRemove);
		
				// Copy the elements after the target value
				System.arraycopy(array, indexToRemove + 1, newArray, indexToRemove, array.length - indexToRemove - 1);
		
				// Update the array associated with the key
				teamRiders.put(entry.getKey(), newArray);
		
				// Exit the loop after deletion
				break;
			}
		}

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,InvalidStageStateException {
    
		// Step 2: Check if the stage ID is recognized
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
		}
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider ID not recognized: " + stageId);
		}

		Stage stage = stages.get(stageId);
		
		// Step 3: Ensure the stage is in "waiting for results" state
		if (stage.getState() != StageState.WAITING_FOR_RESULTS) {
			throw new InvalidStageStateException("Stage is not in 'waiting for results' state.");
		}
		
		// Step 4: Check if the rider has already registered a result for the stage
		if (stage.hasResultForRider(riderId)) {
			throw new DuplicatedResultException("Rider already has a result for the stage.");
		}
		
		// Step 5: Verify if the length of checkpointTimes is valid
		int expectedCheckpointCount = getStageCheckpoints(stageId).length + 2; // Add 2 for start and finish
		if (checkpoints.length != expectedCheckpointCount) {
			throw new InvalidCheckpointTimesException("Invalid number of checkpoint times."+ checkpoints.length + "!=" + expectedCheckpointCount);
		}
		
		// Step 6: Record the rider's times for each checkpoint
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
		for (LocalTime time : riderResults) {
            System.out.println(time);
        }

		System.out.println("length is " + riderResults.length);

		// if (riderResults.length == 0) {
		// 	return new LocalTime[0];
		// }

		LocalTime firstResult = riderResults[0];
        LocalTime lastResult = riderResults[riderResults.length - 1];
		int elapsedHours = lastResult.getHour() - firstResult.getHour();
		int elapsedMinutes = lastResult.getMinute() - firstResult.getMinute();
		int elapsedSeconds = lastResult.getSecond() - firstResult.getSecond();

		if (elapsedSeconds < 0) {
			elapsedMinutes--;
			elapsedSeconds += 60;
		}
		if (elapsedMinutes < 0) {
			elapsedHours--;
			elapsedMinutes += 60;
		}

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
        LocalTime lowestTime = LocalTime.of(0, 0, 0);

        for (int i = 0; i < riderIDs.size(); i++){
            int rider = riderIDs.get(i);
			System.out.println("rider " + rider);
            LocalTime[] checkpointTimes = getRiderResultsInStage(stageId, rider);
            if (checkpointTimes[checkpointTimes.length - 1] == riderElapsed){
                continue;
            } else {
                if (isCloseTogether(checkpointTimes[checkpointTimes.length - 1], riderElapsed)){
                    adjustedTime = checkpointTimes[checkpointTimes.length - 1].isBefore(riderElapsed) ? checkpointTimes[checkpointTimes.length - 1]: riderElapsed;

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

        // Remove rider's results
        stage.removeRiderResultsInStage(riderId);

	}

	@Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {

        if (!stages.containsKey(stageId)) {
            throw new IDNotRecognisedException("Stage ID not recognized: " + stageId);
        }

        Map<Integer, LocalTime> riderTimes = new HashMap<>();

		// CHANGE FOR ELAPSED TIME ONLY
        for (int i = 0; i < riderIDCounter; i++){
			int a = riderIDs.get(i);
			System.out.println("THIS IS A:"+a);
            LocalTime[] riderTimelist = getRiderResultsInStage(stageId, 1);
			LocalTime riderTime = riderTimelist[riderTimelist.length - 1];
            if (riderTime == null){
                return new int[0];
            }
            riderTimes.put(riderIDs.get(i), riderTime);
        }

        List<Integer> sortedRiderIds = new ArrayList<>(riderTimes.keySet());
        sortedRiderIds.sort(Comparator.comparing(riderTimes::get));

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

		LocalTime[] riderTimeArr = new LocalTime[0];

		for (int i = 0; i < raceIDCounter; i++){
			int rider = riderIDs.get(i);
			LocalTime[] riderResults = getRiderResultsInStage(stageId, rider);
			LocalTime finishTime = riderResults[-1];
			LocalTime[] finishArray = new LocalTime[riderTimeArr.length + 1];
			finishArray[i] = finishTime;
			System.arraycopy(riderTimeArr, 0, finishArray, 0, riderTimeArr.length);
		}

		Arrays.sort(riderTimeArr);

		return riderTimeArr;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
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
            out.writeObject(this); // Serialize the MiniCyclingPortal object
        } catch (IOException e) {
            // Re-throw the exception to indicate that an error occurred during file writing
            throw new IOException("Error occurred while saving the MiniCyclingPortal to file: " + e.getMessage());
        }

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
