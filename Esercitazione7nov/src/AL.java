import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class AL implements ActionListener {
	Finestra fin;
	static Socket s;
	public AL(Finestra fin) {
		this.fin=fin;
	}
	@Override
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
		if(e.getActionCommand().equals("Connect")) {
			try {
				s=new Socket(fin.sa.getText(),Integer.parseInt(fin.port.getText()));
				fin.con.setEnabled(false);
				fin.start.setEnabled(true);
				fin.clear.setEnabled(true);
				fin.Discon.setEnabled(true);
			} catch (IOException e1) {
			}
		}
		if(e.getActionCommand().equals("Start")) {
			try {
				PrintWriter out=new PrintWriter(s.getOutputStream());
				out.println("start");
				out.flush();
				fin.log.setText("");
				fin.pdf.setText("");
				fin.mp3.setText("");
				fin.dim.setText("");
				fin.start.setEnabled(false);
				fin.stop.setEnabled(true);
				fin.clear.setEnabled(false);
				fin.Discon.setEnabled(false);
				Scanner sc=new Scanner(s.getInputStream());
				Thread app=new Thread(new thread_append(sc,fin.log,fin.pdf,fin.mp3,fin.dim,fin.start,fin.stop,fin.clear,fin.Discon));
				app.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
		if(e.getActionCommand().equals("Stop")) {
			PrintWriter out=new PrintWriter(s.getOutputStream());
			out.println("stop");
			out.flush();
			
		}
		if(e.getActionCommand().equals("Disconnect")) {
			PrintWriter out=new PrintWriter(s.getOutputStream());
			out.println("disconnect");
			out.flush();
			s.close();
			out.close();
			fin.con.setEnabled(true);
			fin.start.setEnabled(false);
			fin.clear.setEnabled(false);
			fin.Discon.setEnabled(false);
		}
		if(e.getActionCommand().equals("Clear")) {
			fin.sa.setText("");
			fin.port.setText("");
			fin.log.setText("");
			fin.pdf.setText("");
			fin.mp3.setText("");
			fin.dim.setText("");
		}
		}catch(IOException e7) {
			
		}
	}

}
