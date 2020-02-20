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
//
}

