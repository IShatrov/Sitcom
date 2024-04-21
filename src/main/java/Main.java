import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Deque<String> script = new ArrayDeque<>();
        Deque<Actor> order = new ArrayDeque<>();
        Map<Actor, Integer> count = new HashMap<>();

        try {
            SitcomReader.readSitcomScript("script.txt", script, order, count);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Map<Actor, Semaphore> semaphoreMap = new HashMap<>();

        for (Actor actor : Actor.values()) {
            Semaphore sem = new Semaphore(0);
            semaphoreMap.put(actor, sem);
            new Thread(new SitcomActorThread(actor.name, sem, script, count.getOrDefault(actor, 0))).start();
        }

        new Thread(new SitcomDirectorThread(script, order, semaphoreMap)).start();
    }
}
