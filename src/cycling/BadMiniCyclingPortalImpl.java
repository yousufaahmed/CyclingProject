package cycling;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BadMiniCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the MiniCyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class BadMiniCyclingPortalImpl implements MiniCyclingPortal {
	public List<Integer> raceIds = new ArrayList<>();
	public List<Integer> stageIds = new ArrayList<>();
	public Map<Integer, Race> races = new HashMap<>();
	public Map<Integer, Stage> stages = new HashMap<>();
	public Map<Integer, int[]> stagesrace = new HashMap<>();


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

///////////////////////////////////////////////////////////////////
    public BadMiniCyclingPortalImpl(){
		this.raceIds = new ArrayList<>();
		this.races = new HashMap<>();
	}

	@Override
	public int[] getRaceIds() {
		int[] raceIdsArray = raceIds.stream().mapToInt(Integer::intValue).toArray();
		return raceIdsArray;
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
		int raceId = raceIds.size() + 1;
        raceIds.add(raceId);
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
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race with ID " + raceId + " not found.");
		}
	
		races.remove(raceId);

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		if (stagesrace.isEmpty()){
			return 0;
		}
		else if(!stagesrace.containsKey(raceId)) {
			throw new IDNotRecognisedException("Stage with ID " + raceId + " not found.");
		}
		
		int[] stageArray = stagesrace.get(raceId);
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
				int stageId = stageIds.size() + 1;
                stageIds.add(stageId);
				// Map the stage to the race
				Stage stage = new Stage(stageName, description, length, startTime, type);
				stages.put(stageId, stage);
				
				if (getNumberOfStages(raceId) != 0){
					int[] Originalarray = stagesrace.get(raceId);
					int[] newArray = Arrays.copyOf(Originalarray,Originalarray.length + 1);
					newArray[newArray.length - 1] = stageId;
					stagesrace.replace(raceId, newArray);   
				}else{
					int[] newArray = {stageId};
					stagesrace.put(raceId, newArray);
				}

				// Return the unique ID of the stage
				return stageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

		if (!stagesrace.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race ID not recognized: " + raceId);
		}
		
		int[] stagesArr = stagesrace.get(raceId);

		return stagesArr;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Race ID not recognized: " + stageId);
		}

		Stage stage = stages.get(stageId);
		double length = stage.getlength();
		return length;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		

	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
