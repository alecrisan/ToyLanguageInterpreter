package view;

import controller.Controller;
import model.ProgramState;
import model.expression.ArithExp;
import model.expression.BooleanExpr;
import model.expression.ConstExp;
import model.expression.RHExpr;
import model.expression.VarExpr;
import model.statement.AssignStmt;
import model.statement.CloseRFile;
import model.statement.CompoundStmt;
import model.statement.ConditionalStmt;
import model.statement.ForkStmt;
import model.statement.IStatement;
//import model.statement.NewStmt;
//import model.statement.OpenRFile;
//import model.statement.PrintStmt;
//import model.statement.ReadFile;
//import model.statement.WHStmt;
//import model.statement.WhileStmt;
//import repository.IRepo;
//import repository.Repo;
//import utils.ExecStack;
//import utils.FileTable;
//import utils.Heap;
//import utils.MyException;
//import utils.Output;
//import utils.Pair;
//import utils.SymTable;
//
//public class Interpreter {
//
//	public static void main(String args[])
//	{
//		
//		ExecStack<IStatement> exeStack = new ExecStack<>();
//		SymTable<String, Integer> symTable = new SymTable<>();
//		Output<Integer> output = new Output<>();
//		FileTable<Integer, Pair> filetable = new FileTable<>();
//		Heap<Integer> heap = new Heap<>();
//		
//		ArithExp exp1 = new ArithExp('+', new ConstExp(2),
//				new ArithExp('*', new ConstExp(3),
//						new ConstExp(5)));
//		
//		ArithExp exp2 = new ArithExp('+', new ConstExp(5),
//				new ConstExp(6));
//		
//		IStatement st1= new ConditionalStmt(new ConstExp(10),
//				new CompoundStmt((new AssignStmt("v", new
//						ConstExp(5))), new PrintStmt(new ArithExp('/', new ConstExp(3), 
//						new ConstExp(3)))), new PrintStmt(new ConstExp(100)));
//		
//		IStatement st2 = new CompoundStmt(new AssignStmt("s", exp1), 
//				new PrintStmt(new ConstExp(100)));
//		
//		IStatement st3 = new ConditionalStmt(exp2, new 
//				PrintStmt(new ConstExp(20)), new AssignStmt("a", new ConstExp(1)));
//		
//		IStatement st4 = new CompoundStmt(new CompoundStmt(new OpenRFile("var_f", "test.txt"), new CompoundStmt(new ReadFile(new VarExpr("var_f"),"var_c"),
//				new PrintStmt(new VarExpr("var_c")))), new CompoundStmt(new ConditionalStmt(new VarExpr("var_c"), 
//				new CompoundStmt(new ReadFile(new VarExpr("var_f"),"var_c"), new PrintStmt(new VarExpr("var_c"))), new PrintStmt(new ConstExp(0))),
//						new CloseRFile(new VarExpr("var_f"))));
//		
//		IStatement st5 = new CompoundStmt(new CompoundStmt(new ReadFile(new ConstExp(0),"var_c"),
//				new PrintStmt(new VarExpr("var_c"))),new CloseRFile(new ConstExp(0)));
//		
//		
//		//v=10;new(v,20);new(a,22);print(v) 
//		
//		IStatement st6 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("v", new ConstExp(20))), 
//				new CompoundStmt(new NewStmt("a",new ConstExp(22)), new PrintStmt(new VarExpr("v"))));
//		
//		
//		//v=10;new(v,20);  new(a,22);print(100+rH(v));print(100+rH(a)) 
//		
//		IStatement st7 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("v", new ConstExp(20))),
//				new CompoundStmt(new CompoundStmt(new NewStmt("a", new ConstExp(22)), new PrintStmt(new ArithExp('+', new ConstExp(100),
//						new RHExpr("v")))), new PrintStmt(new ArithExp('+', new ConstExp(100),
//						new RHExpr("a")))));
//		
//		//v=10;new(v,20);new(a,22); wH(a,30);print(a);print(rH(a)) 
//		
//		
//		IStatement st8 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new CompoundStmt(new NewStmt("v", new ConstExp(20)),
//				new NewStmt("a", new ConstExp(22)))), 
//				new CompoundStmt(new WHStmt("a", new ConstExp(30)), new CompoundStmt(new PrintStmt(new VarExpr("a")), new PrintStmt(new RHExpr("a")))));
//		
//		
//		//v=10;new(v,20); new(a,22);wH(a,30); print(a);print(rH(a)); a=4;print(rH(a))
//		
//		IStatement st9 = new CompoundStmt(new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("v", new ConstExp(20))),
//				new CompoundStmt(new NewStmt("a", new ConstExp(22)), new WHStmt("a", new ConstExp(30)))), new CompoundStmt(new CompoundStmt(new PrintStmt(new VarExpr("a")), 
//						new PrintStmt(new RHExpr("a"))),
//						new CompoundStmt(new AssignStmt("a", new ConstExp(4)), new PrintStmt(new RHExpr("a")))));
//		
//		//v=6; (while (v-4) print(v);v=v-1);print(v)
//		
//		IStatement st10 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(6)), new WhileStmt(new BooleanExpr(
//				new ArithExp('-', new VarExpr("v"), new ConstExp(4)), new ConstExp(0), ">"), new CompoundStmt(new PrintStmt(new VarExpr("v")), 
//						new AssignStmt("v", new ArithExp('-', new VarExpr("v"), new ConstExp(1)))))), 
//				new PrintStmt(new VarExpr("v")));
//		
//		
////		ProgramState ps1 = new ProgramState(st1, exeStack,symTable, output, filetable, heap);
////		IRepo repo1 = new Repo("test1.txt");
////		Controller ctr1 = new Controller(repo1);
////		ctr1.getRepo().addProgram(ps1);
////		
////		ProgramState ps2 = new ProgramState(st2, exeStack, symTable, output, filetable, heap);
////		IRepo repo2 = new Repo("test2.txt");
////		Controller ctr2 = new Controller(repo2);
////		ctr2.getRepo().addProgram(ps2);
////		
////		ProgramState ps3 = new ProgramState(st3, exeStack, symTable, output, filetable, heap);
////		IRepo repo3 = new Repo("test3.txt");
////		Controller ctr3 = new Controller(repo3);
////		ctr3.getRepo().addProgram(ps3);	
////		
////		ProgramState ps4 = new ProgramState(st4, exeStack, symTable, output, filetable, heap);
////		IRepo repo4 = new Repo("test4.txt");
////		Controller ctr4 = new Controller(repo4);
////		ctr4.getRepo().addProgram(ps4);
////		
////		ProgramState ps5 = new ProgramState(st5, exeStack, symTable, output, filetable, heap);
////		IRepo repo5 = new Repo("test5.txt");
////		Controller ctr5 = new Controller(repo5);
////		ctr5.getRepo().addProgram(ps5);
////		
////		ProgramState ps6 = new ProgramState(st6, exeStack, symTable, output, filetable, heap);
////		IRepo repo6 = new Repo("test6.txt");
////		Controller ctr6 = new Controller(repo6);
////		ctr6.getRepo().addProgram(ps6);
////		
////		ProgramState ps7 = new ProgramState(st7, exeStack, symTable, output, filetable, heap);
////		IRepo repo7 = new Repo("test7.txt");
////		Controller ctr7 = new Controller(repo7);
////		ctr7.getRepo().addProgram(ps7);
////		
////		ProgramState ps8 = new ProgramState(st8, exeStack, symTable, output, filetable, heap);
////		IRepo repo8 = new Repo("test8.txt");
////		Controller ctr8 = new Controller(repo8);
////		ctr8.getRepo().addProgram(ps8);
////		
////		ProgramState ps9 = new ProgramState(st9, exeStack, symTable, output, filetable, heap);
////		IRepo repo9 = new Repo("test9.txt");
////		Controller ctr9 = new Controller(repo9);
////		ctr9.getRepo().addProgram(ps9);
////		
////		ProgramState ps10 = new ProgramState(st10, exeStack, symTable, output, filetable, heap);
////		IRepo repo10 = new Repo("test10.txt");
////		Controller ctr10 = new Controller(repo10);
////		ctr10.getRepo().addProgram(ps10);
//		
////		v=10;new(a,22);
////		 fork(wH(a,30);v=32;print(v);print(rH(a)));
////		 print(v);print(rH(a)) 
//		
//				
//		IStatement st = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("a", new ConstExp(22))), 
//				new CompoundStmt(new ForkStmt(new CompoundStmt(new WHStmt("a", new ConstExp(30)), new CompoundStmt(new AssignStmt("v", new ConstExp(32)), 
//				new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a")))))), 
//				new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a")))));
//		
////		v=10;new(a,22);
////		 fork(wH(a,30);v=32;print(v);fork(print(v));print(rH(a)));
////		 print(v);print(rH(a)) 
//		
//		
//		IStatement stt = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("a", new ConstExp(22))), 
//				new CompoundStmt(new ForkStmt(new CompoundStmt(new WHStmt("a", new ConstExp(30)), new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(32)), 
//				new PrintStmt(new VarExpr("v"))),
//				new CompoundStmt(new ForkStmt(new PrintStmt(new VarExpr("v"))), new PrintStmt(new RHExpr("a")))))), 
//				new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a")))));
//		
////		v=10;new(a,22);
////		 fork(wH(a,30);v=32;print(v);print(rH(a)));
////		fork(print(v));
////		 print(v);print(rH(a)) 
//		
//		
//		IStatement s = new CompoundStmt(new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("a", new ConstExp(22))), 
//				new ForkStmt(new CompoundStmt(new WHStmt("a", new ConstExp(30)), new CompoundStmt(new AssignStmt("v", new ConstExp(32)), 
//				new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a"))))))), new CompoundStmt(new ForkStmt(new PrintStmt(new VarExpr("v"))), 
//				new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a")))));
//		
////		IStatement ss = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("a", new ConstExp(22))), 
////				new CompoundStmt(new ForkStmt(new CompoundStmt(new WHStmt("a", new ConstExp(30)), new CompoundStmt(new AssignStmt("v", new ConstExp(32)), 
////				new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a")))))), new CompoundStmt(new ForkStmt(new PrintStmt(new VarExpr("v"))), 
////						new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a"))))));
//		
//		ProgramState ps1 = new ProgramState(1, st, exeStack,symTable, output, filetable, heap);
//		IRepo repo1 = new Repo("test1.txt");
//		Controller ctr1 = new Controller(repo1);
//		ctr1.getRepo().addProgram(ps1);
//		
//		ProgramState ps2 = new ProgramState(2, stt, exeStack,symTable, output, filetable, heap);
//		IRepo repo2 = new Repo("test2.txt");
//		Controller ctr2 = new Controller(repo2);
//		ctr2.getRepo().addProgram(ps2);
//		
//		ProgramState ps3 = new ProgramState(3, s, exeStack,symTable, output, filetable, heap);
//		IRepo repo3 = new Repo("test3.txt");
//		Controller ctr3 = new Controller(repo3);
//		ctr3.getRepo().addProgram(ps3);
//		
//		
//		TextMenu menu = new TextMenu();
//		menu.addCommand(new ExitCommand("0", "exit"));
////		menu.addCommand(new RunExample("1",st1.toString(),ctr1));
////		menu.addCommand(new RunExample("2",st2.toString(),ctr2));
////		menu.addCommand(new RunExample("3",st3.toString(),ctr3));
////		menu.addCommand(new RunExample("4",st4.toString(),ctr4));
////		menu.addCommand(new RunExample("5",st5.toString(),ctr5));
////		menu.addCommand(new RunExample("6",st6.toString(),ctr6));
////		menu.addCommand(new RunExample("7",st7.toString(),ctr7));
////		menu.addCommand(new RunExample("8",st8.toString(),ctr8));
////		menu.addCommand(new RunExample("9",st9.toString(),ctr9));
////		menu.addCommand(new RunExample("10",st10.toString(),ctr10));
//		
//		menu.addCommand(new RunExample("1", st.toString(), ctr1));
//		menu.addCommand(new RunExample("2", stt.toString(), ctr2));
//		menu.addCommand(new RunExample("3", s.toString(), ctr3));
//		
//		menu.show();
//		
//
//	}
//}
