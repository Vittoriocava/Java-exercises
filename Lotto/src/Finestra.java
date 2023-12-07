import java.awt.*;
import java.util.*;

import javax.swing.*;

public class Finestra {
	JFrame fin;
	JPanel top;
	JButton start;
	JLabel ip;
	JTextArea IP;
	JLabel port;
	JTextArea PORT;
	JButton interrupt;
	JPanel mid;
	ArrayList<ColoredButton> arr;
	JPanel bot;
	JButton con;
	JButton discon;
	JButton clear;
	
	public Finestra(){
		 fin=new JFrame("Vittorio Cava 2047459");
		 top=new JPanel();
		top.setLayout(new FlowLayout());
		 start=new JButton("Start");
		top.add(start);
		 ip=new JLabel("IP Address");
		top.add(ip);
		 IP=new JTextArea("localhost",1,8) ;
		top.add(IP);
		 port=new JLabel("Port");
		top.add(port);
		 PORT=new JTextArea("4400",1,6);
		top.add(PORT);
		 interrupt=new JButton("Interrompi");
		top.add(interrupt);
		fin.add(top,BorderLayout.NORTH);
		 mid=new JPanel();
		mid.setLayout(new GridLayout(1,5));
		 arr=new ArrayList<>();
		for(int i=0;i<5;i++) {
			ColoredButton b=new ColoredButton(String.valueOf(i),Color.LIGHT_GRAY);
			mid.add(b);
			arr.add(b);
		}
		fin.add(mid,BorderLayout.CENTER);
		 bot=new JPanel();
		bot.setLayout(new FlowLayout());
		 con=new JButton("Connect");
		bot.add(con);
		 discon=new JButton("Disconnect");
		bot.add(discon);
		 clear=new JButton("Clear");
		discon.setEnabled(false);
		start.setEnabled(false);
		interrupt.setEnabled(false);
		bot.add(clear);
		fin.add(bot,BorderLayout.SOUTH);
		fin.setDefaultCloseOperation(3);
		fin.setSize(700, 200);
		fin.setVisible(true);
	}
	
}
