package utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ExecStack<T> implements IExecStack<T>{
	
    private List<T> stack;

    public ExecStack() {
        stack = new ArrayList<>();
    }
    
    @Override
    public List<T> getStack()
    {
    	return stack;
    }

    @Override
    public void push(T e) {
        stack.add(e);
    }

    @Override
    public T pop() throws MyException
    {
        if (this.isEmpty())
            throw new MyException("The stack is empty!");
        
        T elem = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);

        return elem;
    }

    @Override
    public T peek() throws MyException
    {
        if (this.isEmpty())
            throw new MyException("The stack is empty!");
        return stack.get(stack.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() 
    {
        String str = "STACK: \n";
        for( T e: stack)
        	str = str + e.toString() + '\n';
        return str;
    }
    
    @Override
    public void clear()
    {
    	this.stack.clear();
    }
    

}
