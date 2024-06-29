package stack;

import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        stack stack = new stack();

        for(int i = 0; i < 5; i++){
            stack.push(i);
        }

        System.out.println(stack.toString());
        System.out.println(stack.peek());
    }
}

