package cycling;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Stage implements Serializable{

	/*
	 * Class for the Stages
	 */

	private String stageName;
	private String description;
	private double length;
	private LocalDateTime startTime;
	private StageType type;
	private Map<Integer, List<LocalTime>> riderResultsMap;
	StageState state;

	public Stage(String stageName, String description, double length, LocalDateTime startTime, StageType type, StageState ... state){
		this.stageName = stageName;
		this.description = description;
		this.length = length;
		this.startTime = startTime;
		this.type = type;
		this.state = StageState.READY;
		this.riderResultsMap = new HashMap<>();
	}
		

	public String getstageName() {
		return stageName;
	}

	public String getDescription(){
		return description;
	}

	public double getlength(){
		return length;
	}  

	public LocalDateTime getstartTime(){
		return startTime;
	}

	public StageType gettype(){
		return type;
	}

	public StageState getState(){
		return state;
	}

	// Default state is WAITING_FOR_RESULTS but if all parameters are entered
	// the state is set to READY
	public void setState() {
        this.state = StageState.WAITING_FOR_RESULTS;
    }

	public void recordRiderResults(int riderId, LocalTime... checkpointTimes) {
        // Record the rider's checkpoint times into the riderResultsMap hashmap
        List<LocalTime> times = List.of(checkpointTimes);
        riderResultsMap.put(riderId, times);
    }

	public boolean hasResultForRider(int riderId) {
        return riderResultsMap.containsKey(riderId);
    }

	// Gets the results from the riderResultsMap and puts it into a list
	public LocalTime[] getRiderResult(int riderId) {
		List<LocalTime> results = riderResultsMap.getOrDefault(riderId, List.of());
        return results.toArray(new LocalTime[0]);
        // return riderResultsMap.getOrDefault(riderId, List.of());
    }

	public void removeRiderResultsInStage(int riderId) {
        riderResultsMap.remove(riderId);
    }
}
