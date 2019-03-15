package model.statement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import model.ProgramState;
import utils.IExecStack;
import utils.IFileTable;
import utils.ISymTable;
import utils.MyException;
import utils.Pair;

public class OpenRFile implements IStatement{

	String var_file_id;
	String filename;
	
	public OpenRFile(String var, String fn)
	{
		var_file_id = var;
		filename = fn;
	}
	
	@Override
	public String toString() 
	{
		return var_file_id + " --> " + filename;
	}
	
	@Override
	public ProgramState execute(ProgramState p) throws MyException 
	{
		
		ISymTable<String, Integer> symtable = p.getSymTable();
		IFileTable<Integer, Pair> fileTable = p.getFileTable();
		
		if(!fileTable.contains(filename)) 
		{
			try 
			{
				BufferedReader bf = new BufferedReader(new FileReader(filename));
				fileTable.add(Integer.valueOf(fileTable.getDescriptor()+1), new Pair(filename, bf));
				symtable.add(var_file_id, fileTable.getDescriptor()+1);
				fileTable.setDescriptor(fileTable.getDescriptor()+1);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		
		}
		else
			throw new MyException("Filename already in the file table!\n");
		
		return null;
	}

}
