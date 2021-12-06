package InvictaParser;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;

public class TextToCsvInvictaParser extends InvictaParser {

    private static final String CSV_FIRST_LINE = "Sentence , Word 1, Word 2,Word 3, Word 4, Word 5, Word 6, Word 7, Word 8";
    private static final String SENTENCE_START = "Sentence ";

    private BufferedReader bufferedReader;
    private Scanner scanner;
    private Writer writer;

    public TextToCsvInvictaParser(Reader reader, Writer writer) {
        bufferedReader = new BufferedReader(reader);
        this.writer = writer;
        scanner = createScannerWithSentenceDelimiter(bufferedReader);
    }

    public void startParsing() {
        addFirsLineToCsv();
        scanInputToCsv();
        closeBufferReader(bufferedReader);
        closeWriter(writer);
    }

    private void scanInputToCsv() {
        String sentence;
        int sentenceNumber = 0;
        while (scanner.hasNext()) {
            sentence = scanner.next();
            String[] sentenceSplitedForWordsInSortedArray = splitSentenceToWordsInSortedArray(sentence);
            sentenceNumber++;
            addSentenceStertWithNumberToCsv(sentenceNumber);
            addWordsToSentenceInCsv(sentenceSplitedForWordsInSortedArray);
        }
    }

    private void addWordsToSentenceInCsv(String[] sentenceSplitedForWordsInSortedArray) {
        for (String word : sentenceSplitedForWordsInSortedArray) {
            if (!word.isBlank()) {
                addCommaWithWordToCsv(word);
            }
        }
    }

    private void addCommaWithWordToCsv(String word) {
        try {
            writer.append(", " + word);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }

    private void addSentenceStertWithNumberToCsv(int sentenceNumber) {
        try {
            writer.append(System.lineSeparator());
            writer.append(SENTENCE_START + sentenceNumber);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }

    private void addFirsLineToCsv() {
        try {
            writer.append(CSV_FIRST_LINE);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }

    private void closeWriter(Writer writer) {
        try {
            writer.close();
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }
}