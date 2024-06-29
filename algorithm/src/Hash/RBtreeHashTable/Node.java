package Hash.RBtreeHashTable;


class Node {
    Node next;
    final int hash;
    final Object key;
    Object value;

    Node(int hash, Object key, Object value) {
        this.hash = hash;
        this.key = key;
        this.value = value;
    }
}
