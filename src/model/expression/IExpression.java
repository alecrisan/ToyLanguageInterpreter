package model.expression;

import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public interface IExpression {
	
	public int eval(ISymTable<String, Integer> symt, IHeap<Integer> heap) throws MyException;

}
