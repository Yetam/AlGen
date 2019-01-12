import java.util.ArrayList;
import java.util.Random;

public class Population {

	/**
	 * Podzia≈Ç na populacje wed≈Çug klasyfikacji ze slajdu 24 prezentacji:
	 * http://wikizmsi.zut.edu.pl/uploads/b/bf/OzWSI_I_S1_c2.pdf
	 */

	// Population P
	ArrayList<Meduse> PopulationP = new ArrayList<Meduse>();

	// Population P_prim
	ArrayList<Meduse> PopulationPprim = new ArrayList<Meduse>();

	// Population O
	ArrayList<Meduse> PopulationO = new ArrayList<Meduse>();

	// Population T
	ArrayList<Meduse> PopulationT = new ArrayList<Meduse>();

	int meduseSize;
	int simSteps;
	int EvolutionSteps = 10;

	int mu;
	int lambda;

	float bestFitnessValue = 0;

	Random rnd = new Random();

	public Population(int MeduseSize, int mu, int lambda, int StepsPerSim) {
		this.meduseSize = MeduseSize;
		this.simSteps = StepsPerSim;
		this.mu = mu;
		this.lambda = lambda;

		makeRandomPopulationP();
		doSimSteps(PopulationP);
	}

	void doSimSteps(ArrayList<Meduse> list) {
		for (int step = 0; step < this.simSteps; step++) {
			for (int ii = 0; ii < list.size(); ii++) {
				list.get(ii).StepAlgorithm();
			}
		}
	}

	void PopulationEvolve(int step) {
		System.out.print("Gen: " + step + " ");

		/*
		 * --- tworzenie populacji rozszerzonej T oraz O na podstawie metody
		 * ruletki
		 */
		System.out.print("T/O creation ... ");
		populationT_outOf_PopulationP();

		/* --- Krzyøowanie meduz w populacji O --- */
		System.out.print("O crossing ... ");
		for (int ii = 0; ii < PopulationO.size() - 1; ii++) {
			Meduse.CrossMeduses(PopulationO.get(ii), PopulationO.get(ii + 1));
		}

		/* --- Mutowanie meduz w populacji O --- */
		System.out.print("O mutating ... ");
		for (int ii = 0; ii < PopulationO.size(); ii++) {
			PopulationO.get(ii).mutate(0.1f);
		}

		/* --- £πczenie populacji O oraz T do populacji P_prim --- */
		System.out.print("P_prim out of T/O ... ");
		for (int ii = 0; ii < PopulationT.size(); ii++) {
			PopulationPprim.add(PopulationT.get(ii));
		}
		for (int ii = 0; ii < PopulationO.size(); ii++) {
			PopulationPprim.add(PopulationO.get(ii));
		}

		/* --- Wykonywanie krokÛw symulacji oraz sortowanie --- */
		System.out.print("P_prim sim & sort ... ");
		doSimSteps(PopulationPprim);
		sortMeduseList(PopulationPprim);
		PopulationP.clear();

		/* --- WybÛr najlepiej przystosowanych do populacji P z P_prim --- */
		System.out.print("P_prim best to P ... ");
		for (int ii = 0; ii < mu; ii++) {
			PopulationP.add(PopulationPprim.get(ii));
		}

		/* --- Pierdo≥y, ale waøne --- */
		System.out.print("Important stuff ... ");
		PopulationPprim.clear();
		bestFitnessValue = PopulationP.get(0).coveredDistance;

		/* --- Pierdo≥y ale ma≥o waøne --- */
		System.out.println(" \n P: " + PopulationP.size() + " T: " + PopulationT.size() + " O: " + PopulationO.size()
				+ " Pp: " + PopulationPprim.size());
	}

	void makeRandomPopulationP() {
		for (int i = 0; i < mu; i++) {
			PopulationP.add(new Meduse(meduseSize));
		}
	}

	void populationT_outOf_PopulationP() {
		Random rnd = new Random();
		PopulationT.clear();
		PopulationO.clear();
		sortMeduseList(PopulationP);

		// tworzenie dystrybyanty rozk≥adu prawdopodobie.Ò
		ArrayList<Float> populationP_CDF = new ArrayList<Float>();
		float sum = 0;
		for (int ii = 0; ii < PopulationP.size(); ii++) {
			sum += PopulationP.get(ii).coveredDistance;
			populationP_CDF.add(PopulationP.get(ii).coveredDistance);
		}

		float prob;
		int drawedIndex = 0;
		for (int ii = 0; ii < lambda; ii++) {
			prob = rnd.nextFloat() * sum;
			for (int jj = 0; jj < populationP_CDF.size(); jj++) {
				// System.out.println(ii + " " + jj);
				if (prob <= populationP_CDF.get(jj).floatValue()) {
					drawedIndex = jj;
					break;
				}
			}
			PopulationT.add(PopulationP.get(drawedIndex));
			PopulationO.add(PopulationP.get(drawedIndex));
		}

	}

	void sortMeduseList(ArrayList<Meduse> unsorted) {

		Meduse tmpMeduse;
		int currentBestFitnessIndex = 0;
		int sortedMeduses = 0;

		while (sortedMeduses < unsorted.size()) { // trzeba posortowaÊ KAØDY
													// element, wiÍc petla
													// iteruje po kaødym
													// elemencie
			currentBestFitnessIndex = sortedMeduses;
			for (int ii = sortedMeduses; ii < unsorted.size(); ii++) { // znajdywanie
																		// najwiÍkszego
																		// eementu
																		// z
																		// zakresu
																		// od
																		// juø
																		// posortowanych
																		// do
																		// koÒca
				if (unsorted.get(ii).coveredDistance > unsorted.get(currentBestFitnessIndex).coveredDistance) {
					currentBestFitnessIndex = ii;
				}
			}
			tmpMeduse = unsorted.get(sortedMeduses);
			unsorted.set(sortedMeduses, unsorted.get(currentBestFitnessIndex));
			unsorted.set(currentBestFitnessIndex, tmpMeduse);

			sortedMeduses++;
		}

	}

	void PrintStats() {
		for (int ii = 0; ii < PopulationP.size(); ii++) {
			System.out.println(PopulationP.get(ii).coveredDistance);
		}
	}

}
