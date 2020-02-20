import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;


public class Scratchpad {
	
	static int numBooks, numLibraries, numDays;
	static List<Book> bookList;
	static List<Library> libraryList;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new FileReader("small.in"));
		numBooks = scanner.nextInt(); // B
		numLibraries = scanner.nextInt(); // L
		numDays = scanner.nextInt(); // D
		scanner.nextLine();
		
		for (int i = 0; i < numBooks; i++) {
			bookList.add(new Book(scanner.nextInt()));
		}
		scanner.nextLine();
		
		
		
		scanner.close();
	}
//
}

