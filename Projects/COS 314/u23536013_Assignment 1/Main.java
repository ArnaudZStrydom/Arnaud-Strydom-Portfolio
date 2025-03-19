import java.io.FileNotFoundException;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // List of TSP files
        String[] files = {"8.txt", "12.txt", "15.txt", "20.txt", "25.txt"};

        // Number of runs
        int numRuns = 10;

        // Table header
        System.out.println("Problem Instance | Algorithm | Seed Value | Cost | Best Solution | Runtime (ms)");

        for (String file : files) {
            try {
                // Read the cities from the file
                List<int[]> cities = TSPDataReader.readCities(file);

                // Calculate initial temperature for Simulated Annealing
                double initialTemp = calculateInitialTemp(cities);

                // Run Simulated Annealing 10 times
                double saBestDistance = Double.MAX_VALUE;
                int[] saBestTour = null;
                long saBestSeed = 0;
                long saTotalRuntime = 0;

                for (int run = 1; run <= numRuns; run++) {
                    long seed = System.currentTimeMillis();
                    SimulatedAnnealing sa = new SimulatedAnnealing(initialTemp, 100000, seed, 10000);
                    long startTime = System.nanoTime();
                    int[] saTour = sa.solve(cities);
                    long endTime = System.nanoTime();
                    double saDistance = TSPUtils.calculateTotalDistance(saTour, cities);

                    if (saDistance < saBestDistance) {
                        saBestDistance = saDistance;
                        saBestTour = saTour;
                        saBestSeed = seed;
                    }
                    saTotalRuntime += (endTime - startTime);
                }

                // Run Tabu Search 10 times
                double tsBestDistance = Double.MAX_VALUE;
                int[] tsBestTour = null;
                long tsBestSeed = 0;
                long tsTotalRuntime = 0;

                for (int run = 1; run <= numRuns; run++) {
                    long seed = System.currentTimeMillis();
                    TabuSearch ts = new TabuSearch(20, 100000, seed, 10000);
                    long startTime = System.nanoTime();
                    int[] tsTour = ts.solve(cities);
                    long endTime = System.nanoTime();
                    double tsDistance = TSPUtils.calculateTotalDistance(tsTour, cities);

                    if (tsDistance < tsBestDistance) {
                        tsBestDistance = tsDistance;
                        tsBestTour = tsTour;
                        tsBestSeed = seed;
                    }
                    tsTotalRuntime += (endTime - startTime);
                }

                // Print results for the current file
                System.out.println(file + " | Tabu | " + tsBestSeed + " | " + tsBestDistance + " | " + Arrays.toString(tsBestTour) + " | " + tsTotalRuntime / numRuns/1000000 ); // Convert to milliseconds
                System.out.println(file + " | SA | " + saBestSeed + " | " + saBestDistance + " | " + Arrays.toString(saBestTour) + " | " + saTotalRuntime / numRuns/1000000 ); // Convert to milliseconds
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + file);
            }
        }
    }

    // Helper method to calculate initial temperature for Simulated Annealing
    private static double calculateInitialTemp(List<int[]> cities) {
        double totalDelta = 0;
        int numSamples = 100; // Number of random samples
        Random rand = new Random();

        for (int i = 0; i < numSamples; i++) {
            int[] tour1 = generateRandomTour(cities.size(), rand);
            int[] tour2 = generateRandomTour(cities.size(), rand);
            double delta = Math.abs(TSPUtils.calculateTotalDistance(tour1, cities) - TSPUtils.calculateTotalDistance(tour2, cities));
            totalDelta += delta;
        }

        return totalDelta / numSamples; // Average delta
    }

    // Helper method to generate a random tour
    private static int[] generateRandomTour(int size, Random rand) {
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