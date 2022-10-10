// Name of Student: Vindhyaa Saravanan
// Student ID: 201542641
// Email: sc21vs@leeds.ac.uk

package comp1721.cwk1;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

// This class implements constructors and methods that make a game, including
// methods to play and save the game.
// ADDITIONAL ADVANCED SOLUTION INCLUDED:
// HISTORY AND SUMMARY STATISTICS
// Displayed after each game, with relevant information saved in history.txt.

public class Game {

    // Private variables
    private int gameNumber;
    private int guess1;
    private int guess2;
    private int guess3;
    private int guess4;
    private int guess5;
    private int guess6;

    private int gameCount;
    private int winCount;
    private int loseCount;
    private int winStreakCount;
    private int maxWinStreak = 0;

    private String target;
    private static String fileContent = "";
    int guessNum = 1;
    int guessedSuccessfully = 0;

    // Constructor that initializes game number based on no of days since 19 June 2021
    public Game(String filename) throws IOException {

        // Create new wordlist object
        WordList myWordList = new WordList(filename);

        // Find game number using date
        // Start Date 19 June 2021
        LocalDate dateBefore = LocalDate.of(2021, Month.JUNE, 19);
        // End Date is now
        LocalDate dateAfter = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

        // Set game number
        gameNumber = (int) daysBetween;

        // Find today's word
        String target = WordList.getWord(gameNumber);
        this.target = target;
    }

    // Constructor that initializes game number with entered number
    public Game(int num, String filename) throws IOException {
        // Initializing Game number
        gameNumber = num;

        // Create new wordlist object
        WordList myWordList = new WordList(filename);
        String target = WordList.getWord(gameNumber);
        this.target = target;
    }

    // Main gameplay function
    public void play() {

        // Printing necessary parts as per display output
        System.out.println("WORDLE " + gameNumber);

        // Setting variable to show win or not yet won using matches() method
        boolean winStatus = false;
        int i = 0;

        // GAME PLAY PROCEDURE
        do {
            // DO WHILE LOOP TO CONTINUE GUESSING TILL WINS OR LOSES

            // If player has run out of guesses
            if (guessNum == 7) {
                // Print required messages
                System.out.println("Nope - Better luck next time!");
                System.out.println("Answer: " + target);
                gameCount++;
                break;
            }

            // Create new Guess and pull in variables
            Guess myGuess = new Guess(guessNum);
            String chosenword = myGuess.getChosenWord().toUpperCase();
            int guessNumber = myGuess.getGuessNumber();

            // If user does not enter word, prompt
            if (chosenword.equals("")) {
                myGuess.readFromPlayer();
            }

            // Now that guess word has been sought, process it
            String guessResult = myGuess.compareWith(target);
            System.out.println(guessResult);
            fileContent = fileContent + guessResult + "\n";

            // Check whether game is won and must be stopped
            winStatus = myGuess.matches(target);

            // Print appropriate message if they win as per no of guesses used
            if (winStatus && guessNum == 1) {
                System.out.println("Superb - Got it in one!");
                guessedSuccessfully = 1;
                // Break the do while loop
                break;
            }
            else if (winStatus && guessNum > 1 && guessNum < 6) {
                System.out.println("Well done!");
                guessedSuccessfully = 1;
                // Break the do while loop
                break;
            }
            else if (winStatus && guessNum == 6) {
                System.out.println("That was a close call!");
                guessedSuccessfully = 1;
                // Break the do while loop
                break;
            }
            // If user failed to guess increment number of guess and start loop again
            guessNum++;

        } while (!winStatus || guessNum < 7);
    }

    // Function to create bars of stars for histogram in advanced solution
    private String convertToStars(int num) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < num; j++) {
            builder.append('*');
        }
        return builder.toString();
    }

    // Function to write game summary to text file
    public void save(String filename) throws IOException {

        // FOR PRINTING SUMMARY OF THIS PARTICULAR GAME TO LASTGAME.TXT
        // Create new filewriter/printwriter for writing lines to file
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // Write summary of game
        printWriter.print(fileContent);

        // Print summary to terminal as well
        System.out.println(fileContent);

        // Close Print writer
        printWriter.close();


        // ADVANCED SOLUTION IMPLEMENTATION CODE

        // Initialize PrintWriter
        FileWriter fileWriter1 = new FileWriter("history.txt",true);
        PrintWriter printWriter1 = new PrintWriter(fileWriter1);

        String guessSuccessful = "False";
        if (guessedSuccessfully == 1) {
            guessSuccessful = "True";
        }
        else {
            guessSuccessful = "False";
        }

        // Write summary of game to history.txt
        printWriter1.println("Game Number: " + gameNumber);
        printWriter1.println("Target word: " + target);
        printWriter1.println("Was target word guessed successfully?:  " + guessSuccessful);
        printWriter1.println("Number of guesses made:  " + guessNum);
        printWriter1.println("");

        printWriter1.close();

        // To read in statistics to prepare summary
        File myHistory = new File("history.txt");
        Scanner myReader = new Scanner(myHistory);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();

            // To parse data from history file and prepare summary
            // Format of data:
            //Game Number: (Game Number)
            //Target word: (String target)
            //Was target word guessed successfully?: (True/False)
            //Number of guesses made:  (Number of guesses taken)

            if (data.contains("Game Number: ")) {
                gameCount++;
            }
            if (data.contains("Was target word guessed successfully?:  True")) {
                winCount++;
                if (winStreakCount == 0) {
                    winStreakCount = 1;
                }
                else {
                    winStreakCount++;
                }
                if (maxWinStreak < winStreakCount) {
                    maxWinStreak = winStreakCount;
                }
            }
            if (data.contains("Was target word guessed successfully?:  False")) {
                if (maxWinStreak < winStreakCount) {
                    maxWinStreak = winStreakCount;
                }
                winStreakCount = 0;
            }
            else if (data.contains("Number of guesses made:  ")) {
                char[] dataArray = data.toCharArray();
                int k;
                for (k = 0; k < data.length(); k++) {
                    if (Character.isDigit(dataArray[k])) {
                        String numString = String.valueOf(dataArray[k]);
                        int guesses = Integer.parseInt(numString);

                        if (guesses == 1) {
                            guess1++;
                        }
                        else if (guesses == 2) {
                            guess2++;
                        }
                        else if (guesses == 3) {
                            guess3++;
                        }
                        else if (guesses == 4) {
                            guess4++;
                        }
                        else if (guesses == 5) {
                            guess5++;
                        }
                        else if (guesses == 6) {
                            guess6++;
                        }
                    }
                }
            }
        }

        // History and Summary Statistics
        System.out.println("HISTORY AND SUMMARY STATISTICS");
        System.out.println("");
        System.out.println("Number of games played:             " + gameCount);
        System.out.println("Number of games won:                " + winCount);
        System.out.println("Number of games lost:               " + (gameCount - winCount));
        System.out.println("Percentage of games that were wins: " + (winCount * 100/gameCount) + "%");
        System.out.println("Length of current winning streak:   " + winStreakCount);
        System.out.println("Longest winning streak:             " + maxWinStreak);
        System.out.println("");

        // Code to format and print Histogram
        System.out.println("Histogram of guess distribution:" );
        System.out.println("User won in 1 guess:      " + convertToStars(guess1));
        System.out.println("User won in 2 guesses:    " + convertToStars(guess2));
        System.out.println("User won in 3 guesses:    " + convertToStars(guess3));
        System.out.println("User won in 4 guesses:    " + convertToStars(guess4));
        System.out.println("User won in 5 guesses:    " + convertToStars(guess5));
        System.out.println("User won in 6 guesses:    " + convertToStars(guess6));
        System.out.println("");

    }

}
