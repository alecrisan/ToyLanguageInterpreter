package model.statement;

import java.io.IOException;

import model.ProgramState;
import model.expression.BooleanExpr;
import model.expression.IExpression;
import utils.IExecStack;
import utils.MyException;

public class SwitchStmt implements IStatement{

	private IExpression exp;
	
	private IExpression exp1;
	private IStatement st1;
	
	private IExpression exp2;
	private IStatement st2;
	
	private IStatement st3;
	
	public SwitchStmt(IExpression e, IExpression e1, IStatement s1, IExpression e2, IStatement s2, IStatement s3)
	{
		this.exp = e;
		this.exp1 = e1;
		this.st1 = s1;
		this.exp2 = e2;
		this.st2 = s2;
		this.st3 = s3;
	}
	
	@Override
	public String toString()
	{
		return "switch("+exp.toString()+")(case "+exp1.toString()+":"+st1.toString()+")(case "+
	exp2.toString()+":"+st2.toString()+")(default: " + st3.toString()+")";
	}
	
	@Override
	public ProgramState execute(ProgramState p) throws MyException, IOException {
		
		IExecStack<IStatement> stack = p.getStack();
		
		IStatement st = new ConditionalStmt(new BooleanExpr(exp, exp1, "=="), st1, new ConditionalStmt(new BooleanExpr(exp, exp2, "=="), st2, st3));
		
		stack.push(st);
		return null;
	}

}
