package model.expression;

import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public class VarExpr implements IExpression {
	
	String id;
	
	public VarExpr(String s)
	{
		id = s;
	}
	
	@Override
	public int eval(ISymTable<String, Integer> symt, IHeap<Integer> heap) throws MyException
	{
		return symt.lookup(id);
	}
	
	@Override
	public String toString()
	{
		return id;
	}

}
