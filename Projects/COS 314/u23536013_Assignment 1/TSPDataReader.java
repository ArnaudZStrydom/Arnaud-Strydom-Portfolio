import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSPDataReader {
    public static List<int[]> readCities(String filename) throws FileNotFoundException {
        List<int[]> cities = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        
        // Skip lines until we reach the NODE_COORD_SECTION
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("NODE_COORD_SECTION")) {
                break;
            }
        }
        
        // Read city coordinates
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("EOF")) {
                break;
            }
            String[] parts = line.split(" ");
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            cities.add(new int[]{x, y});
        }
        
        scanner.close();
        return cities;
    }
}