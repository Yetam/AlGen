package pkg;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Gui extends JFrame {
	
	int width = 500;
	/**
	 * 
	 */
	FieldPanel field;
	Meduza med;

	Gui(Meduza medRef){
		med = medRef;
		this.setTitle("Meduza");
		this.setSize(new Dimension(width, width));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		field = new FieldPanel(medRef);
		this.add(field);
		this.setVisible(true);
	}
}
