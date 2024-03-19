
public class MixedPair<T, W> {
	T a;
	W b;
	public MixedPair(T a, W b){
		this.a=a;
		this.b=b;
	}
	public T first() {return a;}
	public W second() {return b;}
	public void set1st(T x) {a=x;}
	public void set2nd(W x) {b=x;}
}
