
public class Ball {
		
	Vec position = new Vec();
	Vec momentum = new Vec();
	
	Vec force = new Vec();			//wektoe sumarycznej siły dział. na kulę
	Vec linkForce = new Vec();		//wektor siły międzykulkowej
	Vec fricForce = new Vec();		//wektor siły tarcia
	Vec	muscForce = new Vec();		//wektor siły mięśniowej
	Vec	waterForce = new Vec();		//wektor siły napędowej od wody
	
	Vec musclePrintForce = new Vec();
	float mass;
	Meduse meduse;
	
	float R;
	float halfR;
	
	
	Ball(Meduse meduseRef, float xx, float yy, float momentumX, float momentumY, float massRef){
		position.setValues(xx, yy);
		momentum.setValues(momentumX, momentumY);
		mass = massRef;
		R=10;
		halfR = R/2;
		meduse = meduseRef;
	}
	
	/* --- metody algorytmu leapFrog ---*/
	void halfTauMomentumUpdate(){
		momentum.addScaledInto(force, (float) (0.5*meduse.tau));
	}
	void halfTauPositionUpdate(){
		position.addScaledInto(momentum, meduse.tau/mass);
	}
	void fullTauMomentumUpdate(){
		momentum.addScaledInto(force, (float) (0.5*meduse.tau));
	}
	void forceZeroing(){
		force.setValues(0, 0);
		linkForce.setValues(0, 0); 
		fricForce.setValues(0, 0);
		muscForce.setValues(0, 0);
		waterForce.setValues(0, 0);
		musclePrintForce.setValues(0, 0);
	}
	
	//Varia
	void bruteFrcMomentumChange(float newMomX, float newMomY){
		momentum.setValues(newMomX, newMomY);
	}
	void print(){
		System.out.println("Pos:" + position.x + position.y + "mass:" + mass);
	}
	
	public Ball copy(Meduse m) {
		Ball copy = new Ball(m, position.x, position.y, momentum.x, momentum.y, mass);
		return copy;
	}
	
}