
/*
Model the product of an inventory. 
Each product has a name and a list of all the order placed for that product. 
The inventory is modelled as a list.
Using streams perform the following operations:
-Print the sorted list of elements in the inventory
-Check if there is at least one request for each product
-Count how many products have more than 1 order placed
- Select the most requested product
 */

import java.util.*;

public class Inventory {
	
	Collection<Product> products;
	
	Inventory(){
		products = new LinkedList<>();
	}
	
	public void printElements() {
		products.stream().map(p->p.getName()).sorted().forEach(System.out::println);
	}
	
	public boolean atLeastOneOrder() {
		return products.stream().allMatch(p->p.getNumOrders()>0);
	}
	
	public long countMoreThanOneOrder() {
		return products.stream().filter(p->p.getNumOrders()>1).count();
	}
	
	public String mostRequestedProduct() {
		/*Version 1 (does not work if each has 0 request, since it selects the first)
		// to use it you should do .get().getNumOders and check if >0
		Optional<Product> mostReq=products.stream().max(Comparator.comparingInt(Product::getNumOrders));
		if (mostReq.isEmpty())
			return "All products have 0 requests";
		else
			return mostReq.get().getName();
		*/
		/* Version 2 (but uses two streams)
		boolean flag=products.stream().map(Product::getNumOrders).allMatch(x->x>0);
		if (flag)
			return "All products have 0 requests";
		else {
			Optional <Product> mostReq=products.stream().max(Comparator.comparingInt(Product::getNumOrders));
			if (mostReq.isEmpty())
				return "Empty inventory";
			else
				return mostReq.get().getName();
		}
		*/
		// Version 3 (single stream)
		Optional <Product> mostReq1=products.stream().filter(p->p.getNumOrders()>0).max(Comparator.comparingInt(Product::getNumOrders));
		Optional <Map.Entry<String, Integer>> mostReq2 = products.stream()
																.map(x->Map.entry(x.getName(),x.getNumOrders()))
																.filter(x->x.getValue()!=0)
																.max(Comparator.comparingInt(Map.Entry::getValue));
		if (mostReq2.isEmpty())
			return "All products have 0 requests";
		else
			return mostReq2.get().getKey();
		
		
	}
}
