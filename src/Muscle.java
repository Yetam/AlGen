import java.util.ArrayList;

public class Muscle {
	
	//parametry mięśmia
	Meduse meduse;
	int startBall;
	int middBall;
	int endBall;
	float currLength;
	
	float contractLength;		
	float retracrLength;
	float contractTime;
	float retractTime;
	
	boolean rightTwisted;
	
	//parametry skalujące siłę oddziaływania mięśnia
	float retractEpsilon;
	float contractEpsilon;
	
	//wektory pomocnicze
	Vec rik = new Vec();
	Vec rij = new Vec();
	Vec rjk = new Vec();
	Vec middleSumFroce = new Vec();
	
	
	Muscle(Meduse meduse,int startBall,int middBall,int endBall ,float contractLength, float retracrLength, float contractTime,float retractTime, float chanceOfRightTwisted01, float retractEpsilon, float contractEpsilon){
		this.startBall=startBall;
		this.middBall=middBall;
		this.endBall=endBall;
		
		this.meduse = meduse;
		this.contractLength = contractLength;
		this.retracrLength = retracrLength;
		this.contractTime = contractTime*meduse.heartCycleLength;
		this.retractTime = retractTime*meduse.heartCycleLength;
		//this.retractEpsilon = retractEpsilon;
		//this.contractEpsilon = contractEpsilon;
		
		if(retractTime<contractTime){
			currLength = contractLength;
			this.retractEpsilon = 1-(contractTime-retractTime)/meduse.heartCycleLength;
			this.contractEpsilon =(contractTime-retractTime)/meduse.heartCycleLength;
			System.out.println(this.retractEpsilon);
		}
		else{
			currLength = retracrLength;
			this.retractEpsilon = (retractTime-contractTime)/meduse.heartCycleLength;
			this.contractEpsilon = 1-(retractTime-contractTime)/meduse.heartCycleLength;
			System.out.println( (retractTime-contractTime)/meduse.heartCycleLength);
		}
		if(retractEpsilon>contractEpsilon){
			this.retractEpsilon = 0.5f+0.5f*this.retractEpsilon;
			this.contractEpsilon *= 0.5;
		}
		else{
			this.contractEpsilon = 0.5f+0.5f*this.contractEpsilon;
			this.retractEpsilon *= 0.5;
		}
		
		if(chanceOfRightTwisted01 > 0.5){
			rightTwisted = true;
		}
		else{
			rightTwisted = false;
		}
	}
	void print(){
		//System.out.println("conaLength:" + contractLength + " retLength: " + retracrLength + " conTime: " + contractTime + " retTime: " + retractTime + " RightTwist?: " + rightTwisted);
		System.out.printf("cotrLength: %.2f retrLength %.2f contrTime %.2f retrTime %.2f, retrEps %.2f contrEps %.2f \n", contractLength, retracrLength, contractTime, retractTime, retractEpsilon, contractEpsilon);
		System.out.flush();
	}
	
	void muscleImpact(){
	
		rik.subInto(meduse.balls.get(endBall).position, meduse.balls.get(startBall).position);	//obliczanie długości mięśnia
		float muscleLength = rik.length();
		
		/*operacje na wektorze kula1->kula2*/
		rij.subInto(meduse.balls.get(middBall).position, meduse.balls.get(startBall).position);	//obliczanie długości
		rij.scaleToUnit();																		//skalowanie do wektora jedn.
		rij.rotateNegPi2();																		//obrót o -pi/2
		
		/*operacje na wektorze kula2->kula1*/
		rjk.subInto(meduse.balls.get(endBall).position, meduse.balls.get(middBall).position);	//obliczanie długości
		rjk.scaleToUnit();																		//skalowanie do wektora jedn.
		rjk.rotateNegPi2();																		//obrót o -pi/2
		
		//Obliczanie siły oddziaływania mięśnia
		float scaleFactor = (muscleLength-currLength)*(muscleLength-currLength)/(currLength*currLength) * 10000 * Math.signum((muscleLength-currLength));
		if(scaleFactor > 0)
			scaleFactor*=retractEpsilon;
		else
			scaleFactor*=contractEpsilon;
		
		rij.scale( scaleFactor );
		rjk.scale( scaleFactor );
		
		//jeśli mmięsień lewoskrętny to poprzednia logika zgadza się co do znaku
		if(!rightTwisted){
			rij.scale(-1);
			rjk.scale(-1);
		}
		meduse.balls.get(startBall).muscForce.add(rij);	//zapis siły mięśniowej do atomu 1
		meduse.balls.get(endBall).muscForce.add(rjk);	//zapis siły mięśniowej do atomu 3
		
		meduse.balls.get(startBall).musclePrintForce.add(rij);	//zapis siły do rysowania do atomu 1
		meduse.balls.get(endBall).musclePrintForce.add(rjk);	//zapis siły do rysowania do atomu 3
		
		meduse.balls.get(middBall).muscForce.sub(rij);			//zapis siły mięśniowej do atomu 2
		meduse.balls.get(middBall).muscForce.sub(rjk);			//zapis siły mięśniowej do atomu 2
		meduse.balls.get(middBall).musclePrintForce.sub(rij);	//zapis siły do rysowania do atomu 3
		meduse.balls.get(middBall).musclePrintForce.sub(rjk);	//zapis siły do rysowania do atomu 3
		
	}
	
	/**
	 * update aktualnej pozycj mięśnia do której bedzie dążyć. Mięsień dąży albo do pozycji krótkiej albo drugiej. WYbór zależy od pozycji cyklu bicia serca
	 */
	void updateMuscle(){
		
		if(retractTime<contractTime){
			if( (meduse.heartCycle < retractTime) || (meduse.heartCycle > contractTime) ){
				currLength = contractLength;
			}
			else
				currLength = retracrLength;
		}
		else{
			if( (meduse.heartCycle < contractTime) || (meduse.heartCycle > retractTime) )
				currLength = retracrLength;
			else
				currLength = contractLength;
		}

	}
	
	float dragFactor(float f){
		return (float) (f*f/ Math.pow(1000, 2));
	}
	

}
