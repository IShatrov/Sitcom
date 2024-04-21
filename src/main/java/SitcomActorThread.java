import java.util.Deque;
import java.util.concurrent.Semaphore;

public class SitcomActorThread extends Thread {
    final String name;
    final Semaphore sem;
    final Deque<String> script;
    final int toralPhrases;

    int saidPhrases;

   public SitcomActorThread(String name, Semaphore sem, Deque<String> script, int toralPhrases) {
       this.name = name;
       this.sem = sem;
       this.script = script;
       this.toralPhrases = toralPhrases;
       saidPhrases = 0;
   }

   public void run() {
       while (saidPhrases < toralPhrases) {
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
           saidPhrases++;
           System.out.println(name + ": " + script.removeFirst());

           sem.drainPermits();
           script.notify();
       }

   }
}
