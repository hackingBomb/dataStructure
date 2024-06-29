package Hash.RBtreeHashTable;


class SinglyLinkedList {

    private Node head;
    private int size = 0;

    private Node findNode(Object key) {
        // TODO : 동일한 key의 노드를 찾는다
        Node pointer = head;
        if (null == head) {
            return null;
        }
        do {
            // TODO : 동일한 키를 발견하면 해당 노드를 반환
            if (pointer.key.equals(key)) {
                return pointer;
            }
        } while ((pointer = pointer.next) != null);
        return null;
    }

    Object getValue(Object key) {
        Node node = findNode(key);
        if (null == node) {
            return null;
        }
        return node.value;
    }

    boolean isEmpty() {
        return 0 == size;
    }

    int getSize() {
        return size;
    }

    Node removeFirst() {
        // TODO : 첫 번째 노드 삭제하는 메서드
        Node node = head;
        if (null != node) {
            head = head.next;
            --size;
        }
        return node;
    }

    void add(int hash, Object key, Object value) {
        Node node = new Node(hash, key, value);

        if (null == head) {
            // TODO : 맨 처음 노드를 삽입하는 경우
            head = node;
            ++size;
        } else {
            Node pointer = head;
            do {
                if (pointer.key.equals(key)) {
                    pointer.value = value;
                    break;
                } else if (null == pointer.next){
                    pointer.next = node;
                    ++size;
                    break;
                } else {
                    pointer = pointer.next;
                }
            } while (true);
        }
    }

    void remove(Object key) {
        // TODO : 해당 key 해당하는 Node를 삭제한다.
        if (null != head && head.key.equals(key)) {
            head = head.next;
            --size;
        } else {
            Node pointer = head;
            // TODO : 다음 노드가 null도 아니고, 동일한 키가 아니면 포인터를 이동
            while (null != pointer.next && !pointer.next.key.equals(key)) {
                pointer = pointer.next;
            }
            // TODO : 다음 노드가 null은 아니나 찾는 키가 맞으면 노드의 연결을 끊는다
            if (null != pointer.next && pointer.next.key.equals(key)) {
                pointer.next = pointer.next.next;
                --size;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node pointer = head;
        stringBuilder.append("head").append(" -> ");
        while (null != pointer) {
            stringBuilder.append("(").append(pointer.key).append(",").append(pointer.value).append(")").append(" -> ");
            pointer = pointer.next;
        }
        stringBuilder.append("null");
        return stringBuilder.toString();
    }
}
