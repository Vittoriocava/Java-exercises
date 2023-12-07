import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Observable implements Observer, Runnable {
   private static final Logger logger = Logger.getLogger(ServerThread.class.getName());
   private ThreadPoolExecutor pool;
   private ServerSocket serverSocket;
   private int port;
   private boolean running;

   public ServerThread(int port) {
      this.port = port;
      this.pool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
   }

   public int getClientCount() {
      return this.pool.getActiveCount();
   }

   public void run() {
      this.running = true;

      try {
         this.serverSocket = new ServerSocket(this.port);
         this.serverSocket.setSoTimeout(1000);
      } catch (IOException var3) {
         logger.log(Level.SEVERE, "I/O error opening server socket, exiting...", var3);
         this.running = false;
         return;
      }

      logger.info("Server listening on port " + this.serverSocket.getLocalPort());

      while(this.running) {
         try {
            Socket sock = this.serverSocket.accept();
            logger.info(String.format("Connection established: %s", sock.getRemoteSocketAddress()));
            ClientThread clientThread = new ClientThread(sock);
            clientThread.addObserver(this);
            this.pool.execute(clientThread);
            this.setChanged();
            this.notifyObservers(sock);
         } catch (SocketTimeoutException var4) {
            if (Thread.currentThread().isInterrupted()) {
               this.stop();
               this.setChanged();
               this.notifyObservers();
            }
         } catch (IOException var5) {
            if (this.running) {
               logger.log(Level.WARNING, "I/O error when handling client connection request", var5);
            }
         }
      }

   }

   public void stop() {
      if (this.running) {
         try {
            logger.info("Shutting down server...");
            this.running = false;
            this.serverSocket.close();
            this.pool.shutdown();
            if (!this.pool.awaitTermination(100L, TimeUnit.MILLISECONDS)) {
               this.pool.shutdownNow();
               if (!this.pool.awaitTermination(100L, TimeUnit.MILLISECONDS)) {
                  logger.severe("Pool did not terminate");
               }
            }

            logger.info("Server shutdown complete");
         } catch (InterruptedException | IOException var2) {
            logger.log(Level.WARNING, "Server interrupted while shutting down", var2);
            this.pool.shutdownNow();
            Thread.currentThread().interrupt();
         }

      }
   }

   public void update(Observable o, Object arg) {
      if (o instanceof ClientThread) {
         this.setChanged();
         this.notifyObservers();
      }

   }
}
