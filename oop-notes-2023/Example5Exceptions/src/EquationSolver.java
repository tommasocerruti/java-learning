
public class EquationSolver {
	private double a, b, c;
	public double root1, root2;
	public void setA(double a) {
		this.a = a;
	}
	public void setB(double b) {
		this.b = b;
	}
	public void setC(double c) {
		this.c = c;
	}
	public void solve() throws NegativeDeterminantException{
		double determinant=b*b-4*a*c;
		if (determinant<0)
			throw new NegativeDeterminantException();
		root1=(-b+Math.sqrt(determinant)/(2*a));
		root2=(-b-Math.sqrt(determinant)/(2*a));
	}
	
}
