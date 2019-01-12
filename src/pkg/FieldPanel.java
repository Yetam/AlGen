package pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FieldPanel extends JPanel {
	
	/**
	 * 
	 */
	Meduza meduza;
	int R=20;
	boolean printForces;
	private static final long serialVersionUID = 1L;
	
	
	FieldPanel(Meduza m){
		//sizeOfCell=3;
		meduza = m;
		this.setSize(new Dimension(800, 800));
		this.printForces = printForces;
	}
	
	public void paintComponent(Graphics g){
		/*
		g.setColor(new Color(100, 100, 100));
		for(int i=0;i<meduza.n-1;i++){
			g.drawLine(
					(int)meduza.balls.get(i).position.x + (int)meduza.balls.get(i).halfR, 
					(int)meduza.balls.get(i).position.y + (int)meduza.balls.get(i).halfR, 
					(int)meduza.balls.get(i+1).position.x + (int)meduza.balls.get(i+1).halfR, 
					(int)meduza.balls.get(i+1).position.y + (int)meduza.balls.get(i+1).halfR);
		}
		*/
		g.setColor(new Color(0, 0, 0));
		//na prawo A
		g.drawLine(
				(int)valid( meduza.xx ),
				(int)valid( meduza.yy ),
				(int)(valid( meduza.xx ) + R*Math.cos(meduza.dir - meduza.currentA)),
				(int)(valid( meduza.yy ) + R*Math.sin(meduza.dir - meduza.currentA))
				);
		//na prawo B
		g.drawLine(
				(int)(valid( meduza.xx ) + R*Math.cos(meduza.dir - meduza.currentA)),
				(int)(valid( meduza.yy ) + R*Math.sin(meduza.dir - meduza.currentA)),
				(int)(valid( meduza.xx ) + R*Math.cos(meduza.dir - meduza.currentA) + R*Math.cos(meduza.dir-meduza.currentA-meduza.currentB)) ,
				(int)(valid( meduza.yy ) + R*Math.sin(meduza.dir - meduza.currentA) + R*Math.sin(meduza.dir-meduza.currentA-meduza.currentB))
				);
		
		//na lewo A
		g.drawLine(
				(int)valid( meduza.xx ),
				(int)valid( meduza.yy ),
				(int)(valid( meduza.xx ) + R*Math.cos(meduza.dir + meduza.currentA)),
				(int)(valid( meduza.yy ) + R*Math.sin(meduza.dir + meduza.currentA))
				);
		//na lewo B
		g.drawLine(
				(int)(valid( meduza.xx ) + R*Math.cos(meduza.dir + meduza.currentA)),
				(int)(valid( meduza.yy ) + R*Math.sin(meduza.dir + meduza.currentA)),
				(int)(valid( meduza.xx ) + R*Math.cos(meduza.dir + meduza.currentA) + R*Math.cos(meduza.dir+meduza.currentA+meduza.currentB)) ,
				(int)(valid( meduza.yy ) + R*Math.sin(meduza.dir + meduza.currentA) + R*Math.sin(meduza.dir+meduza.currentA+meduza.currentB))
				);

	}
	
	int valid(float position){
		return (int) (position + 250);
	}
}
