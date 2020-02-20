import java.io.*;
import java.util.*;


public class Scratchpad {

	static String[] INPUT_FILES = new String[]{
			"a_example.txt",
			"b_read_on.txt",
			"c_incunabula.txt",
			"d_tough_choices.txt",
			"e_so_many_books.txt",
			"f_libraries_of_the_world.txt"
	};
	
	static int numBooks, numLibraries, numDays;
	static List<Book> bookList;
	
	static List<Library> libraryList;
	static Library fastestSignupLibrary = new Library(0, Integer.MAX_VALUE, 0, -1);
	
	
	//	static List<Library> signedUpLibraries;
	static SortedSet<Library> signedUpLibraries;
	
	static String outFile = "differently.txt" + System.currentTimeMillis();
	
	public static void main(String[] args) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileReader(INPUT_FILES[0]));
		numBooks = scanner.nextInt(); // B
		numLibraries = scanner.nextInt(); // L
		numDays = scanner.nextInt(); // D
		scanner.nextLine();
		
		bookList = new ArrayList<>(numBooks);
		libraryList = new ArrayList<>(numLibraries);
		signedUpLibraries = new TreeSet<>(Comparator.comparingInt(o -> o.signupOrder));
		
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
			//Luc
			if (libraryList.get(i).daysToSignup < fastestSignupLibrary.daysToSignup) {
				fastestSignupLibrary = libraryList.get(i);
			}
			//End Luc
		}
		
		scanner.close();


//		MVP_boyd();
		mvp_Luc();
		System.out.println(signedUpLibraries.size());
		PrintOutput();
	}

	public static void MVP_boyd() {
		Comparator<Library> libraryComparator = Comparator.comparingInt(l -> l.numBooks);
		signedUpLibraries = new TreeSet<>(libraryComparator);

		Comparator<Book> bookComparator = Comparator.comparingInt(b -> b.score);

		for (Library l : libraryList) {
			for(Book b: l.booksInLibrary){
				l.scannedBooks = new TreeSet<>(bookComparator);
				l.scannedBooks.add(b);
			}
			signedUpLibraries.add(l);
		}
	}
	
	// LUC
	public static void mvp_Luc() {
		signedUpLibraries.add(fastestSignupLibrary);
		fastestSignupLibrary.signupOrder = 0;
		for(Library l : libraryList){
			l.scannedBooks.addAll(l.booksInLibrary);
		}
		signedUpLibraries.addAll(libraryList);
		
	}
	
	// END LUC
	
	public static void PrintOutput() {
		try {
			PrintWriter pr = new PrintWriter(new FileWriter(new File(outFile)));
			int score = 0;
			pr.println(signedUpLibraries.size());
			for (Library library : signedUpLibraries) {
				pr.print(library.id);
				pr.print(" ");
				pr.print(library.scannedBooks.size());
				pr.println();
				for (Book book : library.scannedBooks) {
					pr.print(book.id + " ");
					score += book.score;
				}
				pr.print("\b");
			}
			pr.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
//
}

