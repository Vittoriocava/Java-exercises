import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

public class AL implements ActionListener {
	Finestra fin;
	Socket s;
	PrintWriter out;
	Scanner sc;
	
	AL(Finestra fin){
		this.fin=fin;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Connect")) {
			try {
				
				s=new Socket(fin.IP.getText(),Integer.parseInt(fin.PORT.getText()));
				sc=new Scanner(s.getInputStream());
				out=new PrintWriter(s.getOutputStream());
				fin.start.setEnabled(true);
				fin.discon.setEnabled(true);
				fin.con.setEnabled(false);
			} catch (NumberFormatException | IOException e1) {
				JOptionPane.showMessageDialog(null, "Errore", "ServerErrato", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		if(e.getActionCommand().equals("Start")) {
			int c=0;
			for(int i=0;i<fin.arr.size();i++) {
				if(!fin.arr.get(i).getDigit().equals("")) {
					c++;
				}
			}
			if(c==5) {
				out.println("start");
				out.flush();
				fin.start.setEnabled(false);
				fin.discon.setEnabled(false);
				fin.clear.setEnabled(false);
				fin.interrupt.setEnabled(true);
				for(int i=0;i<fin.arr.size();i++) {
					fin.arr.get(i).setEnabled(false);
					fin.arr.get(i).changeColor(Color.LIGHT_GRAY);
				}
				Thread r=new Thread(new Scarica(fin,sc));
				r.start();
				
			}
		}
		if(e.getActionCommand().equals("Interrompi")) {
			out.println("interrompi");
			out.flush();
		}
		
		if(e.getActionCommand().equals("Disconnect")) {
			out.println("disconnect");
			out.flush();
			try {
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sc.close();
			out.close();
			fin.start.setEnabled(false);
			fin.discon.setEnabled(false);
			fin.clear.setEnabled(false);
			fin.interrupt.setEnabled(false);
			fin.con.setEnabled(true);
		}
		if(e.getActionCommand().equals("Clear")) {
			for(int i=0;i<fin.arr.size();i++) {
				fin.arr.get(i).changeColor(Color.LIGHT_GRAY);
				fin.arr.get(i).setTextDigit("");
			}
		}
			
	}

}
