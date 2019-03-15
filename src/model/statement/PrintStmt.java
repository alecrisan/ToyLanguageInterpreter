package model.statement;

import model.ProgramState;

import model.expression.IExpression;
import utils.IHeap;
import utils.IOutput;
import utils.ISymTable;
import utils.MyException;


public class PrintStmt implements IStatement{
	
	IExpression exp;
	
	public PrintStmt(IExpression e)
	{
		this.exp = e;
	}
	
	
	@Override
	public ProgramState execute(ProgramState p)
	{
		ISymTable<String, Integer> t = p.getSymTable();
		IOutput<Integer> o = p.getOutput();
		IHeap<Integer> heap = p.getHeap();
		
		try
		{
			o.add(exp.eval(t, heap));
				
		}
		catch(MyException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	@Override
	public String toString()
	{ 
		return "print(" + exp.toString() +")";
	}

}
