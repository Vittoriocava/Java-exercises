import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Observable implements Runnable {
   private static final Logger logger = Logger.getLogger(ClientThread.class.getName());
   private final ExecutorService executor;
   private final Socket sock;
   private GameThread st;
   private boolean running;

   public ClientThread(Socket sock) {
      this.sock = (Socket)Objects.requireNonNull(sock, "Client socket must not be null");
      this.executor = Executors.newSingleThreadExecutor();
   }

   public void run() {
      this.running = true;

      try {
         Scanner in = new Scanner(this.sock.getInputStream());
         Throwable var2 = null;

         try {
            PrintWriter pw = new PrintWriter(this.sock.getOutputStream());
            Throwable var4 = null;

            try {
               while(this.running) {
                  if (Thread.currentThread().isInterrupted()) {
                     throw new InterruptedException();
                  }

                  String cmd = in.nextLine();
                  logger.info("Received: " + cmd.substring(0, Math.min(cmd.length(), 100)));
                  if (cmd.equalsIgnoreCase("start")) {
                     if (this.st != null) {
                        this.st.stop();
                        this.executor.awaitTermination(100L, TimeUnit.MILLISECONDS);
                     }

                     this.st = new GameThread(pw);
                     this.executor.execute(this.st);
                  } else {
                     if (!cmd.equalsIgnoreCase("stop")) {
                        if (cmd.equalsIgnoreCase("disconnect")) {
                           if (this.st != null) {
                              this.st.stop();
                              this.executor.awaitTermination(100L, TimeUnit.MILLISECONDS);
                           }
                        } else {
                           pw.println("error");
                           pw.flush();
                           logger.warning("Client misbehaved, force connection close.");
                        }
                        break;
                     }

                     if (this.st != null) {
                        this.st.stop();
                        this.executor.awaitTermination(100L, TimeUnit.MILLISECONDS);
                     }
                  }
               }

               this.sock.close();
            } catch (Throwable var45) {
               var4 = var45;
               throw var45;
            } finally {
               if (pw != null) {
                  if (var4 != null) {
                     try {
                        pw.close();
                     } catch (Throwable var44) {
                        var4.addSuppressed(var44);
                     }
                  } else {
                     pw.close();
                  }
               }

            }
         } catch (Throwable var47) {
            var2 = var47;
            throw var47;
         } finally {
            if (in != null) {
               if (var2 != null) {
                  try {
                     in.close();
                  } catch (Throwable var43) {
                     var2.addSuppressed(var43);
                  }
               } else {
                  in.close();
               }
            }

         }
      } catch (NoSuchElementException var49) {
         logger.info("Connection reset by peer " + this.sock.getRemoteSocketAddress());
      } catch (InterruptedException | IOException var50) {
         logger.log(Level.WARNING, String.format("Error handling client %s: ", this.sock.getRemoteSocketAddress()), var50);
      } finally {
         this.stop();
         this.setChanged();
         this.notifyObservers(this.running);
      }

   }

   public void stop() {
      if (this.running) {
         try {
            logger.info("Closing connection to " + this.sock.getRemoteSocketAddress());
            this.executor.shutdownNow();
            this.sock.close();
         } catch (IOException var2) {
            logger.log(Level.WARNING, "I/O Error closing client socket", var2);
         }

         this.running = false;
      }
   }
}
