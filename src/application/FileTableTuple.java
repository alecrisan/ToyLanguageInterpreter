package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileTableTuple {
	
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty filename;
	
	public FileTableTuple(Integer i, String s)
	{
		id = new SimpleIntegerProperty(i);
		filename = new SimpleStringProperty(s);
	}
	
	public FileTableTuple() {
		// TODO Auto-generated constructor stub
		id = new SimpleIntegerProperty();
		filename = new SimpleStringProperty();
	}

	public String getFilename()
	{
		return filename.get();
	}
	
	public Integer getId()
	{
		return id.get();
	}

	public void setFilename(String f)
	{
		filename.set(f);
	}
	
	public void setId(Integer i)
	{
		id.set(i);
	}

}
