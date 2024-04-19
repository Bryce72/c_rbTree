import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Driver {
    public static void main(String[] args) throws IOException {

        String outputFilePath = "TIME_TEST1.csv";  // will need to manually change to cater to file.

        int[] steps = {10,100,1000,10000};


        List<Long> listoftime = Time.timing(steps);

        CSVHandler.exporttoCSV(listoftime, outputFilePath);

        System.out.println("Time has been recorded to file: " + outputFilePath);

    }
}