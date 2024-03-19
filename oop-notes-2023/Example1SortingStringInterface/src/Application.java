import java.util.Arrays;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] strings = {"apple","banana","apricot","blueberry","watermelon","orange"};
		System.out.println("Unsorted array: "+ Arrays.toString(strings));
		StringSorter.sortStrings(strings, Application::sortAscending);
		System.out.println("Array sorted in ascending order: "+ Arrays.toString(strings));
		StringSorter.sortStrings(strings, Application::sortDescending);
		System.out.println("Array sorted in descending order: "+ Arrays.toString(strings));
	}
	
	public static void sortAscending(String[] strings){
		Arrays.sort(strings);
	}
	
	public static void sortDescending(String[] strings){
		Arrays.sort(strings, (s1,s2)->s2.compareTo(s1));
	}
	

}
