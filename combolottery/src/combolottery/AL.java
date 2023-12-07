package combolottery;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

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
				s=new Socket(fin.SA.getText(),Integer.valueOf(fin.PORT.getText()));
				sc=new Scanner(s.getInputStream());
				out=new PrintWriter(s.getOutputStream());
				fin.con.setEnabled(false);
				fin.discon.setEnabled(true);
				fin.start.setEnabled(true);
				fin.stop.setEnabled(true);
				fin.reset.setEnabled(true);
				fin.rand.setEnabled(true);
				for(int i=0;i<5;i++) {
					for(int j=0;j<5;j++) {
						fin.map.get(i).get(j).setReadOnly(false);
					}
				}
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getActionCommand().equals("Start")) {
			int c=0;
			boolean t=true;
			for(int i=0;i<5;i++) {
				for(int h=0;h<5;h++) {
					if(fin.map.get(i).get(h).isNumberSelected()){;
					c++;
					}
					for(int j=0;j<5;j++) {
						if(h!=j&&fin.map.get(i).get(h).getSelectedIndex()==(fin.map.get(i).get(j)).getSelectedIndex()) {
							t=false;
						}
					}
				}
			}
			if(c==25&& t) {
				out.println("start");
				out.flush();
				fin.stop.setEnabled(true);
				fin.start.setEnabled(false);
				fin.discon.setEnabled(false);
				fin.reset.setEnabled(false);
				fin.rand.setEnabled(false);
				for(int i=0;i<5;i++) {
					for(int j=0;j<5;j++) {
						fin.map.get(i).get(j).setReadOnly(true);
						fin.map.get(i).get(j).setChecked(false);
					}
				}
				Thread r=new Thread(new Scarica(fin,sc));
				r.start();
			}
			else {
				JOptionPane.showMessageDialog(null, "Numeri non inseriti correttamente");
			}
		}
		if(e.getActionCommand().equals("Stop")) {
			out.println("stop");
			out.flush();

			
		}
		if(e.getActionCommand().equals("Reset")) {
			for(int i=0;i<5;i++) {
				for(int j=0;j<5;j++) {
					fin.map.get(i).get(j).setReadOnly(false);
					fin.map.get(i).get(j).setSelectedIndex(0);
					fin.map.get(i).get(j).setChecked(false);
				}
			}
		}
		if(e.getActionCommand().equals("Randomize")) {
			Random rand=new Random();
			for(int i=0;i<5;i++) {
				for(int j=0;j<5;j++) {
					int ra=rand.nextInt(90);
					while(ra<1) {
						ra=rand.nextInt(90);
						
					}
					int c=0;
						for(int h=0;h<5;h++) {
							if(fin.map.get(i).get(h).getSelectedIndex()!=ra) {
								c++;
							}
						}
					if(c==5) {
					fin.map.get(i).get(j).setSelectedIndex(ra);
					}
				}
			}
		}
		if(e.getActionCommand().equals("Disconnect")) {
			out.print("disconnect");
			out.flush();
			fin.stop.setEnabled(false);
			fin.start.setEnabled(false);
			fin.discon.setEnabled(false);
			fin.reset.setEnabled(false);
			fin.rand.setEnabled(false);
			fin.con.setEnabled(true);
			try {
				s.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sc.close();
			out.close();
		}
	}

}
