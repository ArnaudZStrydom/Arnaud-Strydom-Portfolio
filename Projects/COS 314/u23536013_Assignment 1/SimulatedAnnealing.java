import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private double initialTemp; // Initial temperature (T0)
    private int maxIterations;
    private Random rand;
    private int noImprovementLimit; // Stopping condition: no improvement for N iterations

    public SimulatedAnnealing(double initialTemp, int maxIterations, long seed, int noImprovementLimit) {
        this.initialTemp = initialTemp;
        this.maxIterations = maxIterations;
        this.rand = new Random(seed);
        this.noImprovementLimit = noImprovementLimit;
    }

    public int[] solve(List<int[]> cities) {
        int[] currentTour = generateRandomTour(cities.size());
        int[] bestTour = currentTour.clone();
        double bestDistance = TSPUtils.calculateTotalDistance(bestTour, cities);

        int iterationsWithoutImprovement = 0;

        for (int iteration = 1; iteration <= maxIterations; iteration++) {
            double temperature = initialTemp / Math.log(iteration + 1); // Logarithmic cooling schedule

            int[] newTour = perturb(currentTour);
            double currentDistance = TSPUtils.calculateTotalDistance(currentTour, cities);
            double newDistance = TSPUtils.calculateTotalDistance(newTour, cities);
            double delta = newDistance - currentDistance;

            if (delta < 0 || Math.exp(-delta / temperature) > rand.nextDouble()) {
                currentTour = newTour.clone();
            }

            if (TSPUtils.calculateTotalDistance(currentTour, cities) < bestDistance) {
                bestTour = currentTour.clone();
                bestDistance = TSPUtils.calculateTotalDistance(bestTour, cities);
                iterationsWithoutImprovement = 0; // Reset counter
            } else {
                iterationsWithoutImprovement++;
            }

            // Stop if no improvement for N iterations
            if (iterationsWithoutImprovement >= noImprovementLimit) {
                break;
            }
        }

        return bestTour;
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