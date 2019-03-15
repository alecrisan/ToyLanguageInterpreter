package model.statement;

import java.io.IOException;

import model.ProgramState;
import utils.ExecStack;
import utils.IExecStack;
import utils.ISymTable;
import utils.MyException;

public class ForkStmt implements IStatement 
{
	
	IStatement stmt;
	static int counter = 0;
	
	public ForkStmt(IStatement s)
	{
		stmt = s;
	}

	@Override
	public ProgramState execute(ProgramState p) throws MyException, IOException 
	{
		ISymTable<String, Integer> table = p.getSymTable().copyTable();
		IExecStack<IStatement> stack = new ExecStack<IStatement> ();
		
		ProgramState prg = new ProgramState(p.getId()*10 + counter, stmt, stack, table, p.getOutput(), p.getFileTable(), p.getHeap());
		
		counter = counter + 1;
		return prg;
	}
	
	@Override
	public String toString()
	{
		return "fork(" + stmt.toString() + ")";
	}
	

}
