
public class Box <T>{
	private T object;
	
	public Box(){
		object=null;
	}
	
	public T get(){
		T temp=object;
		object=null;
		return temp;
	}
	
	public void put(T obj){
		this.object=obj;
	}
	
	public boolean isEmpty(){
		if (object==null)
			return true;
		else
			return false;
	}
}


