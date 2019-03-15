package utils;

import java.util.HashMap;
import java.util.Map;

public interface IHeap<Integer> {
	
	public void add(Integer t1, Integer t2);
	
	public void setFirstFree(int f);
	
	public int getFirstFree();
	
	public void setContent(Map<Integer, Integer> map);
	
	public Integer lookup(Integer t1) throws MyException;
	
	public void update(Integer t1, Integer t2);
	
	boolean isDefined(Integer id);
	
	boolean contains(Integer v);
	
	public int size();
	
	public Map<Integer, Integer> getHeap();	
	
	public void clear();

}
