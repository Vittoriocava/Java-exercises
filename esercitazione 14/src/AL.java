import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AL implements ActionListener {
	Finestra fin;
	public AL(Finestra Fin){
		this.fin=Fin;
	}
	Socket s;
	PrintWriter out;
	Scanner sc;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="Connect") {
			try {
				
				s=new Socket(fin.Ip.getText(),Integer.parseInt(fin.Port.getText()));
				out=new PrintWriter(s.getOutputStream());
				sc=new Scanner(s.getInputStream());
				fin.con.setEnabled(false);
				fin.discon.setEnabled(true);
				fin.start.setEnabled(true);
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getActionCommand()=="Start") {
			out.println("start");
			out.flush();
			fin.sx.setBackground(Color.LIGHT_GRAY);
			fin.cx.setBackground(Color.LIGHT_GRAY);
			fin.dx.setBackground(Color.LIGHT_GRAY);
			fin.discon.setEnabled(false);
			fin.start.setEnabled(false);
			fin.stop.setEnabled(true);
			Thread r=new Thread(new scan(sc,this.fin));
			r.start();
			
		}
		if(e.getActionCommand()=="Stop") {
			out.println("stop");
			out.flush();
			fin.discon.setEnabled(true);
			fin.start.setEnabled(true);
			fin.stop.setEnabled(false);
			
		}
		if(e.getActionCommand()=="Disconnect") {
			out.println("disconnect");
			out.flush();
			try {
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fin.sx.setBackground(Color.LIGHT_GRAY);
			fin.cx.setBackground(Color.LIGHT_GRAY);
			fin.dx.setBackground(Color.LIGHT_GRAY);
			fin.discon.setEnabled(false);
			fin.start.setEnabled(false);
			fin.con.setEnabled(true);
			
		}
		

	}

}
