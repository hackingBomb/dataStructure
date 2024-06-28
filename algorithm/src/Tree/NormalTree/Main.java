package Tree.NormalTree;

import Tree.NormalTree.normaltree.NormalTree;

public class Main {

    public static void main(String[] args) {
        NormalTree normalTree = new NormalTree();
        normalTree.addData(1);
        normalTree.addData(2);
        normalTree.addData(3);
        normalTree.addData(4);
        normalTree.addData(5);
        normalTree.addData(6);
        normalTree.addData(7);

        // TODO : 전위 순회
        System.out.println("전위 순회");
        normalTree.preOrderTraversal();
        // TODO : 중위 순회
        System.out.println("중위 순회");
        normalTree.inorderTraversal();
        // TODO : 후위 순회
        System.out.println("후위 순회");
        normalTree.postOrderTraversal();

    }
}
