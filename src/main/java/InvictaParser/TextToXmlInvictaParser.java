package InvictaParser;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;

public class TextToXmlInvictaParser extends InvictaParser {

    private BufferedReader inputBufferReader;
    private StreamResult outputStreamResult;
    private TransformerHandler transformerHandler;
    private Scanner scanner;

    public TextToXmlInvictaParser(Reader reader, Writer writer) {
        this.inputBufferReader = new BufferedReader(reader);
        this.outputStreamResult = new StreamResult(writer);
        scanner = createScannerWithSentenceDelimiter(inputBufferReader);
    }

    // Recommended for large amounts of data.
    public TextToXmlInvictaParser(Reader reader, StreamResult streamResult) {
        this.inputBufferReader = new BufferedReader(reader);
        this.outputStreamResult = streamResult;
        scanner = createScannerWithSentenceDelimiter(inputBufferReader);
    }

    public void startParsing() {
        setUpXmlPropety();
        startXml();
        startTextInXml();
        scanInputToXml();
        closeTextInXml();
        closeXml();
    }

    private void scanInputToXml() {
        String sentence;
        while (scanner.hasNext()) {
            sentence = scanner.next();
            String[] sentenceSplitedForWordsInSortedArray = splitSentenceToWordsInSortedArray(sentence);
            addSentenceToXml(sentenceSplitedForWordsInSortedArray);
        }
    }

    private void setUpXmlPropety() {
        try {
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            transformerHandler = tf.newTransformerHandler();
            Transformer serializer = transformerHandler.getTransformer();
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformerHandler.setResult(outputStreamResult);
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }

    private void startXml() {
        try {
            transformerHandler.startDocument();
        } catch (SAXException exception) {
            exception.printStackTrace();
        }
    }

    private void startTextInXml() {
        try {
            transformerHandler.startElement(null, null, "text", null);
        } catch (SAXException exception) {
            exception.printStackTrace();
        }
    }

    private void addSentenceToXml(String[] sentenceSplitForWordsInArray) {
        try {
            openSentenceInXml();
            addWordsToSentenceInXml(sentenceSplitForWordsInArray);
            closeSentenceInXml();
        } catch (SAXException exception) {
            exception.printStackTrace();
        }
    }

    private void addWordsToSentenceInXml(String[] sentenceSplitForWordsInArray) throws SAXException {
        for (String word : sentenceSplitForWordsInArray) {
            if (!word.isBlank()) {
                addWordToSentenceInXml(word);
            }
        }
    }

    private void openSentenceInXml() throws SAXException {
        transformerHandler.startElement(null, null, "sentence", null);
    }

    private void addWordToSentenceInXml(String word) throws SAXException {
        transformerHandler.startElement(null, null, "word", null);
        transformerHandler.characters(word.toCharArray(), 0, word.length());
        transformerHandler.endElement(null, null, "word");
    }

    private void closeSentenceInXml() throws SAXException {
        transformerHandler.endElement(null, null, "sentence");
    }

    private void closeTextInXml() {
        try {
            transformerHandler.endElement(null, null, "text");
        } catch (SAXException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }

    private void closeXml() {
        try {
            transformerHandler.endDocument();
        } catch (SAXException exception) {
            LOGGER.log(Level.SEVERE, exception.toString(), exception);
            exception.printStackTrace();
        }
    }
}