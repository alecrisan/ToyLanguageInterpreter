package model.expression;

import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public class ArithExp implements IExpression{
	
	IExpression exp1;
	IExpression exp2;
	int op;
	
	public ArithExp(char operator, IExpression operand1,
            IExpression operand2) {
		this.op = operator;
		this.exp1 = operand1;
		this.exp2 = operand2;
	}

	@Override
	public String toString() {
		switch (op) 
		{
		case '+':
			return exp1.toString() + " + "
			+ exp2.toString();
		case '-':
			return exp1.toString() + " - "
			+ exp2.toString();
		case '*':
			return exp1.toString() + " * "
			+ exp2.toString();
		case '/': 
			return exp1.toString() + " / "
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
		case '+':
			return result1 + result2;
		case '-':
			return result1 - result2;
		case '*':
			return result1 * result2;
		case '/': 
		{
			if (result2 == 0) 
			{
				throw new MyException("Cannot " +
						"divide by 0");
			} 
			else 
				return result1 / result2;
			
		}
		default:
			throw new MyException("Invalid " +
					"operator");
		}
	}

}
