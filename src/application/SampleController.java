package application;


import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
import model.statement.ConsAssigStmt;
import model.statement.ForStmt;
import model.statement.ForkStmt;
import model.statement.IStatement;
import model.statement.NewStmt;
import model.statement.OpenRFile;
import model.statement.PrintStmt;
import model.statement.ReadFile;
import model.statement.SwitchStmt;
import model.statement.WHStmt;
import model.statement.WhileStmt;
import repository.IRepo;
import repository.Repo;
import utils.ExecStack;
import utils.FileTable;
import utils.Heap;
import utils.Output;
import utils.Pair;
import utils.SymTable;

public class SampleController {
	
	
	private IRepo repo;
	
	@FXML
	private ListView<IStatement> listView;
	
	@FXML
	private Button changeButton;
	
	@FXML
	private Label labelPrograms;
	
	
	private IStatement selected;

	private Stage primaryStage;
	
	@FXML 
	void initialize()
	{
		populate();
		
		listView.setItems(getPrgList());
		
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStatement>() {
	    	  
            @Override
            public void changed(ObservableValue<? extends IStatement> observable,  IStatement oldValue, IStatement newValue) {
            	
                
                System.out.println("Selected item: " + newValue);
                
                selected = newValue;
            }
        });
		
		
	}
	
	public void setStage(Stage stage)
	{
		primaryStage = stage;
	}
	
	@FXML
	public void handleButtonAction(ActionEvent event) throws IOException
	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Window2.fxml"));
		Scene secondScene = new Scene(loader.load());
		SecondController SecondController = loader.getController();
		SecondController.setProgram(selected);
		
		Stage newStage = new Stage();
		
		newStage.setScene(secondScene);
		
		newStage.initModality(Modality.WINDOW_MODAL);
		
		newStage.initOwner(primaryStage);
		
		newStage.show();
		
	}
	
	
	void populate()
	{
		ExecStack<IStatement> exeStack = new ExecStack<>();
		SymTable<String, Integer> symTable = new SymTable<>();
		Output<Integer> output = new Output<>();
		FileTable<Integer, Pair> filetable = new FileTable<>();
		Heap<Integer> heap = new Heap<>();

		ArithExp exp1 = new ArithExp('+', new ConstExp(2),
				new ArithExp('*', new ConstExp(3),
						new ConstExp(5)));

		ArithExp exp2 = new ArithExp('+', new ConstExp(5),
				new ConstExp(6));

		IStatement st1= new ConditionalStmt(new ConstExp(10),
				new CompoundStmt((new AssignStmt("v", new
						ConstExp(5))), new PrintStmt(new ArithExp('/', new ConstExp(3), 
								new ConstExp(3)))), new PrintStmt(new ConstExp(100)));

		IStatement st2 = new CompoundStmt(new AssignStmt("s", exp1), 
				new PrintStmt(new ConstExp(100)));

		IStatement st3 = new ConditionalStmt(exp2, new 
				PrintStmt(new ConstExp(20)), new AssignStmt("a", new ConstExp(1)));

		IStatement st4 = new CompoundStmt(new CompoundStmt(new OpenRFile("var_f", "test.txt"), new CompoundStmt(new ReadFile(new VarExpr("var_f"),"var_c"),
				new PrintStmt(new VarExpr("var_c")))), new CompoundStmt(new ConditionalStmt(new VarExpr("var_c"), 
						new CompoundStmt(new ReadFile(new VarExpr("var_f"),"var_c"), new PrintStmt(new VarExpr("var_c"))), new PrintStmt(new ConstExp(0))),
						new CloseRFile(new VarExpr("var_f"))));

		IStatement st5 = new CompoundStmt(new CompoundStmt(new ReadFile(new ConstExp(0),"var_c"),
				new PrintStmt(new VarExpr("var_c"))),new CloseRFile(new ConstExp(0)));


		//v=10;new(v,20);new(a,22);print(v) 

		IStatement st6 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("v", new ConstExp(20))), 
				new CompoundStmt(new NewStmt("a",new ConstExp(22)), new PrintStmt(new VarExpr("v"))));


		//v=10;new(v,20);  new(a,22);print(100+rH(v));print(100+rH(a)) 

		IStatement st7 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("v", new ConstExp(20))),
				new CompoundStmt(new CompoundStmt(new NewStmt("a", new ConstExp(22)), new PrintStmt(new ArithExp('+', new ConstExp(100),
						new RHExpr("v")))), new PrintStmt(new ArithExp('+', new ConstExp(100),
								new RHExpr("a")))));

		//v=10;new(v,20);new(a,22); wH(a,30);print(a);print(rH(a)) 


		IStatement st8 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new CompoundStmt(new NewStmt("v", new ConstExp(20)),
				new NewStmt("a", new ConstExp(22)))), 
				new CompoundStmt(new WHStmt("a", new ConstExp(30)), new CompoundStmt(new PrintStmt(new VarExpr("a")), new PrintStmt(new RHExpr("a")))));


		//v=10;new(v,20); new(a,22);wH(a,30); print(a);print(rH(a)); a=4;print(rH(a))

		IStatement st9 = new CompoundStmt(new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("v", new ConstExp(20))),
				new CompoundStmt(new NewStmt("a", new ConstExp(22)), new WHStmt("a", new ConstExp(30)))), new CompoundStmt(new CompoundStmt(new PrintStmt(new VarExpr("a")), 
						new PrintStmt(new RHExpr("a"))),
						new CompoundStmt(new AssignStmt("a", new ConstExp(4)), new PrintStmt(new RHExpr("a")))));

		//v=6; (while (v-4) print(v);v=v-1);print(v)

		IStatement st10 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(6)), new WhileStmt(new BooleanExpr(
				new ArithExp('-', new VarExpr("v"), new ConstExp(4)), new ConstExp(0), ">"), new CompoundStmt(new PrintStmt(new VarExpr("v")), 
						new AssignStmt("v", new ArithExp('-', new VarExpr("v"), new ConstExp(1)))))), 
				new PrintStmt(new VarExpr("v")));

		//	v=10;new(a,22);
		//	 fork(wH(a,30);v=32;print(v);print(rH(a)));
		//	 print(v);print(rH(a)) 


		IStatement st11 = new CompoundStmt(new CompoundStmt(new AssignStmt("v", new ConstExp(10)), new NewStmt("a", new ConstExp(22))), 
				new CompoundStmt(new ForkStmt(new CompoundStmt(new WHStmt("a", new ConstExp(30)), new CompoundStmt(new AssignStmt("v", new ConstExp(32)), 
						new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a")))))), 
						new CompoundStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new RHExpr("a")))));


		//open(a,test1.txt); print(20);

		IStatement st12 = new CompoundStmt(new OpenRFile("a", "test1.txt"), new PrintStmt(new ConstExp(20)));

		//	v=20;
		//	(for(v=0;v<3;v=v+1) fork(print(v);v=v+1) );
		//	print(v*10) 

		IStatement st13 = new CompoundStmt(new AssignStmt("v", new ConstExp(20)), 
				new CompoundStmt(new ForStmt("v", new ConstExp(0), 
						new ConstExp(3), new ArithExp('+', new VarExpr("v"), new ConstExp(1)), new ForkStmt(new CompoundStmt(new PrintStmt(new VarExpr("v")), 
								new AssignStmt("v",new ArithExp('+', new VarExpr("v"), new ConstExp(1)))))), 
						new PrintStmt(new ArithExp('*', new VarExpr("v"), new ConstExp(10)))));

		//	a=1;b=2;c=5;
		//	switch(a*10)
		//	(case (b*c) print(a);print(b)) (case (10) print(100);print(200)) (default print(300));
		//	print(300)

		IStatement st14 = new CompoundStmt(new CompoundStmt(new AssignStmt("a", new ConstExp(1)), 
				new CompoundStmt(new AssignStmt("b", new ConstExp(2)), new AssignStmt("c", new ConstExp(5)))), 
				new CompoundStmt(new SwitchStmt(new ArithExp('*', new VarExpr("a"), new ConstExp(10)), 
				new ArithExp('*', new VarExpr("b"), new VarExpr("c")), new CompoundStmt(new PrintStmt(new VarExpr("a")), new PrintStmt(new VarExpr("b"))), 
				new ConstExp(10), new CompoundStmt(new PrintStmt(new ConstExp(100)), new PrintStmt(new ConstExp(200))), new PrintStmt(new ConstExp(300))), 
				new PrintStmt(new ConstExp(300))));
		
		//		a=1;b=2;
		//		c=a?100:200;
		//		print(c);
		//		c= (b-2)?100:200; print(c);
		
		IStatement st15 = new CompoundStmt(new CompoundStmt(new CompoundStmt(new AssignStmt("a", new ConstExp(1)), 
				new AssignStmt("b", new ConstExp(2))), 
				new CompoundStmt(new ConsAssigStmt("c", new VarExpr("a"), new ConstExp(100), new ConstExp(200)), 
				new PrintStmt(new VarExpr("c")))), 
				new CompoundStmt(new ConsAssigStmt("c", new ArithExp('-', new VarExpr("b"), new ConstExp(2)), new ConstExp(100), new ConstExp(200)), new PrintStmt(new VarExpr("c"))));

		
		
		ProgramState ps1 = new ProgramState(1, st1, exeStack,symTable, output, filetable, heap);
		ProgramState ps2 = new ProgramState(2, st2, exeStack, symTable, output, filetable, heap);
		ProgramState ps3 = new ProgramState(3, st3, exeStack, symTable, output, filetable, heap);
		ProgramState ps4 = new ProgramState(4, st4, exeStack, symTable, output, filetable, heap);
		ProgramState ps5 = new ProgramState(5, st5, exeStack, symTable, output, filetable, heap);
		ProgramState ps6 = new ProgramState(6, st6, exeStack, symTable, output, filetable, heap);
		ProgramState ps7 = new ProgramState(7, st7, exeStack, symTable, output, filetable, heap);
		ProgramState ps8 = new ProgramState(8, st8, exeStack, symTable, output, filetable, heap);
		ProgramState ps9 = new ProgramState(9, st9, exeStack, symTable, output, filetable, heap);
		ProgramState ps10 = new ProgramState(10, st10, exeStack, symTable, output, filetable, heap);
		ProgramState ps11 = new ProgramState(11, st11, exeStack, symTable, output, filetable, heap);
		ProgramState ps12 = new ProgramState(12, st12, exeStack, symTable, output, filetable, heap);
		ProgramState ps13 = new ProgramState(13, st13, exeStack, symTable, output, filetable, heap);
		ProgramState ps14 = new ProgramState(14, st14, exeStack, symTable, output, filetable, heap);
		ProgramState ps15 = new ProgramState(15, st15, exeStack, symTable, output, filetable, heap);
		
		repo = new Repo("test1.txt");

		repo.addProgram(ps1);
		repo.addProgram(ps2);
		repo.addProgram(ps3);
		repo.addProgram(ps4);
		repo.addProgram(ps5);
		repo.addProgram(ps6);
		repo.addProgram(ps7);
		repo.addProgram(ps8);
		repo.addProgram(ps9);
		repo.addProgram(ps10);
		repo.addProgram(ps11);
		repo.addProgram(ps12);
		repo.addProgram(ps13);
		repo.addProgram(ps14);
		repo.addProgram(ps15);
	}
	
	private ObservableList<IStatement> getPrgList()
	{
		
		ArrayList<IStatement> pr = (ArrayList<IStatement>)repo.getStatements();
		
		ObservableList<IStatement> prgs = FXCollections.observableArrayList(pr);
	     return prgs;
	}
}