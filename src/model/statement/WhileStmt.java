package model.statement;

import java.io.IOException;

import model.ProgramState;
import model.expression.IExpression;
import utils.IExecStack;
import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public class WhileStmt implements IStatement{

	IExpression exp;
	IStatement stm;
	
	public WhileStmt(IExpression e, IStatement s)
	{
		this.exp = e;
		this.stm = s;
	}
	
	@Override
	public String toString()
	{
		return "while(" + this.exp.toString() + ") " + this.stm.toString() + " ";
	}
	
	@Override
	public ProgramState execute(ProgramState p) throws MyException, IOException 
	{
		IExecStack<IStatement> stack = p.getStack();
		ISymTable<String, Integer> symt= p.getSymTable();
		IHeap<Integer> heap = p.getHeap();
		
		if(this.exp.eval(symt, heap) == 1)
		{
			stack.push(this);
			stack.push(this.stm);
		}
		
		return null;
	}
	

}
