package pkg;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Meduza m = new Meduza();
		Gui gui = new Gui(m);
		gui.repaint();
		for(int i =0; i < 100; i++) {
			m.timeStep();
			Thread.sleep(100);
			System.out.println((0.01f + i*0.01f) + " " + m.getCurrentPositionA() + " " + m.getCurrentPositionB());
			gui.repaint();
		}

	}

}
