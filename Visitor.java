/**
 * Interface to be implemented
 *
 * @param <Key> Data type
 */
public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n
		 *            the visited node
		 */
		public void visit(Node<Key> n);
	}