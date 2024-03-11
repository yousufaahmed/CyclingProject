package cycling;

import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException {
        MiniCyclingPortal miniCyclingPortal = new BadMiniCyclingPortalImpl();
        miniCyclingPortal.createRace("TourFR", "The famous cycling race");
        miniCyclingPortal.createRace("TourEU", "The famous cycling race in EU");

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
    
    }
}