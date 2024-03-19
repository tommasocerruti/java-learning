import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// exercise menu
		System.out.println("MENU EXERCISE:");

		HashSet<String> cinnamonRollRecipe = new HashSet<>();
		cinnamonRollRecipe.add("Flour");
		cinnamonRollRecipe.add("Milk");
		cinnamonRollRecipe.add("Butter");
		cinnamonRollRecipe.add("Sugar");
		cinnamonRollRecipe.add("Salt");
		cinnamonRollRecipe.add("Cinnamon");
		cinnamonRollRecipe.add("Yeast");
		cinnamonRollRecipe.add("Flour");
		System.out.println("Ingredients needed "+cinnamonRollRecipe.toString());
		
		HashSet<String> ingredientsAtHome=new HashSet<>();
		ingredientsAtHome.add("Flour");
		ingredientsAtHome.add("Eggs");
		ingredientsAtHome.add("Butter");
		ingredientsAtHome.add("Cookies");
		ingredientsAtHome.add("Salt");
		ingredientsAtHome.add("Cocoa powder");
		ingredientsAtHome.add("Raisin");
		ingredientsAtHome.add("Almonds");
		System.out.println("Ingredients available "+ingredientsAtHome.toString());

		HashSet<String> toBuy=new HashSet<>();
		cinnamonRollRecipe.forEach(
				x->{
					if(!(ingredientsAtHome.contains(x)))
						toBuy.add(x);
				});
		if (toBuy!=null) {
			System.out.print("The ingredients to buy are:\n");
			toBuy.forEach(System.out::println);}
		
		// exercise encoding/decoding
		
		System.out.println("\nENCODING/DECODING EXERCISE:");

		Map<Character, Character> encodingMap=new HashMap<>();
		Map<Character, Character> decodingMap=new HashMap<>();
		
		encodingMap.put('a','6');
		encodingMap.put('e','1');
		encodingMap.put('i','2');
		encodingMap.put('o','3');
		encodingMap.put('u','4');
		encodingMap.put('a','0'); //it will replace the element inside
		
		for (Map.Entry<Character, Character> e: encodingMap.entrySet()){
			System.out.println(e.getKey()+": "+e.getValue());
		}
		for (Map.Entry<Character, Character> e: encodingMap.entrySet()){
			decodingMap.put(e.getValue(),e.getKey());
		}
		
		String secret="Bears beets batttlestar galactica";
		
		BiFunction<Map<Character,Character>,String, String> encoder;
		encoder=(encoding, str)->{
			StringBuffer encr=new StringBuffer(str);
			for (int i=0; i<encr.length(); i++){
				Character c=encr.charAt(i);
				if (encoding.containsKey(c))
					encr.setCharAt(i, encoding.get(c));
			}
			return encr.toString();
		};
		
		String encrSecret=encoder.apply(encodingMap, secret);
		System.out.println(encrSecret);
		
		BiFunction<Map<Character,Character>,String, String> decoder;
		decoder=(decoding, secr)->{
			StringBuffer dec=new StringBuffer(secr);
			for (int i=0; i<dec.length(); i++){
				Character c=dec.charAt(i);
				if (decoding.containsKey(c))
					dec.setCharAt(i, decoding.get(c));
			}
			return dec.toString();
		};
		
		String decSecret=decoder.apply(decodingMap, encrSecret);
		System.out.println(decSecret);
	}

}
