/**
 * Copyright Codelatte
 */
package Tree.RBTree.rbt;

import static Tree.RBTree.rbt.Node.BLACK;
import static Tree.RBTree.rbt.Node.RED;

public class RedBlackTree {

    private Node root;

    public void add(int key) {
        Node newNode = new Node();
        newNode.key = key;
        if (null == root) {
            root = newNode;
        } else {
            insertNode(root, newNode);
        }
        root.color = BLACK;
    }

    private void insertNode(Node x, Node newNode) {

        if (x.key > newNode.key && !isExist(x.left)) {
            x.left = newNode;
            newNode.parent = x;
        } else if (x.key <= newNode.key && !isExist(x.right)) {
            x.right = newNode;
            newNode.parent = x;
        } else if (x.key > newNode.key) {
            insertNode(x.left, newNode);
        } else {
            insertNode(x.right, newNode);
        }
        insertFixUp(x);
    }


    private boolean isRed(Node x) {
        return isExist(x) && RED == x.color;
    }

    private boolean isBlack(Node x) {
        return !isExist(x) || BLACK == x.color;
    }

    private boolean isExist(Node x) {
        return null != x;
    }

    private void insertFixUp(Node g) {
        if (isRed(g.right) && isRed(g.right.left) &&
                isBlack(g.left)) {
            // TODO : RL회전
            LL_rotate(g.right);
            swapColor(g, g.right);
            RR_rotate(g);
        } else if (isRed(g.right) && isRed(g.right.right) &&
                isBlack(g.left)) {
            // TODO : RR회전
            swapColor(g, g.right);
            RR_rotate(g);
        } else if (isRed(g.right) &&
                (isRed(g.right.right) || isRed(g.right.left))) {
            // TODO : recoloring
            g.color = RED;
            g.right.color = BLACK;
            g.left.color = BLACK;
        } else if (isRed(g.left) && isRed(g.left.right) &&
                isBlack(g.right)) {
            // TODO : LR회전
            RR_rotate(g.left);
            swapColor(g, g.left);
            LL_rotate(g);
        } else if (isRed(g.left) && isRed(g.left.left) &&
                isBlack(g.right)) {
            // TODO : LL회전
            swapColor(g, g.left);
            LL_rotate(g);
        } else if (isRed(g.left) &&
                (isRed(g.left.left) || isRed(g.left.right))) {
            // TODO : recoloring
            g.color = RED;
            g.left.color = BLACK;
            g.right.color = BLACK;
        }

    }

    private void swapColor(Node nodeA, Node nodeB) {
        int color = nodeA.color;
        nodeA.color = nodeB.color;
        nodeB.color = color;
    }

    public void remove(int key) {
        removeNode(root, key);
    }

    private void removeNode(Node x, int key) {
        if (null == x) {
            throw new RuntimeException("키를 찾을 수 없습니다.");
        } else if (x.key > key) {
            removeNode(x.left, key);
        } else if (x.key < key) {
            removeNode(x.right, key);
        } else {
            if (isExist(x.left)) {
                Node predecessor = getLargestNode(x.left);
                int removeKey = x.key;
                x.key = predecessor.key;
                predecessor.key = removeKey;
                removeNode(x.left, predecessor.key);
            } else if (isExist(x.right)) {
                Node successor = getSmallestNode(x.right);
                int removeKey = x.key;
                x.key = successor.key;
                successor.key = removeKey;
                removeNode(x.right, successor.key);
            } else {
                // x 노드가 삭제할 노드
                if (root == x) {
                    root = null;
                } else {
                    if (isBlack(x)) {
                        removeFixup(x);
                    }
                    // TODO : 연결 해제
                    if (x.parent.left == x) {
                        x.parent.left = null;
                    } else if (x.parent.right == x) {
                        x.parent.right = null;
                    }
                    x.parent = null;
                }
            }
        }
    }

    private void removeFixup(Node x) {
        // TODO : black Leaf 를 삭제하면 조정해야함
        while (x != root && isBlack(x)) {
            // TODO : 우측이 형제 노드
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (isRed(w)) {
                    // TODO : case 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    RR_rotate(x.parent);
                    w = x.parent.right;
                }
                if (isBlack(w.left) && isBlack(w.right)) {
                    // TODO : case 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (isBlack(w.right)) {
                        // TODO : case 3
                        w.left.color = BLACK;
                        w.color = RED;
                        LL_rotate(w);
                        w = x.parent.right;
                    }
                    // TODO : case 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    RR_rotate(x.parent);
                    break;
                }
            } else if (x == x.parent.right) {
                // TODO : 좌측이 형제 노드
                Node w = x.parent.left;
                if (isRed(w)) {
                    // TODO : case 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    LL_rotate(x.parent);
                    w = x.parent.left;
                }
                if (isBlack(w.left) && isBlack(w.right)) {
                    // TODO : case 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (isBlack(w.left)) {
                        // TODO : case 3
                        w.right.color = BLACK;
                        w.color = RED;
                        RR_rotate(w);
                        w = x.parent.left;
                    }
                    // TODO : case 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    LL_rotate(x.parent);
                    break;
                }
            }
        }
        // 변경된 x가 루트 노드이면서 빨강 노드인 경우가
        // 있기 때문에 마지막으로 x 노드를 검정 노드로 변경한다
        x.color = BLACK;
    }

    private Node getLargestNode(Node x) {
        if (null == x.right) {
            return x;
        }
        return getLargestNode(x.right);
    }

    private Node getSmallestNode(Node x) {
        if (null == x.left) {
            // TODO : 좌측 자식 노드가 존재하지 않는다. (가장 작은 노드)
            return x;
        }
        return getSmallestNode(x.left);
    }

    public int search(int key) {
        return searchNode(root, key).key;
    }

    private Node searchNode(Node parent, int key) {
        if (null == parent) {
            throw new RuntimeException("키를 찾을 수 없습니다.");
        } else if (parent.key > key) {
            parent = searchNode(parent.left, key);
        } else if (parent.key < key) {
            parent = searchNode(parent.right, key);
        }
        return parent;
    }

    private void LL_rotate(Node P) {
        /**
         * 축(L)을 중심으로 우측으로 끌어당긴다.
         *     GP(?) or GP(?)    GP(?) or GP(?)
         *         ↘    ↙          ↘    ↙
         *          P(4)    =>      L(2)
         *        ↙                ↙    ↘
         *     L(2)             LL(1)    P(4)
         *    ↙    ↘                    ↙
         * LL(1)   LR(3)            LR(3)
         */
        // TODO : 아래(부모 -> 자식)로 향하는 포인터 재설정
        Node GP = P.parent;
        Node L = P.left;
        Node LR = L.right;
        L.right = P;
        P.left = LR;

        // TODO : 위(자식 -> 부모)로 향하는 포인터 재설정
        P.parent = L;
        if (isExist(LR)) {
            LR.parent = P;
        }

        // TODO : 위(부모 -> 조부모)로 향하는 포인터 재설정
        L.parent = GP;

        // TODO : 아래(조부모->부모)로 향하는 포인터 재설정
        if (!isExist(GP)) {
            this.root = L;
        } else {
            /**
             * 만약에 조부모가 있을경우
             * 부모가 조부모의 어느 자식(좌,우)인지 모른다.
             * 조부모의 이전 자식이 부모 노드였으므로 동일한
             * 링크를 찾으면 조부모의 어느 자식(좌,우)인지 찾을 수 있다.
             */
            if (GP.right == P) {
                GP.right = L;
            } else if (GP.left == P) {
                GP.left = L;
            }
        }
    }

    private void RR_rotate(Node P) {
        /**
         * 축을(R) 중심으로 좌측으로 끌어당긴다.
         * GP(?) or GP(?)       GP(?) or GP(?)
         *     ↘    ↙               ↘    ↙
         *      P(1)        =>       R(3)
         *         ↘                ↙    ↘
         *          R(3)        P(1)      RR(4)
         *         ↙    ↘           ↘
         *      RL(2)   RR(4)        RL(2)
         */
        // TODO : 아래(부모 -> 자식)로 향하는 포인터 재설정
        Node GP = P.parent;
        Node R = P.right;
        Node RL = R.left;
        R.left = P;
        P.right = RL;

        // TODO : 위(자식 -> 부모)로 향하는 포인터 재설정
        P.parent = R;
        if (isExist(RL)) {
            RL.parent = P;
        }

        // TODO : 위(부모 -> 조부모)로 향하는 포인터 재설정
        R.parent = GP;

        // TODO : 아래(조부모->부모)로 향하는 포인터 재설정
        if (!isExist(GP)) {
            this.root = R;
        } else {
            /**
             * 만약에 조부모가 있을경우
             * 부모가 조부모의 어느 자식(좌,우)인지 모른다.
             * 조부모의 이전 자식이 부모 노드였으므로 동일한
             * 링크를 찾으면 조부모의 어느 자식(좌,우)인지 찾을 수 있다.
             */
            if (GP.right == P) {
                GP.right = R;
            } else if (GP.left == P) {
                GP.left = R;
            }
        }
    }

    private void printHelper(Node root, String indent, boolean last) {
        if (root != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            String sColor = root.color == RED ? "RED" : "BLACK";
            System.out.println(root.key + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }

    public void printTree() {
        printHelper(this.root, "", true);
    }

    public void traversal() {
        inorderTraversal(root);
        System.out.println("");
    }

    private void inorderTraversal(Node node) {
        if (null == node) {
            return;
        }
        inorderTraversal(node.left);
        System.out.printf("%d ", node.key);
        inorderTraversal(node.right);
    }
}
