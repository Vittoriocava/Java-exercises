import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread implements Runnable {
   private static final Logger logger = Logger.getLogger(GameThread.class.getName());
   private static final int nitems = 90;
   private static final int nextractions = 30;
   private boolean finished;
   private boolean interrupted;
   private final PrintWriter pw;
   private final Random rng;

   public GameThread(PrintWriter pw) {
      this.pw = pw;
      this.rng = new Random();
   }

   public GameThread(PrintWriter pw, long seed) {
      this.pw = pw;
      this.rng = new Random(seed);
   }

   public void run() {
      try {
         List<Integer> bag = new LinkedList();

         int i;
         for(i = 1; i <= 90; ++i) {
            bag.add(i);
         }

         for(i = 0; i < 30 && !this.finished; ++i) {
            TimeUnit.MILLISECONDS.sleep(1000L);
            if (Thread.currentThread().isInterrupted()) {
               throw new InterruptedException();
            }

            if (this.finished) {
               break;
            }

            int pick = (Integer)bag.remove(this.rng.nextInt(bag.size()));
            this.pw.println(String.format("%d", pick));
            this.pw.flush();
            logger.log(Level.INFO, String.format("Sent number: %d (%d still to extract)", pick, 30 - i - 1));
         }

         this.finished = true;
      } catch (InterruptedException var7) {
         logger.log(Level.WARNING, "Sender thread interrupted", var7);
         this.stop();
      } finally {
         this.pw.println("+");
         this.pw.flush();
      }

   }

   public void stop() {
      if (!this.finished) {
         this.finished = true;
         this.interrupted = true;
      }

   }
}
