package utils;

import java.util.HashMap;

@SuppressWarnings("hiding")
public class FileTable<Integer, Pair> implements IFileTable<Integer, Pair>{

	int fd;
	HashMap<Integer, Pair> table;
	
	public FileTable()
	{
		fd = 0;
		table = new HashMap<Integer, Pair>();
	}
	
	@Override
	public void add(Integer key, Pair value) {
		// TODO Auto-generated method stub
		this.table.put(key, value);
		
	}
	@Override
	public Pair lookup(Integer key) throws MyException {
		// TODO Auto-generated method stub
		if(this.table.get(key) != null)
			return (Pair)table.get(key);
		else
		{
			throw new MyException("File not opened!");
		}
	}
	@Override
	public boolean contains(String fn) {
		// TODO Auto-generated method stub
		for(HashMap.Entry<Integer, Pair> e: table.entrySet())
		{
			Pair p = e.getValue();
			String f = ((utils.Pair)p).getFilename();
			if(f.equals(fn) == true)
				return true;
		}
		return false;
		
	}
	@Override
	public int getDescriptor() { 
		// TODO Auto-generated method stub
		return this.fd;
	}
	@Override
	public void setDescriptor(int fd) {
		// TODO Auto-generated method stub
		this.fd = fd;
		
	}
	@Override
	public void delete(Integer key) {
		// TODO Auto-generated method stub
		this.table.remove(key); 
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.table.size();
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.table.clear();
	}
	
	 @Override
	 public String toString() 
	 {
		 String dictionaryString = "FILETABLE: \n";
		 
		 for(HashMap.Entry<Integer, Pair> e : table.entrySet())
		 {
			 dictionaryString = dictionaryString + e.getKey().toString() 
					 + " --> " + e.getValue().toString() + '\n';
		 }
		 
		 if(table.isEmpty())
			 return dictionaryString + "Empty\n";

		 return dictionaryString;
	 }
	
	 public HashMap<Integer, Pair> getFileTable()
	 {
		 return table;
	 }
	
	
}
