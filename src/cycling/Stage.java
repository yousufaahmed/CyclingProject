package cycling;
import java.time.LocalDateTime;

// import javax.swing.plaf.nimbus.State;
public class Stage {

	private String stageName;
	private String description;
	private double length;
	private LocalDateTime startTime;
	private StageType type;
	StageState state;

	public Stage(String stageName, String description, double length, LocalDateTime startTime, StageType type, StageState ... state){
		this.stageName = stageName;
		this.description = description;
		this.length = length;
		this.startTime = startTime;
		this.type = type;
		this.state = StageState.READY;
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

	public void setState() {
        this.state = StageState.WAITING_FOR_RESULTS;
    }

    // @Override
	// public double getStageLength(int stageId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public void removeStageById(int stageId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub

	// }

	// @Override
	// public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
	// 		Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
	// 		InvalidStageTypeException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
	// 		InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
	// 	// TODO Auto-generated method stub
	// 	return 0;
	// }

	// @Override
	// public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
	// 	// TODO Auto-generated method stub

	// }

	// @Override
	// public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
	// 	// TODO Auto-generated method stub

	// }

	// @Override
	// public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
	// 	// TODO Auto-generated method stub
	// 	return null;
	// }
}
