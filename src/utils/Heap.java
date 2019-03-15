package utils;

import java.util.HashMap;
import java.util.Map;

public class Heap<Integer> implements IHeap<Integer> {
	
	Map<Integer, Integer> heap;
	int freeLocation;
	
	public Heap()
	{
		this.heap = new HashMap<Integer, Integer>();
		this.freeLocation = 1;
	}
	
	@Override
	public void setFirstFree(int f)
	{
		this.freeLocation = f;
	}
	
	@Override
	public int getFirstFree()
	{
		return this.freeLocation;
	}
	
	@Override
	public void setContent(Map<Integer, Integer> map)
	{
		this.heap = map;
	}
	
	
	@Override
	public void add(Integer t1, Integer t2) 
	{
		// TODO Auto-generated method stub
	
		heap.put(t1, t2);
		
	}
	

	@Override
	public Integer lookup(Integer t1) throws MyException
	{
		// TODO Auto-generated method stub
		if(isDefined(t1))
			return heap.get(t1);
		else
			throw new MyException("Heap:No value defined for this key");
		
		
	}

	@Override
	public void update(Integer t1, Integer t2)
	{
		// TODO Auto-generated method stub
		if (heap.containsKey(t1)) 
            heap.put(t1, t2);
		
	}

	@Override
	public boolean isDefined(Integer id) 
	{
		// TODO Auto-generated method stub
		return heap.containsKey(id);
	}

	@Override
	public boolean contains(Integer v) 
	{
		// TODO Auto-generated method stub
		return heap.get(v) != null;
	}
	
	@Override
	public int size()
	{
		return heap.size();
	}
	
	 @Override
	 public String toString() 
	 {
		 String dictionaryString = "HEAP: \n";
		 
		 for(HashMap.Entry<Integer, Integer> e : heap.entrySet())
		 {
			 dictionaryString = dictionaryString + e.getKey().toString() 
					 + " --> " + e.getValue().toString() + '\n';
		 }
		 
		 if(heap.isEmpty())
			 return dictionaryString + "Empty\n";

		 return dictionaryString;
	 }

	 @Override
	 public Map<Integer, Integer> getHeap() 
	 {
		 return heap;
	 }
	 
	 @Override
	 public void clear()
	 {
		 this.heap.clear();

	 }
}
