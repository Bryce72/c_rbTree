import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVHandler {
    public static void exporttoCSV(List<Long> list, String filePath) throws IOException
    {
        List<String> linestowrite = new ArrayList<>();
        for(Long item : list)
        {
            linestowrite.add(item.toString());
        }
        Files.write(Path.of(filePath), linestowrite);
    }
}
