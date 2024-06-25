package application;

class SplayTree {
    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    private Node root;

    public SplayTree() {
        root = null;
    }

    private Node rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    private Node splay(Node root, int key) {
        if (root == null || root.key == key)
            return root;

        if (root.key > key) {
            if (root.left == null) return root;

            if (root.left.key > key) {
                root.left.left = splay(root.left.left, key);
                root = rightRotate(root);
            } else if (root.left.key < key) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null)
                    root.left = leftRotate(root.left);
            }

            return (root.left == null) ? root : rightRotate(root);
        } else {
            if (root.right == null) return root;

            if (root.right.key > key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null)
                    root.right = rightRotate(root.right);
            } else if (root.right.key < key) {
                root.right.right = splay(root.right.right, key);
                root = leftRotate(root);
            }

            return (root.right == null) ? root : leftRotate(root);
        }
    }

    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }
        root = splay(root, key);
        if (root.key == key) return;

        Node newNode = new Node(key);
        if (root.key > key) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        } else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }
        root = newNode;
    }

    public void delete(int key) {
        if (root == null) return;
        root = splay(root, key);
        if (key != root.key) return;

        if (root.left == null) {
            root = root.right;
        } else {
            Node temp = root;
            root = splay(root.left, key);
            root.right = temp.right;
        }
    }

    public Node search(int key) {
        root = splay(root, key);
        return (root != null && root.key == key) ? root : null;
    }

    public void inorder() {
        inorderHelper(root);
    }

    private void inorderHelper(Node root) {
        if (root != null) {
            inorderHelper(root.left);
            System.out.print(root.key + " ");
            inorderHelper(root.right);
        }
    }
}
public class SplayTreeProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SplayTree tree = new SplayTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        System.out.println("Inorder traversal of the modified Splay tree is:");
        tree.inorder();

        tree.search(20);
        System.out.println("\nInorder traversal after searching 20:");
        tree.inorder();

        tree.delete(30);
        System.out.println("\nInorder traversal after deleting 30:");
        tree.inorder();

	}

}
