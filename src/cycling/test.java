package cycling;

public class test {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException {
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
        miniCyclingPortal.removeRaceById(1);
        String racedetail2 = miniCyclingPortal.viewRaceDetails(1);
        System.out.println(racedetail2);
    
    }
}