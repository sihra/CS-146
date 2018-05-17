/**
 * Class that executes the functions of the Red Black Tree
 * 
 * @author Gurdev Sihra
 *
 * @param <Key>
 *            - generic data type
 */
public class RedBlackTree<Key extends Comparable<Key>> implements Visitor {
	private Node<Key> root; // Starting node of the tree

	/**
	 * Constructor that initializes the root
	 */
	public RedBlackTree() {
		root = null;
	}

	/**
	 * Method that checks if the node is a leaf
	 * 
	 * @param n
	 *            - node to be checked
	 * @return - true if it is a lead, false if not
	 */
	public boolean isLeaf(Node<Key> n) {
		if (n.equals(root) && n.leftChild == null && n.rightChild == null)
			return true;
		if (n.equals(root))
			return false;
		if (n.leftChild == null && n.rightChild == null) {
			return true;
		}
		return false;
	}

	/**
	 * Visit method implemnted by the Visitor interface
	 */
	@Override
	public void visit(Node n) {
		System.out.println(n.key);
	}

	/**
	 * Metho that prints the tree from the root
	 */
	public void printTree() { // preorder: visit, go left, go right
		Node<Key> currentNode = root;
		printTree(currentNode);
	}

	/**
	 * Recursive method that prints the tree in order
	 * 
	 * @param node
	 */
	public void printTree(Node<Key> node) {
		System.out.print(node.key);
		if (node.leftChild != null) {
			printTree(node.leftChild);
		}
		if (node.rightChild != null) {
			printTree(node.rightChild);
		}
	}

	/**
	 * Method that adds a node with data into the Red Black tree
	 * 
	 * @param data
	 *            - data of the node to be added
	 */
	public void addNode(Key data) {
		Node<Key> current = root;
		Node<Key> add = new Node<Key>(data);
		if (root == null) {
			root = add;
			add.isRoot = true;
		}
		while (current != null) {
			int answer = current.compareTo(add);
			if (answer == 0) {
				break;
			} else if (answer < 0) {
				if (current.rightChild != null) {
					current = current.rightChild;
				} else {
					current.rightChild = add;
					add.parent = current;
					add.color = 0;
					fixTree(add);
					break;
				}
			} else if (answer > 0) {
				if (current.leftChild != null) {
					current = current.leftChild;
				} else {
					current.leftChild = add;
					add.parent = current;
					add.color = 0;
					fixTree(add);
					break;
				}
			}
		}
		fixTree(add);
	}

	/**
	 * Method that inserts the data of the node into the Red Black tree
	 * 
	 * @param data
	 *            - data of the node
	 */
	public void insert(Key data) {
		System.out.println("key: " + data);
		addNode(data);
	}

	/**
	 * Method that looks for a node with the specified data
	 * 
	 * @param k
	 *            - data to be searched
	 * @return - node with the data
	 */
	public Node<Key> lookup(Key k) {
		Node<Key> current = root;
		Node<Key> find = new Node<Key>(k);
		while (current != null) {
			if (current.compareTo(find) == 0) {
				return current;
			} else {
				if (current.compareTo(find) > 0) {
					current = current.leftChild;
				} else if (current.compareTo(find) < 0) {
					current = current.rightChild;
				}
			}
		}
		return null;
	}

	/**
	 * Method that gets the sibling of a node
	 * 
	 * @param n
	 *            - Node to get sibling of
	 * @return - sibling of n
	 */
	public Node<Key> getSibling(Node<Key> n) {
		if (n != null && n.parent != null) {
			if (n.parent.leftChild.compareTo(n) == 0) {
				return n.parent.rightChild;
			}
			return n.parent.leftChild;
		}
		return null;
	}

	/**
	 * Method that gets the aunt of the node
	 * 
	 * @param n
	 *            - Node to get aunt of
	 * @return - aunt of the node
	 */
	public Node<Key> getAunt(Node<Key> n) {
		if (n != null) {
			if (n.parent != null) {
				if (n.parent.parent != null) {
					if (n.parent.parent.leftChild != null) {
						if (n.parent.parent.leftChild.compareTo(n.parent) == 0) {
							return n.parent.parent.rightChild;
						}
					}
					return n.parent.parent.leftChild;
				}
			}
		}
		return null;
	}

	/**
	 * Method that gets the grandparent of the node
	 * 
	 * @param n
	 *            - node to find grandparent of
	 * @return - grandparent of n
	 */
	public Node<Key> getGrandparent(Node<Key> n) {
		return n.parent.parent;
	}

	/**
	 * Method that performs a rotateLeft on a node in the tree
	 * 
	 * @param rotateX
	 *            - node to be rotated
	 */
	public void rotateLeft(Node<Key> rotateX) {
		Node<Key> rotateY = rotateX.rightChild;
		Node<Key> yLeft = rotateY.leftChild;
		rotateX.rightChild = yLeft;
		if (rotateY.leftChild != null) {
			yLeft.parent = rotateX;
		}
		rotateY.parent = rotateX.parent;
		if (rotateX.parent == null) {
			root = rotateY;
			rotateY.isRoot = true;
			rotateX.isRoot = false;
		} else if (rotateX == rotateX.parent.leftChild) {
			rotateX.parent.leftChild = rotateY;
		} else {
			rotateX.parent.rightChild = rotateY;
		}
		rotateY.leftChild = rotateX;
		rotateX.parent = rotateY;
	}

	/**
	 * Method that performs a rotateRight on a node in the tree
	 * 
	 * @param rotateX
	 *            - node to be rotated
	 */
	public void rotateRight(Node<Key> rotateY) {
		Node<Key> rotateX = rotateY.leftChild;
		Node<Key> xRight = rotateX.rightChild;
		rotateY.leftChild = xRight;
		if (xRight != null) {
			xRight.parent = rotateY;
		}
		rotateX.parent = rotateY.parent;
		if (rotateY.parent == null) {
			root = rotateX;
			rotateX.isRoot = true;
			rotateY.isRoot = false;
		} else if (rotateY == rotateY.parent.leftChild) {
			rotateY.parent.leftChild = rotateX;
		} else {
			rotateY.parent.rightChild = rotateX;
		}
		rotateX.rightChild = rotateY;
		rotateY.parent = rotateX;
	}

	/**
	 * Method that fixes the tree
	 * 
	 * @param current
	 *            - Node to start fixing from
	 */
	public void fixTree(Node<Key> current) {
		if (current == root) {
			current.color = 1;
			return; // tree is balanced
		}
		if (current.parent.color == 1) {
			return; // tree is balanced
		}
		if (current.color == 0 && current.parent.color == 0) {
			Node<Key> aunt = getAunt(current);
			Node<Key> gramps = getGrandparent(current);
			Node<Key> parent = current.parent;
			/**
			 * When g is added g is red, parent is red, uncle node is red push blackness
			 * from grandparent single rotate left
			 */

			if (aunt == null || aunt.color == 1) {

				if (gramps.leftChild == parent && parent.rightChild == current) {
					rotateLeft(parent);
					fixTree(parent);
				} else if (gramps.rightChild == parent && parent.leftChild == current) {
					rotateRight(parent);
					fixTree(parent);
				} else if (gramps.leftChild == parent && parent.leftChild == current) {
					parent.color = 1;
					gramps.color = 0;
					rotateRight(gramps);
					return; // tree is balanced
				} else if (gramps.rightChild == parent && parent.rightChild == current) {
					parent.color = 1;
					gramps.color = 0;
					rotateLeft(gramps);
					return; // tree is balanced
				}
			} else if (aunt.color == 0) {
				parent.color = 1;
				aunt.color = 1;
				gramps.color = 0;
				fixTree(gramps);
			}
		}
	}

	/**
	 * Method that checks if a node is empty
	 * 
	 * @param n
	 *            - node
	 * @return - true if empty, false if not
	 */
	public boolean isEmpty(Node<Key> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	/**
	 * Method that checks if a node is a left child
	 * 
	 * @param parent
	 *            - parent of node
	 * @param child
	 *            - node to be evaluated
	 * @return - true is left, false if not
	 */
	public boolean isLeftChild(Node<Key> parent, Node<Key> child) {
		if (child.compareTo(parent) < 0) {// child is less than parent
			return true;
		}
		return false;
	}

	/**
	 * Method that visits nodes in a preorder
	 * 
	 * @param v
	 *            - node to continue from
	 */
	public void preOrderVisit(Visitor<Key> v) {
		preOrderVisit(root, v);
	}

	/**
	 * Method that performs visit on the nodes
	 * 
	 * @param n
	 *            - node
	 * @param v
	 *            - visitor object
	 */
	private static <Key extends Comparable<Key>> void preOrderVisit(Node<Key> n, Visitor<Key> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}

	/**
	 * Getter method for the node
	 * 
	 * @return - root of the tree
	 */
	public Node<Key> root() {
		return root;
	}
}
