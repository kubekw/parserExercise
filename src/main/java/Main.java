import InvictaParser.TextToCsvInvictaParser;
import InvictaParser.TextToXmlInvictaParser;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length<2){
            System.out.println("you did not provide the correct arguments. Give the input file path as the first argument" +
                    " and the destination file path as the second argument. e.g. java Main input.txt myFileName \n" +
                    "try again ...");
            return;
        }
        else {
            FileReader fileReader = new FileReader(args[0]);
            FileWriter fileWriter = new FileWriter(args[1] + "xml");

            TextToXmlInvictaParser textToXml = new TextToXmlInvictaParser(fileReader, fileWriter);
            textToXml.startParsing();

            FileReader fileReaderCsv = new FileReader(args[0]);
            FileWriter fileWriterCsv = new FileWriter(args[1] + "csv");

            TextToCsvInvictaParser textToCsv = new TextToCsvInvictaParser(fileReaderCsv, fileWriterCsv);
            textToCsv.startParsing();
        }

    }
}
