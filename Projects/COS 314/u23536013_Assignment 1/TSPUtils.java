import java.util.List;
public class TSPUtils {
    public static double calculateDistance(int[] city1, int[] city2) {
        int dx = city1[0] - city2[0];
        int dy = city1[1] - city2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double calculateTotalDistance(int[] tour, List<int[]> cities) {
        double totalDistance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            totalDistance += calculateDistance(cities.get(tour[i]), cities.get(tour[i + 1]));
        }
        // Return to the starting city
        totalDistance += calculateDistance(cities.get(tour[tour.length - 1]), cities.get(tour[0]));
        return totalDistance;
    }
}