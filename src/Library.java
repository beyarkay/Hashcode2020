import java.util.HashSet;
import java.util.Set;

public class Library {
	public int id, signupOrder;
	public int numBooks, daysToSignup, booksShippedDaily;
	public Set<Book> booksInLibrary;
	public Set<Book> scannedBooks;
	
	boolean SignedUp;
	
	public Library() {
	}
	
	public Library(int numBooks, int signups, int books, int id) {
		this.numBooks = numBooks;
		daysToSignup = signups;
		booksShippedDaily = books;
		this.id = id;
		booksInLibrary = new HashSet<>(numBooks);
		scannedBooks = new HashSet<>();
	}
}
