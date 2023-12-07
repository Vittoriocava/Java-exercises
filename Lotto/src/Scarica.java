import java.awt.Color;
import java.util.*;

import javax.swing.JOptionPane;

public class Scarica implements Runnable {
	Finestra fin;
	Scanner sc;
	Scarica(Finestra fin,Scanner sc){
		this.fin=fin;
		this.sc=sc;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int c=0;
		while(sc.hasNext()) {
			String s=sc.next();
			System.out.println(s);
			String[] sp=s.split(";"); 
			if(sp[0].equals("*")||sp[1].equals("*")) {
				if(c>0) {
					JOptionPane.showMessageDialog(null, "User Wins", "Risultato Partita",1);
				}
				else {
					JOptionPane.showMessageDialog(null, "Server Wins", "Risultato Partita",1);
				}
				fin.start.setEnabled(true);
				fin.discon.setEnabled(true);
				fin.clear.setEnabled(true);
				fin.interrupt.setEnabled(false);
				for(int i=0;i<fin.arr.size();i++) {
					fin.arr.get(i).setEnabled(true);
				}
				return;
			}
			else if(sp[0].equals("-1")||sp[1].equals("-1")) {
				JOptionPane.showMessageDialog(null, "Server Wins", "Risultato Partita",1);
				fin.start.setEnabled(true);
				fin.discon.setEnabled(true);
				fin.clear.setEnabled(true);
				fin.interrupt.setEnabled(false);
				for(int i=0;i<fin.arr.size();i++) {
					fin.arr.get(i).setEnabled(true);
				}
				return;
			}
			else {
				if(fin.arr.get(Integer.parseInt(sp[0])).getDigit().equals(sp[1])) {
					fin.arr.get(Integer.parseInt(sp[0])).changeColor(Color.GREEN);
					c++;
				}
				else if(!fin.arr.get(Integer.parseInt(sp[0])).getDigit().equals(sp[1])) {
					fin.arr.get(Integer.parseInt(sp[0])).changeColor(Color.RED);
				}
			}
			
		}
	}

}
