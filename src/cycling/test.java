package cycling;

public class test {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException {
        MiniCyclingPortal miniCyclingPortal = new BadMiniCyclingPortalImpl();
        int raceId1 = miniCyclingPortal.createRace("TourFR", "The famous cycling race");
        int raceId2 = miniCyclingPortal.createRace("TourEU", "The famous cycling race in EU");

        int[] raceIds = miniCyclingPortal.getRaceIds();
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
    
    }
}