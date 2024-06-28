package Tree.NormalTree.normaltree;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.*;

public class NormalTree {

    private Node root;

    public void addData(int key) {
        Node newNode = new Node();
        newNode.key = key;
        if (null == root) {
            root = newNode;
        } else {
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node x = queue.poll();
                if (null != x.left) {
                    queue.offer(x.left);
                } else {
                    x.left = newNode;
                    break;
                }
                if (null != x.right) {
                    queue.offer(x.right);
                } else {
                    x.right = newNode;
                    break;
                }
            }
        }
    }

    public void levelOrder() {
        // TODO : 순회
        if (null == root) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node parentNode = queue.poll();

            System.out.printf("%d ", parentNode.key);

            if (null != parentNode.left) {
                queue.offer(parentNode.left);
            }
            if (null != parentNode.right) {
                queue.offer(parentNode.right);
            }
        }
        System.out.println("");
    }

    public void inorderTraversal() {
        // TODO : 중위 순회
        inOrder(root);
        System.out.println("");
    }

    public void preOrderTraversal() {
        // TODO : 전위 순회
        preOrder(root);
        System.out.println("");
    }

    public void postOrderTraversal() {
        // TODO : 후위 순회
        postOrder(root);
        System.out.println("");
    }

    private void preOrder(Node node) {
        // TODO : 전위 순회
        if (null == node) {
            return;
        }
        visit(node);
        preOrder(node.left);
        preOrder(node.right);
    }

    private void inOrder(Node node) {
        // TODO : 중위 순회
        if (null == node) {
            return;
        }
        inOrder(node.left);
        visit(node);
        inOrder(node.right);
    }

    private void postOrder(Node node) {
        // TODO : 후위 순회
        if (null == node) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        visit(node);
    }

    private void visit(Node node) {
        System.out.printf("%d ", node.key);
    }

    private void printHelper(Node x, String indent, boolean last) {
        if (x != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            System.out.println(x.key);
            printHelper(x.left, indent, false);
            printHelper(x.right, indent, true);
        }
    }

    public void printTree() {
        printHelper(this.root, "", true);
    }

}