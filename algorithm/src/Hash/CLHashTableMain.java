package Hash;

import Hash.RBtreeHashTable.CLHashTable;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

public class CLHashTableMain {

    public static void main(String[] args) {

        CLHashTable clHashTable = new CLHashTable(4, true);
        for (int i = 1; i < 20; i++) {
            clHashTable.put("key" + i, i);
        }
        clHashTable.printHashTable();
        System.out.println(clHashTable.get("key15"));
        System.out.println(clHashTable.get("key19"));

        clHashTable.remove("key1");
        clHashTable.remove("key19");
        clHashTable.remove("key11");
        clHashTable.remove("key15");
        clHashTable.printHashTable();

    }

}
