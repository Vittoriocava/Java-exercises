import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Scarica implements Runnable {
	Finestra fin;
	Scanner sc;
	Socket s;
	HashMap<Integer,TicketCell> map;
	Scarica(Finestra fin,Scanner sc,HashMap<Integer,TicketCell> map,Socket s){
		this.fin=fin;
		this.sc=sc;
		this.map=map;
		this.s=s;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int c=0;
		System.out.println("ho fanculato start");
		while(sc.hasNext()) {
			String h=sc.next();
			System.out.println(h);
			if(h.equals("+")) {
				if(c!=30) {
					fin.logi.append("----------Partita Stoppata----------");
				}
				else {
					fin.logi.append("----------Fine Partita----------");
				}
				fin.start.setEnabled(true);
				fin.stop.setEnabled(false);
				fin.discon.setEnabled(true);
				fin.con.setEnabled(false);
				return;
			}
			if(map.keySet().contains(Integer.parseInt(h))) {
				map.get(Integer.parseInt(h)).setSelected(true);
			}
			fin.logi.append("Estratto: "+h+"\n");
			c++;
		}
	}

}
