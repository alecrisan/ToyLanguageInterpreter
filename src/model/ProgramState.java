package model;

import java.io.IOException;

import model.statement.IStatement;


import utils.IExecStack;
import utils.IFileTable;
import utils.IHeap;
import utils.ISymTable;
import utils.MyException;
import utils.Pair;
import utils.IOutput;

public class ProgramState {
	
	int id;
	IStatement stmt;
	IExecStack<IStatement> execStack;
	ISymTable<String, Integer> symTable;
	IFileTable<Integer, Pair> filetable;
	IOutput<Integer> output;
	IHeap<Integer> heap;
	
	public ProgramState(int i, IStatement s, IExecStack<IStatement> e, ISymTable<String, Integer> t, IOutput<Integer> o, IFileTable<Integer, Pair> f,
			IHeap<Integer> h)
	{
		id = i;
		execStack = e;
		symTable = t;
		output = o;
		filetable = f;
		stmt = s;
		heap = h;
		this.execStack.push(stmt);
	}
	
	public int getId()
	{
		return id;
	}
	
	public IStatement getStatement()
	{
		return stmt;
	}
	

	public IExecStack<IStatement> getStack()
	{
		return execStack;
	}
	
	public ISymTable<String, Integer> getSymTable()
	{
		return symTable;
	}
	
	public IFileTable<Integer, Pair> getFileTable()
	{
		return filetable;
	}
	
	public IOutput<Integer> getOutput()
	{
		return output;
	}
	
	public IHeap<Integer> getHeap()
	{
		return heap;
	}
	
	public Boolean isNotCompleted()
	{
		if(execStack.isEmpty())
			return false;
		
		return true;
	}
	
	public ProgramState execOneStep() throws MyException, IOException
	{
		if(execStack.isEmpty()) 
				throw new MyException("Empty stack!");
		
		IStatement crtStmt = execStack.pop();
		
		return crtStmt.execute(this);
	}
	
	@Override
	public String toString()
	{
		return '\n' + "ID: " + Integer.toString(id) + '\n' + stmt.toString()+ '\n'+ execStack.toString() + '\n' + symTable.toString() + '\n' + output.toString() + '\n' + filetable.toString() + '\n'
				+ heap.toString() + '\n';
	}
	

}
