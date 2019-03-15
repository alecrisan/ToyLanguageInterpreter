package model.expression;

import utils.IHeap;
import utils.ISymTable;

public class ConstExp implements IExpression {
	
	int val;
	
	public ConstExp(int v)
	{
		this.val = v;
	}
	
	@Override
	public int eval(ISymTable<String, Integer> symt, IHeap<Integer> heap)
	{
		return val;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(val);
	}

}
