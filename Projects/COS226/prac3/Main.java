import java.util.*;

public class Main {
    public static void main(String[] args) {
        int task = 0;
        if (args.length > 0) {
            task = Integer.parseInt(args[0]);
        }

        switch (task) {
            case 1:
                task1();
                break;

            default:
                task1();
                break;
        }
    }

    public static void task1() {
        System.out.println("### TwoWritesAndFourReads:");
        TwoWritesAndFourReads();

        System.out.println("### BackToBackWrites:");
        BackToBackWrites();

        System.out.println("### NonOverlappingReads:");
        NonOverlappingReads();

        System.out.println("### ComplexScenario:");
        ComplexScenario();

        System.out.println("### NoWritesScenario:");
        NoWritesScenario();

        System.out.println("### MultipleConsecutiveWrites:");
        MultipleConsecutiveWrites();

        System.out.println("### ReadOverlappingMultipleWrites:");
        ReadOverlappingMultipleWrites();
    }

    public static void TwoWritesAndFourReads() {
        Register register = new Register();
        register.write(0, 1, 3);
        register.read(2, 4);
        register.read(4, 5);
        register.write(1, 6, 10);
        register.read(7, 8);
        register.read(8, 9);
        register.read(12, 13);

        List<List<Integer>> validReads = register.getValidReadValues();
        System.out.println("Valid Reads: " + validReads);
    }

    public static void BackToBackWrites() {
        Register register = new Register();
        register.write(0, 1, 5); // W(0)
        register.write(3, 6, 10); // W(3)
        register.read(2, 4); // R¹
        register.read(7, 8); // R²

        List<List<Integer>> validReads = register.getValidReadValues();
        System.out.println("Valid Reads: " + validReads);
    }

    public static void NonOverlappingReads() {
        Register register = new Register();
        register.write(0, 1, 3); // W(0)
        register.read(4, 5); // R¹
        register.read(6, 7); // R²
        register.read(8, 9); // R³

        List<List<Integer>> validReads = register.getValidReadValues();
        System.out.println("Valid Reads: " + validReads);
    }

    public static void ComplexScenario() {
        Register register = new Register();
        register.write(0, 1, 3); // W(0)
        register.read(4, 5); // R¹
        register.write(6, 7, 10); // W(6)
        register.read(11, 12); // R²
        register.write(13, 14, 15); // W(13)
        register.read(16, 17); // R³
        register.read(18, 19); // R⁴

        List<List<Integer>> validReads = register.getValidReadValues();
        System.out.println("Valid Reads: " + validReads);
    }

    public static void NoWritesScenario() {
        Register register = new Register();
        register.read(0, 1); // R¹
        register.read(2, 3); // R²
        register.read(4, 5); // R³

        List<List<Integer>> validReads = register.getValidReadValues();
        System.out.println("Valid Reads: " + validReads);
    }

    public static void MultipleConsecutiveWrites() {
        Register register = new Register();
        register.write(1, 0, 2); // W(1)
        register.write(2, 3, 5); // W(2)
        register.write(3, 6, 8); // W(3)
        register.read(9, 10); // R¹

        List<List<Integer>> validReads = register.getValidReadValues();
        System.out.println("Valid Reads: " + validReads);
    }

    public static void ReadOverlappingMultipleWrites() {
        Register register = new Register();
        register.write(1, 0, 3); // W(1)
        register.read(2, 4); // R¹
        register.write(2, 3, 5); // W(2)
        register.read(4, 6); // R²

        List<List<Integer>> validReads = register.getValidReadValues();
        System.out.println("Valid Reads: " + validReads);
    }
}
