import java.util.*;

public class TabuSearch {
    private int tabuListSize;
    private int maxIterations;
    private Random rand;
    private int noImprovementLimit; // Stopping condition: no improvement for N iterations

    public TabuSearch(int tabuListSize, int maxIterations, long seed, int noImprovementLimit) {
        this.tabuListSize = tabuListSize;
        this.maxIterations = maxIterations;
        this.rand = new Random(seed);
        this.noImprovementLimit = noImprovementLimit;
    }

    public int[] solve(List<int[]> cities) {
        int[] currentTour = generateRandomTour(cities.size());
        int[] bestTour = currentTour.clone();
        double bestDistance = TSPUtils.calculateTotalDistance(bestTour, cities);

        Queue<String> tabuList = new LinkedList<>(); // Store moves as "i,j"
        int iterationsWithoutImprovement = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            int[] newTour = perturb(currentTour);
            String move = getMove(currentTour, newTour); // Get the move (e.g., "i,j")

            if (!tabuList.contains(move)) {
                if (tabuList.size() >= tabuListSize) {
                    tabuList.poll(); // Remove the oldest move
                }
                tabuList.add(move); // Add the new move

                double newDistance = TSPUtils.calculateTotalDistance(newTour, cities);
                double currentDistance = TSPUtils.calculateTotalDistance(currentTour, cities);

                if (newDistance < currentDistance) {
                    currentTour = newTour.clone();

                    if (newDistance < bestDistance) {
                        bestTour = newTour.clone();
                        bestDistance = newDistance;
                        iterationsWithoutImprovement = 0; // Reset counter
                    }
                } else {
                    iterationsWithoutImprovement++;
                }
            }

            // Stop if no improvement for N iterations
            if (iterationsWithoutImprovement >= noImprovementLimit) {
                break;
            }
        }

        return bestTour;
    }

    private String getMove(int[] currentTour, int[] newTour) {
        // Find the indices of the swapped cities
        for (int i = 0; i < currentTour.length; i++) {
            if (currentTour[i] != newTour[i]) {
                for (int j = i + 1; j < currentTour.length; j++) {
                    if (currentTour[j] != newTour[j]) {
                        return i + "," + j; // Return the move as "i,j"
                    }
                }
            }
        }
        return ""; // No move (should not happen)
    }

    private int[] perturb(int[] tour) {
        int[] newTour = tour.clone();
        int i = rand.nextInt(tour.length);
        int j = rand.nextInt(tour.length);
        // Swap two cities
        int temp = newTour[i];
        newTour[i] = newTour[j];
        newTour[j] = temp;
        return newTour;
    }

    private int[] generateRandomTour(int size) {
        int[] tour = new int[size];
        for (int i = 0; i < size; i++) {
            tour[i] = i;
        }
        // Shuffle the tour to make it random
        for (int i = 0; i < size; i++) {
            int j = rand.nextInt(size);
            int temp = tour[i];
            tour[i] = tour[j];
            tour[j] = temp;
        }
        return tour;
    }
}