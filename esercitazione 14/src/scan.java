import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;
public class scan implements Runnable {
	Scanner sc;
	Finestra fin;
	HashMap<Integer,Color> map;
	scan(Scanner sc,Finestra fin){
		this.sc=sc;
		this.fin=fin;
		this.map=new HashMap<>();
		this.map.put(0, Color.WHITE);
		this.map.put(1, Color.BLACK);
		this.map.put(2, Color.GREEN);
		this.map.put(3, Color.RED);
		this.map.put(4, Color.YELLOW);
		this.map.put(5, Color.BLUE);
		this.map.put(6, Color.ORANGE);

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while(sc.hasNextLine()) {
			String[] sp=sc.nextLine().split(";");
			if(Integer.parseInt(sp[0])==-1 &&sp[1].equals("-1")) {
				return;
			}
			if(sp[1].equals("SX")) {
				fin.sx.setBackground(map.get(Integer.parseInt(sp[0])));
			}
			else if(sp[1].equals("CX")) {
				fin.cx.setBackground(map.get(Integer.parseInt(sp[0])));
			}
			else if(sp[1].equals("DX")) {
				fin.dx.setBackground(map.get(Integer.parseInt(sp[0])));
			}
		}
	}

}
