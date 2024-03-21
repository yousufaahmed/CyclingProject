package cycling;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException,InvalidLocationException, InvalidStageStateException,
    InvalidStageTypeException,DuplicatedResultException, InvalidCheckpointTimesException, IOException {
        MiniCyclingPortal miniCyclingPortal = new BadMiniCyclingPortalImpl();
        miniCyclingPortal.createRace("TourFR", "The famous cycling race");
        miniCyclingPortal.createRace("TourEU", "The famous cycling race in EU");
        Random random = new Random();

        // Generate a random integer
        int randomNumber = random.nextInt(Integer.MAX_VALUE);

        System.out.println("Random Integer: " + randomNumber);

        int[] raceIds = miniCyclingPortal.getRaceIds();
        System.out.println(raceIds);
        if (raceIds.length > 0) {
            System.out.println("Race IDs:");
        
            // Iterate through the array and print each ID
            for (int raceId : raceIds) {
                System.out.println(raceId);
            }
        } else {
            System.out.println("No races found.");
        }

        String racedetail = miniCyclingPortal.viewRaceDetails(1);
        System.out.println(racedetail);
        //miniCyclingPortal.removeRaceById(1);
        String racedetail2 = miniCyclingPortal.viewRaceDetails(2);
        System.out.println(racedetail2);
        LocalDateTime startTime = LocalDateTime.of(2022, 5, 15, 14, 30);
        miniCyclingPortal.addStageToRace(1,"Theboulder","An epic climb against boulders",6.0,startTime,StageType.HIGH_MOUNTAIN);
        miniCyclingPortal.addStageToRace(1,"Theboulder2","An epic climb against boulders 2",7.0,startTime,StageType.HIGH_MOUNTAIN);

        int nstage = miniCyclingPortal.getNumberOfStages(1);
        System.out.println(nstage);
        //miniCyclingPortal.addStageToRace(1,"Theboulder3","An epic climb against boulders 3",7.5,startTime,StageType.HIGH_MOUNTAIN);
        miniCyclingPortal.removeStageById(1);
        int nostage = miniCyclingPortal.getNumberOfStages(1);
        System.out.println(nostage);
        miniCyclingPortal.concludeStagePreparation(2);
        miniCyclingPortal.addIntermediateSprintToStage(2, 2.0);
        miniCyclingPortal.addCategorizedClimbToStage(2, 3.0, CheckpointType.C2, 3.0, 2.0);
        miniCyclingPortal.addIntermediateSprintToStage(2, 3.0);
        int a=miniCyclingPortal.getStageCheckpoints(2).length;
        System.out.println("Number of checkpoints in stage 2 :"+ a);
        miniCyclingPortal.removeCheckpoint(1);
        int b=miniCyclingPortal.getStageCheckpoints(2).length;
        System.out.println("Number of checkpoints in stage 2 :"+ b);
        miniCyclingPortal.createTeam("StrawHats","group of pirates");
        miniCyclingPortal.createTeam("StrawCaps","group of pirates with caps");
        int[] teams = miniCyclingPortal.getTeams();
        for (int team : teams) {
            System.out.println(team);
        }
        miniCyclingPortal.removeTeam(1);
        int[] teams2 = miniCyclingPortal.getTeams();
        for (int team : teams2) {
            System.out.println(team);
        }
        miniCyclingPortal.createRider(2,"John",1985);
        LocalTime start = LocalTime.of(8, 0); // Example start time
        LocalTime checkpoint1 = LocalTime.of(8, 30); // Example checkpoint time 1
        LocalTime checkpoint2 = LocalTime.of(9, 0); // Example checkpoint time 2
        LocalTime checkpoint3 = LocalTime.of(9, 15); // Example checkpoint time 1
        LocalTime finish = LocalTime.of(9, 30); // Example finish time
        // Assemble the array of checkpoint times, including start and finish
        LocalTime[] checkpointTimes = {start, checkpoint1, checkpoint2,checkpoint3, finish};
        miniCyclingPortal.registerRiderResultsInStage(2,1, checkpointTimes);
        LocalTime[] result = miniCyclingPortal.getRiderResultsInStage(2,1);
        System.out.println(result);
        for (LocalTime time : result) {
            System.out.println(time);
        }
        miniCyclingPortal.deleteRiderResultsInStage(2, 1);
        //LocalTime[] result2 = miniCyclingPortal.getRiderResultsInStage(2,1);
        //System.out.println(result2);
        //for (LocalTime time2 : result2) {
         //   System.out.println(time2);
        //}
        String filename = "cyclingPortal.ser";
        miniCyclingPortal.saveCyclingPortal(filename);
    }
}