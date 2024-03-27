package cycling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Race extends Cycling implements Serializable {
	private List<Integer> stagesinrace;

	/**
	 * Class for the Race
	 * 
	 * @author Yousuf Ahmed, Sri Guhanathan
	 */


	/**
	 * Race Constructor Class
	 */
	public Race(String name, String description) {
		super(name, description);
		this.stagesinrace = new ArrayList<>();
	}

	/**
	 * Adds the stage to the race
	 * 
	 * @param stageId	Id of the current stage
	 */
	public void addstageinRace(int stageId){
		stagesinrace.add(stageId);
	}

	/**
	 * Gets the stages inside of the race
	 * 
	 * @return array of stages in a particular race
	 */
	public int[] getstageinRace(){
		int[] array = new int[stagesinrace.size()];
		for (int i = 0; i < stagesinrace.size(); i++) {
            array[i] = stagesinrace.get(i);
        }
		return array;
	}
	
	/**
	 * removes a particular stage from a race
	 * 
	 * @param stageId		Id of the stage to be removed
	 */
	public void removestageinRace(int stageId){
		if (stagesinrace.contains(stageId)) {
			// If it exists, remove it
			stagesinrace.remove(stageId);
		}
	}
}

