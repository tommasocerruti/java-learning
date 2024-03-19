
public class Pair<T> {
	T a, b;
	public Pair(T a, T b){
		this.a=a;
		this.b=b;
	}
	public T first() {return a;}
	public T second() {return b;}
	public void set1st(T x) {a=x;}
	public void set2nd(T x) {b=x;}
	
	
}
