import java.util.*;

public class NumberGame {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    // Leaderboard: store attempts and player number
    private static final List<Integer> leaderboard = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("🎯 Welcome to the Number Guessing Game!");

        int totalScore = 0, gamesPlayed = 0, bestScore = Integer.MAX_VALUE;
        boolean playAgain = true;

        while (playAgain) {
            int maxAttempts = chooseDifficulty();
            if (maxAttempts == 0) { // Exit option chosen
                break;
            }

            int maxRange = chooseRange();

            long startTime = System.currentTimeMillis();
            int attemptsUsed = playRound(maxAttempts, maxRange);
            long timeTaken = System.currentTimeMillis() - startTime;

            gamesPlayed++;
            if (attemptsUsed > 0) {  // player guessed correctly
                int score = calculateScore(maxAttempts, attemptsUsed);
                totalScore += score;
                bestScore = Math.min(bestScore, attemptsUsed);
                updateLeaderboard(attemptsUsed);
                System.out.println("⏱ Time taken: " + (timeTaken / 1000.0) + " seconds");
                System.out.println("🏆 You earned " + score + " points this round!");
            }

            playAgain = askPlayAgain();
        }

        printSummary(gamesPlayed, totalScore, bestScore);
        printLeaderboard();

        System.out.println("Thanks for playing! 👋");
    }

    private static int chooseDifficulty() {
        while (true) {
            System.out.println("\nChoose difficulty:");
            System.out.println("1. Easy (10 attempts)");
            System.out.println("2. Medium (7 attempts)");
            System.out.println("3. Hard (5 attempts)");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = getValidInteger();
            switch (choice) {
                case 1: return 10;
                case 2: return 7;
                case 3: return 5;
                case 4: return 0; // Exit
                default:
                    System.out.println("⚠ Invalid choice! Please select 1-4.");
            }
        }
    }

    private static int chooseRange() {
        System.out.println("\nChoose number range:");
        System.out.println("1. 1 to 50");
        System.out.println("2. 1 to 100");
        System.out.println("3. 1 to 200");
        System.out.print("Enter choice: ");

        int choice = getValidInteger();
        switch (choice) {
            case 1: return 50;
            case 3: return 200;
            default: return 100;
        }
    }

    private static int playRound(int maxAttempts, int maxRange) {
        int targetNumber = random.nextInt(maxRange) + 1;
        int attempts = 0;

        System.out.println("\nI have selected a number between 1 and " + maxRange + ". Can you guess it?");
        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int userGuess = getValidInteger();
            if (userGuess < 1 || userGuess > maxRange) {
                System.out.println("⚠ Please guess a number within the range 1 to " + maxRange + ".");
                continue;
            }
            attempts++;

            if (userGuess == targetNumber) {
                System.out.println("🎉 Congratulations! You guessed the number " + targetNumber + " in " + attempts + " attempts.");
                return attempts; // success
            } else if (userGuess < targetNumber) {
                System.out.println("Too low! " + giveHint(userGuess, targetNumber));
            } else {
                System.out.println("Too high! " + giveHint(userGuess, targetNumber));
            }
        }

        System.out.println("❌ Sorry, you've run out of attempts. The correct number was " + targetNumber + ".");
        return -1; // failure
    }

    private static String giveHint(int guess, int target) {
        int diff = Math.abs(guess - target);
        if (diff > 20) return "You're way off!";
        else if (diff > 10) return "You're getting closer!";
        else return "Very close!";
    }

    private static int getValidInteger() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("⚠ Invalid input! Please enter a number: ");
            }
        }
    }

    private static boolean askPlayAgain() {
        while (true) {
            System.out.print("Do you want to play again? (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) return true;
            else if (input.equals("no") || input.equals("n")) return false;
            else System.out.println("⚠ Please answer 'yes' or 'no'.");
        }
    }

    private static int calculateScore(int maxAttempts, int attemptsUsed) {
        // Score multiplier based on difficulty
        int baseScore = 10;
        double multiplier = 1.0;
        if (maxAttempts == 10) multiplier = 1.0;      // Easy
        else if (maxAttempts == 7) multiplier = 1.5;  // Medium
        else if (maxAttempts == 5) multiplier = 2.0;  // Hard

        // More points for fewer attempts
        int score = (int) (baseScore * multiplier * ((double)(maxAttempts - attemptsUsed + 1) / maxAttempts));
        return Math.max(score, 1); // at least 1 point
    }

    private static void updateLeaderboard(int attempts) {
        leaderboard.add(attempts);
        Collections.sort(leaderboard);
        if (leaderboard.size() > 3) {
            leaderboard.remove(leaderboard.size() - 1); // keep top 3 only
        }
    }

    private static void printSummary(int gamesPlayed, int totalScore, int bestScore) {
        System.out.println("\n🎮 Game Over!");
        System.out.println("Total games played: " + gamesPlayed);
        System.out.println("Your total score: " + totalScore);
        if (bestScore != Integer.MAX_VALUE) {
            System.out.println("Your best performance: guessed in " + bestScore + " attempt(s).");
        }
    }

    private static void printLeaderboard() {
        System.out.println("\n🏅 Leaderboard (Top 3 best attempts):");
        if (leaderboard.isEmpty()) {
            System.out.println("No scores yet.");
        } else {
            for (int i = 0; i < leaderboard.size(); i++) {
                System.out.println((i + 1) + ". " + leaderboard.get(i) + " attempt(s)");
            }
        }
    }
}
