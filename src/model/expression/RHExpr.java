package model.expression;

import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public class RHExpr implements IExpression{
	
	String var_name;
	
	public RHExpr(String v)
	{
		var_name = v;
	}

	@Override
	public int eval(ISymTable<String, Integer> symt, IHeap<Integer> heap) throws MyException 
	{
		
		int v = symt.lookup(var_name);
		
		int content = heap.lookup(v);
		
		return content;
	}
	
	@Override
	public String toString()
	{
		return "rH(" + var_name + ")"; 
	}

}
