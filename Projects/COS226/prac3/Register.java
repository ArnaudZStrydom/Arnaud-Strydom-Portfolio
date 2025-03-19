import java.util.*;

public class Register {
    List<RegisterOperation> operations = new ArrayList<>();

    void write(int value, int startTime, int endTime) {
        operations.add(new RegisterOperation(RegisterOperation.Type.WRITE, value, startTime, endTime));
    }

    void read(int startTime, int endTime) {
        operations.add(new RegisterOperation(RegisterOperation.Type.READ, null, startTime, endTime));
    }

    List<List<Integer>> getValidReadValues() {
        List<List<Integer>> validReads = new ArrayList<>();
        
        // Determine valid values for each read operation
        for (RegisterOperation readOp : operations) {
            if (readOp.type == RegisterOperation.Type.READ) {
                List<Integer> validValues = new ArrayList<>();
                
                Integer latestValidValue = null;
                boolean validWriteFound = false;
                
                // Iterate through operations to find valid values for the current read operation
                for (RegisterOperation writeOp : operations) {
                    if (writeOp.type == RegisterOperation.Type.WRITE) {
                        if (writeOp.endTime <= readOp.startTime) {
                            // Update the latest valid value before the read starts
                            latestValidValue = writeOp.value;
                            validWriteFound = true;
                        } else if (writeOp.startTime < readOp.endTime && writeOp.endTime >= readOp.startTime) {
                            // If there's an overlap, add the value (for completeness, but should be covered by latest valid value logic)
                            validValues.add(writeOp.value);
                        }
                    }
                }
                
                // Add the latest valid value found to the list of valid values
                if (latestValidValue != null) {
                    validValues.add(latestValidValue);
                } else if (!validWriteFound) {
                    // If no valid writes were found, add null (or handle as needed)
                    validValues.add(null);
                }
                
                // Remove duplicates and ensure valid values are unique
                validValues = new ArrayList<>(new HashSet<>(validValues));
                
                validReads.add(validValues);
            }
        }
        
        // Handle case where no valid reads were found
        if (validReads.isEmpty()) {
            return Collections.emptyList();
        }
        
        // Generate all valid combinations of read values
        return generateCombinations(validReads);
    }

    private List<List<Integer>> generateCombinations(List<List<Integer>> validReads) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>()); // Start with an empty combination
        
        for (List<Integer> validValues : validReads) {
            List<List<Integer>> newResult = new ArrayList<>();
            for (List<Integer> sequence : result) {
                for (Integer value : validValues) {
                    List<Integer> newSequence = new ArrayList<>(sequence);
                    newSequence.add(value);
                    newResult.add(newSequence);
                }
            }
            result = newResult;
        }
        
        return result;
    }
}
