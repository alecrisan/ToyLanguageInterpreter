package model.statement;

import java.io.BufferedReader;
import java.io.IOException;

import model.ProgramState;
import model.expression.IExpression;
import utils.IExecStack;
import utils.IFileTable;
import utils.IHeap;
import utils.ISymTable;
import utils.MyException;
import utils.Pair;

public class CloseRFile implements IStatement{
	
	IExpression exp_file_id;
	
	public CloseRFile(IExpression e)
	{
		exp_file_id = e;
	}
	
	@Override
	public String toString() 
	{
		return exp_file_id.toString();
	}
	
	@Override
	public ProgramState execute(ProgramState p) 
	{
		
		ISymTable<String, Integer> symtable = p.getSymTable();
		IFileTable<Integer, Pair> fileTable = p.getFileTable();
		IHeap<Integer> heap = p.getHeap();
		
		try 
		{
			int e = this.exp_file_id.eval(symtable, heap);
			Pair pair = fileTable.lookup(Integer.valueOf(e));
			BufferedReader bf = pair.getBF();
			bf.close();
			fileTable.delete(Integer.valueOf(e));
		}
		catch (MyException e) 
		{
			System.out.println(e.getMessage());
		} 
		catch (IOException e1) 
		{
			
			e1.printStackTrace();
		}
		
		return null;
	}

}
