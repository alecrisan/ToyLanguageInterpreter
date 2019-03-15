package utils;

import java.util.ArrayList;
import java.util.List;

public class Output<T> implements IOutput<T> {

	private List<T> list;

    public Output() 
    {
        list = new ArrayList<T>();
    }
    
    @Override
    public List<T> getOut()
    {
    	return list;
    }

    @Override
    public void add(T e) 
    {
        list.add(e);
    }

    @Override
    public void remove(T e) 
    {
        list.remove(e);
    }

    @Override
    public boolean isEmpty() 
    {
        return list.isEmpty();
    }

    @Override
    public int size() 
    {
        return list.size();
    }

    @Override
    public T get(int index) throws MyException
    {
        if (list.size() == 0)
            throw new MyException("Empty list!");
        
        return list.get(index);
    }

    @Override
    public String toString() 
    {
        String str = "OUTPUT: \n";
        for( T e : list)
        	str = str + e.toString() + '\n';
        
       if(list.isEmpty() == true)
    	   str = str + "Empty\n";
        
        return str;
        	
    }
    
    @Override 
    public void clear()
    {
    	this.list.clear();
    }
}
