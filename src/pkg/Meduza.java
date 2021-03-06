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
	//float currentA = 1.8f;
	//float currentB = 0.6f;
	float currentA;
	float currentB;
	
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
	
	final float velBA;
	final float velBB;
	
	final float tBA;
	final float tBB;
	
	final float angleBA;
	final float angleBB;
	
	float area;
	float d;
	float velocity;

	
	Meduza(){
		Random rnd = new Random();
				
		RetractStartA = 0.3f;
		ContractStartA = 0.2f;
		RetractStartB = 0.3f;
		ContractStartB = 0.2f;
		
		RetractPhiA = 1.6f;
		ContractPhiA = 3f;
		
		RetractPhiB = 0.0f;
		ContractPhiB = 1f;
		
		xx=0;
		yy=0;
		dir=0;
		
		area = calculateArea();
		currentA = getCurrentPositionA();
		currentB = getCurrentPositionB();
		velocity = 0;
		
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
		
		if(ContractStartB < RetractStartB){ //jesli true to najpierw jest faza retract
			ContractDurB = RetractStartB - ContractStartB;
			RetractDurB = 1-ContractDurB;
			velBA = (RetractPhiB - ContractPhiB)/ RetractDurB;
			velBB = -(RetractPhiB - ContractPhiB)/ ContractDurB;
			
			tBA = ContractStartB;
			tBB = RetractStartB;
			
			angleBA = RetractPhiB;
			angleBB = ContractPhiB;

		}
		else{
			RetractDurB = ContractStartB - RetractStartB;
			ContractDurB = 1-RetractDurB;
			velBA = (ContractPhiB - RetractPhiB)/ ContractDurB;
			velBB = -(ContractPhiB - RetractPhiB)/ RetractDurB;
			
			tBA = RetractStartB;
			tBB = ContractStartB;
			
			angleBA = ContractPhiB;
			angleBB = RetractPhiB;
		}
	}
	
	void timeStep() {
		time += 0.01f;
		currentA = getCurrentPositionA();
		currentB = getCurrentPositionB();
		time = time%1f;
		
		float newArea = calculateArea();
		this.d = calculateD();
		float change = (newArea-area)/(this.d);
		System.out.println(change);
		area = newArea;
		
		velocity += change;
		xx+=velocity;
		
		velocity *= 0.95;
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
		if(time >= 0 && time < tBA) {
			return angleBA -(tBA-time)*velBA;
		} else if(time >= tBA && time < tBB) {
			return angleBA + (time - tBA)*velBB;
		} else if(time > tBB) {
			return angleBB + (time - tBB)*velBA;
		}
		return 0f;	
		}
	
	float calculateArea(){
		float p1=0, p2=0;
		p1 = (float) (0.5*Math.sin(2*(Math.PI-currentA)));
		p2 = (float) (( Math.sqrt(2-2*Math.cos(2*(Math.PI-currentA))) + Math.sin(Math.PI-currentA-currentB) ) * Math.sin(currentA+currentB-Math.PI/2f));
		return p1+p2;
	}
	float calculateD(){
		float d, delta;
		delta = (float) (2*(Math.PI-currentA));
		d = (float) (Math.sqrt(2-Math.cos(delta)) + 2*Math.sin(Math.PI-currentA-currentB));
		return d;
	}
}
