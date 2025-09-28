import java.util.Scanner;
import java.util.Locale;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US); // For consistent decimal formatting

        int numSubjects = getNumberOfSubjects(scanner);
        String[] subjectNames = getSubjectNames(scanner, numSubjects);
        int[] marks = getMarks(scanner, subjectNames);

        int totalMarks = calculateTotal(marks);
        double averagePercentage = calculateAveragePercentage(totalMarks, numSubjects);
        char grade = calculateGrade(averagePercentage);
        int highestMark = findHighest(marks);
        int lowestMark = findLowest(marks);

        printReport(subjectNames, marks, totalMarks, averagePercentage, grade, highestMark, lowestMark);

        scanner.close();
    }

    private static int getNumberOfSubjects(Scanner scanner) {
        int numSubjects;
        do {
            System.out.print("Enter the number of subjects: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter a positive integer for number of subjects: ");
                scanner.next();
            }
            numSubjects = scanner.nextInt();
        } while (numSubjects <= 0);
        return numSubjects;
    }

    private static String[] getSubjectNames(Scanner scanner, int numSubjects) {
        String[] names = new String[numSubjects];
        scanner.nextLine(); // consume leftover newline
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter name of subject " + (i + 1) + ": ");
            names[i] = scanner.nextLine().trim();
            if (names[i].isEmpty()) {
                names[i] = "Subject " + (i + 1);
            }
        }
        return names;
    }

    private static int[] getMarks(Scanner scanner, String[] subjectNames) {
        int[] marks = new int[subjectNames.length];
        for (int i = 0; i < subjectNames.length; i++) {
            marks[i] = getValidMark(scanner, subjectNames[i]);
        }
        return marks;
    }

    private static int getValidMark(Scanner scanner, String subjectName) {
        int mark;
        do {
            System.out.print("Enter marks obtained in " + subjectName + " (0-100): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter an integer between 0 and 100: ");
                scanner.next();
            }
            mark = scanner.nextInt();
        } while (mark < 0 || mark > 100);
        return mark;
    }

    private static int calculateTotal(int[] marks) {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }

    private static double calculateAveragePercentage(int totalMarks, int numSubjects) {
        return (double) totalMarks / (numSubjects * 100) * 100;
    }

    private static char calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return 'A';
        } else if (averagePercentage >= 80) {
            return 'B';
        } else if (averagePercentage >= 70) {
            return 'C';
        } else if (averagePercentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    private static int findHighest(int[] marks) {
        int highest = marks[0];
        for (int mark : marks) {
            if (mark > highest) {
                highest = mark;
            }
        }
        return highest;
    }

    private static int findLowest(int[] marks) {
        int lowest = marks[0];
        for (int mark : marks) {
            if (mark < lowest) {
                lowest = mark;
            }
        }
        return lowest;
    }

    private static void printReport(String[] subjectNames, int[] marks, int totalMarks, double averagePercentage, char grade, int highestMark, int lowestMark) {
        System.out.println("\n----- Grade Report -----");
        for (int i = 0; i < subjectNames.length; i++) {
            double subjectPercentage = (double) marks[i];
            System.out.printf("%-15s: %3d / 100 (%.2f%%)%n", subjectNames[i], marks[i], subjectPercentage);
        }
        System.out.println("----------------------------");
        System.out.println("Total Marks       : " + totalMarks + " / " + (subjectNames.length * 100));
        System.out.printf("Average Percentage: %.2f%%%n", averagePercentage);
        System.out.println("Grade             : " + grade);
        System.out.println("Highest Mark      : " + highestMark);
        System.out.println("Lowest Mark       : " + lowestMark);
    }
}
