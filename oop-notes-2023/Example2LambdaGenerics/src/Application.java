
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Example encoder
		Encoder e= (str,k)->{
			StringBuffer sb=new StringBuffer(str);
			for (int j=0; j<sb.length();j++){
				if (j%2==0){
					sb.setCharAt(j, (char)(sb.charAt(j)+k));
				}
			}
			return new String(sb);
		};
		
		//Example decoder
		Decoder d= (str,k)->{
			StringBuffer sb=new StringBuffer(str);
			for (int j=0; j<sb.length();j++){
				if (j%2==0){
					sb.setCharAt(j, (char)(sb.charAt(j)-k));
				}
			}
			return new String(sb);
		};
		
		String secret="Dogs are better than cats";
		String encodedSecret=e.encode(secret, 69);
		System.out.println(encodedSecret);
		String decodedSecret=d.decode(encodedSecret, 69);
		System.out.println(decodedSecret);	
		
		//Example single type pair
		Pair<String> sp=new Pair<> ("one","two");
		Pair<Integer> ip=new Pair<> (1,2);
		String a=sp.second();
		int b=ip.first();
		//String bString=ip.second(); ERROR!
		
		//Example mixed pair
		MixedPair<Integer, String> mxp=new MixedPair<>(1,"String");
		
		//Example generic box
		System.out.println();
		//integer
		Box<Integer> intBox = new Box<>();
		System.out.println("Is the box empty? "+ intBox.isEmpty());
		intBox.put(1);
		System.out.println("Is the box empty? "+ intBox.isEmpty());
		int i=intBox.get();
		System.out.println(i);
		System.out.println("Is the box empty? "+ intBox.isEmpty());
		System.out.println();
		//string
		Box<String> strBox = new Box<>();
		System.out.println("Is the box empty? "+ strBox.isEmpty());
		strBox.put("Ciao");
		System.out.println("Is the box empty? "+ strBox.isEmpty());
		String word=strBox.get();
		System.out.println(word);
		System.out.println("Is the box empty? "+ strBox.isEmpty());
		System.out.println();
		//string pair
		Box<Pair<String>> pairBox = new Box<>();
		System.out.println("Is the box empty? "+ pairBox.isEmpty());
		pairBox.put(new Pair<String>("Hello","World"));
		System.out.println("Is the box empty? "+ pairBox.isEmpty());
		Pair<String> p=pairBox.get();
		System.out.println(p.first()+" "+p.second());
		System.out.println("Is the box empty? "+ pairBox.isEmpty());
		System.out.println();
		boolean bool=sp instanceof Pair; //yes
		//boolean bool2=sp instanceof Pair<String>; //cannot be done
	}

}
