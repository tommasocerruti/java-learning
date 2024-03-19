
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EquationSolver eq=new EquationSolver();
		eq.setA(1);
		eq.setB(3); 
		eq.setC(2);
		try {
			eq.solve();
			System.out.println("Roots: "+eq.root1+" "+eq.root2);
		} catch (NegativeDeterminantException e) {
			System.out.println(e.getMessage());
		}
		eq.setA(1);
		eq.setB(-2);
		eq.setC(2);
		try {
			eq.solve();
			System.out.println("Roots: "+eq.root1+" "+eq.root2);
		} catch (NegativeDeterminantException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
