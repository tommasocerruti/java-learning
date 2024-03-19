import java.util.*;

public class Product {
	
	private String name;
	private List<String> orders;
	Integer numOrders;

	public String getName() {
		return name;
	}
	public Collection getOrders() {
		return orders;
	}
	public Integer getNumOrders() {
		return numOrders;
	}
	public void addOrder(String nameOrder) {
		orders.add(nameOrder);
	}
	
}
