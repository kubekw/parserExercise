package InvictaParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class InvictaParser {

    protected static final String SENTENCE_DELIMITER = "(?<=[.?!;])\\s+(?=\\p{Lu})";
    protected static final String NOT_WORDS_AND_NOT_DIGITS_TO_REMOVE_FROM_SENTENCES = "([^A-Za-z0-9']|\\B'|'\\B)";
    protected static final Comparator<String> WORDS_SORTING_ORDER = String.CASE_INSENSITIVE_ORDER;
    protected static final Logger LOGGER = Logger.getLogger(InvictaParser.class.getName());

    protected Scanner createScannerWithSentenceDelimiter(BufferedReader bufferedReader) {
        Scanner scanner = new Scanner(bufferedReader);
        scanner.useDelimiter(SENTENCE_DELIMITER);
        return scanner;
    }

    protected String[] splitSentenceToWordsInSortedArray(String sentence) {
        sentence = removeNotWordsAndNotDigitsFromString(sentence);
        String[] sentenceSplitedForWordsInSortedArray = sentence.split(" ");
        sortWordsInArrayInSetupOrder(sentenceSplitedForWordsInSortedArray);
        return sentenceSplitedForWordsInSortedArray;
    }

    private String removeNotWordsAndNotDigitsFromString(String sentence) {
        sentence = sentence.replaceAll(NOT_WORDS_AND_NOT_DIGITS_TO_REMOVE_FROM_SENTENCES, " ");
        return sentence;
    }

    private void sortWordsInArrayInSetupOrder(String[] sentenceSplitForWordsInArray) {
        Arrays.sort(sentenceSplitForWordsInArray, WORDS_SORTING_ORDER);
    }

    protected void closeBufferReader(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }
}
