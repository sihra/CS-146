import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Class that tests adding a dictionary to a RedBlackTree and spelchecking the Caged Bird prom
 * @author Gurdev Sihra
 *
 */
public class DictionaryTester {
	
	@Test
	public void test() throws IOException {
		
		// Start time from when the dictionary is being created
		long begin = System.currentTimeMillis();
		try { 
			RedBlackTree rbt = new RedBlackTree();
			BufferedReader buffer = new BufferedReader(new FileReader(new File("Dictionary.txt")));
			String line = null;
			while ((line = buffer.readLine()) != null) {
				line = buffer.readLine();
				rbt.insert(line);
			}

			// Counters for how many times spellCheck was used and not found in the dictionary
			int misses = 0;
			int hits = 0;  
			buffer = new BufferedReader(new FileReader("Poem.txt"));
			while ((line = buffer.readLine()) != null) {
				// Separates words in a line and makes them all lower case
				String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
				for (String word : words) {
					word.replaceAll("[.]", "");
					word.replaceAll("[,]", "");
					Node find = rbt.lookup(word);
					if (find == null) {
						misses++;
					} else {
						hits++;
					}
				}
			}
			System.out.println("SpellCheck Hits Count: " + hits);
			System.out.println("SpellCheck Misses Count " + misses);
		} catch (IOException f) {
			System.out.println(f.getMessage());
		}
		
		// End time for after the spellchecking has beenn completed and results are printed
		long end = System.currentTimeMillis();
		System.out.println("Time: " + end);
	}
	
	public static void main(String[] args) throws IOException {
		DictionaryTester d = new DictionaryTester();
		d.test();
	}

}
