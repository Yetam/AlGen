package pkg;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Meduza m = new Meduza();
		Gui gui = new Gui(m);
		gui.repaint();
		for(int i =0; i < 100; i++) {
			m.timeStep();
			System.out.println((0.01f + i*0.01f) + " " + m.getCurrentPositionA() );
			
		}

	}

}
