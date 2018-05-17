import java.awt.Color;

/**
 * Node class for the Red Black tree
 * 
 * @author Gurdev Sihra
 *
 * @param <Key>
 *            Data type
 */
public class Node<Key extends Comparable<Key>> {
	Key key; // Node's data
	Node<Key> parent; // Node's parent
	Node<Key> leftChild; // Node's left child
	Node<Key> rightChild;// Node's right child
	boolean isRoot; // checks if node is the root
	boolean isRed; // checks is not id red
	int color; // 0 for red and 1 for black

	/**
	 * Node constructor
	 * 
	 * @param data
	 *            - data in node
	 */
	public Node(Key data) {
		this.key = data;
		leftChild = null;
		rightChild = null;
		isRoot = false;
	}

	/**
	 * CompareTo method for all data types
	 * 
	 * @param n
	 *            - Node to be compared
	 * @return 0 , if same, 1, if this is bigger, -1 if this is smaller
	 */
	public int compareTo(Node<Key> n) { // this < that <0
		return key.compareTo(n.key); // this > that >0
	}

	/**
	 * Method that checks if a node is a leaf
	 * 
	 * @return - true if leaf, false if not
	 */
	public boolean isLeaf() {
		if (isRoot && this.leftChild == null && this.rightChild == null)
			return true;
		if (isRoot)
			return false;
		if (this.leftChild == null && this.rightChild == null) {
			return true;
		}
		return false;
	}
}