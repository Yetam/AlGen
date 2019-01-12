
public class Link {
	
	Meduse meduse;
	int startBall;
	int midball;
	int endBall;
	
	Vec Vij = new Vec();
	Vec Vjk = new Vec();
	Vec Vmid = new Vec();
	Vec localForce = new Vec();
	Vec rij = new Vec();
	Vec rjk = new Vec();
	float criticalVelocitySquared = (float) Math.pow(50, 2);
	
	public Link copy(Meduse m) {
		return new Link(m, startBall, midball, endBall);
	}
	
	Link(Meduse meduse, int startBall, int midBall, int endBall){
		this.meduse = meduse;
		this.startBall = startBall;
		this.midball = midBall;
		this.endBall = endBall;
	}
	
	void linkImpact(){
		
		rij.subInto(meduse.balls.get(midball).position, meduse.balls.get(startBall).position);
		rjk.subInto(meduse.balls.get(endBall).position, meduse.balls.get(midball).position);
		rij.rotateNegPi2();
		rjk.rotateNegPi2();
		
		if(!meduse.muscleList.get(startBall).rightTwisted){
			rij.scale(-1f);
			rjk.scale(-1f);
		}
		Vij.addInto(meduse.balls.get(startBall).momentum, meduse.balls.get(midball).momentum);
		Vjk.addInto(meduse.balls.get(endBall).momentum, meduse.balls.get(midball).momentum);
		Vij.scale(1f);
		Vjk.scale(1f);
		
		Vij.casOntoDirectionOfVec(rij);
		Vjk.casOntoDirectionOfVec(rjk);
		
		Vij.scale(Vij.length() );
		Vjk.scale(Vjk.length() );
	/*	
		if(startBall==0){
			if(Vij.length() > 1000)
				Vij.scale(1000/Vij.length());
			meduse.balls.get(startBall).linkForce.addScaledInto(Vij,1f);
		}
		if(endBall==(meduse.n-1)){
			if(Vjk.length() > 1000)
				Vjk.scale(1000/Vjk.length());
			meduse.balls.get(endBall).linkForce.addScaledInto(Vjk,1f);
		}
		*/
		
		Vmid.addInto(Vij, Vjk);
		float VmidLength = Vmid.length();
		if(VmidLength > 1000){
			Vmid.scale(1000/VmidLength);
		}
		
		Vmid.scale(1f);
		//System.out.println("frc: " +Vmid.length());
		meduse.balls.get(midball).linkForce.add(Vmid);
		
		
		/*
		rij.subInto(meduse.balls.get(endBall).position, meduse.balls.get(startBall).position);
		rij.rotateNegPi2();
		
		Vmean.addScaledInto(meduse.balls.get(startBall).momentum , meduse.balls.get(startBall).mass );
		Vmean.addScaledInto(meduse.balls.get(startBall).momentum , meduse.balls.get(startBall).mass );
		Vmean.scale(0.5f);
		Vmean.casOntoDirectionOfVec(rij);
		
		float VmeanLength = Vmean.length();
		VmeanLength = dragFactor(VmeanLength);
		
		localForce.setValues(0, 0);
		localForce.add(meduse.balls.get(startBall).momentum);
		localForce.scale(VmeanLength* meduse.balls.get(startBall).mass);
		meduse.balls.get(startBall).waterForce.add(localForce);
		
		//System.out.printf(" F1: %.2f ", localForce.length());
		
		localForce.setValues(0, 0);
		localForce.add(meduse.balls.get(endBall).momentum);
		localForce.scale(VmeanLength* meduse.balls.get(endBall).mass*1000);
		meduse.balls.get(endBall).waterForce.add(localForce);
	
		//System.out.printf(" F2: %.2f \n",  localForce.length());
		//System.out.flush();
		*/
	}
	float dragFactor(float velocity){
		if(velocity<50){
			return (velocity*velocity) / criticalVelocitySquared;
		}
		else{
			return 1.f;
		}
	}

}
