import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		/*
		//Example with StringWriter and StringReader
		try (StringWriter stringWriter=new StringWriter(); 
			PrintWriter printWriter=new PrintWriter(stringWriter)){
			
			//Writing data to StringWriter
			printWriter.print("Hello");
			printWriter.print(" ");
			printWriter.print("World!");
			String output=stringWriter.toString();
			System.out.println("Output from StringWriter: ");
			System.out.println(output);
			
			//Read from a string using StringReader
			StringReader stringReader=new StringReader(output);
			BufferedReader bufferedReader=new BufferedReader(stringReader);
			System.out.println("\nReading data using StringReader in a buffered fashion: ");
			String line;
			while((line=bufferedReader.readLine())!=null)
				System.out.println(line);
		}
		catch (IOException eio) {
			eio.printStackTrace();
		}
		
		//example with err
		System.err.println("This is an error message");
		
		//example with in
		byte [] buffer=new byte[4096];
		int correctReading=System.in.read(buffer);
		if (correctReading!=-1) {
			System.out.print(new String(buffer,0,correctReading));
		}
		
		/*
		//example from slides about file reading
		
		//char by char
		long start=System.nanoTime();
		try(Reader src = new FileReader("input.txt"); Writer dest = new FileWriter("output.txt");){
			int in;
			while((in=src.read())!=1)
				dest.write(in);
		}
		catch(IOException eio) {
			eio.printStackTrace();
		}
		long end=System.nanoTime();
		
		//line by line (10x more efficient)
		System.out.println("It took "+end-start+"ns to complete this block");
		try(Reader src = new FileReader("input.txt"); 
			Writer dest = new FileWriter("output.txt");){
			int in;
			char [] bufferLine=new char[4096];
			while((in=src.read(bufferLine))!=1)
				dest.write(bufferLine,0,in);
		}
		catch(IOException eio) {
			eio.printStackTrace();
		}
		*/
		
		// MOST IMPORTAN!!!!
		try(Reader src = new FileReader("input.txt"); Writer dest = new FileWriter("output.txt");){
				int in;
				char [] bufferLine=new char[4096];
				while((in=src.read(bufferLine))!=1)
					dest.write(bufferLine,0,in);
			}
			catch(IOException eio) {
				eio.printStackTrace();
			}
		
		// writing/reading binary data
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.bin")/*FileInputStream("data.bin")*/)){
			int intValue=69; //=dis.readInt();
			double doubleValue=4.20; //=dis.readDouble();
			boolean booleanValue=true; //=dis.read
			
			dos.writeInt(intValue);
			dos.writeDouble(doubleValue);
			dos.writeBoolean(booleanValue);
			System.out.println("Data has been written to bin file");
		}
		catch (IOException ioe) {
			ioe.getStackTrace();
		}
		
		try (DataInputStream dis = new DataInputStream(new FileInputStream("data.bin")/*FileInputStream("data.bin")*/)){
			int intValue=dis.readInt(); //=dis.readInt();
			double doubleValue=dis.readDouble(); //=dis.readDouble();
			boolean booleanValue=dis.readBoolean(); //=dis.read
			System.out.println("\nData has been read from bin file");
			System.out.println("int: "+intValue);
			System.out.println("double: "+doubleValue);
			System.out.println("boolean: "+booleanValue);
		}
		catch (IOException ioe) {
			ioe.getStackTrace();
		}
		
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
		List<Person> someIdiots = Stream.of(p1,p2,p3).collect(Collectors.toList());
		
		
		//serialize
		try(ObjectOutputStream serializer=new ObjectOutputStream(new FileOutputStream("people.ser"))){
			serializer.writeObject(someIdiots);
		}
		catch (IOException ioe) {
			ioe.getStackTrace();
		}
		//deserialize
		List<Person> peopleList= new ArrayList();
		try(ObjectInputStream deserializer=new ObjectInputStream(new FileInputStream("people.ser"))){
			peopleList=(List<Person>)deserializer.readObject();
		}
		catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}
		
		//example of using Files for reading a file (all lines together)
		Path filePath=Path.of("input.txt");
		//using list
		try {
			List<String> lines=Files.readAllLines(filePath);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		//using stream (example count num of words)
		try {
			Path inFilePath=Path.of("input.txt");
			Map<String,Long> occurences=Files.lines(inFilePath)
			.flatMap(line->Stream.of(line.split("\\s+")))
			.collect(Collectors.groupingBy(String::toLowerCase,Collectors.counting()));
			
			Path outFilepath=Path.of("word_counts.txt");
			StringBuilder content=new StringBuilder();
			for (Map.Entry<String, Long> entry: occurences.entrySet()) {
				content.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
			}
			Files.write(outFilepath, content.toString().getBytes());
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		//String Tokenizer example
		String sentence="Hello Word! This is a sample sentence";
		StringTokenizer tokenizer= new StringTokenizer(sentence," ");
		while(tokenizer.hasMoreTokens()) {
			String token=tokenizer.nextToken();
			System.out.println(token);
		}
		
		
		/*
		Try it yourself
• A file contains on each line name of a person, age, and a list of hobbies.
• Fields are comma separated, hobbies are a list separated by an empty space.
• Read the file
• Using streams find the most popular hobby among people in the file and the 
  oldest and the youngest person performing that hobby
		*/
		
		Path peopleFilePath=Path.of("people.txt");
		try {
			List<Person> people=Files.lines(peopleFilePath)
									.map(line->{
										String[] parts=line.split(", ");
										String name=parts[0];
										int age=Integer.parseInt(parts[1]);
										List<String> hobbies=Arrays.asList(parts[2].split(" "));
										return new Person(name,age,hobbies);
									}).collect(Collectors.toList());
			
			//most popular hobby
			Map.Entry<String,Long> mostPopularHobby=people.stream()
					.flatMap(p->p.getHobbies().stream())
					.collect(Collectors.groupingBy(hobby->hobby, Collectors.counting()))
					.entrySet()
					.stream()
					.max(Map.Entry.comparingByValue())
					.orElse(Map.entry("",0L));
			
			//people with that hobby
			List<Person> peopleWithMostPopularHobby=people.stream()
					.filter(person->person.getHobbies().contains(mostPopularHobby.getKey()))
					.collect(Collectors.toList());
			
			//oldest person
			Optional<Person> oldestPerson=peopleWithMostPopularHobby.stream()
											.max(Comparator.comparingInt(Person::getAge));
			Optional<Person> youngestPerson=peopleWithMostPopularHobby.stream()
											.min(Comparator.comparingInt(Person::getAge));
			System.out.println("\nPEOPLE ANALYSIS:");
			System.out.println("Most popular hobby: "+mostPopularHobby.getKey());
			oldestPerson.ifPresent(p->System.out.println("Oldest: "+p.getName()));
			youngestPerson.ifPresent(p->System.out.println("Youngest: "+p.getName()+"\n"));
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		//REGEX
		
		//ex1: define a regex to find emails in a text
		String input="Please contact john@example.com. if you cannot reach him, write to tomce@gmail.com and zioskenz@polito.it";
		String patternString="[+a-z0-9._%-]+@[a-z0-9.-]+\\.[a-z]{2,}";
		Pattern emailPattern=Pattern.compile(patternString);
		Matcher emailMatcher=emailPattern.matcher(input);
		while(emailMatcher.find()) {
			String email=emailMatcher.group();
			System.out.println(email);
		}
		
		//ex2: reading a plant catalogue in xml using Scanner
		HashMap<String, HashMap<String, String>> plantCatalog = new HashMap<>();
		try {
			
			File file =new File("plant.catalog.xml");
			Scanner scanner =new Scanner(file);
			
			//Pattern to match the opnening tag of a plant element
			Pattern plantStartPattern=Pattern.compile("<PLANT>");
			Pattern plantEndPattern=Pattern.compile("</PLANT>");
			Pattern tagPattern=Pattern.compile("<(\\w+)>(.*)</\\1>)");
			String currentPlantTag=null;
			HashMap<String,String> currentPlant=null;
			
			//Read each line of the XML file
			while(scanner.hasNextLine()) {
				String line=scanner.nextLine().trim();//blank spaces at the beg or end
				//Check if it contains the start of a new plant element
				Matcher plantStartMatcher=plantStartPattern.matcher(line);
				if (plantStartMatcher.find()) {//new plant
					//Create a new HashMap corresponding to the current plant element
					currentPlant=new HashMap<>();
				}
				//Check if the current line contains the end of the current plant
				Matcher plantEndMatcher=plantEndPattern.matcher(line);
				if (plantEndMatcher.find()) {
					//Add the current plant to the catalog
					if (currentPlant!=null && currentPlantTag!=null)
						plantCatalog.put(currentPlantTag, currentPlant);
					currentPlant=null;
					currentPlantTag=null;
				}
				//Check if the current line contains a subtage of a plant and its value inside
				Matcher tagMatcher=tagPattern.matcher(line);
				if (tagMatcher.find()) {
					//Extract the subtag and its value
					String subtag=tagMatcher.group(1);
					String value=tagMatcher.group(2);
					if (currentPlant!=null) {
						//Add subtag and value to the current plant
						currentPlant.put(subtag, value);
						//Use subtag COMMON as the key in the catalog
						if (currentPlantTag==null)
							currentPlantTag=value;
					}
				}
			}
			scanner.close();
			
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		//Print catalog
		for (String plantTag: plantCatalog.keySet()) {
			HashMap<String, String> plant= plantCatalog.get(plantTag);
			System.out.println("Plant Tag: "+plantTag);
			for (String subtag: plant.keySet()) {
				String value=plant.get(subtag);
				System.out.println(subtag+": "+value);
			}
		}
		//10:30
	}
}
