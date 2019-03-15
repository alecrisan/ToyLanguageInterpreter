package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SymTableTuple {
	
	private final SimpleStringProperty varName;
	private final SimpleIntegerProperty value;
	
	public SymTableTuple(String s, Integer i)
	{
		varName = new SimpleStringProperty(s);
		value = new SimpleIntegerProperty(i);
	}
	
	public SymTableTuple() {
		// TODO Auto-generated constructor stub
		varName = new SimpleStringProperty();
		value = new SimpleIntegerProperty();
	}

	public String getVarName()
	{
		return varName.get();
		
	}
	
	public Integer getValue()
	{
		return value.get();
	}

	public void setVarName(String var)
	{
		varName.set(var);
	}
	
	public void setValue(Integer v)
	{
		value.set(v);
	}
}
