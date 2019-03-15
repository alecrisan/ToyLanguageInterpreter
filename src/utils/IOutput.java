package utils;

import java.util.List;

public interface IOutput<T> {
	
	public List<T> getOut();
	
	public void add(T t);
	
	public void remove(T e);
	
    public boolean isEmpty();
    
    public int size();

    public T get(int index) throws MyException;
    
    public void clear();

}
