import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FieldPanel extends JPanel {
	
	/**
	 * 
	 */
	Meduse meduza;
	int R=10;
	int hR;
	boolean printForces;
	private static final long serialVersionUID = 1L;
	
	
	FieldPanel(Meduse m, boolean printForces){
		//sizeOfCell=3;
		meduza = m;
		hR=R/2;
		this.setSize(new Dimension(800, 800));
		this.printForces = printForces;
	}
	
	public void paintComponent(Graphics g){
		
		g.setColor(new Color(100, 100, 100));
		for(int i=0;i<meduza.n-1;i++){
			g.drawLine(
					(int)meduza.balls.get(i).position.x + (int)meduza.balls.get(i).halfR, 
					(int)meduza.balls.get(i).position.y + (int)meduza.balls.get(i).halfR, 
					(int)meduza.balls.get(i+1).position.x + (int)meduza.balls.get(i+1).halfR, 
					(int)meduza.balls.get(i+1).position.y + (int)meduza.balls.get(i+1).halfR);
		}
		
		g.setColor(new Color(0, 100, 0));
		g.fillOval((int)meduza.balls.get(0).position.x, (int)meduza.balls.get(0).position.y,(int)meduza.balls.get(0).R, (int)meduza.balls.get(0).R);
		g.setColor(new Color(0, 0, 0));
		for(int i=1;i<meduza.n;i++){
			g.fillOval(
					(int)meduza.balls.get(i).position.x, 
					(int)meduza.balls.get(i).position.y,
					(int)meduza.balls.get(i).R, 
					(int)meduza.balls.get(i).R);
		}
		if(printForces){
			g.setColor(new Color(0, 0, 200));	//rysowanie si� dzia�aj�cych na kule
			for(int i=0;i<meduza.n;i++){
				g.drawLine((int)(meduza.balls.get(i).position.x + (int)meduza.balls.get(i).halfR),
						(int)(meduza.balls.get(i).position.y + (int)meduza.balls.get(i).halfR),
						(int)(meduza.balls.get(i).position.x + (int)meduza.balls.get(i).halfR + meduza.balls.get(i).musclePrintForce.x/10),
						(int)(meduza.balls.get(i).position.y + (int)meduza.balls.get(i).halfR + meduza.balls.get(i).musclePrintForce.y/10));
			}
			g.setColor(new Color(200, 0, 0));	//rysowanie mi�ni
			for(int i=0;i<meduza.n-2;i++){
				g.drawLine(
						(int)meduza.balls.get(i).position.x + (int)meduza.balls.get(i).halfR, 
						(int)meduza.balls.get(i).position.y + (int)meduza.balls.get(i).halfR, 
						(int)meduza.balls.get(i+2).position.x + (int)meduza.balls.get(i).halfR, 
						(int)meduza.balls.get(i+2).position.y + (int)meduza.balls.get(i).halfR);
			}
		}
		/*
		
		g.fillRect(0 , 0 , 5 , 5);
		
		for(int xx=0 ; xx<grid.getMaxX() ; xx++){
			for(int yy=0 ; yy<grid.getMaxY() ; yy++){
				g.setColor(this.pickColor(grid.getCell(xx, yy)));
				g.setColor(this.pickColor(grid.getCell(xx, yy)));
			}
		}
		
		g.setColor(new Color(0, 0, 0));
		for(int i=0;i<antlist.size();i++){
			g.fillRect(antlist.get(i).x*sizeOfCell+sizeOfCell/2 ,antlist.get(i).y*sizeOfCell+sizeOfCell/2 , 5,5 );
		}
		
		
		
		
*/
	}
	

}
