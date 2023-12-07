import java.awt.*;

import javax.swing.*;

public class Finestra {
	JFrame fin;
	JLabel SA;
	JTextArea sa;
	JLabel PORT;
	JTextArea port;
	JButton con;
	JButton discon;
	JPanel top;
	JPanel mid;
	JPanel cartella;
	TicketCell[][] mat;
	JPanel log;
	JScrollPane logs;
	JTextArea logi;
	JButton start;
	JButton stop;
	JPanel bot;
	public Finestra() {
		fin=new JFrame ("Vittorio Cava 2047459");
		fin.setDefaultCloseOperation(3);
		SA=new JLabel("Server Address");
		sa=new JTextArea("localhost",1,12);
		PORT=new JLabel("Port");
		port=new JTextArea("4400",1,5);
		con=new JButton("Connect");
		discon=new JButton("Disconnect");
		discon.setEnabled(false);
		top=new JPanel();
		top.setLayout(new FlowLayout());
		top.add(SA);
		top.add(sa);
		top.add(PORT);
		top.add(port);
		top.add(con);
		top.add(discon);
		fin.add(top,BorderLayout.NORTH);
		mid=new JPanel();
		mid.setLayout(new BorderLayout());
		cartella=new JPanel();
		cartella.setBorder(BorderFactory.createTitledBorder("Cartella"));
		cartella.setLayout(new GridLayout(3,5,2,2));
		mat=new TicketCell[3][5];
		for(int i = 0;i<3;i++) {
			for(int j=0;j<5;j++) {
				TicketCell h=new TicketCell();
				mat[i][j]=h;
				cartella.add(h);
			}
		}
		log=new JPanel();
		log.setBorder(BorderFactory.createTitledBorder("Log"));
		logi=new JTextArea(20,30);
		logs=new JScrollPane(logi);
		log.add(logs);
		mid.add(log,BorderLayout.EAST);
		mid.add(cartella,BorderLayout.CENTER);
		fin.add(mid,BorderLayout.CENTER);
		start=new JButton("Start");
		stop=new JButton("Stop");
		bot=new JPanel();
		bot.setLayout(new FlowLayout());
		bot.add(start);
		start.setEnabled(false);
		bot.add(stop);
		bot.setEnabled(false);		
		fin.add(bot,BorderLayout.SOUTH);
		fin.setVisible(true);
		fin.pack();
	}
	
}
