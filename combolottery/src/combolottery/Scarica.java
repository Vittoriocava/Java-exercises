package combolottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
		HashMap<Integer,Integer> num=new HashMap<>();
		for(int i=0;i<5;i++) {
			num.put(i, 0);
		}
		while(sc.hasNextLine()) {
			String s=sc.nextLine();
			if(s.equals("done")) {
				String str="";
				for(int i=0;i<5;i++) {
					str+="Ruota "+String.valueOf(i+1)+": "+String.valueOf(num.get(i))+"\n";
				}
				JOptionPane.showMessageDialog(null, str, "Vincenti", 1);
				fin.start.setEnabled(true);
				fin.reset.setEnabled(true);
				fin.rand.setEnabled(true);
				fin.discon.setEnabled(true);
				fin.stop.setEnabled(false);
				for(int i=0;i<5;i++) {
					for(int j=0;j<5;j++) {
						fin.map.get(i).get(j).setReadOnly(true);
					}
				}
				return;
			}
			else if(s.equals("interrupted")) {
				JOptionPane.showMessageDialog(null, "Partita Interrotta", "Interruzione", 1);
				fin.start.setEnabled(true);
				fin.reset.setEnabled(true);
				fin.rand.setEnabled(true);
				fin.discon.setEnabled(true);
				fin.stop.setEnabled(false);
				for(int i=0;i<5;i++) {
					for(int j=0;j<5;j++) {
						fin.map.get(i).get(j).setReadOnly(true);
					}
				}
				return;
			}
			else {
				String[] sp=s.split(":");
				for(int i=0;i<fin.map.get(Integer.parseInt(sp[0])-1).size();i++) {
					if(fin.map.get(Integer.parseInt(sp[0])-1).get(i).getSelectedIndex()==Integer.parseInt(sp[1])) {
						fin.map.get(Integer.parseInt(sp[0])-1).get(i).setChecked(true);
						int g=num.get(Integer.parseInt(sp[0])-1)+1;
						num.replace(Integer.parseInt(sp[0])-1, g);
						
					}
				}
			}
		}
	}

}
