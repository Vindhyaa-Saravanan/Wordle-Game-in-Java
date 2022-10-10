// Name of Student: Vindhyaa Saravanan
// Student ID: 201542641
// Email: sc21vs@leeds.ac.uk

package comp1721.cwk1;

import java.util.Scanner;

// This class initializes and contains methods that work on a Guess object,
// including functions that compare guess with answer and produce interpretable
// results and check if game is won (winning guess).

public class Guess {
  // Use this to get player input in readFromPlayer()
  private static final Scanner INPUT = new Scanner(System.in);

  private int guessNumber = 0;
  private String chosenword = "";

  // Constructor to initialize and validate guess number
  public Guess(int num) {

      // Initialize guess number
      guessNumber = num;
      if (guessNumber < 1 || guessNumber > 6) {
        throw new GameException("Number of guesses not valid!");
      }

  }

  // Constructor to initialize and validate guess number and chosen word
  public Guess(int num, String word) {

      // Initialize guess number
      guessNumber = num;
      if (guessNumber < 1 || guessNumber > 6) {
          throw new GameException("Number of guesses not valid!");
      }

      // Initialize word chosen by user
      chosenword = String.valueOf(word);
      // To find whether it has 5 characters
      int characterCount = chosenword.length();
      // to find whether it has 5 alphabets
      int letterCount = 0;
      char[] array = chosenword.toCharArray();

      // Iterate over array and count number of letters
      int i;
      for (i = 0; i < chosenword.length(); i++)
      {
          if (Character.isLetter(array[i])) {
              letterCount++;
          }
      }

      // Exception checking
      if (characterCount != 5 || letterCount != 5) {
          throw new GameException("Guessed word is not valid!");
      }

      // Set chosen word to all caps for further processing
      chosenword = chosenword.toUpperCase();
  }

  // Getter for guess number
  public int getGuessNumber() {
      return guessNumber;
  }

  // Getter for guess word
  public String getChosenWord() {
      return chosenword;
  }

  // Function to prompt player for guess word
  public void readFromPlayer() {
      boolean guessValid = false;
      do {
          System.out.print("Enter guess (" + guessNumber + "/6): ");
          chosenword = INPUT.next().toUpperCase();
          // To find whether it has 5 characters
          int characterCount = chosenword.length();
          // to find whether it has 5 alphabets
          int letterCount = 0;
          char[] array = chosenword.toCharArray();

          // Iterate over array and count number of letters
          int i;
          for (i = 0; i < chosenword.length(); i++)
          {
              if (Character.isLetter(array[i])) {
                  letterCount++;
              }
          }

          // Exception checking
          if (characterCount != 5 || letterCount != 5) {
              System.out.println("Guessed word is not valid!");
              guessValid = false;
          }
          else {
              guessValid = true;
          }
      }while(!guessValid);
  }

  // Function to compare guess with answer and return visible output
  public String compareWith(String target) {

      // Initialize new output string to store final result
      String output = "";

      // Converting both strings to arrays for easy comparison
      char[] arrayChosen = chosenword.toCharArray();
      char[] arrayTarget = target.toCharArray();
      char[] explored = new char[5];

      // Iterate over each letter of chosenword and decide a fate for it
      int i;
      for (i = 0; i < 5; i++) {

          int characterStatus = 0;

          // Account for the SOUPS thing
          if (explored != null) {
              if (arrayChosen[i] == explored[0]) {
                  characterStatus = 1;
              }
              else if (arrayChosen[i] == explored[1]) {
                  characterStatus = 1;
              }
              else if (arrayChosen[i] == explored[2]) {
                  characterStatus = 1;
              }
              else if (arrayChosen[i] == explored[3]) {
                  characterStatus = 1;
              }
              else if (arrayChosen[i] == explored[4]) {
                  characterStatus = 1;
              }
          }

              // If letter is correct and in correct place
              // Assign GREEN colour coding
              if (arrayChosen[i] == arrayTarget[i]) {
                  output = output + "\033[30;102m " + arrayChosen[i] + " \033[0m";
                  explored[i] = arrayChosen[i];
              }

              // If letter is correct but in wrong place
              // Assign YELLOW colour coding
              else if (arrayChosen[i] == arrayTarget[0] && (i != 0) && (characterStatus != 1)) {
                  output = output + "\033[30;103m " + arrayChosen[i] + " \033[0m";
                  explored[i] = arrayChosen[i];
              }

              else if (arrayChosen[i] == arrayTarget[1] && (i != 1) && (characterStatus != 1)) {
                  output = output + "\033[30;103m " + arrayChosen[i] + " \033[0m";
                  explored[i] = arrayChosen[i];
              }

              else if (arrayChosen[i] == arrayTarget[2] && (i != 2) && (characterStatus != 1)) {
                  output = output + "\033[30;103m " + arrayChosen[i] + " \033[0m";
                  explored[i] = arrayChosen[i];
              }

              else if (arrayChosen[i] == arrayTarget[3] && (i != 3) && (characterStatus != 1)) {
                  output = output + "\033[30;103m " + arrayChosen[i] + " \033[0m";
                  explored[i] = arrayChosen[i];
              }

              else if (arrayChosen[i] == arrayTarget[4] && (i != 4) && (characterStatus != 1)) {
                  output = output + "\033[30;103m " + arrayChosen[i] + " \033[0m";
                  explored[i] = arrayChosen[i];
              }

              // If letter is incorrect
              // Assign WHITE colour coding
              else {
                  output = output + "\033[30;107m " + arrayChosen[i] + " \033[0m";
                  explored[i] = arrayChosen[i];
              }

          }
      // Return final prepared string
      return output;
  }

  // Function to check if current guess matches answer
  public boolean matches(String target) {

      // Converting both strings to arrays for easy comparison
      char[] arrayChosen = chosenword.toCharArray();
      char[] arrayTarget = target.toCharArray();

      // Iterate over both strings and check if all letters are exactly matching
      int i;
      int numMatching = 0;
      for (i = 0; i < 5; i++) {
          if (arrayChosen[i] == arrayTarget[i]) {
              numMatching++;
          }
      }

      int winStatus = 0;

      // If all 5 letters are matching
      if (numMatching == 5) {
          winStatus = 1;
          return true;
      }
      return false;
  }


}
