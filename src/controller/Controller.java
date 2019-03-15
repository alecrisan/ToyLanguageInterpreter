package controller;

import java.io.IOException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import model.ProgramState;
import model.statement.IStatement;
import repository.IRepo;
import utils.IExecStack;
import utils.IFileTable;
import utils.IHeap;
import utils.MyException;
import utils.Pair;

public class Controller {
	
	IRepo repo;
	ExecutorService executor;
	
	public Controller(IRepo r)
	{
		this.repo = r;
	}
	
	public IRepo getRepo()
	{
		return repo;
	}
	
	public void setExecutor(ExecutorService e)
	{
		this.executor = e;
	}
	
//	public ProgramState execOneStep(ProgramState p) throws MyException, IOException
//	{
//		IExecStack<IStatement> s = p.getStack();
//		if(s.isEmpty()) 
//				throw new MyException("Empty stack!");
//		
//		IStatement crtStmt = s.pop();
//		
//		return crtStmt.execute(p);
//	}
	
//	public void execComplete() throws MyException, IOException
//	{
//		ProgramState p = repo.getCrtPrg(); 
//
//		System.out.println(p.toString());
//			
//			while(p.getStack().isEmpty() == false)
//			{
//				System.out.println('\n');
//				execOneStep(p);
//				
//				p.getHeap().setContent(conservativeGarbageCollector(
//						 p.getSymTable().getDictionary().values(),
//						 p.getHeap().getHeap()));
//				
//				this.repo.logPrgStateExec(p);
//				System.out.println(p.toString());
//			}
//			
//	}
	
	
	public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException 
	{
		prgList.forEach(prg->{
			try 
			{
				repo.logPrgStateExec(prg);
				System.out.println('\n');
				System.out.println(prg.toString());
			} 
			catch (IOException e1) 
			{

				e1.printStackTrace();
			}
		});

		//RUN concurrently one step for each of the existing PrgStates

		//prepare the list of callables
		List<Callable<ProgramState>> callList = prgList.stream()
				.map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.execOneStep();}))
				.collect(Collectors.toList());

		//start the execution of the callables
		//it returns the list of new created PrgStates (namely threads)
		List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
				.map(future -> { try { return future.get();}
				catch(Exception e) 
				{
					System.out.println(e.getMessage());
				}
				return null;})
				.filter(p -> p!=null)
				.collect(Collectors.toList());

		//add the new created threads to the list of existing threads

		prgList.addAll(newPrgList);

		prgList.forEach(prg ->
		{
			try {
				repo.logPrgStateExec(prg);
				System.out.println('\n');
				System.out.println(prg.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		 

		//Save the current programs in the repository
		repo.setPrgList(prgList);
	}
	
	public void allStep() throws InterruptedException 
	{
		executor = Executors.newFixedThreadPool(2);
		
		//remove the completed programs
		List<ProgramState> prgList=removeCompletedPrg(repo.getPrograms());
		
		while(prgList.size() > 0)
		{
			IHeap<Integer> heap = prgList.get(0).getHeap();
			prgList.forEach(p -> heap.setContent(conservativeGarbageCollector(
					 p.getSymTable().getDictionary().values(),
					 p.getHeap().getHeap())));
			
			oneStepForAllPrg(prgList);
			
			//remove the completed programs
			prgList = removeCompletedPrg(repo.getPrograms());
		}
		executor.shutdownNow();
		//HERE the repository still contains at least one Completed Prg
		// and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
		//setPrgList of repository in order to change the repository
		
		
		List<ProgramState> plist = repo.getPrograms();
		
		IFileTable<Integer, Pair> table = plist.get(0).getFileTable();
		plist.forEach(p -> {for(Integer e : closeOpenFiles(p.getSymTable().getDictionary().values(), 
					 table.getFileTable())) 
			 {
				 try 
				 {
					table.lookup(e).getBF().close();
				 } 
				 catch (IOException | MyException e1) 
				 {
					e1.printStackTrace();
				 }
				 table.delete(e);
			 }});

		repo.setPrgList(prgList);
	}
	
	public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList)
	{
		return inPrgList.stream()
				 .filter(p -> p.isNotCompleted())
				 .collect(Collectors.toList());
	}
	
	public List<Integer> closeOpenFiles(Collection<Integer> symTableValues,Map<Integer, Pair> fileTable) {
		return fileTable.entrySet().stream().filter(entry -> symTableValues.contains(entry.getKey())).map(entry -> entry.getKey()).collect(Collectors.toList());
	}
	
	public Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer,Integer> heap)
	{
			return heap.entrySet().stream()
			 .filter(e->symTableValues.contains(e.getKey()))
			 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

}
