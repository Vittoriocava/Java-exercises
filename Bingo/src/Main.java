import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Finestra fin=new Finestra();
		AL act=new AL(fin);
		fin.con.addActionListener(act);
		fin.discon.addActionListener(act);
		fin.start.addActionListener(act);
		fin.stop.addActionListener(act);
		
		
	}

}
