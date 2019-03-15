package model.statement;

import java.io.IOException;

import model.ProgramState;
import model.expression.IExpression;
import utils.IHeap;
import utils.ISymTable;
import utils.MyException;

public class WHStmt implements IStatement{

	
	String var_name;
	IExpression exp;
	
	public WHStmt(String v, IExpression e)
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
		
		int address = symt.lookup(var_name);
		
		if(heap.isDefined(address))
			heap.update(address, v);
		
		return null;
	}
	
	@Override
	public String toString()
	{
		return "wH(" + var_name + "," + exp.toString() + ")"; 
	}


}
