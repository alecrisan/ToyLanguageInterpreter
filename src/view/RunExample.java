package view;

import java.io.IOException;

import controller.Controller;
import model.ProgramState;
import utils.MyException;

public class RunExample extends Command {
	
	 private Controller ctr;
	 
	 public RunExample(String key, String desc,Controller ctr)
	 {
		 super(key, desc);
		 this.ctr=ctr;
	 }
	 
	 @Override
	 public void execute() throws MyException, IOException
	 {
		 try
		 {
			 ProgramState p = ctr.getRepo().getCrtPrg();
			 p.getStack().clear();
			 p.getOutput().clear();
			 p.getSymTable().clear();
			 p.getFileTable().clear();
			 p.getStack().push(p.getStatement());
			 //ctr.getRepo().clearPath();
			 //ctr.execComplete(); 
			 ctr.allStep();
		 }
		 catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
//		 finally
//		 {
//			 for(Integer e : ctr.closeOpenFiles(ctr.getRepo().getCrtPrg().getSymTable().getDictionary().values(), 
//					 ctr.getRepo().getCrtPrg().getFileTable().getFileTable())) 
//			 {
//				 ctr.getRepo().getCrtPrg().getFileTable().lookup(e).getBF().close();
//				 ctr.getRepo().getCrtPrg().getFileTable().delete(e);
//			 }
//		 }
	 }
}