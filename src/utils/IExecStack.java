package utils;

import java.util.List;

public interface IExecStack<T> {
	
	public List<T> getStack();
	
	public void push(T t);
	
	public T pop() throws MyException;
	
	public T peek() throws MyException;
	
	public boolean isEmpty();
	
	public void clear();

}
