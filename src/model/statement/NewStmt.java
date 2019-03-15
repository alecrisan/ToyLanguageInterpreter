package model.statement;

import java.io.IOException;

import model.ProgramState;
import model.expression.IExpression;
import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public class NewStmt implements IStatement {
	
	String var_name;
	IExpression exp;
	
	public NewStmt(String v, IExpression e)
	{
		var_name = v;
		exp = e;
	}

	@Override
	public ProgramState execute(ProgramState p) throws MyException, IOException 
	{
		ISymTable<String, Integer> symt= p.getSymTable();
		IHeap<Integer> heap = p.getHeap();
		
		int v = exp.eval(symt, heap);
		
		int address = heap.getFirstFree();
		
		heap.add(address, v);
		
		if(symt.isDefined(var_name))
			symt.update(var_name, address);
		else
			symt.add(var_name, address);
		
		heap.setFirstFree(address+1);
		
		return null;
	}
	
	@Override
	public String toString()
	{
		return "new(" + var_name + "," + exp.toString() + ")"; 
	}

}
