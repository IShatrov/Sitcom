import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Deque<String> script = new ArrayDeque<>();
        Deque<Actor> order = new ArrayDeque<>();

        script.addLast("Hello");
        order.addLast(Actor.MONICA);
        script.addLast("How are you?");
        order.addLast(Actor.MONICA);
        script.addLast("Fine, uu?");
        order.addLast(Actor.CHANDLER);

        Map<Actor, Semaphore> semaphoreMap = new HashMap<>();

        for (Actor actor : Actor.values()) {
            Semaphore sem = new Semaphore(0);
            semaphoreMap.put(actor, sem);
            new Thread(new SitcomActorThread(actor.name, sem, script)).start();
        }

        new Thread(new SitcomDirectorThread(script, order, semaphoreMap)).start();
    }
}
