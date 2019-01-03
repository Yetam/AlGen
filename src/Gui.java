import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Gui extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FieldPanel field;
	Meduse meduse;

	Gui(Meduse medRef,boolean printforces){
		meduse = medRef;
		this.setTitle("Meduza");
		this.setSize(new Dimension(800, 900));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		field = new FieldPanel(medRef,printforces);
		this.add(field);
		this.setVisible(true);
	}
}
