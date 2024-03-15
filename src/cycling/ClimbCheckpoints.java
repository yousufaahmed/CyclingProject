package cycling;

// ADD INHERITANCE IF WE HAVE THE TIME

public class ClimbCheckpoints extends Checkpoints{

  private Double location;
  private CheckpointType type;
  private Double averageGradient;
  private Double length;
    
  public ClimbCheckpoints(Double location, CheckpointType type, Double averageGradient, Double length){
    super(location);
    this.type = type;
    this.averageGradient = averageGradient;
    this.length = length;
  
  }

  public Double getLocation() {
    return location;
  }

  public CheckpointType getCheckpointType() {
    return type;
  }

  public Double getAverageGradient() {
    return averageGradient;
  }

  public Double getLength() {
    return length;
  }
}
