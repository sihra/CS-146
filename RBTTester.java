import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * Tester class for the RedBlackTree class
 * @author Gurdev Sihra
 *
 */
public class RBTTester {

	@Test
	// Test the Red Black Tree
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		assertEquals("DBACFEHGIJ", makeString(rbt));
		String str = "Color: 1, Key:D Parent: \n" + "Color: 1, Key:B Parent: D\n" + "Color: 1, Key:A Parent: B\n"
				+ "Color: 1, Key:C Parent: B\n" + "Color: 1, Key:F Parent: D\n" + "Color: 1, Key:E Parent: F\n"
				+ "Color: 0, Key:H Parent: F\n" + "Color: 1, Key:G Parent: H\n" + "Color: 1, Key:I Parent: H\n"
				+ "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));

	} 

	@Test
	public void checkRBT() {
		// Creating a Red Black Tree
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("Cat");
		Node root = rbt.root();
		
		// Checks the isEmpty() and isLeaf() methods
		assertEquals(rbt.isEmpty(root), false);
		assertEquals(rbt.isLeaf(root), true);
		rbt.insert("Dog");
		Node find = rbt.lookup("Dog");
		assertEquals(find.isLeaf(), true);
		assertEquals(root.isLeaf(), false);
		assertEquals(rbt.isLeaf(find), true);
		
		// Checks the isLeftChild() method
		assertEquals(rbt.isLeftChild(find.parent, find), false);
		rbt.insert("Apple");
		Node find1 = rbt.lookup("Apple");
		
		// Checks the getSibling method
		assertEquals(rbt.getSibling(find1), find);
		assertEquals(rbt.isLeftChild(find1.parent, find1), true);
		
		// Checks the getAunt() method
		assertNull(rbt.getAunt(find1));
		assertNull(rbt.getSibling(root));
		
		// Checks the printTree() method
		PrintStream out = System.out;
		 ByteArrayOutputStream stream = new ByteArrayOutputStream();
		 System.setOut(new PrintStream(stream));
		 rbt.printTree();
		 System.setOut(out);
		 String output = new String(stream.toByteArray());
		 assertTrue(output.contains("CatAppleDog"));
	}

	/**
	 * Method that returns the redBlackTree in String format
	 * @param t - tree to be inserted
	 * @return - String of the tree
	 */
	public static String makeString(RedBlackTree t) {
		class MyVisitor implements Visitor {
			String result = "";

			public void visit(Node n) {
				result = result + n.key;
			}
		}
		;
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}

	/**
	 * Method that returns the redBlackTree in String format for the first test
	 * @param t - tree to be inserted
	 * @return - String of the tree
	 */
	public static String makeStringDetails(RedBlackTree t) { 
		{
			class MyVisitor implements Visitor {
				String result = "";

				public void visit(Node n) {
					if (n != null) {
						if (n.parent != null) {
							result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + n.parent.key
									+ "\n";
						} else {
							result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + "\n";
						}
					}
				}
			}
			MyVisitor v = new MyVisitor();
			t.preOrderVisit(v);

			return v.result;
		}
	}
}
