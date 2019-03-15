package utils;

import java.util.HashMap;

public interface ISymTable<T1, T2> 
{
	
	public void add(T1 t1, T2 t2);
	
	public T2 lookup(T1 t1) throws MyException;
	
	public void update(T1 t1, T2 t2);
	
	boolean isDefined(T1 id);
	
	boolean contains(T1 v);
	
	public int size();
	
	public HashMap<T1, T2> getDictionary();	
	
	public void clear();
	
	public SymTable<T1,T2> copyTable();

}
