import java.util.Deque;
import java.util.concurrent.Semaphore;

public class SitcomActorThread extends Thread {
    final String name;
    final Semaphore sem;
    final Deque<String> script;

   public SitcomActorThread(String name, Semaphore sem, Deque<String> script) {
       this.name = name;
       this.sem = sem;
       this.script = script;
   }

   public void run() {
       while (true) {
           try {
               sem.acquire();

               say();
           } catch(Exception ex) {
               System.out.println("Error in thread " + name);
               ex.printStackTrace();
           }
       }
   }

   private synchronized void say() {
       synchronized (script) {
           System.out.println(name + ": " + script.removeFirst());

           sem.drainPermits();
           script.notify();
       }

   }
}
