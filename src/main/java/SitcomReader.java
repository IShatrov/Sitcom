import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.Map;
import java.util.Scanner;

public class SitcomReader {
    private static final String SEP = ":";

    public static void readSitcomScript(String filename, Deque<String> script, Deque<Actor> order, Map<Actor, Integer> count)
            throws FileNotFoundException, IllegalArgumentException {
        File file = new File(filename);

        Scanner scn = new Scanner(file);

        while (scn.hasNext()) {
            String line = scn.nextLine();

            int sepIndex = line.indexOf(SEP);

            if (sepIndex == -1) {
                throw new IllegalArgumentException("Line missing separator");
            }

            Actor actor = null;
            for (Actor possibleActor : Actor.values()) {
                if (line.substring(0, sepIndex).equals(possibleActor.name)) {
                    actor = possibleActor;
                    break;
                }
            }

            int currentCount = count.getOrDefault(actor, 0);
            count.put(actor, currentCount + 1);

            order.addLast(actor);
            script.addLast(line.substring(sepIndex + 1));
        }
    }
}
