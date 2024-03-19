import java.util.ArrayList;
import java.util.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

public class Application {
	
	static class Dummy<T>{
		private T dummyValue;
		public Dummy (T dummyValue){
			this.dummyValue=dummyValue;
		}
		public T getDummy(){
			return dummyValue;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Method 1: from an array
		String [] animals= {"Lion","Monkey","Dog","Cat"};
		//Source
		Stream<String> animalsStream=Arrays.stream(animals);
		//Terminal
		animalsStream.forEach(System.out::println);
		
		System.out.println();
		
		//Method 2: directly from a list of parameters
		Stream<String> fruitsStream=Stream.of("Apple","Orange","Lemon");
		fruitsStream.forEach(System.out::println);
		
		System.out.println();
		
		//Method 3: from a collection
		List<Dummy<String>> dummyList = new LinkedList<>();
		dummyList.add(new Dummy<String>("fw14egf"));
		dummyList.add(new Dummy<String>("ft43egf"));
		dummyList.add(new Dummy<String>("f245egf"));
		dummyList.add(new Dummy<String>("fwee252"));
		Stream<Dummy<String>> dummyStream=dummyList.stream();
		dummyStream.forEach(x->System.out.println(x.getDummy()));
		
		System.out.println();
		
		//Method 4: source generation
		
		//Case 1: create an empty stream
		Stream emptyStream=Stream.empty();
		if(emptyStream.count()==0)
			System.out.println("Bro this stream is empty");
		
		System.out.println();
		
		//Case 2: use a Supplier to generate the elements
		Stream<Double> doublesStream=Stream.generate(Math::random);
		doublesStream.limit(10).forEach(System.out::println);
		//note: until the forEach the values are not generated
		// if limit is not specified it is printing infinite values
		
		System.out.println();
		
		//another example with suppliers
		Random random=new Random();
		int low=97; //97 means 'a'
		int high=122; //122 means 'z'
		Stream<String> randomStrings=Stream.generate(
				()->new StringBuilder()
				.append((char)(random.nextInt(high-low)+low))
				.append((char)(random.nextInt(high-low)+low))
				.append((char)(random.nextInt(high-low)+low))
				.append((char)(random.nextInt(high-low)+low))
				.toString()
				);
		randomStrings.limit(10).forEach(System.out::println);
		
		System.out.println();
		
		//Case 3: use a UnaryOperator interface and generate stream from
		Stream<Integer> by2CounterStream=Stream.iterate(0, x->x+2);
		by2CounterStream.limit(25).forEach(System.out::println);
		
		System.out.println();
		
		//note: we need to regenerate the stream after we terminated it
		Stream<String> stringStream=Stream.of("mother","father","son","daugher","grandma","grandpa","niece","uncle");
		stringStream.filter(x->x.contains("m")).forEach(System.out::println);
		
		System.out.println();

		//given a stream of unsorted integers, skip first 2 elements, sort values, filter values bigger than 100 and smaller than 10, plus remove duplicates
		Stream<Integer> intStream=Stream.of(9,34,42,15,8,8,242,2,42,32);
		intStream.skip(2)
		.sorted()
		.filter(x->(x>10 && x<100))
		.distinct()
		.forEach(System.out::println);
		
		System.out.println();

		//Map examples
		
		//example 1, print all dummies using map
		Stream<Dummy<Integer>> dummyIntStream=Stream.of(new Dummy<Integer>(9),new Dummy<Integer>(8), new Dummy<Integer>(10));
		dummyIntStream.map(Dummy::getDummy).
		forEach(System.out::println);
		
		System.out.println();
		
		//example 2, map each string in an array of String to their length
		Stream<String> vegetablesStream=Stream.of("carrot","onion","pumpkin","cabbage","tomato");
		vegetablesStream.map(String::length).forEach(System.out::println);
		
		System.out.println();

		//example 3, with double mapping
		Stream<Dummy<String>> anotherDummyStream=dummyList.stream();
		anotherDummyStream.map(Dummy::getDummy).mapToInt(String::length).forEach(System.out::println);
		
		System.out.println();
	
		
		//example 1
		
		/* Given a stream of Strings perform the following operations
		 * - filter strings shorter than 4 characters
		 * - sort strings in descending order according to string length
		 * - remove duplicated strings
		 * - map remaining strings to their length
		 */
		
		System.out.println("FROM HEEEERE");
		
		Stream<String> 		stringseh=Stream.of("words","that","are","shorter","than","four","characters","will","be","filetered","			four","words");
		stringseh.filter(s->s.length()>=4)
				.sorted(Comparator.comparing(String::length).reversed())
				.distinct().map(String::length)
				.forEach(System.out::println);
		
		System.out.println("UNTIL HEEEEERE\n");
		
		
		
		Stream<String> sportStream=Stream.of("row","rowing","soccer","volleyball","kayaking","tt");
		sportStream.filter(x->x.length()>4)
		.sorted(Comparator.comparing(String::length).reversed())
		.distinct()
		.map(String::length)
		.forEach(System.out::println);
		
		
		//example 2 (min 55)
		
		/* Given a stream of Strings perform the following operations
		 * - encode the strings by replacing vowels with 'a':'0', 'e':'1', 'i':'2', ...
		 * - select only strings with more than 2 numerical character after encoding
		 * - print the result
		 */
		
		
		Stream<String> 		stringsah=Stream.of("words","that","are","shorter","than","four","characters","will","be","filetered",
				"four","words");
		
		System.out.println("\nBEGINS EXACTLY HERE...");
		stringsah.map(word->{
				Map<Character,Character> encoder=new HashMap<>();
				encoder.put('a', '1');
				encoder.put('e', '2');
				encoder.put('i', '3');
				encoder.put('o', '4');
				encoder.put('u', '5');
				StringBuffer buffer= new StringBuffer(word);
				for (int i=0; i<word.length();i++)
					if (encoder.containsKey(buffer.charAt(i)))
						buffer.setCharAt(i, encoder.get(buffer.charAt(i)));
				return buffer.toString();
				})
				.filter(w->{
					int count=0;
					for (int i=0; i<w.length();i++)
						if ((w.charAt(i)>='0')&&(w.charAt(i)<='9'))
							count++;
					return count>2;
							
				})
				.forEach(System.out::println);
		
		System.out.println("GOOOOOOD JOBBB...\n");
		
		
		
		
		
		
		
		Stream<String> encStream=Stream.of("ciao","hello","salut","hallo","zicc","fratm","gingino","gig");
		
		
		Map<Character, Character> encodingMap=new HashMap<>();
		Map<Character, Character> decodingMap=new HashMap<>();
		
		encodingMap.put('a','0' );
		encodingMap.put('e','1');
		encodingMap.put('i','2');
		encodingMap.put('o','3');
		encodingMap.put('u','4');
		
		for (Map.Entry<Character, Character> e: encodingMap.entrySet()){
			System.out.println(e.getKey()+": "+e.getValue());
		}
		for (Map.Entry<Character, Character> e: encodingMap.entrySet()){
			decodingMap.put(e.getValue(),e.getKey());
		}
		
		Function<String, String> encoder;
		encoder=(str)->{
			StringBuffer encr=new StringBuffer(str);
			for (int i=0; i<encr.length(); i++){
				Character c=encr.charAt(i);
				if (encodingMap.containsKey(c))
					encr.setCharAt(i, encodingMap.get(c));
			}
			return encr.toString();
		};
		
		encStream.map(encoder)
		.filter(s->{
			int count=0;
			for (int i=0; i<s.length(); i++){
				if (Character.isDigit(s.charAt(i)))
					count++;
			}
			return count>2;
		})
		.forEach(System.out::println);
		System.out.println();

		
		//Example 1 flatmap: class Person contains name, age of the person and a list of hobbies
		//using flatmap find all the unique hobbies
		List<String> hobbies1= new ArrayList<>();
		hobbies1.add("rowing");
		hobbies1.add("music");
		hobbies1.add("computer");
		List<String> hobbies2= new ArrayList<>();
		hobbies2.add("rowing");
		hobbies2.add("soccer");
		hobbies2.add("cycling");
		List<String> hobbies3= new ArrayList<>();
		hobbies3.add("cycling");
		hobbies3.add("music");
		hobbies3.add("computer");
		Person p1=new Person("Tommaso",20,hobbies1);
		Person p2=new Person("Ozgun",23,hobbies2);
		Person p3=new Person("Canberk",22,hobbies3);
		Stream<Person> personStream = Stream.of(p1,p2,p3);
		personStream. //stream of people
		map(Person::getHobbies). //stream of collection of hobbies
		flatMap(Collection::stream). //stream of hobbies
		distinct().
		forEach(System.out::println);
		System.out.println();
		
		//Example 2 flatmap: given a list of sentences, generate a stream containing each unique word in the sentences sorted alphabetically
		
		System.out.println("\nbegins fucking here no joke");
		Stream<String> ssss=Stream.of("hi I am tommaso","hi I am canberk","how are you","I am good thx");
		ssss.map(s->s.split(" "))
			.flatMap(Arrays::stream)
			.map(s->{
				StringBuffer buffer=new StringBuffer(s);
				for (int i=0; i<s.length();i++) 
					if (Character.isUpperCase(s.charAt(i)))
						buffer.setCharAt(i, Character.toLowerCase(buffer.charAt(i)));
				return buffer.toString();
			})
			.distinct()
			.sorted()
			.forEach(System.out::println);
		System.out.println("ends here no fucking joke\n");
		
		
		
		
		List<String> sentences = new LinkedList<String>();
		sentences.add("The quick brown fox");
		sentences.add("The brown bear is strong");
		sentences.add("The brown animal is QUICK and STRONG");
		Stream<List<String>> sentencesStream = Stream.of(sentences);
		sentencesStream.flatMap(Collection::stream)
			.map(s->s.split(" "))
			.flatMap(Arrays::stream)
			.map(s->{
				StringBuffer sb = new StringBuffer(s);
				for (int i=0; i<sb.length(); i++){
					if (Character.isUpperCase(sb.charAt(i)))
						sb.setCharAt(i, Character.toLowerCase(sb.charAt(i)));
				}
				return sb.toString();
			})
			.distinct()
			.sorted()
			.forEach(System.out::println);
		System.out.println();

		
		// Terminal operations
		
		//forEach
		Stream<Double> someNumbersOne=Stream.generate(Math::random);
		someNumbersOne.limit(5).map(x->String.valueOf((int)(x*10))+" ").forEach(System.out::print);
		//or equivalently directly on forEach
		Stream<Double> someNumbersTwo=Stream.generate(Math::random);
		someNumbersTwo.limit(5).forEach(x->System.out.print((int)(x*10)+" "));
		System.out.println();
		
		//findAny  (to get the any element of the stream, if small )
		Stream<String> againStringStream = Stream.of("Panda","Kyoto","Sunglasses","Almond");
		Optional<String> anyString=againStringStream.findAny();
		System.out.println(anyString.orElse("Empty!"));
		System.out.println();
		
		//findFirst (to get the first element of the stream)
		Stream<String> anotherStringStream = Stream.of("Panda","Kyoto","Sunglasses","Almond");
		Optional<String> firstString=anotherStringStream.findFirst();
		System.out.println(firstString.orElse("Empty!"));
		System.out.println();

		//min or max (in this case get the longest string in the stream)
		Stream<String> newStringStream = Stream.of("Panda","Kyoto","Sunglasses","Almond");
		Optional<String> maxString = newStringStream.max(Comparator.comparingInt(String::length));
		System.out.println(maxString.orElse("Empty!"));
		System.out.println();
		
		//count (in this case count how many Persons have rowing as hobby)
		Person[] peopleArray={
				new Person("Lorenzo",25,Arrays.asList("running","rowing","drugs")),
				p1,p2,p3
		};
		Stream<Person> peopleStream=Arrays.stream(peopleArray);
		
		long num_count=Arrays.stream(peopleArray).filter(p->p.getHobbies().contains("rowing")).count();
		long num_count_pretty=Arrays.stream(peopleArray).map(p->p.getHobbies()).flatMap(Collection::stream).filter(h->(h.compareTo("rowing")==0)).count();
		System.out.println("\n\n\n I counted "+num_count_pretty+" people that love rowing... nice very nice\n\n\n");
		boolean checkIfSomeoneRows=Arrays.stream(peopleArray).map(p->p.getHobbies()).flatMap(Collection::stream).anyMatch(h->(h.compareTo("rowing")==0));
		System.out.println("Does anyone row? "+checkIfSomeoneRows);
		boolean checkIfEveryoneRows=Arrays.stream(peopleArray).map(p->p.getHobbies()).flatMap(Collection::stream).allMatch(h->(h.compareTo("rowing")==0));
		System.out.println("Does everyone row? "+checkIfSomeoneRows);
		Map<Boolean,List<String>> rowers=Arrays.stream(peopleArray).collect(Collectors.partitioningBy(h->
		h.getHobbies().contains("rowing"),mapping(Person::getName,toList())));
		System.out.println("Here its the map with those who row or not:\n"+rowers);		
		
		/*
		int count= (int)peopleStream.map(Person::getHobbies)
					.flatMap(Collection::stream)
					.filter(s->s.equals("rowing"))
					.count();
		System.out.println(count+"\n");
		*/
		
		//anyMatch (in this case if at least a Person has running as hobby
		/*
		boolean flag=peopleStream.map(Person::getHobbies)
					.flatMap(Collection::stream)
					.anyMatch(h->h.equals("running"));
		System.out.println("Does somebody like running? " + flag);
		 */
		
		//allMatch (in this case if every person has at least two hobbies)
		boolean verify=peopleStream.map(Person::getHobbies)
					.allMatch(c -> c.size()>=2);
		System.out.println("Does everyone have at least two hobbies? " + verify);
		
		//noneMatch (in this case if they all do not contain numbers),
		Stream<String> alphanumericalStream=Stream.of("ciao","hello","scuol4");
		boolean status=alphanumericalStream.noneMatch(s->{for(int i=0;i<s.length();i++){
															if (Character.isDigit(s.charAt(i)))
																return true;}
															return false;
															});
		System.out.println("Is every string without a number? " + status);
		
		// EXAMPLE OF PRODUCT-INVENTORY
		
		// Reduce
		
		//example 1: a stream of numbers to reduce in the sum of even numbers in the stream
		List<Integer> numbers=Arrays.asList(1,2,4,5,6,8,23,23,35,64);
		int sumOfEvenNumbers = numbers.stream()
									  .filter(x->x%2==0)
									  .reduce(0, (x1,x2)->x1+x2);
		
		//example 2: concatenate single words in a sentence
		List<String> strings=Arrays.asList("Hello","I","am","Tommaso");
		String sentence = strings.stream()
								 .reduce("", (s1,s2)->s1.isEmpty() ? s2:s1+" "+s2); //to avoid special case of first iteration
		
		//example 3: sum all ages of Person in a list
		int sumAge = Arrays.stream(peopleArray).map(Person::getAge).reduce(0,(a1,a2)->a1+a2);
		
		
		// Collect
		
		//example 1: counting
		long count=numbers.stream().collect(Collectors.counting());
		
		//example 2: summing
		long sum=numbers.stream().collect(Collectors.summingInt(Integer::new));
		
		//example 3: summarizing
		IntSummaryStatistics intStats=numbers.stream().collect(Collectors.summarizingInt(Integer::new));
		
		//example 4: transform again the stream into a collection
		List<Integer> evenNumebers=numbers.stream().filter(n->n%2==0).collect(Collectors.toList());
		
		//example 5: joining streams
		Stream<String> wordsss=Stream.of("jim","canberk","ozgun");
		String guysss=wordsss.collect(Collectors.joining(",","???","!!!"));
		
		
		// Collecting using groupingBy
		
		//example 1: GroupingBy Age v1 (age,person)
		
		
		Map<Integer,List<Person>> ageOfEachPerson=Arrays.stream(peopleArray).collect(Collectors.groupingBy(Person::getAge,TreeMap::new,toList()));
		System.out.println("\n\n\nHey bro this is the age of every person in order!\n"+ageOfEachPerson+"\n\n\n");
		
		
		
		
		Map<Integer,List<Person>> personByAge1=Arrays.stream(peopleArray)
													.collect(groupingBy(Person::getAge, TreeMap::new, toList()));
		System.out.println(personByAge1+"\n");
		
		//example 2: GroupingBy Age v2 (age,personName)
		
		Map<Integer,List<String>> ageOfEachPersonName=Arrays.stream(peopleArray).collect(Collectors.groupingBy(Person::getAge,mapping(Person::getName,toList())));
		Map<Integer,List<String>> ageOfEachPersonNameButCooler=new TreeMap<>(Comparator.reverseOrder());
		ageOfEachPersonNameButCooler.putAll(ageOfEachPersonName);
		
		System.out.println("\n\n\nHey bro this is the age of every person but mapping their name!\n"+ageOfEachPersonName+"\n"+ageOfEachPersonNameButCooler+"\n\n\n");

		
		
		
		
		
		
		Map<Integer,List<String>> personByAge2=Arrays.stream(peopleArray)
													.collect(groupingBy(Person::getAge,mapping(Person::getName,toList())));
		System.out.println(personByAge2+"\n");										
		
		// Partition by example (Partition by having rowing as hobby)
		Map<Boolean, List<String>> personsRowing=Arrays.stream(peopleArray)
													   .collect(partitioningBy(p->p.getHobbies().contains("rowing"),collectingAndThen(mapping(Person::getName, toList()), LinkedList::new)));
		System.out.println("Who does have rowing as a hobby?");
		for (Map.Entry<Boolean, List<String>> entry : personsRowing.entrySet()) {
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		System.out.println();
		//Example: Group according to hobby
		
		Map<String, List<String>> salamalekum = Arrays.stream(peopleArray)
			    .collect(Collectors.groupingBy(p -> p.getHobbies().stream().distinct().findFirst().orElse("No Hobby"), 
			                                   Collectors.mapping(Person::getName, Collectors.toList())));

		Map<String,List<String>> porcodddio = Arrays.stream(peopleArray)
				.flatMap(person->person.getHobbies().stream().map(hobby->Map.entry(hobby,person.getName())))
				.collect(groupingBy(Map.Entry::getKey,mapping(Map.Entry::getValue, toList())));
		
		
		System.out.println(salamalekum+"\nalekumsalam\n");
		
		
		
		
		Map<String,List<String>> hobbiesToPersonsMap=Arrays.stream(peopleArray)
														   .flatMap(person->person.getHobbies().stream().map(hobby->Map.entry(hobby,person.getName())))
														   .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue,toList())));
		// array of persons -> stream of person -> stream of array of hobbies -> stream of hobbies -> map of hobbies (with key hobby and person) -> grouping the key, but the value should be only the value (i.e. the name of the person) to a list.
		System.out.println(hobbiesToPersonsMap);
		
		
		
	}

}
