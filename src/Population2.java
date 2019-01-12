import java.util.ArrayList;

public class Population2 {
	ArrayList<Meduse> Pop = new ArrayList<Meduse>();
	int gen = 0;
	int maxTestIterations = 20000;

	Population2() {
		Pop.add(new Meduse(4));
	}

	void GenerationX() {

		/**
		 * <pop ma jeden element> 1. dodaj do pop klona pierwszego 2. zmutuj go
		 * 3. wykonaj (na przyk³ad) N=5000 iteracji symulacji dla ka¿dego 4.
		 * posortuj wed³ug ,,coveredDistance,, 5. wywal gorszego z pop 6.
		 * zresetuj pozosta³y element Meduse.resetMeduse();
		 */

		// 1
		// tutaj kopiuje element nr1 jako element nr 2
		// Pop.add( klon )
		//

		// 2
		Pop.get(1).mutateMeduse();

		// 3
		for (int i = 0; i < maxTestIterations; i++) {
			Pop.get(0).StepAlgorithm();
			Pop.get(1).StepAlgorithm();
		}

		// 4

	}
}
