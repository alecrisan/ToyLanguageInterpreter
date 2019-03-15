package model.statement;

import java.io.IOException;

import model.ProgramState;
import utils.MyException;

public interface IStatement {
	
	public ProgramState execute(ProgramState p) throws MyException, IOException;

}
