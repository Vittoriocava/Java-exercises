import java.util.*;

import javax.swing.*;

public class thread_append implements Runnable {
	Scanner sc;
	JTextArea log;
	JTextArea pdf;
	JTextArea mp3;
	JTextArea dim;
	JButton start;
	JButton stop;
	JButton clear;
	JButton Discon;
	public thread_append(Scanner sc, JTextArea log,JTextArea pdf,JTextArea mp3,JTextArea dim,JButton start,JButton stop,JButton clear,JButton Discon) {
		this.sc=sc;
		this.log=log;
		this.pdf=pdf;
		this.mp3=mp3;
		this.dim=dim;
		this.start=start;
		this.stop=stop;
		this.clear=clear;
		this.Discon=Discon;
	}
	@Override
	public void run() {
		while(sc.hasNextLine()) {
			String a=sc.nextLine();
			if(a.equals("END")||a.equals("INTERRUPTED")) {
				start.setEnabled(true);
				stop.setEnabled(false);
				clear.setEnabled(true);
				Discon.setEnabled(true);
				break;
			}
			String[] sp=a.split(":");
			System.out.println(sp[0]);
			log.append(sp[1]+" "+sp[2]+"\n");
			if(sp[0].equals("PDF")) {
				pdf.append(sp[1].split("[.]")[0]+"\n");	
			}
			else if(sp[0].equals("MP3")) {
				mp3.append(sp[1].split("[.]")[0]+"\n");
			}
			if(dim.getText().equals("")) {
				dim.setText(sp[2].split(" ")[0]+" KB");
			}
			else if(!dim.getText().equals("")){
			dim.setText(Double.toString((Double.parseDouble(dim.getText().split(" ")[0])+Double.parseDouble(sp[2].split(" ")[0])))+" KB");
			}
			}
	}

}
