package utils;

import java.util.HashMap;

public interface IFileTable<Integer, Pair> {
	
	public void add(Integer key, Pair value);
	
	public Pair lookup(Integer key) throws MyException;
	
	boolean contains(String fn);
	
	int getDescriptor();
	
	void setDescriptor(int fd);
	
	void delete(Integer key);
	
	public int size();

	public void clear();
	
	public HashMap<Integer, Pair> getFileTable();

}
