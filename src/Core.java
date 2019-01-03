import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Core {
	

	public static void main(String[] args) throws InterruptedException {
		
		Meduse meduza = new Meduse(3);		//stwórz meduzę
		Gui gui = new Gui(meduza,false);	//stwórz gui
		
		
		
		/*
		
		for(int i=0;i<5000000;i++){
			meduza.StepAlgorithm();			//wykonaj krok symulacji
			
			if(i%1 ==0){							//takie czary do przyspieszania symulacji
				//TimeUnit.MILLISECONDS.sleep(1);
				gui.repaint();
			}
				
				switch (i) {
				case 100:
					//meduza.balls.get(1).momentum.setValues(0, -100);	//nadawanie ustalonych pędów; obsolete
					break;
				}
			
		}
		*/
		
		
		

	}

}
