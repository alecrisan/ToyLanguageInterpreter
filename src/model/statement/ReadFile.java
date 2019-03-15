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

public class ReadFile implements IStatement {
	
	IExpression exp_file_id;
	String var_name;
	
	public ReadFile(IExpression ex, String var)
	{
		exp_file_id = ex;
		var_name = var;
	}
	
	@Override
	public String toString() 
	{
		return var_name + " --> " + exp_file_id.toString();
	}

	
	@Override
	public ProgramState execute(ProgramState p) throws MyException, IOException
	{
		ISymTable<String, Integer> symtable = p.getSymTable();
		IFileTable<Integer, Pair> fileTable = p.getFileTable();
		IHeap<Integer> heap = p.getHeap();
		
		int value;
		int e = this.exp_file_id.eval(symtable, heap);
		Pair pair = fileTable.lookup(Integer.valueOf(e));
		BufferedReader bf = pair.getBF();
		String read = bf.readLine();
		if (read == null)
			value = 0;
		else
			value = Integer.parseInt(read);
		if(symtable.contains(var_name))
			symtable.update(var_name, value);
		else
			symtable.add(var_name, value);
		
		return null;
	}
}
