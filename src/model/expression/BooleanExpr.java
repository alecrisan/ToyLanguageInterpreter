package model.expression;

import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public class BooleanExpr implements IExpression{

	IExpression exp1;
	IExpression exp2;
	String op;
	
	public BooleanExpr(IExpression e1, IExpression e2, String o)
	{
		this.exp1 = e1;
		this.exp2 = e2;
		this.op = o;
	}
	
	@Override
	public String toString() {
		switch (op) 
		{
		case "<":
			return exp1.toString() + " < "
			+ exp2.toString();
		case "<=":
			return exp1.toString() + " <= "
			+ exp2.toString();
		case ">":
			return exp1.toString() + " > "
			+ exp2.toString();
		case ">=": 
			return exp1.toString() + " >= "
			+ exp2.toString();
		case "==": 
			return exp1.toString() + " == "
			+ exp2.toString();
		case "!=": 
			return exp1.toString() + " != "
			+ exp2.toString();
		}
		return null;
		
	}
	
	@Override
	public int eval(ISymTable<String, Integer> t, IHeap<Integer> heap) throws MyException
	{
		int result1 = exp1.eval(t, heap);
		int result2 = exp2.eval(t, heap);
		switch (op) {
		case "<":
		{
			if(result1 < result2)
				return 1;
			return 0;
		}
		case "<=":
		{
			if(result1 <= result2)
				return 1;
			return 0;
		}
		case ">":
		{
			if(result1 > result2)
				return 1;
			return 0;
		}
		case ">=": 
		{
			if(result1 >= result2)
				return 1;
			return 0;
		}
		case "==": 
		{
			if(result1 == result2)
				return 1;
			return 0;
		}
		case "!=": 
		{
			if(result1 != result2)
				return 1;
			return 0;
		}
		default:
			throw new MyException("Invalid " +
					"operator");
		}
	}


}
