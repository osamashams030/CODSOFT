import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int range = upperBound - lowerBound + 1;
        int randomNumber, userGuess, attempts, rounds = 0, score = 0;

        boolean playAgain = true;

        while (playAgain) {
            // Generate a random number within the specified range
            randomNumber = random.nextInt(range) + lowerBound;

            System.out.println("Welcome to the Number Guessing Game!");
            System.out.println("Guess a number between " + lowerBound + " and " + upperBound);

            attempts = 0;
            boolean guessedCorrectly = false;

            while (!guessedCorrectly) {
                System.out.print("Enter your guess: ");
                userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == randomNumber) {
                    System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                    guessedCorrectly = true;
                    score += attempts;
                } else if (userGuess < randomNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }

                // Check if the maximum attempts have been reached
                if (attempts == 3) {
                    System.out.println("Sorry, you've reached the maximum number of attempts. The correct number was: " + randomNumber);
                    break;
                }
            }

            rounds++;
            
            // Ask if the user wants to play again
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainResponse = scanner.next().toLowerCase();

            if (playAgainResponse.equals("yes")) {
                playAgain = true;
            }
            else{
                playAgain= false;
            }
        }

        System.out.println("Thank you for playing! Your total score is: " + score);
        System.out.println("Total rounds played: " + rounds);

        // Close the scanner to prevent resource leak
        scanner.close();
    }
}

