package Tree.bTree.btree;

import java.util.ArrayList;
import java.util.List;

class Node {

    List<Integer> keys;
    List<Node> nodes;

    Node(int t) {
        this.keys = new ArrayList<>(2 * t);
        this.nodes = new ArrayList<>(2 * t);
    }


}
