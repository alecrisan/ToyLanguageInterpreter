package utils;

import java.util.HashMap;
import java.util.Map.Entry;

import utils.MyException;

public class SymTable<T1, T2> implements ISymTable<T1, T2>{

	HashMap<T1, T2> dictionary;
	
	public SymTable()
	{
		this.dictionary = new HashMap<T1, T2>();
	}
	
	@Override
	public void add(T1 t1, T2 t2) 
	{
		// TODO Auto-generated method stub
	
		dictionary.put(t1, t2);
		
	}
	

	@Override
	public T2 lookup(T1 t1) throws MyException
	{
		// TODO Auto-generated method stub
		if(isDefined(t1))
			return dictionary.get(t1);
		else
			throw new MyException("SymTable:No value defined for this key");
		
		
	}

	@Override
	public void update(T1 t1, T2 t2)
	{
		// TODO Auto-generated method stub
		if (dictionary.containsKey(t1)) 
            dictionary.put(t1, t2);
		
	}

	@Override
	public boolean isDefined(T1 id) 
	{
		// TODO Auto-generated method stub
		return dictionary.containsKey(id);
	}

	@Override
	public boolean contains(T1 v) 
	{
		// TODO Auto-generated method stub
		return dictionary.get(v) != null;
	}
	
	@Override
	public int size()
	{
		return dictionary.size();
	}
	
	 @Override
	 public String toString() 
	 {
		 String dictionaryString = "SYMTABLE: \n";
		 
		 for(HashMap.Entry<T1, T2> e : dictionary.entrySet())
		 {
			 dictionaryString = dictionaryString + e.getKey().toString() 
					 + " --> " + e.getValue().toString() + '\n';
		 }
		 
		 if(dictionary.isEmpty())
			 return dictionaryString + "Empty\n";

		 return dictionaryString;
	 }

	 @Override
	 public HashMap<T1, T2> getDictionary() 
	 {
		 return dictionary;
	 }
	 
	 @Override
	 public void clear()
	 {
		 this.dictionary.clear();
	 }
	 
	 @Override
	 public SymTable<T1,T2> copyTable()
	 {
		 SymTable<T1, T2> copy = new SymTable<T1, T2> ();
		 
		 for(Entry<T1, T2> e : this.dictionary.entrySet())
		 {
			 T1 key = e.getKey();
			 T2 value = e.getValue();
			 copy.add(key, value);
		 }
		 
		 return copy;
	 }
	
	

}
