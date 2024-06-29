package Hash.RBtreeHashTable;



import static Hash.RBtreeHashTable.TreeNode.BLACK;
import static Hash.RBtreeHashTable.TreeNode.RED;

class RedBlackTree {

    private TreeNode root;
    private int size;

    void add(int hash, Object key, Object value) {
        TreeNode newTreeNode = new TreeNode(hash, key, value);
        if (null == root) {
            root = newTreeNode;
        } else {
            insertNode(root, newTreeNode);
        }
        ++size;
        root.color = BLACK;
    }

    private void insertNode(TreeNode x, TreeNode newNode) {
        /**
         * 삽입 시 해시값을 기준으로 삽입하되 동일한 키인 경우 값만 덮어써야 한다.
         */
        if (x.key.equals(newNode.key)) {
            // TODO : 동일한 키인 경우 값만 덮어쓴다.
            x.value = newNode.value;
        } else if (x.hash > newNode.hash && !isExist(x.left)) {
            x.left = newNode;
            newNode.parent = x;
        } else if (x.hash <= newNode.hash && !isExist(x.right)) {
            x.right = newNode;
            newNode.parent = x;
        } else if (x.hash > newNode.hash) {
            insertNode(x.left, newNode);
        } else {
            insertNode(x.right, newNode);
        }
        insertFixUp(x);
    }

    int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return null == root;
    }

    private boolean isRed(TreeNode x) {
        return isExist(x) && RED == x.color;
    }

    private boolean isBlack(TreeNode x) {
        return !isExist(x) || BLACK == x.color;
    }

    private boolean isExist(TreeNode x) {
        return null != x;
    }

    private void insertFixUp(TreeNode g) {
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

    private void swapColor(TreeNode TreeNodeA, TreeNode TreeNodeB) {
        int color = TreeNodeA.color;
        TreeNodeA.color = TreeNodeB.color;
        TreeNodeB.color = color;
    }

    void remove(Object key) {
        int hash = CLHashTable.secondaryHash(key);
        removeNode(root, hash, key);
    }

    TreeNode removeFirst() {
        TreeNode treeNode = root;
        if (null == treeNode) {
            return null;
        }
        TreeNode tempTreeNode = new TreeNode(treeNode.hash, treeNode.key, treeNode.value);
        removeNode(root, treeNode.hash, treeNode.key);
        return tempTreeNode;
    }

    private void removeNode(TreeNode x, int hash, Object key) {
        // TODO : 해시값을 이용하여 1차적으로 노드를 찾고 키와 비교한다.
        if (null == x) {
            return;
        } else if (x.hash > hash) {
            removeNode(x.left, hash, key);
        } else if (x.hash < hash) {
            removeNode(x.right, hash, key);
        } else {
            /**
             * 해시값은 같으나 키는 다를 수 있다.
             * 그럴 경우 우측 자식 또는 좌측 자식이 동일한 해시값이다
             * 우측 또는 좌측 자식에서 다시 탐색한다
             */
            if (!x.key.equals(key)) {
                if (isExist(x.left) && x.left.hash == hash) {
                    removeNode(x.left, hash, key);
                }
                if (isExist(x.right) && x.right.hash == hash) {
                    removeNode(x.right, hash, key);
                }
            } else if (isExist(x.left)) {
                TreeNode predecessor = getLargestTreeNode(x.left);
                swap(predecessor, x);
                removeNode(x.left, predecessor.hash, predecessor.key);
            } else if (isExist(x.right)) {
                TreeNode successor = getSmallestTreeNode(x.right);
                swap(successor, x);
                removeNode(x.right, successor.hash, successor.key);
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
                --size;
            }
        }
    }

    private void swap(TreeNode a, TreeNode b) {
        Object tempKey = a.key;
        Object tempValue = a.value;
        int tempHash = a.hash;

        a.key = b.key;
        a.value = b.value;
        a.hash = b.hash;

        b.key = tempKey;
        b.value = tempValue;
        b.hash = tempHash;
    }

    private void removeFixup(TreeNode x) {
        // TODO : black Leaf 를 삭제하면 조정해야함
        while (x != root && isBlack(x)) {
            // TODO : 우측이 형제 노드
            if (x == x.parent.left) {
                TreeNode w = x.parent.right;
                if (isRed(w)) {
                    // case 1
                    w.color = BLACK;
                    x.parent.color = RED;
                    RR_rotate(x.parent);
                    w = x.parent.right;
                }
                if (isBlack(w.left) && isBlack(w.right)) {
                    // case 2
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (isBlack(w.right)) {
                        // case 3
                        w.left.color = BLACK;
                        w.color = RED;
                        LL_rotate(w);
                        w = x.parent.right;
                    }
                    // case 4
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    RR_rotate(x.parent);
                    break;
                }
            } else if (x == x.parent.right) {
                // TODO : 좌측이 형제 노드
                TreeNode w = x.parent.left;
                if (isRed(w)) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    LL_rotate(x.parent);
                    w = x.parent.left;
                }
                if (isBlack(w.left) && isBlack(w.right)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (isBlack(w.left)) {
                        // case 3
                        w.right.color = BLACK;
                        w.color = RED;
                        RR_rotate(w);
                        w = x.parent.left;
                    }
                    // case 4
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

    private TreeNode getLargestTreeNode(TreeNode x) {
        if (null == x.right) {
            return x;
        }
        return getLargestTreeNode(x.right);
    }

    private TreeNode getSmallestTreeNode(TreeNode x) {
        if (null == x.left) {
            // TODO : 좌측 자식 노드가 존재하지 않는다. (가장 작은 노드)
            return x;
        }
        return getSmallestTreeNode(x.left);
    }


    Object getValue(Object key) {
        int hash = CLHashTable.secondaryHash(key);
        TreeNode treeNode = searchTreeNode(root, hash, key);
        if (null == treeNode) {
            return null;
        }
        return treeNode.value;
    }

    private TreeNode searchTreeNode(TreeNode x, int hash, Object key) {
        // TODO : 해시값을 이용하여 1차적으로 노드를 찾고 키와 비교한다.
        if (null == x) {
            return null;
        } else if (x.hash > hash) {
            x = searchTreeNode(x.left, hash, key);
        } else if (x.hash < hash) {
            x = searchTreeNode(x.right, hash, key);
        } else if (x.key.equals(key)) {
            return x;
        } else {
            /**
             * 해시값은 같으나 키는 다를 수 있다.
             * 그럴 경우 우측 자식 또는 좌측 자식이 동일한 해시값이다
             * 우측 또는 좌측 자식에서 다시 탐색한다
             */
            if (isExist(x.left) && x.left.hash == hash) {
                TreeNode node = searchTreeNode(x.left, hash, key);
                if (node.key.equals(key)) {
                    x = node;
                }
            }
            if (isExist(x.right) && x.right.hash == hash) {
                TreeNode node = searchTreeNode(x.right, hash, key);
                if (node.key.equals(key)) {
                    x = node;
                }
            }
        }
        return x;
    }

    private void LL_rotate(TreeNode P) {
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
        TreeNode GP = P.parent;
        TreeNode L = P.left;
        TreeNode LR = L.right;
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

    private void RR_rotate(TreeNode P) {
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
        TreeNode GP = P.parent;
        TreeNode R = P.right;
        TreeNode RL = R.left;
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

    void traversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(TreeNode node) {
        if (null == node) {
            return ;
        }
        inorderTraversal(node.left);
        System.out.printf("(%s,%s) ", node.key, node.value);
        inorderTraversal(node.right);
    }
}
