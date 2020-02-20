import java.io.*;
import java.util.*;


public class Scratchpad {
	
	static int caseNumber = 5;
	
	static String[] INPUT_FILES = new String[]{
			"a_example.txt",
			"b_read_on.txt",
			"c_incunabula.txt",
			"d_tough_choices.txt",
			"e_so_many_books.txt",
			"f_libraries_of_the_world.txt"
	};

	static String[] OUTPUT_FILES = new String[]{
			"output/a.txt",
			"output/b.txt",
			"output/c.txt",
			"output/d.txt",
			"output/e.txt",
			"output/f.txt"
	};

	static int numBooks, numLibraries, numDays;
	static List<Book> bookList;

	static List<Library> libraryList;
	static Library fastestSignupLibrary = new Library(0, Integer.MAX_VALUE, 0, -1);


	//	static List<Library> signedUpLibraries;
	static SortedSet<Library> signedUpLibraries;

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			caseNumber = Integer.parseInt(args[0]);
		}
		
		Scanner scanner = new Scanner(new FileReader(INPUT_FILES[caseNumber]));
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


		stu();
//		boyd_mvp();
		PrintOutput();
	}

	public static void boyd_mvp() {
		Comparator<Library> libraryComparator = new Comparator<Library>() {
			@Override
			public int compare(Library o1, Library o2) {
				if (o1.numBooks < o2.numBooks) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		
		signedUpLibraries = new TreeSet<>(libraryComparator);
		
		Comparator<Book> bookComparator = Comparator.comparingInt(b -> -b.score);
		
		for (Library l : libraryList) {
//			System.out.print("size " + l.booksInLibrary.size());
			
			l.scannedBooks.addAll(l.booksInLibrary);
			l.scannedBooks.sort(bookComparator);

//			System.out.println(l.scannedBooks.size());
			signedUpLibraries.add(l);
		}
	}
	
	public static void boyd_attempt1() {
		Comparator<Library> libraryComparator = Comparator.comparingInt(l -> l.numBooks);
		signedUpLibraries = new TreeSet<>(libraryComparator);
		
		Comparator<Book> bookComparator = Comparator.comparingInt(b -> -b.score);
		
		for (Library l : libraryList) {
//			System.out.print("size " + l.booksInLibrary.size());
			
			l.scannedBooks.addAll(l.booksInLibrary);
			l.scannedBooks.sort(bookComparator);
			
			
//			System.out.println(" " + l.scannedBooks.size());
			
			signedUpLibraries.add(l);
		}
	}
	
	
	// LUC
	public static void mvp_Luc() {
		signedUpLibraries.add(fastestSignupLibrary);
		fastestSignupLibrary.signupOrder = 0;
		for (Library l : libraryList) {
			l.scannedBooks.addAll(l.booksInLibrary);
		}
		signedUpLibraries.addAll(libraryList);
		
	}
	
	static void stu() {
		Comparator<Book> bookComparator = Comparator.comparingInt(b -> -b.score);
		for (Library library : libraryList) {
			library.scannedBooks.addAll(library.booksInLibrary);
			library.scannedBooks.sort(bookComparator);
			
			int totalBooksScore = 0;
			for (Book book : library.booksInLibrary)
				totalBooksScore += book.score;
			
			
			//daysToSignup + intFloor(library.numBooks / library.booksShippedDaily);
			int lifespan = library.daysToSignup + library.numBooks + library.booksShippedDaily - 1 / library.booksShippedDaily;
			
			library.score = (double) totalBooksScore / lifespan;
		}
		libraryList.sort(Comparator.comparingDouble(o -> o.score));
		
		//Use library with min signup time first
		int index = -1;
		int minSignupTime = Integer.MAX_VALUE;
		
		for (int i = 0; i < libraryList.size(); i++) {
			if (libraryList.get(i).daysToSignup < minSignupTime) {
				index = i;
				minSignupTime = libraryList.get(i).daysToSignup;
			}
		}
		
		signedUpLibraries.add(libraryList.remove(index));

//		for (int d = 0; libraryList.size() > 0 && d < numDays; d += libraryList.get(0).daysToSignup) {
//			signedUpLibraries.add(libraryList.remove(0));
//		}
		
		int d = 0;
		while (libraryList.size() > 0 && d < numDays) {
			d += libraryList.get(0).daysToSignup;
			signedUpLibraries.add(libraryList.remove(0));
		}
	}
	
	
	public static void PrintOutput() throws IOException {
		File directory = new File("output/");
		if (!directory.exists())
			directory.mkdir();
		
		PrintWriter pr = new PrintWriter(new FileWriter(new File(OUTPUT_FILES[caseNumber])));
		int score = 0;
		pr.println(signedUpLibraries.size());
		for (Iterator<Library> iterator = signedUpLibraries.iterator(); iterator.hasNext(); ) {
			Library library = iterator.next();
			
			pr.print(library.id);
			pr.print(" ");
			pr.print(library.scannedBooks.size());
			pr.println();
			for (Iterator<Book> iter = library.scannedBooks.iterator(); iter.hasNext(); ) {
				Book book = iter.next();
				pr.print(book.id);
				if (iter.hasNext()) {
					pr.print(" ");
				}
			}
			
			if (iterator.hasNext()) {
				pr.println();
			}
		}
		pr.close();
		
		
	}
//
}

