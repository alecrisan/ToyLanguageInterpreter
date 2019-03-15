package repository;

import java.io.IOException;
import java.util.List;

import model.ProgramState;
import model.statement.IStatement;
import utils.MyException;

public interface IRepo {
	
	public ProgramState getCrtPrg() throws MyException;
	
	public String getPath();
	
	public void clearPath() throws IOException;
	
	public void addProgram(ProgramState p);
	
	public void setCurrent(int c);
	
	public List<ProgramState> getPrograms();
	
	public List<IStatement> getStatements();
	
	public void setPrgList(List<ProgramState> l);
	
	public void logPrgStateExec(ProgramState p) throws IOException;

}
