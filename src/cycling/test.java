package cycling;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException,InvalidLocationException, InvalidStageStateException,
    InvalidStageTypeException,DuplicatedResultException, InvalidCheckpointTimesException, IOException, NameNotRecognisedException, ClassNotFoundException{
        CyclingPortal miniCyclingPortal = new CyclingPortalImpl();
        miniCyclingPortal.createRace("TourFR", "The famous cycling race");
        miniCyclingPortal.createRace("TourEU", "The famous cycling race in EU");

        // Generate a random integer

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
        // miniCyclingPortal.addStageToRace(1,"Theboulder3","An epic climb against boulders 3",7.5,startTime,StageType.HIGH_MOUNTAIN);
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
        miniCyclingPortal.createRider(2,"Brian",1977);
        miniCyclingPortal.createRider(2,"Connor",1991);
        LocalTime start = LocalTime.of(8, 0); 
        LocalTime checkpoint1 = LocalTime.of(8, 30);
        LocalTime checkpoint2 = LocalTime.of(9, 0); 
        LocalTime checkpoint3 = LocalTime.of(9, 15); 
        LocalTime finish = LocalTime.of(9, 30,2,2); 

        LocalTime[] checkpointTimes = {start, checkpoint1, checkpoint2,checkpoint3, finish};
        miniCyclingPortal.registerRiderResultsInStage(2,1, checkpointTimes);
        LocalTime[] result = miniCyclingPortal.getRiderResultsInStage(2,1);

        LocalTime start2 = LocalTime.of(8, 0); 
        LocalTime checkpoint12 = LocalTime.of(8, 30,0); 
        LocalTime checkpoint22 = LocalTime.of(9, 0,0); 
        LocalTime checkpoint32 = LocalTime.of(9, 15,0); 
        LocalTime finish2 = LocalTime.of(9, 30,1,5); 

        LocalTime[] checkpointTimes2 = {start2, checkpoint12, checkpoint22,checkpoint32, finish2};
        miniCyclingPortal.registerRiderResultsInStage(2,2, checkpointTimes2);
        LocalTime[] result2 = miniCyclingPortal.getRiderResultsInStage(2,2);

        LocalTime start3 = LocalTime.of(8, 0); // Example start time
        LocalTime checkpoint13 = LocalTime.of(8, 30); // Example checkpoint time 1
        LocalTime checkpoint23 = LocalTime.of(9, 0); // Example checkpoint time 2
        LocalTime checkpoint33 = LocalTime.of(9, 15); // Example checkpoint time 1
        LocalTime finish3 = LocalTime.of(9, 30,3,3); // Example finish time
        // Assemble the array of checkpoint times, including start and finish
        LocalTime[] checkpointTimes3 = {start3, checkpoint13, checkpoint23,checkpoint33, finish3};
        miniCyclingPortal.registerRiderResultsInStage(2,3, checkpointTimes3);
        LocalTime[] result3 = miniCyclingPortal.getRiderResultsInStage(2,3);

        System.out.println(result);
        for (LocalTime time : result) {
            System.out.println(time);
        }
        System.out.println(result2);
        for (LocalTime time : result2) {
            System.out.println(time);
        }
        System.out.println(result3);
        for (LocalTime time : result3) {
            System.out.println(time);
        }
        //miniCyclingPortal.deleteRiderResultsInStage(2, 1);
        LocalTime adjtime = miniCyclingPortal.getRiderAdjustedElapsedTimeInStage(2, 1);
        System.out.println("adjusted time "+adjtime);
        int[] rankresult = miniCyclingPortal.getRidersRankInStage(2);
        for (int ac : rankresult) {
            System.out.println(ac);
        }
        LocalTime[] adjresult = miniCyclingPortal.getRankedAdjustedElapsedTimesInStage(2);
        for (LocalTime tim : adjresult) {
            System.out.println(tim);
        }
        int[] gnrank = miniCyclingPortal.getRidersGeneralClassificationRank(1);
        for (int ac : gnrank) {
            System.out.println(ac);
        }

        LocalTime[] gntime = miniCyclingPortal.getGeneralClassificationTimesInRace(1);
        for (LocalTime tim : gntime) {
            System.out.println(tim);
        }

        int[] points = miniCyclingPortal.getRidersPointsInStage(2);
        for (int i : points) {
            System.out.println(i);
        }

        int[] mpoints = miniCyclingPortal.getRidersMountainPointsInStage(2);
        for (int i : mpoints) {
            System.out.println(i);
        }

        miniCyclingPortal.saveCyclingPortal("cyclingPortal");


        // LocalTime[] result2 = miniCyclingPortal.getRiderResultsInStage(2,1);
        // System.out.println(result2);
        // for (LocalTime time2 : result2) {
        //    System.out.println(time2);
        // }
        // int[] resultTime = miniCyclingPortal.getRidersRankInStage(2);

        // miniCyclingPortal.loadCyclingPortal("cyclingPortal");

        // System.out.println(miniCyclingPortal.getRaceIds());
        
        System.out.println("RiderPOINTS");
        int[] points1 = miniCyclingPortal.getRidersPointsInRace(1);
        for (int i : points1) {
            System.out.println(i);
        }

        System.out.println("MountainPOINTS");
        int[] points2 = miniCyclingPortal.getRidersMountainPointsInRace(1);
        for (int i : points2) {
            System.out.println(i);
        }
    }
}