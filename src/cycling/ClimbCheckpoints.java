package cycling;


public class ClimbCheckpoints extends Checkpoints{

  /**
   * Class for Categorised Climb Checkpoints
   * @author Yousuf Ahmed, Sri Guhanathan
   */

  private Double location;
  private CheckpointType type;
  private Double averageGradient;
  private Double length;


  /**
   * ClimbCheckpoints Constructor Class
   */  
  public ClimbCheckpoints(Double location, CheckpointType type, Double averageGradient, Double length){
    super(location);
    this.type = type;
    this.averageGradient = averageGradient;
    this.length = length;
  
  }

  /**
   * Gets the location
   * 
   * @return location of the checkpoint
   */
  public Double getLocation() {
    return location;
  }

  /**
   * Gets the type of checkpoint
   * 
   * @return type of the checkpoint
   */
  public CheckpointType getCheckpointType() {
    return type;
  }

  /**
   * Gets the average gradient of the checkpoint
   * 
   * @return average gradient of the checkpoint
   */
  public Double getAverageGradient() {
    return averageGradient;
  }

  /**
   * Gets the length of the checkpoint
   * 
   * @return length of the checkpoint
   */
  public Double getLength() {
    return length;
  }
}
