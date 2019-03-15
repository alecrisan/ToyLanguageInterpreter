package model.statement;

import java.io.IOException;

import model.ProgramState;
import model.expression.IExpression;
import utils.IExecStack;
import utils.MyException;

public class ConsAssigStmt implements IStatement {

	String v;
	IExpression exp1;
	IExpression exp2;
	IExpression exp3;
	
	public ConsAssigStmt(String var, IExpression e1, IExpression e2, IExpression e3)
	{
		this.v = var;
		this.exp1 = e1;
		this.exp2 = e2;
		this.exp3 = e3;
	}
	
	@Override
	public String toString()
	{
		return v + "=" + exp1.toString() + "?" + exp2.toString() + ":" + exp3.toString();
	}
	
	@Override
	public ProgramState execute(ProgramState p) throws MyException, IOException {
		IExecStack<IStatement> stack = p.getStack();
		
		IStatement s = new ConditionalStmt(exp1, new AssignStmt(v, exp2), new AssignStmt(v, exp3));
		
		stack.push(s);
		return null;
	}

}
