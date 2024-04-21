import java.util.Deque;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class SitcomDirectorThread extends Thread {
    final Deque<String> script;
    final Deque<Actor> order;
    Map<Actor, Semaphore> semaphoreMap;

    SitcomDirectorThread(Deque<String> script, Deque<Actor> order, Map<Actor, Semaphore> semaphoreMap) {
        this.script = script;
        this.order = order;
        this.semaphoreMap = semaphoreMap;
    }

    public void run() {
        while (!order.isEmpty()) {
                pushActor();
        }
    }

    private synchronized void pushActor() {
        Actor current;

        synchronized (script) {
            current = order.removeFirst();

            semaphoreMap.get(current).release(1);

            try {
                script.wait();
            } catch (Exception ex) {
                System.out.println("Error in director");
                ex.printStackTrace();
            }

            semaphoreMap.get(current).drainPermits();
        }
    }
}

