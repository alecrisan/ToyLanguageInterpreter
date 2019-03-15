package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeapTuple {
	
	private final SimpleIntegerProperty address;
	private final SimpleIntegerProperty content;
	
	public HeapTuple(Integer a, Integer c)
	{
		address = new SimpleIntegerProperty(a);
		content = new SimpleIntegerProperty(c);
	}
	
	public HeapTuple() {
		// TODO Auto-generated constructor stub
		address = new SimpleIntegerProperty();
		content = new SimpleIntegerProperty();
	}

	public Integer getAddress()
	{
		return address.get();
		
	}
	
	public Integer getContent()
	{
		return content.get();
	}

	public void setAddress(Integer a)
	{
		address.set(a);
	}
	
	public void setContent(Integer c)
	{
		content.set(c);
	}

}
