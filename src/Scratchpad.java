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
	//	static List<Library> signedUpLibraries;
	static SortedSet<Library> signedUpLibraries;

	static String outFile = "f.txt";

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileReader(INPUT_FILES[5]));
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


		MVP_boyd();
		System.out.println(signedUpLibraries.size());
		PrintOutput();
	}

	public static void MVP_boyd() {
		Comparator<Library> libraryComparator = Comparator.comparingInt(l -> l.numBooks);
		signedUpLibraries = new TreeSet<>(libraryComparator);

		Comparator<Book> bookComparator = Comparator.comparingInt(b -> b.score);

		for (Library l : libraryList) {
//			System.out.print(l.id);
//			System.out.print(" ");
			System.out.print("size " + l.booksInLibrary.size());

			l.scannedBooks = new TreeSet<>(bookComparator);
			l.scannedBooks.addAll(l.booksInLibrary);

			System.out.println(" " + l.scannedBooks.size());

//			System.out.println(l.scannedBooks.size());
			signedUpLibraries.add(l);
		}
	}


	public static void PrintOutput() {
		try {
			PrintWriter pr = new PrintWriter(new FileWriter(new File(outFile)));

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
					if(iter.hasNext()){
						pr.print(" ");
					}
				}

				if (iterator.hasNext()) {
					pr.println();
				}
			}
			pr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//
}

