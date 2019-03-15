package model.statement;

import model.ProgramState;

import model.expression.IExpression;
import utils.IHeap;
//import utils.IExecStack;
import utils.ISymTable;
import utils.MyException;

public class AssignStmt implements IStatement{
	
	String id;
	IExpression exp;
	
	public AssignStmt(String s, IExpression e)
	{
		this.id = s;
		this.exp = e;
	}
	
	@Override
	public ProgramState execute(ProgramState p) throws MyException
	{
		ISymTable<String, Integer> symt= p.getSymTable();
		IHeap<Integer> heap = p.getHeap();
		
		int result = exp.eval(symt, heap);
		
		if (symt.isDefined(id)) 
			symt.update(id, result);
		else 
			symt.add(id, result);
			
		return null;
	}
	
	@Override
	public String toString()
	{ 
		return id + " = " + exp.toString();
	}
	

}
