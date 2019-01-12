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
	float cuurentA;
	float currentB;
	
	//Ogolne
	float time=0;
	float RetractDurA;
	float RetractDurB;
	float ContractDurA;
	float ContractDurB;
	
	Meduza(){
		Random rnd = new Random();
				
		RetractStartA = 0.2f;
		ContractStartA = 0.6f;
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
		}
		else{
			RetractDurA = ContractStartA - RetractStartA;
			ContractDurA = 1-RetractDurA;
		}
	}
	
	float getCurrentPositionA(){
		return 0f;
	}
	float getCurrentPositionB(){
		return 0f;
	}
	
	
}
