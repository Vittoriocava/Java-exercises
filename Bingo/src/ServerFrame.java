import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class ServerFrame extends JFrame implements Observer, ActionListener {
   private static final int LOG_SIZE = 1000;
   private JButton startButton = new JButton("Start");
   private JButton stopButton;
   private JButton clearButton;
   private JTextField portField;
   private JLabel portLabel;
   private JLabel clientsLabel;
   private JTextArea logArea;
   private ServerThread serverThread;
   private ExecutorService executor;

   public ServerFrame() {
      super("Lab Server");
      this.startButton.setMnemonic(83);
      this.stopButton = new JButton("Stop");
      this.stopButton.setMnemonic(84);
      this.clearButton = new JButton("Clear Log");
      this.clearButton.setMnemonic(67);
      this.portField = new JTextField(10);
      this.portLabel = new JLabel("Port");
      this.portField.setText("4400");
      this.clientsLabel = new JLabel("Active Clients: 0");
      this.logArea = new JTextArea(25, 80);
      this.logArea.setEditable(false);
      ((AbstractDocument)this.logArea.getDocument()).setDocumentFilter(new ServerFrame.LogDocumentFilter(1000));
      JPanel topPanel = new JPanel(new BorderLayout());
      JPanel confPanel = new JPanel();
      JPanel logPanel = new JPanel(new BorderLayout());
      confPanel.add(this.startButton);
      confPanel.add(this.stopButton);
      confPanel.add(this.portLabel);
      confPanel.add(this.portField);
      confPanel.add(this.clearButton);
      topPanel.add(confPanel, "West");
      topPanel.add(this.clientsLabel, "East");
      topPanel.setBorder(BorderFactory.createTitledBorder("Server Configuration"));
      logPanel.add(new JScrollPane(this.logArea), "Center");
      logPanel.setBorder(BorderFactory.createTitledBorder("Log Area"));
      this.add(topPanel, "North");
      this.add(logPanel, "Center");
      this.startButton.setActionCommand("start");
      this.startButton.addActionListener(this);
      this.stopButton.setActionCommand("stop");
      this.stopButton.addActionListener(this);
      this.clearButton.setActionCommand("clear");
      this.clearButton.addActionListener(this);
      this.stopButton.setEnabled(false);
      LogManager.getLogManager().getLogger("").addHandler(new ServerFrame.LogHandler());
      this.executor = Executors.newSingleThreadExecutor();
      this.setDefaultCloseOperation(3);
      this.pack();
      this.setLocationRelativeTo((Component)null);
      this.setVisible(true);
   }

   public void update(Observable o, Object arg) {
      if (o instanceof ServerThread) {
         ServerThread server = (ServerThread)o;
         SwingUtilities.invokeLater(() -> {
            this.clientsLabel.setText("Active Clients: " + server.getClientCount());
         });
      }

   }

   public void actionPerformed(ActionEvent e) {
      String cmd = e.getActionCommand();
      if (cmd.equals("start")) {
         try {
            this.serverThread = new ServerThread(Integer.parseInt(this.portField.getText()));
            this.serverThread.addObserver(this);
            this.executor.execute(this.serverThread);
            this.startButton.setEnabled(false);
            this.stopButton.setEnabled(true);
            this.setDefaultCloseOperation(0);
         } catch (NumberFormatException var5) {
            JOptionPane.showMessageDialog(this, "Formato porta errato", "Errore", 0);
         }
      } else if (cmd.equals("stop")) {
         try {
            this.serverThread.stop();
            this.executor.awaitTermination(100L, TimeUnit.MILLISECONDS);
            this.startButton.setEnabled(true);
            this.stopButton.setEnabled(false);
            this.setDefaultCloseOperation(3);
         } catch (InterruptedException var4) {
            var4.printStackTrace();
         }
      } else if (cmd.equals("clear")) {
         SwingUtilities.invokeLater(() -> {
            this.logArea.setText("");
         });
      }

   }

   private class LogHandler extends Handler {
      LogHandler() {
         this.setFormatter(new SimpleFormatter());
         this.setLevel(Level.ALL);
      }

      public void publish(LogRecord record) {
         if (this.isLoggable(record)) {
            String msg = this.getFormatter().format(record);
            SwingUtilities.invokeLater(() -> {
               ServerFrame.this.logArea.append(msg);
               ServerFrame.this.logArea.setCaretPosition(ServerFrame.this.logArea.getText().length());
            });
         }
      }

      public void flush() {
      }

      public void close() throws SecurityException {
      }
   }

   private class LogDocumentFilter extends DocumentFilter {
      private int maxLines;

      LogDocumentFilter(int maxLines) {
         this.maxLines = maxLines;
      }

      public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
         super.insertString(fb, offset, string, attr);
         this.truncate(fb);
      }

      public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
         super.replace(fb, offset, length, text, attrs);
         this.truncate(fb);
      }

      private void truncate(FilterBypass fb) throws BadLocationException {
         int lines = ServerFrame.this.logArea.getLineCount();
         if (lines > this.maxLines) {
            int linesToRemove = lines - this.maxLines - 1;
            int lengthToRemove = ServerFrame.this.logArea.getLineStartOffset(linesToRemove);
            this.remove(fb, 0, lengthToRemove);
         }

      }
   }
}
