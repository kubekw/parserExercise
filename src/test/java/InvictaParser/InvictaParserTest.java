package InvictaParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvictaParserTest {
    private FileReader fileReader;
    private StringWriter stringWriter;
    private String expectedXml;
    private String expectedCsv;

    public InvictaParserTest() throws IOException {
        expectedXml = new String(Files.readAllBytes(Paths.get("expectedXml.xml")), StandardCharsets.UTF_8);
        expectedCsv = new String(Files.readAllBytes(Paths.get("expectedCsv.csv")), StandardCharsets.UTF_8);
    }

    @Test
    public void testXmlParsing() throws FileNotFoundException {
        stringWriter = new StringWriter();
        stringWriter.getBuffer();
        fileReader = new FileReader("testInputText.txt");
        TextToXmlInvictaParser textToXmlInvictaParser = new TextToXmlInvictaParser(fileReader, stringWriter);
        textToXmlInvictaParser.startParsing();
        String parsingResult = stringWriter.toString();
        assertEquals(expectedXml, parsingResult);
    }

    @Test
    public void testXmlParsingWithWithespaces() throws FileNotFoundException {
        stringWriter = new StringWriter();
        stringWriter.getBuffer();
        fileReader = new FileReader("testInputTextWithWhitespaces.txt");
        TextToXmlInvictaParser textToXmlInvictaParser = new TextToXmlInvictaParser(fileReader, stringWriter);
        textToXmlInvictaParser.startParsing();
        String parsingResult = stringWriter.toString();
        assertEquals(expectedXml, parsingResult);
    }

    @Test
    public void testCsvParsing() throws FileNotFoundException {
        stringWriter = new StringWriter();
        stringWriter.getBuffer();
        fileReader = new FileReader("testInputText.txt");
        TextToCsvInvictaParser textToCsvInvictaParser = new TextToCsvInvictaParser(fileReader, stringWriter);
        textToCsvInvictaParser.startParsing();
        String parsingResult = stringWriter.toString();
        assertEquals(expectedCsv, parsingResult);
    }

    @Test
    public void testCsvParsingWithWhitespaces() throws FileNotFoundException {
        stringWriter = new StringWriter();
        stringWriter.getBuffer();
        fileReader = new FileReader("testInputTextWithWhitespaces.txt");
        TextToCsvInvictaParser textToCsvInvictaParser = new TextToCsvInvictaParser(fileReader, stringWriter);
        textToCsvInvictaParser.startParsing();
        String parsingResult = stringWriter.toString();
        assertEquals(expectedCsv, parsingResult);
    }
}