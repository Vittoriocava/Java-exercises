import java.io.*;

import javax.swing.JOptionPane;

public class NumberInput implements Reader {

	@Override
	public String read() {
			String name=JOptionPane.showInputDialog("Inserire numero: ");
			String s=name;
			while(s.length()>1||!Character.isDigit(s.toCharArray()[0])||Integer.parseInt(s)<0 ||Integer.parseInt(s)>9) {
				name=JOptionPane.showInputDialog("Non valido, reinserire numero: ");
				s=name;
			}
			return s;
	}

}
