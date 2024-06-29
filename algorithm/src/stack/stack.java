package stack;

public class stack {
    private int top = -1;
    private Object[] stack;

    public stack(int size){
        stack = new Object[size];
    }

    public stack(){
        stack = new Object[5];
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public boolean isFull(){
        return top == stack.length - 1;
    }

    public void push(Object value){
        if(isFull()){
            throw new RuntimeException("Stack is Full");
        }
        stack[++top] = value;
    }

    public Object pop(){
        if(isEmpty()){
            throw new RuntimeException("Stack is empty");
        }
        return stack[top--];
    }

    public Object peek(){
        if(isEmpty()){
            System.out.println("Stack is empty");
        } else if(isFull()){
            System.out.println("Stack is Full");
        }
        return stack[top];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Bottom | ");
        for(int i = 0; i <= top; i++){
            sb.append(stack[i].toString() + " | ");
        }
        sb.append("Top");
        return sb.toString();
    }
}
