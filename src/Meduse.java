import java.util.ArrayList;
import java.util.Random;

public class Meduse {
	
	//KULKOWE
	ArrayList<Muscle> muscleList = new ArrayList<>();	//lista mięśni
	ArrayList<Link> linkList = new ArrayList<>();		//kista połączeń sztywnych
	ArrayList<Ball> balls = new ArrayList<>();			//lista kul
	
	int n;						//rozmiar meduzy, liczba kul
	float R=45;					//odległość między kulami
	float epsilon = 100;		//epsilon do sił międzykulkowych
	float tau = (float) 0.001;	//krok czasowy
	float time = 0;				//zmienna na czas całkowity
	
	float heartCycle=0;			//moment cyklu serca
	float heartCycleLength=2f;	//długość całk. cyjklu serca

	Vec rij = new Vec();					//wektor pomocniczy na odległość między dwoma kulkami
	Vec locForce = new Vec();				//wektor pomocniczy na siłę mieðzy dwoma kulkami
	Vec friction = new Vec();				//wektor pomocniczy na siłę tarcia
	Vec perpR = new Vec();					//wektor pomocniczy na wektor prostopadły do linku
	Vec meanForceOfMuscle = new Vec();		//wektor pomocniczy na średnią siłę mieśnia (?)
	Vec meanForceOfReaction = new Vec();	//wektor pomocniczy na średnią siłę oddziaływania woda-meduza
	
	/* --- zmienne funkcji celu --- */
	float coveredDistance;
	Vec coveredDistanceVec = new Vec();
	
	Random meduse_rnd = new Random();
	
	/**
	 * Konstruktor tworzący meduzę - ustala kule i mięśnie
	 * @param n - liczba kul meduzy
	 */
	Meduse(int n){
		Random rnd = new Random();
		this.n=n;
		
		balls.add(new Ball(this,300,300, 0, 0, 1));
		
		for(int i=1;i<n;i++){
			Vec newPos = randomRVector(R);	//
			balls.add(new Ball(
					this,
					balls.get(i-1).position.x + newPos.x,
					balls.get(i-1).position.y + newPos.y,
					0, 
					0, 
					1)
					);
		}
		for(int i=0;i<n-2;i++){
			linkList.add(new Link(
					this, 
					i,
					i+1,
					i+2));
		}
		for(int i=0;i<n-2;i++){
			muscleList.add(
					new Muscle(
							this,
							i,
							i+1, 
							i+2, 
							1f*R,
							1.9f*R,
							rnd.nextFloat(),
							rnd.nextFloat(),
							rnd.nextFloat(),
							rnd.nextFloat(),
							rnd.nextFloat()
							)
					);
			muscleList.get(i).print();
		}
		
	}
	void resetMeduse(){
System.out.println(1);
		balls.clear();
		linkList.clear();
System.out.println(1);

		balls.add(new Ball(this,300,300, 0, 0, 1));
		for(int i=1;i<n;i++){
			Vec newPos = randomRVector(R);	//
			balls.add(new Ball(
					this,
					balls.get(i-1).position.x + newPos.x,
					balls.get(i-1).position.y + newPos.y,
					0, 
					0, 
					1)
					);
		}
		System.out.println(1);
		for(int i=0;i<n-2;i++){
			linkList.add(new Link(
					this, 
					i,
					i+1,
					i+2));
		}
	}
	
	/**
	 * Oblicza siły oddziaływania kula-kula i zapisuje do linkForce danej kuli
	 */
	void calculateForcesOfLinks(){
		
		for(int ballIndex=0;ballIndex<n;ballIndex++){
			rij.setValues(0, 0);
			friction.setValues(0, 0);
		
			//DODAWANIE SI� ODDZIA�YWANIA MI�DZY KULKAMI aka SI� SPRʯYSTO�CI LINK�W
			if(ballIndex > 0){
				rij.subInto(balls.get(ballIndex).position, balls.get(ballIndex-1).position);
				float rijL = rij.length();
				rij.scale(epsilon*(R-rijL));
			
				balls.get(ballIndex).linkForce.add(rij);
				balls.get(ballIndex-1).linkForce.sub(rij);
			
			}
			if(ballIndex < n-1){
				rij.subInto(balls.get(ballIndex).position, balls.get(ballIndex+1).position);
				float rijL = rij.length();
				rij.scale(epsilon*(R-rijL));
			
				balls.get(ballIndex).linkForce.add(rij);
				balls.get(ballIndex+1).linkForce.sub(rij);
			}
		}
		meanForceOfReaction.setValues(0, 0);
		for(int ballIndex=0;ballIndex<n;ballIndex++){
			meanForceOfReaction.add(balls.get(ballIndex).linkForce);
		}
		meanForceOfReaction.scale(1f/(float)n);
		for(int ballIndex=0;ballIndex<n;ballIndex++){
			balls.get(ballIndex).linkForce.sub(meanForceOfReaction);
		}

	}
	
	/**
	 * Oblicza siły oddziaływania ruchu poączeń z wodą - siłę napędową izapisuje do
	 */
	void calculateForcesOfWater(){
		for(int i=0; i<n-2; i++)
			linkList.get(i).linkImpact();
			
		//Sila wody to wektor skierowany prostopadle do miesnia zalezny od zmiany pola trojkata IJK dla danej trojki
		/*
		for(int i=0;i<n-2;i++){
			muscleList.get(i).calculateWaterImpact();
		}
		*/
	}
	/**
	 * Oblicza siły hamowania kul przez wodę
	 */
	void calculateForcesOfFriction(){
		for(int ballIndex=0;ballIndex<n;ballIndex++){
			balls.get(ballIndex).fricForce.setValues(0, 0);
			friction.setValues( balls.get(ballIndex).momentum.x , balls.get(ballIndex).momentum.y);
			friction.scale( dragFactor(balls.get(ballIndex).momentum.length()) );
			balls.get(ballIndex).fricForce.sub(friction);
		}
	}
	/**
	 * Oblicza siły działania mięśni na kule
	 */
	void calculateForcesOfMuscles(){
		for(int ballIndex=0;ballIndex<n-2;ballIndex++){
			muscleList.get(ballIndex).muscleImpact();
		}
		meanForceOfMuscle.setValues(0, 0);
		for(int ballIndex=0;ballIndex<n;ballIndex++){
			meanForceOfMuscle.add(balls.get(ballIndex).muscForce);
		}
		meanForceOfMuscle.scale(1f/(float)n);
		for(int ballIndex=0;ballIndex<n;ballIndex++){
			balls.get(ballIndex).muscForce.sub(meanForceOfMuscle);
		}
	}
	/**
	 * sumuje siły oddziaływań połączeń, mięśni i tarcia
	 */
	void sumForces(){
		for(int ballIndex=0 ; ballIndex<n ; ballIndex++){
			balls.get(ballIndex).force.add( balls.get(ballIndex).linkForce );
			balls.get(ballIndex).force.add( balls.get(ballIndex).muscForce );
			balls.get(ballIndex).force.add( balls.get(ballIndex).fricForce );
			//balls.get(ballIndex).force.add( balls.get(ballIndex).waterForce );
		}
	}
	
	/**
	 * Główny algorytm Leap Frog
	 */
	void StepAlgorithm(){
		
		/* UPDATE MOMENTUM TAU/2 - algorytm Leap Frog*/
		for(int i=0;i<n;i++){
			balls.get(i).halfTauMomentumUpdate();
		}
		
		/*UPDATE POSITION TAU/2 - algorytm Leap Frog*/
		for(int i=0;i<n;i++){
			balls.get(i).halfTauPositionUpdate();
		}
		
		/*ZEROING OF FORCES*/
		for(int i=0;i<n;i++){
			balls.get(i).forceZeroing();
		}
		
		/*UPDATE FORCES*/
		calculateForcesOfLinks();		//oblicz siły oddziaływania międzykulkowych
		calculateForcesOfFriction();	//oblicz siły tarcia kule-woda
		calculateForcesOfMuscles();		//oblicz siły oddziływania mięśnie-kule
		calculateForcesOfWater();		//oblicz siły tarcia połączeń-woda (siły napędowe meduzy)
		sumForces();					//zsumuj siły działające per kulka
		
		/*UPDATE MOMENTUM TAU - algorytm Leap Frog*/
		for(int i=0;i<n;i++){
			balls.get(i).fullTauMomentumUpdate();
		}
		
		/*UPDATE MUSCLE POSITIONS - algorytm Leap Frog*/
		for(int i=0;i<n-2;i++){
			muscleList.get(i).updateMuscle();
		}
		
		heartBeat();	//update cyklu serca
		time+=tau;		//update czasu
		
		/* obliczanie f-cji celu */
		coveredDistanceVec.setValues(balls.get(1).position.x - 300, balls.get(1).position.y - 300);
		coveredDistance = coveredDistanceVec.length();
	}
	void heartBeat(){
		heartCycle+=tau;
		if(heartCycle > heartCycleLength)
			heartCycle =0;
	}
	/**
	 * Znajduje losowy wektor o długości R
	 * @param R - długość wektora
	 * @return
	 */
	Vec randomRVector(float R){
		Random rnd = new Random();
		float phi = (float) (rnd.nextFloat()*2*Math.PI);
		Vec validPosition = new Vec( (float)(R*Math.cos(phi)) ,(float)( R*Math.sin(phi)) );
		return validPosition;
	}
	float dragFactor(float momentumLength){
		float dragF = 11f;
		return dragF;
	}
	
	void mutateMeduse(){
		//mutacja miesni
		for(int i=0;i<n-2;i++){
			muscleList.get(i).contractEpsilon	= mutateValueUniform( muscleList.get(i).contractEpsilon  );
			muscleList.get(i).contractLength 	= mutateValueUniform( muscleList.get(i).contractLength );
			muscleList.get(i).contractTime 		= mutateValueUniform( muscleList.get(i).contractTime  );
			muscleList.get(i).retractEpsilon 	= mutateValueUniform( muscleList.get(i).retractEpsilon  );
			muscleList.get(i).retracrLength 	= mutateValueUniform( muscleList.get(i).retracrLength  );
			muscleList.get(i).retractTime 		= mutateValueUniform( muscleList.get(i).retractTime  );		
			
		}
	}
	
	static void CrossMeduses(Meduse man, Meduse woman){
		float manQuotion = man.coveredDistance / (woman.coveredDistance + man.coveredDistance);
		float womanQuotion = woman.coveredDistance / (woman.coveredDistance + man.coveredDistance);
		
		float tmpValMen = 0;
		float tmpValWoman = 0;
		boolean tmpStateMan = false;
		boolean tmpStateWoman = false;
		
		//rozmnazanie parametrow meduzy
		//tmpVal = men.heartCycle*womenQuotion + woman.heartCycle*menQuotion;
		//woman.heartCycle = tmpVal/2;
		tmpValMen = crossValues(man.heartCycleLength, woman.heartCycleLength, manQuotion, womanQuotion);
		tmpValWoman = crossValues(man.heartCycleLength, woman.heartCycleLength, womanQuotion, manQuotion);
		man.heartCycleLength = tmpValMen;
		woman.heartCycleLength = tmpValWoman;
		
		
		//rozmna�anie kul
		for(int ii=0 ; ii<man.balls.size() ; ii++){
			tmpValMen = crossValues(man.balls.get(ii).mass, woman.balls.get(ii).mass, manQuotion, womanQuotion);
			tmpValWoman = crossValues(man.balls.get(ii).mass, woman.balls.get(ii).mass, womanQuotion , manQuotion);
			man.balls.get(ii).mass = tmpValMen;
			woman.balls.get(ii).mass = tmpValWoman;
		}
		
		//rozmna�anie miesni
		for(int ii=0 ; ii<man.muscleList.size() ; ii++){
			
			tmpValMen = crossValues(man.muscleList.get(ii).contractLength, woman.muscleList.get(ii).contractLength , manQuotion , womanQuotion);
			tmpValWoman = crossValues(man.muscleList.get(ii).contractLength, woman.muscleList.get(ii).contractLength , womanQuotion , manQuotion); 
			man.muscleList.get(ii).contractLength = tmpValMen;
			woman.muscleList.get(ii).contractLength = tmpValWoman;
			
			tmpValMen = crossValues(man.muscleList.get(ii).retracrLength, woman.muscleList.get(ii).retracrLength , manQuotion , womanQuotion);
			tmpValWoman = crossValues(man.muscleList.get(ii).retracrLength, woman.muscleList.get(ii).retracrLength , womanQuotion , manQuotion); 
			man.muscleList.get(ii).retracrLength = tmpValMen;
			woman.muscleList.get(ii).retracrLength = tmpValWoman;
			
			tmpValMen = crossValues(man.muscleList.get(ii).contractTime, woman.muscleList.get(ii).contractTime , manQuotion , womanQuotion);
			tmpValWoman = crossValues(man.muscleList.get(ii).contractTime, woman.muscleList.get(ii).contractTime , womanQuotion , manQuotion); 
			man.muscleList.get(ii).contractTime = tmpValMen;
			woman.muscleList.get(ii).contractTime = tmpValWoman;
			
			tmpValMen = crossValues(man.muscleList.get(ii).retractTime, woman.muscleList.get(ii).retractTime , manQuotion , womanQuotion);
			tmpValWoman = crossValues(man.muscleList.get(ii).retractTime, woman.muscleList.get(ii).retractTime , womanQuotion , manQuotion); 
			man.muscleList.get(ii).retractTime = tmpValMen;
			woman.muscleList.get(ii).retractTime = tmpValWoman;
			
			tmpStateMan = crossStates(man.muscleList.get(ii).rightTwisted, woman.muscleList.get(ii).rightTwisted, manQuotion);
			tmpStateWoman = crossStates(man.muscleList.get(ii).rightTwisted, woman.muscleList.get(ii).rightTwisted, womanQuotion);
			man.muscleList.get(ii).rightTwisted = tmpStateMan;
			woman.muscleList.get(ii).rightTwisted = tmpStateWoman;
			
			man.muscleList.get(ii).calculateMoveEpsilons();
			woman.muscleList.get(ii).calculateMoveEpsilons();
		}
		
		//rozmnazanie polaczen
		
	}
	static float crossValues(float val1, float val2, float q1, float q2  ){
		return (val1*q1 + val2*q2)/2;
	}
	static boolean crossStates(boolean val1, boolean val2, float q1 ){
		Random rnd = new Random();
		float probability = rnd.nextFloat();
		if(probability>q1)
			return val1;
		else
			return val2;
	}
	void mutate(float sigma){
		Random rnd = new Random();
		
		//mutacja danych meduzy
		heartCycleLength = mutateValue(heartCycleLength, sigma, rnd.nextFloat());
		
		//mutacja kul
		 for(int ii=0 ; ii<balls.size() ; ii++){
			 balls.get(ii).mass = mutateValue(balls.get(ii).mass, sigma, rnd.nextFloat());
		 }
		//mutacja mie�ni
		 for(int ii=0 ; ii<muscleList.size() ; ii++){
			 muscleList.get(ii).contractLength = mutateValue(muscleList.get(ii).contractLength, sigma, rnd.nextFloat());
			 muscleList.get(ii).retracrLength = mutateValue(muscleList.get(ii).retracrLength, sigma, rnd.nextFloat());
			 muscleList.get(ii).contractTime = mutateValue(muscleList.get(ii).contractTime, sigma, rnd.nextFloat());
			 muscleList.get(ii).retractTime = mutateValue(muscleList.get(ii).retractTime, sigma, rnd.nextFloat());
			 
			 if(rnd.nextFloat() < sigma){
				 muscleList.get(ii).rightTwisted = !muscleList.get(ii).rightTwisted;
			 }
			 muscleList.get(ii).calculateMoveEpsilons();
		 }
		
	}
	float mutateValue(float val, float maxchange, float rnd01){
		return val + (val*maxchange)*(rnd01*2-1);
	}
	float mutateValueUniform(float value){
		value += value*(meduse_rnd.nextFloat()-0.5)/10;
		return value;
	}
}

