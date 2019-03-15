package model.statement;

import model.ProgramState;

import utils.IExecStack;

public class CompoundStmt implements IStatement{
	
	IStatement first;
	IStatement second;
	
	public CompoundStmt(IStatement f, IStatement s)
	{
		this.first = f;
		this.second = s;
	}
	
	@Override
	public ProgramState execute(ProgramState p)
	{
		IExecStack<IStatement> s = p.getStack();
		s.push(second);
		s.push(first);
		return null;
	}
	
	@Override 
	public String toString()
	{
		return "("+first.toString() + ";" + second.toString()+")";
	}

}
