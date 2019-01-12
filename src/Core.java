import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Core {
	

	public static void main(String[] args) throws InterruptedException {
		
//TO MNIEJ WIECEJ TAK POWINNO DZIA�A�, AAAAAALE...
		/*
		Population p1 = new Population(5, 10, 50, 1000);
		
		for(int i=0 ; i<1 ; i++){
			p1.PopulationEvolve(i);
		}
		p1.PrintStats();
		*/
		
		
//... AAAALE NARAZIE TAMTO NIE DZIALA WIEC DZIALA TO - TO PO PROSTU TWORZY JEDNA MEDUZE I JA ITERUJE
		Meduse meduza = new Meduse(4);		//stwórz meduzę o dlugosci (argument)
		Gui gui = new Gui(meduza,true);	//stwórz gui
		
		
		
		for(int i=0;i<5000000;i++){
			meduza.StepAlgorithm();			//wykonaj krok symulacji
			
			if(i%1 ==0){							//takie czary do przyspieszania symulacji
				TimeUnit.MILLISECONDS.sleep(1);
				gui.repaint();
			}
				
				switch (i) {
				case 1000:
					//meduza.balls.get(1).momentum.setValues(0, -100);	//nadawanie ustalonych pędów; obsolete
					meduza.resetMeduse();
					break;
				}
			
		}
		
		
		
		

	}

}
