package model.statement;

import java.io.IOException;

import model.ProgramState;
import model.expression.BooleanExpr;
import model.expression.IExpression;
import model.expression.VarExpr;
import utils.IExecStack;
import utils.MyException;

public class ForStmt implements IStatement{

	String v;
	IExpression exp1;
	IExpression exp2;
	IExpression exp3;
	IStatement stm;
	
	public ForStmt(String var, IExpression e1, IExpression e2, IExpression e3, IStatement s)
	{
		this.v = var;
		this.exp1 = e1;
		this.exp2 = e2;
		this.exp3 = e3;
		this.stm = s;
	}
	
	@Override
	public String toString()
	{
		return "for(" + v + "=" + exp1.toString()+ ";v<" + exp2.toString() + ";v=" + exp3.toString() + ") " + stm.toString();
	}
	
	
	@Override
	public ProgramState execute(ProgramState p) throws MyException, IOException {
		IExecStack<IStatement> stack = p.getStack();
		
		IStatement newS = new CompoundStmt(new AssignStmt(v, exp1), new WhileStmt(new BooleanExpr(new VarExpr("v"), exp2, "<"),
				new CompoundStmt(stm, new AssignStmt(v, exp3))));
		
		stack.push(newS);
		
		return null;
	}

}
