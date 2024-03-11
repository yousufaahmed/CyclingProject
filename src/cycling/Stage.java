package cycling;
import java.time.LocalDateTime;
public class Stage {

	private String stageName;
	private String description;
	private double length;
	private LocalDateTime startTime;
	private StageType type;

	public double getStageLength(int stageId){
		return length;
	}

	public Stage(String stageName, String description, double length, LocalDateTime startTime, StageType type){
		this.description = description;
	}

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
