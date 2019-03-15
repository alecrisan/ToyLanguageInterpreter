package model.statement;

import model.statement.IStatement;
import utils.IExecStack;
import utils.IHeap;
import utils.ISymTable;
import utils.MyException;
import model.ProgramState;
import model.expression.IExpression;

public class ConditionalStmt implements IStatement{
	
	IExpression exp;
	IStatement thenS;
	IStatement elseS;
	
	public ConditionalStmt(IExpression e, IStatement t, IStatement el)
	{
		exp = e;
		thenS = t;
		elseS = el;
	}
	
	@Override
	public String toString()
	{ 
		return "IF("+ exp.toString()+ ") THEN(" + thenS.toString()
	+ ") ELSE(" + elseS.toString()+ ")" ;
	}
	
	@Override
	public ProgramState execute(ProgramState p)
	{
		ISymTable<String, Integer> t = p.getSymTable();
		IExecStack<IStatement> s = p.getStack();
		IHeap<Integer> heap = p.getHeap();
		
		try
		{
			if(exp.eval(t, heap) != 0)
				s.push(thenS);
			else
				s.push(elseS);
		}
		catch(MyException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	

}
