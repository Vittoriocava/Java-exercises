import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
   private static final String logFormat = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s [%2$s] %5$s%6$s%n";

   private static void runServerCLI(int port) {
      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.execute(new ServerThread(port));
   }

   private static void runServerGUI() {
      new ServerFrame();
   }

   public static void main(String[] args) {
      System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s [%2$s] %5$s%6$s%n");
      boolean useGui = true;
      int port = 4400;
      String[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String arg = var3[var5];
         if (arg.equalsIgnoreCase("-nogui")) {
            useGui = false;
         } else {
            try {
               port = Integer.parseInt(arg);
            } catch (NumberFormatException var8) {
               System.err.println("Invalid port number: " + arg);
               System.exit(1);
            }
         }
      }

      if (useGui) {
         runServerGUI();
      } else {
         runServerCLI(port);
      }

   }
}
