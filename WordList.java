// Name of Student: Vindhyaa Saravanan
// Student ID: 201542641
// Email: sc21vs@leeds.ac.uk

package comp1721.cwk1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

// This class reads list of words from given data file and retrieves target word
// for this particular game.

public class WordList {

    // Initializing required variables
    private static List<String> words;

    // Constructor that reads file and makes list of words
    public WordList(String filename) throws IOException {

        // Get path of desired file to be read
        Path path = Paths.get(filename);
        // Read all lines of file as strings and store in list of strings called word
        words = Files.readAllLines(path);

    }

    // Function to find size of word list
    public static int size() {
        // Save size of list word
        int size = words.size();
        return size;
    }

    // Function to retrieve word from words.txt
    public static String getWord(int n) {

        // Error checking to see if asking for valid word
        // If word not valid throw Game Exception
        if (n < 0 || n > (words.size() - 1)) {
            throw new GameException("Word not available for this game number");
        }

        // If valid return word
        return words.get(n);
    }
}
