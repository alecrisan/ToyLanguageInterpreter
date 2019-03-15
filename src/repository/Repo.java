package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import java.util.List;

import model.ProgramState;
import model.statement.IStatement;
import utils.MyException;

public class Repo implements IRepo{
	
	List<ProgramState> programs;
	int current;
	String logFilePath;
	
	public Repo(String logF)
	{
		programs = new ArrayList<ProgramState>();
		current = 0;
		logFilePath = logF;
	}

	@Override
	public ProgramState getCrtPrg() throws MyException
	{
		if(this.programs.size() != 0)
			return this.programs.get(current);
		else
			throw new MyException("No current program");
	}
	
	@Override
	public String getPath()
	{
		return this.logFilePath;
	}
	

	@Override
	public void addProgram(ProgramState p) 
	{
		programs.add(p);
	}
	
	@Override
	public void setCurrent(int c)
	{
		current = c;
	}
	
	@Override
	public List<ProgramState> getPrograms()
	{
		return programs;
	}
	
	@Override
	public List<IStatement> getStatements()
	{
		List<IStatement> l = new ArrayList<IStatement>();
		
		for(ProgramState p : programs)
		{
			IStatement s = p.getStatement();
			l.add(s);
		}
		return l;
		
	}
	
	@Override
	public void clearPath() throws IOException
	{
		PrintWriter logFile = new PrintWriter(new BufferedWriter(
				new FileWriter(logFilePath, false)));
	}
	
	@Override
	public void logPrgStateExec(ProgramState p) throws IOException
	{
		PrintWriter logFile = new PrintWriter(new BufferedWriter(
				new FileWriter(logFilePath, true)));
		
		logFile.append('\n');
		
		logFile.append("STATEMENT: \n");
		logFile.append('\n');
		logFile.append("ID:" + Integer.toString(p.getId()) + '\n');
		logFile.append('\n');
		
		logFile.append('\n');
		logFile.append(p.getStatement().toString());
		logFile.append('\n');
		
		logFile.append('\n');
		logFile.append(p.getStack().toString());
		logFile.append('\n');

		logFile.append('\n');
		logFile.append(p.getSymTable().toString());
		logFile.append('\n');

		logFile.append('\n');
		logFile.append(p.getOutput().toString());
		logFile.append('\n');

		logFile.append('\n');
		logFile.append(p.getFileTable().toString());
		logFile.append('\n');
		
		logFile.append('\n');
		logFile.append(p.getHeap().toString());
		logFile.append('\n');
		
		logFile.append('\n');
		
		logFile.flush();
		logFile.close();
	}

	@Override
	public void setPrgList(List<ProgramState> l) 
	{
		this.programs = l;
	}
	

}
