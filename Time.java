
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Time {


    public static List<Long> timing(int[] steps)
    {
        /// code you need for setup, don't recall while typing
        long start;
        long end;
        List<Long> timekept = new ArrayList<>();
        for (int i = 0; i < steps.length; i++)
        {
            start = System.currentTimeMillis();
            RedBlackTree tree = new RedBlackTree();
            for (int j = 0; j < steps[i]; j++)
            {
                tree.insertNode(j);
            }
            timekept.add(System.currentTimeMillis() - start);
        }
        return timekept;
    }
}

