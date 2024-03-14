package cycling;
import java.util.Random;
import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException,InvalidLocationException, InvalidStageStateException,
    InvalidStageTypeException {
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
    }
}