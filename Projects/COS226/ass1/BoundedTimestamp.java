public class BoundedTimestamp {
    public final int[] timestamp;

    public BoundedTimestamp(int[] timestampArray) {
        //create a new timestamp with the same values as the input array
        this.timestamp = timestampArray;
    }

    public BoundedTimestamp(int n) {
        this.timestamp = new int[n];
        //explicitly set new timestamp to 0,0,0...
        for (int i = 0; i < n; i++) {
            timestamp[i] = 0;
        }
    }

    // Returns 1 if this comes before the other (i.e this is earlier), -1 if this comes after the other (i.e this is later), 0 if they are equal
    public int compare(BoundedTimestamp other) {
        for (int i = 0; i < this.timestamp.length; i++) {
            if (this.timestamp[i] != other.timestamp[i]) {
                if ((this.timestamp[i] + 1) % 3 == other.timestamp[i]) {
                    return 1; // This is before the other
                } else {
                    return -1; // This is after the other
                }
            }
        }
        return 0; // Timestamps are equal
    }

    

    public static BoundedTimestamp getNext(BoundedTimestamp[] label, int indexAskingForLabel) {
        int n = label[0].timestamp.length;
        int[] newTimestamp = new int[n];

        // Start with the maximum of all timestamps
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = 0; j < label.length; j++) {
                if (j != indexAskingForLabel) {
                    max = Math.max(max, label[j].timestamp[i]);
                }
            }
            newTimestamp[i] = max;
        }

        while (true) {
            // Increment the timestamp
            for (int i = n - 1; i >= 0; i--) {
                newTimestamp[i] = (newTimestamp[i] + 1) % 3;
                if (newTimestamp[i] != 0) {
                    break;
                }
            }

            BoundedTimestamp candidate = new BoundedTimestamp(newTimestamp);
            boolean valid = true;

            // Check if the candidate is valid (comes after all other timestamps except the requesting one)
            for (int i = 0; i < label.length; i++) {
                if (i != indexAskingForLabel) {
                    int comparison = candidate.compare(label[i]);
                    if (comparison != -1) {
                        valid = false;
                        break;
                    }
                }
            }

            if (valid) {
                return candidate;
            }

            // If we've wrapped around to all zeros, return the original state
            boolean allZeros = true;
            for (int i = 0; i < n; i++) {
                if (newTimestamp[i] != 0) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros) {
                return new BoundedTimestamp(n);
            }
        }
    }

    //you may add varibles and methods as needed

    @Override
    public String toString() {
        return java.util.Arrays.toString(timestamp);
    }

}
