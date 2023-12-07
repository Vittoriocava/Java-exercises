import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

public class AL implements ActionListener {
	Finestra fin;
	static Socket s;
	static PrintWriter out;
	static Scanner sc;
	HashMap<Integer,TicketCell> mapa;
	ArrayList<Integer> arr;
	AL(Finestra fin){
		this.fin=fin;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Connect")){
			try {
				s=new Socket(fin.sa.getText(),Integer.parseInt(fin.port.getText()));
				sc=new Scanner(s.getInputStream());
				out=new PrintWriter(s.getOutputStream());
				fin.start.setEnabled(true);
				fin.stop.setEnabled(false);
				fin.discon.setEnabled(true);
				fin.con.setEnabled(false);
				arr=new ArrayList<>();
				Random rand=new Random();
				while(arr.size()<=15) {
					int r=rand.nextInt(90);
					if(!arr.contains(r)&&r>0) {
						arr.add(r);
					}
					
				}
				arr.sort(null);
				
				
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getActionCommand().equals("Start")) {
			fin.start.setEnabled(false);
			fin.stop.setEnabled(true);
			fin.discon.setEnabled(false);
			fin.con.setEnabled(false);
			out.println("start");
			out.flush();
			System.out.println("ho printato start");
			HashMap<Integer,TicketCell> mapa=new HashMap<>();
			int p=-2;
			for(int j=0;j<5;j++) {
				p+=2;
				for(int h=0;h<3;h++) {
					mapa.put(arr.get(j+h+p),fin.mat[h][j]);
					fin.mat[h][j].setValue(arr.get(j+h+p));
					fin.mat[h][j].setSelected(false);
					}
				}
			Thread scarica=new Thread(new Scarica(fin,sc,mapa,s));
			scarica.start();
			
		}
		if(e.getActionCommand().equals("Stop")) {
			out.println("stop");
			out.flush();
			

		}
		if(e.getActionCommand().equals("Disconnect")) {
			out.println("disconnect");
			out.flush();
			fin.start.setEnabled(false);
			fin.stop.setEnabled(false);
			fin.discon.setEnabled(false);
			fin.con.setEnabled(true);
			try {
				s.close();
				sc.close();
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
