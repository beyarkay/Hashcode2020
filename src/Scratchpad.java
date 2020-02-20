import java.io.*;
import java.util.*;


public class Scratchpad {
	
	static int caseNumber = 0;
	
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
	static ArrayList<Library> signedUpLibraries;
	
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
		signedUpLibraries = new ArrayList<>();
		
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
		HashSet<Book> usedBooks = new HashSet<>();
		
		Comparator<Book> bookComparator = Comparator.comparingInt(b -> -b.score);
		
		libraryList.remove(fastestSignupLibrary);
		signedUpLibraries.add(fastestSignupLibrary);
		
		int d = fastestSignupLibrary.daysToSignup;
		
		int nBooksToSend = Math.min(fastestSignupLibrary.booksInLibrary.size(), (numDays - d) * fastestSignupLibrary.booksShippedDaily);
		fastestSignupLibrary.scannedBooks.sort(bookComparator);
		for (int i = 0; i < nBooksToSend; i++) {
			fastestSignupLibrary.scannedBooks.add(fastestSignupLibrary.booksInLibrary.get(i));
			usedBooks.add(fastestSignupLibrary.booksInLibrary.get(i));
		}
		
		for (Library library : libraryList) {
			library.booksInLibrary.sort(bookComparator);
		}
		
		while (libraryList.size() > 0) {
			
			Library bestLibrary = new Library(0, Integer.MAX_VALUE, 0, -1);
			bestLibrary.score = Double.MIN_VALUE;
			
			for (Library library : libraryList) {
				nBooksToSend = Math.min(library.booksInLibrary.size(), (numDays - d) * library.booksShippedDaily);
				int totalBooksScore = 0;
				for (int i = 0; i < nBooksToSend; i++)
					totalBooksScore += library.booksInLibrary.get(i).score;
				
				
				//daysToSignup + intCeil(library.numBooks / library.booksShippedDaily);
//				int lifespan = library.daysToSignup + ((library.numBooks + library.booksShippedDaily - 1) / library.booksShippedDaily);

//				library.score = (double) totalBooksScore / lifespan;
				library.score = totalBooksScore;
				if (totalBooksScore > bestLibrary.score) {
					bestLibrary = library;
				}
			}
			
			nBooksToSend = Math.min(bestLibrary.booksInLibrary.size(), (numDays - d) * bestLibrary.booksShippedDaily);
			int k = nBooksToSend;
			for (int i = 0; i < k; i++) {
				if (!usedBooks.contains(bestLibrary.booksInLibrary.get(i))) {
					usedBooks.add(bestLibrary.booksInLibrary.get(i));
					bestLibrary.scannedBooks.add(bestLibrary.booksInLibrary.get(i));
				} else {
					k++;
					if(k>bestLibrary.numBooks) break;
				}
			}
			if (bestLibrary.scannedBooks.size() != 0) {
				signedUpLibraries.add(bestLibrary);
				libraryList.remove(bestLibrary);
			}
		}

//		for (int d = 0; libraryList.size() > 0 && d < numDays; d += libraryList.get(0).daysToSignup) {
//			signedUpLibraries.add(libraryList.remove(0));
//		}
		
//		int d = 0;
//		while (libraryList.size() > 0) {
//			d += libraryList.get(0).daysToSignup;
//			signedUpLibraries.add(libraryList.remove(0));
//		}
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

