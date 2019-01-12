package pkg;

import java.util.Random;

public class Meduza {
	
	//Algenowe
	float RetractStartA;
	float ContractStartA;
	float RetractStartB;
	float ContractStartB;
	float RetractPhiA;
	float ContractPhiA;
	float RetractPhiB;
	float ContractPhiB;
	
	//Niealgenowe
	float xx;
	float yy;
	float dir;
	float cuurentA = 1.8f;
	float currentB = 0.6f;
	
	//Ogolne
	float time=0;
	float RetractDurA;
	float RetractDurB;
	float ContractDurA;
	float ContractDurB;
	
	
	// zmienne pomocnicze
	final float velAA;
	final float velAB;
	
	final float tAA;
	final float tAB;
	
	final float angleAA;
	final float angleAB;

	
	Meduza(){
		Random rnd = new Random();
				
		RetractStartA = 0.6f;
		ContractStartA = 0.2f;
		RetractStartB = 0.1f;
		ContractStartB = 0.8f;
		
		RetractPhiA = 0.7f;
		ContractPhiA = 0.3f;
		
		RetractPhiB = 0.6f;
		ContractPhiB = 0.2f;
		
		
		
		xx=0;
		yy=0;
		dir=0;
		
		//OBLICZANIE CZASU PRZEZ JAKI BEDZIE SIE SKURCZAC I ROZKURCZAC MEISIEN
		if(ContractStartA < RetractStartA){ //jesli true to najpierw jest faza retract
			ContractDurA = RetractStartA - ContractStartA;
			RetractDurA = 1-ContractDurA;
			velAA = (RetractPhiA - ContractPhiA)/ RetractDurA;
			velAB = -(RetractPhiA - ContractPhiA)/ ContractDurA;
			
			tAA = ContractStartA;
			tAB = RetractStartA;
			
			angleAA = RetractPhiA;
			angleAB = ContractPhiA;

		}
		else{
			RetractDurA = ContractStartA - RetractStartA;
			ContractDurA = 1-RetractDurA;
			velAA = (ContractPhiA - RetractPhiA)/ ContractDurA;
			velAB = -(ContractPhiA - RetractPhiA)/ RetractDurA;
			
			tAA = RetractStartA;
			tAB = ContractStartA;
			
			angleAA = ContractPhiA;
			angleAB = RetractPhiA;
		}
	}
	
	void timeStep() {
		time += 0.01f;
	}
	
	float getCurrentPositionA(){
		
		if(time >= 0 && time < tAA) {
			return angleAA -(tAA-time)*velAA;
		} else if(time >= tAA && time < tAB) {
			return angleAA + (time - tAA)*velAB;
		} else if(time > tAB) {
			return angleAB + (time - tAB)*velAA;
		}
		return 0f;
	}
	float getCurrentPositionB(){
		return 0f;
	}
	
	
}
