import java.util.ArrayList;
import java.util.Random;

public class Population {
	
	/**
	 * Podział na populacje według klasyfikacji ze slajdu 24 prezentacji:
	 * http://wikizmsi.zut.edu.pl/uploads/b/bf/OzWSI_I_S1_c2.pdf
	 */
	
	//Population P
	ArrayList<Meduse> PopulationP = new ArrayList<Meduse>();
	
	//Population P_prim
	ArrayList<Meduse> PopulationPprim = new ArrayList<Meduse>();
	ArrayList<Meduse> PopulationPprimSorted = new ArrayList<Meduse>();
	
	//Population O
	ArrayList<Meduse> PopulationO = new ArrayList<Meduse>();
	
	//Population T
	ArrayList<Meduse> PopulationT = new ArrayList<Meduse>();
	
	int meduseSize;
	int simSteps;
	
	int mu;
	int lambda;
	
	Random rnd = new Random();
	
	public Population( int MeduseSize, int mu, int lambda, int StepsPerSim) {
		this.meduseSize = MeduseSize;
		this.simSteps = StepsPerSim;
		this.mu = mu;
		this.lambda = lambda;
	}
	
	void ResetPopulations(){
		PopulationP.clear();
		PopulationT.clear();
	}
	
	void makeRandomPopulationP(){
		for(int i=0 ; i<mu ; i++){
			PopulationP.add(new Meduse(meduseSize));
		}
	}
	void populationT_outOf_PopulationP(){
		PopulationT.clear();
		for(int ii=0; ii<lambda ; ii++){
			int drawedMeduse = rnd.nextInt(mu)+1;
			PopulationT.add(PopulationP.get(drawedMeduse));
		}
	}
	void sortP_prim(){
		
		int maxFitnessIndex=0;
		float maxFitness;
		Meduse tmpMeduse;
		
		while( !PopulationPprim.isEmpty() ){
			maxFitness=0;
			for(int ii=0 ; ii<PopulationPprim.size() ; ii++){
				if( PopulationPprim.get(ii).coveredDistance > maxFitness ){
					maxFitnessIndex = ii;
					maxFitness = PopulationPprim.get(ii).coveredDistance;
				}
			}
			PopulationPprimSorted.add( PopulationPprim.get(maxFitnessIndex) );
			PopulationPprim.remove(maxFitnessIndex);
		}
		
	}

}
