package Tree.RBTree;

import Tree.RBTree.rbt.RedBlackTree;

public class Main {

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.add(5);
        redBlackTree.add(2);
        redBlackTree.add(9);
        redBlackTree.add(3);
        redBlackTree.add(7);
        redBlackTree.add(4);
        redBlackTree.add(8);
        redBlackTree.add(1);
        redBlackTree.add(6);
        redBlackTree.printTree();
        System.out.println("traversal");
        redBlackTree.traversal();

        System.out.println(redBlackTree.search(1));
    }
}
