import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Task 1: Testing the compare method
        System.out.println("Testing the compare method:");
        BoundedTimestamp ts1 = new BoundedTimestamp(new int[]{0, 1});
        BoundedTimestamp ts2 = new BoundedTimestamp(new int[]{1, 0});
        BoundedTimestamp ts3 = new BoundedTimestamp(new int[]{2, 2, 1});
        BoundedTimestamp ts4 = new BoundedTimestamp(new int[]{2, 0, 2});
        BoundedTimestamp ts5 = new BoundedTimestamp(new int[]{1, 0, 0});
        BoundedTimestamp ts6 = new BoundedTimestamp(new int[]{2, 2, 1});
        BoundedTimestamp ts7 = new BoundedTimestamp(new int[]{0, 0, 1});
        BoundedTimestamp ts8 = new BoundedTimestamp(new int[]{0, 1, 0});
        BoundedTimestamp ts9 = new BoundedTimestamp(new int[]{1, 0, 0});
        BoundedTimestamp ts10 = new BoundedTimestamp(new int[]{1, 1, 1});

        System.out.println(Arrays.toString(ts1.timestamp) + " compared to " + Arrays.toString(ts2.timestamp) + ": " + ts1.compare(ts2)); // Should print 1
        System.out.println(Arrays.toString(ts2.timestamp) + " compared to " + Arrays.toString(ts1.timestamp) + ": " + ts2.compare(ts1)); // Should print -1
        System.out.println(Arrays.toString(ts3.timestamp) + " compared to " + Arrays.toString(ts4.timestamp) + ": " + ts3.compare(ts4)); // Should print 1
        System.out.println(Arrays.toString(ts3.timestamp) + " compared to " + Arrays.toString(ts5.timestamp) + ": " + ts3.compare(ts5)); // Should print -1
        System.out.println(Arrays.toString(ts3.timestamp) + " compared to " + Arrays.toString(ts6.timestamp) + ": " + ts3.compare(ts6)); // Should print 0
        System.out.println(Arrays.toString(ts7.timestamp) + " compared to " + Arrays.toString(ts8.timestamp) + ": " + ts7.compare(ts8)); // Should print -1
        System.out.println(Arrays.toString(ts8.timestamp) + " compared to " + Arrays.toString(ts9.timestamp) + ": " + ts8.compare(ts9)); // Should print 1
        System.out.println(Arrays.toString(ts9.timestamp) + " compared to " + Arrays.toString(ts10.timestamp) + ": " + ts9.compare(ts10)); // Should print -1

        // Task 2: Testing the getNext method
        System.out.println("\nTesting the getNext method:");
        BoundedTimestamp[] labels1 = {
            new BoundedTimestamp(new int[]{0, 0}),
            new BoundedTimestamp(new int[]{0, 0}),
            new BoundedTimestamp(new int[]{0, 1})
        };
        int indexRequesting1 = 1;
        BoundedTimestamp nextTs1 = BoundedTimestamp.getNext(labels1, indexRequesting1);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting1 + ": " + nextTs1);

        BoundedTimestamp[] labels2 = {
            new BoundedTimestamp(new int[]{2, 0}),
            new BoundedTimestamp(new int[]{1, 1}),
            new BoundedTimestamp(new int[]{2, 1})
        };
        int indexRequesting2 = 0;
        BoundedTimestamp nextTs2 = BoundedTimestamp.getNext(labels2, indexRequesting2);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting2 + ": " + nextTs2);

        BoundedTimestamp[] labels3 = {
            new BoundedTimestamp(new int[]{0, 0, 1}),
            new BoundedTimestamp(new int[]{0, 1, 0}),
            new BoundedTimestamp(new int[]{1, 0, 0})
        };
        int indexRequesting3 = 2;
        BoundedTimestamp nextTs3 = BoundedTimestamp.getNext(labels3, indexRequesting3);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting3 + ": " + nextTs3);

        BoundedTimestamp[] labels4 = {
            new BoundedTimestamp(new int[]{1, 1, 1}),
            new BoundedTimestamp(new int[]{2, 0, 0}),
            new BoundedTimestamp(new int[]{2, 1, 0})
        };
        int indexRequesting4 = 1;
        BoundedTimestamp nextTs4 = BoundedTimestamp.getNext(labels4, indexRequesting4);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting4 + ": " + nextTs4);

        BoundedTimestamp[] labels5 = {
            new BoundedTimestamp(new int[]{0, 0, 0}),
            new BoundedTimestamp(new int[]{0, 0, 1}),
            new BoundedTimestamp(new int[]{0, 1, 0}),
            new BoundedTimestamp(new int[]{1, 0, 0})
        };
        int indexRequesting5 = 3;
        BoundedTimestamp nextTs5 = BoundedTimestamp.getNext(labels5, indexRequesting5);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting5 + ": " + nextTs5);

        BoundedTimestamp[] labels6 = {
            new BoundedTimestamp(new int[]{1, 0, 0}),
            new BoundedTimestamp(new int[]{1, 1, 0}),
            new BoundedTimestamp(new int[]{1, 1, 1})
        };
        int indexRequesting6 = 0;
        BoundedTimestamp nextTs6 = BoundedTimestamp.getNext(labels6, indexRequesting6);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting6 + ": " + nextTs6);

        BoundedTimestamp[] labels7 = {
            new BoundedTimestamp(new int[]{2, 2, 2}),
            new BoundedTimestamp(new int[]{2, 2, 1}),
            new BoundedTimestamp(new int[]{2, 1, 1}),
            new BoundedTimestamp(new int[]{1, 1, 1})
        };
        int indexRequesting7 = 2;
        BoundedTimestamp nextTs7 = BoundedTimestamp.getNext(labels7, indexRequesting7);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting7 + ": " + nextTs7);

        BoundedTimestamp[] labels8 = {
            new BoundedTimestamp(new int[]{1, 0}),
            new BoundedTimestamp(new int[]{1, 1}),
            new BoundedTimestamp(new int[]{2, 0}),
            new BoundedTimestamp(new int[]{2, 1})
        };
        int indexRequesting8 = 1;
        BoundedTimestamp nextTs8 = BoundedTimestamp.getNext(labels8, indexRequesting8);
        System.out.println("Next smallest legal timestamp for index " + indexRequesting8 + ": " + nextTs8);
    }
}