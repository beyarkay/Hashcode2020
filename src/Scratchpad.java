import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Scratchpad {
	
	static int m, n;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new FileReader("small.in"));
		m = scanner.nextInt();
		n = scanner.nextInt();
		
		scanner.close();
	}
}
