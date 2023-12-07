package combolottery;

import java.awt.*;
import java.util.*;

import javax.swing.*;

public class Finestra {
	JFrame fin;
	JPanel top;
	JLabel sa;
	JTextArea SA;
	JLabel port;
	JTextArea PORT;
	JButton con;
	JButton discon;
	JPanel mid;
	HashMap<Integer,ArrayList<LotteryComboBox>> map;
	JPanel bot;
	JButton start;
	JButton stop;
	JButton reset;
	JButton rand;
	Finestra(){
		 fin=new JFrame("Vittorio Cava 2047459");
		 top=new JPanel();
		 top.setLayout(new FlowLayout());
		 sa=new JLabel("Server Address");
		 top.add(sa);
		 SA=new JTextArea("localhost",1,12);
		 top.add(SA);
		 port=new JLabel("Port");
		 top.add(port);
		 PORT=new JTextArea("4400",1,6);
		 top.add(PORT);
		 con=new JButton("Connect");
		 top.add(con);
		 discon=new JButton("Disconnect");
		 discon.setEnabled(false);
		 top.add(discon);
		 fin.add(top,BorderLayout.NORTH);
		 mid=new JPanel();
		 mid.setLayout(new GridLayout(5,6,3,3));
		 mid.setBorder(BorderFactory.createTitledBorder("Lotteria"));
		 map=new HashMap<>();
		 for(int j=0;j<5;j++) {
			 map.put(j, new ArrayList<>());
		 }
		 for(int i=0;i<5;i++) {
			 mid.add(new JLabel("Ruota "+String.valueOf(i+1)));
			 for(int j=0;j<5;j++) {
				 LotteryComboBox b=new LotteryComboBox();
				 b.setReadOnly(false);
				 map.get(i).add(b);
				 mid.add(b);
			 }
		 }
		 fin.add(mid,BorderLayout.CENTER);
		 bot=new JPanel();
		 bot.setLayout(new FlowLayout());
		 start=new JButton("Start");
		 start.setEnabled(false);
		 bot.add(start);
		 stop=new JButton("Stop");
		 bot.add(stop);
		 stop.setEnabled(false);
		 reset=new JButton("Reset");
		 bot.add(reset);
		 reset.setEnabled(false);
		 rand=new JButton("Randomize");
		 bot.add(rand);
		 rand.setEnabled(false);
		 fin.add(bot,BorderLayout.SOUTH);
		 fin.setSize(530,220);
		 fin.setDefaultCloseOperation(3);
		 fin.setVisible(true);
		
		 
	}
	
}
