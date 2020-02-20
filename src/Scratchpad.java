import java.io.*;
import java.util.*;


public class Scratchpad {
	
	static int numBooks, numLibraries, numDays;
	static List<Book> bookList;
	static List<Library> libraryList;
//	static List<Library> signedUpLibraries;
	static SortedSet<Library> signedUpLibraries;
	
	static String outFile = "output.txt";
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new FileReader("b_read_on.txt"));
		numBooks = scanner.nextInt(); // B
		numLibraries = scanner.nextInt(); // L
		numDays = scanner.nextInt(); // D
		scanner.nextLine();
		
		bookList = new ArrayList<>(numBooks);
		libraryList = new ArrayList<>(numLibraries);
		
		for (int i = 0; i < numBooks; i++) {
			bookList.add(new Book(scanner.nextInt(), i));
		}
		scanner.nextLine();
		
		for (int i = 0; i < numLibraries; i++) {
			int Nj = scanner.nextInt();
			libraryList.add(new Library(Nj, scanner.nextInt(), scanner.nextInt(), i));
			scanner.nextLine();
			for (int j = 0; j < Nj; j++) {
				libraryList.get(i).booksInLibrary.add(bookList.get(scanner.nextInt()));
			}
		}
		
		scanner.close();
	}
	
	public static void PrintOutput(){
		try {
			PrintWriter pr = new PrintWriter(new FileWriter(new File(outFile)));
			
			pr.println(signedUpLibraries.size());
			
			
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
//
}

