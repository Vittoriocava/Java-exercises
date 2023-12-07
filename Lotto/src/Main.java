import java.awt.*;
import java.util.*;

import javax.swing.*;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Finestra fin=new Finestra();
		AL act=new AL(fin);
		fin.con.addActionListener(act);
		fin.discon.addActionListener(act);
		fin.interrupt.addActionListener(act);
		fin.clear.addActionListener(act);
		fin.start.addActionListener(act);
		
	}

}
