import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Initialize shared resources
        Output output = new Output();
        Threeterson lock = new Threeterson(output);
        Queue<Paper> allPapers = new LinkedList<>();

        // Populate the queue with papers
        int numberOfPapers = 100; // Number of papers to be marked
        for (int i = 0; i < numberOfPapers; i++) {
            allPapers.add(new Paper());
        }

        // Create and start marker threads
        int numberOfMarkers = 3; // Number of marker threads
        Thread[] markerThreads = new Thread[numberOfMarkers];
        for (int i = 0; i < numberOfMarkers; i++) {
            Marker marker = new Marker(lock, output, allPapers);
            markerThreads[i] = new Thread(marker, "Marker-" + i);
            markerThreads[i].start();
        }

        // Wait for all marker threads to finish
        for (Thread markerThread : markerThreads) {
            markerThread.join();
        }

        // Validate the output
        OutputValidator validator = new OutputValidator(output);
        boolean isValid = validator.validate();

        // Print the validation result and output log
        System.out.println("Validation result: " + isValid);
        System.out.println("Output log:");
        System.out.println(output.toString());
    }
}
