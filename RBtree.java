import java.awt.Color;
import java.util.ArrayList;

public class RBtree<T extends Comparable<T>> {
	private Node root;

	public RBtree(T data) {
		root = new Node(data);
	}

	private void printTree(Node current) {
		if (current.left == null & current.right == null) {
			System.out.print(" leaf: " + current.data);
		}
		if (current.left != null) {
			printTree(current.left);
			System.out.print(" left: " + current.left.data);
		}
		if (current.right != null) {
			printTree(current.right);
			System.out.print(" right: " + current.right.data);
		}
	}

	public boolean lookUp(T key) {
		Node current = root;

		while (current != null) {
			T data = current.data;
			if (data.compareTo(key) == 0) {
				return true;
			} else {
				if (data.compareTo(key) > 0) {
					current = current.left;
				} else if (data.compareTo(key) < 0) {
					current = current.right;
				}
			}
		}
		return false;
	}

	public void addNode(String adding) {
		Node current = root;
		T key = (T) adding;
		Node addNode = new Node((T) key);
		while (current != null) {
			// If new data is bigger than current node
			if (key.compareTo(current.data) > 0) {
				if (current.right != null) {
					current = current.right;
				} else {
					current.right = addNode;
					addNode.parent = current;
					addNode.color = Color.RED;
					break;
				}
			} else if (key.compareTo(current.data) < 0) {
				if (current.left != null) {
					current = current.left;
				} else {
					current.left = addNode;
					addNode.parent = current;
					addNode.color = Color.RED;
					break;
				}
			}
		}
	}

	public Node getSibling(Node findSib) {
		Node current = root;
		String level = "null";
		while (current != null) {
			T data = current.data;
			int answer = data.compareTo(findSib.data);
			if (answer == 0) {
				if (current.parent == null) {
					System.out.println("Hit root");
					return null;
				}
				if (level.equals("right")) {
					return current.parent.left;
				}
				if (level.equals("left")) {
					return current.parent.right;
				}
			} else if (answer > 0) {
				current = current.left;
				level = "left";
			} else if (answer < 0) {
				current = current.right;
				level = "right";
			}
		}
		return null;
	}

	public Node getAunt(Node findAunt) {
		Node current = root;
		String level = "null";
		while (current != null) {
			T data = current.data;
			int answer = data.compareTo(findAunt.data);
			if (answer == 0) {
				if (current.parent == null) {
					System.out.println("Hit root");
					return null;
				}
				if (level.equals("right")) {
					return getSibling(current.parent.left);
				}
				if (level.equals("left")) {
					return getSibling(current.parent.right);
				}
			} else if (answer > 0) {
				current = current.left;
				level = "left";
			} else if (answer < 0) {
				current = current.right;
				level = "right";
			}
		}
		return null;

	}

	public Node getGrandparent(Node findGramps) {
		Node current = root;
		while (current != null) {
			int answer = current.data.compareTo(findGramps.data);
			if (answer == 0) {
				return current.parent.parent;
			} else if (answer > 0) {
				current = current.left;
			} else if (answer < 0) {
				current = current.right;
			}
		}
		return null;
	}

	public void rotateLeft(Node rotateX) {
		Node rotateY = rotateX.right;
		Node yLeft = rotateY.left;
		rotateY.left = rotateX;
		rotateX.right = yLeft;
		if (rotateX.parent.left.data == rotateX.data) {
			rotateX.parent.left = rotateY;
		} else {
			rotateX.parent.right = rotateY;
		}
		rotateY.parent = rotateX.parent;
		rotateX.parent = rotateY;
	}

	public void rotateRight(Node rotateY) {
		Node rotateX = rotateY.left;
		Node xRight = rotateX.right;
		xRight.right = rotateY;
		if (rotateY.parent.left.data == rotateY.data) {
			rotateY.parent.left = rotateX;
		} else {
			rotateY.parent.right = rotateX;
		}
		rotateX.parent = rotateY.parent;
		rotateY.parent = rotateX;
	}

	public void fixTree(Node current) {
		if (current == root) {
			current.color = Color.BLACK;
			return; // tree is balanced
		}
		if (current.parent.color == Color.BLACK) {
			return; // tree is balanced
		}

		if (current.color == Color.RED && current.parent.color == Color.RED) {
			Node aunt = getAunt(current);
			Node gramps = getGrandparent(current);
			Node parent = current.parent;
			if(aunt.data == null || aunt.color == Color.BLACK) {
				if(gramps.left == parent && parent.right == current) {
					rotateLeft(parent);
					fixTree(parent);
				}
				else if(gramps.right == parent && parent.left == current) {
					rotateRight(parent);
					fixTree(parent);
				}
				else if(gramps.left == parent && parent.left == current) {
					parent.color = Color.BLACK;
					gramps.color = Color.RED;
					rotateRight(gramps);
					return; // tree is balanced
				}
				else if(gramps.right == parent && parent.right == current) {
					parent.color = Color.BLACK;
					gramps.color = Color.RED;
					rotateLeft(gramps);
					return; // tree is balanced
				}
			}
			else if(aunt.color == Color.RED) {
				parent.color = Color.BLACK;
				aunt.color = Color.BLACK;
				gramps.color = Color.RED;
				fixTree(gramps);
			}
		}
	}

	public int compareTo(T o) {
		return (this.compareTo(o));

	}

	class Node {
		private Node left;
		private Node right;
		private T data;
		private Color color;
		private Node parent;

		public Node(T data) {
			this.data = data;
			left = null;
			left.color = Color.BLACK;
			right = null;
			right.color = Color.BLACK;
		}

		public Object data() {
			return data;
		}
	}

	/**
	 * // If this node is bigger than current if
	 * (findSib.data.compareTo(current.data) > 0) { if (current.right != null &&
	 * current.right.data.compareTo(findSib.data) == 0) { return current.left; }
	 * current = current.right; } else if (findSib.data.compareTo(current.data) < 0)
	 * { if (current.left != null && current.left.data.compareTo(findSib.data) == 0)
	 * { return current.right; } current = current.left; }
	 * 
	 * 
	 * 
	 * 
	 * Node current = root; String child = null; while (current != null) { if
	 * (current.right != null) { if (findAunt.data.compareTo(current.right.data) ==
	 * 0) { return (getSibling(current)); } else if
	 * (findAunt.data.compareTo(current.data) > 0) { current = current.right; } } if
	 * (current.left != null) { if (findAunt.data.compareTo(current.left.data) == 0)
	 * { return (getSibling(current)); }
	 * 
	 * else if (findAunt.data.compareTo(current.data) < 0) { current = current.left;
	 * } } } return null;
	 * 
	 * 
	 * 
	 * 
	public void initializeParent(Node current) {
		if (current.left != null) {
			current.left.parent = current;
			initializeParent(current.left);
		}
		if (current.right != null) {
			current.right.parent = current;
			initializeParent(current.right);
		}
	}
	 * 
	 * 
	 */

}
