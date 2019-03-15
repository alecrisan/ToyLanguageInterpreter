package application;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import controller.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import model.ProgramState;
import model.expression.ArithExp;
import model.expression.ConstExp;
import model.statement.AssignStmt;
import model.statement.CompoundStmt;
import model.statement.ConditionalStmt;
import model.statement.IStatement;
import model.statement.PrintStmt;
import repository.IRepo;
import repository.Repo;
import utils.ExecStack;
import utils.FileTable;
import utils.Heap;
import utils.IFileTable;
import utils.IHeap;
import utils.ISymTable;
import utils.Output;
import utils.Pair;
import utils.SymTable;

public class SecondController {
	
	private IRepo repo;
	
	private Controller ctr;
	
	@FXML 
	private TextField NoOfPrg;
	
	@FXML 
	private TableView<HeapTuple> heap;
	
	@FXML
	private TableColumn<HeapTuple, Integer> address;
	
	@FXML
	private TableColumn<HeapTuple, Integer> content;
	
	@FXML 
	private ListView<Integer> out;
	
	@FXML
	private TableView<FileTableTuple> filetable;
	
	@FXML
	private TableColumn<FileTableTuple, String> filenameC;
	
	@FXML
	private TableColumn<FileTableTuple, Integer> idC;
	
	@FXML
	private TableView<SymTableTuple> symtable;
	
	@FXML
	private TableColumn<SymTableTuple, String> varName;
	
	@FXML
	private TableColumn<SymTableTuple, Integer> value;
	
	@FXML
	private ListView<Integer> listPrgIDs;
	
	@FXML
	private ListView<IStatement> stack;
	
	@FXML
	private Button runOneStepButton;
	
	private IStatement selectedSt;
	
	private Integer selectedID;
	
	
	public void setProgram(IStatement selected)
	{
		this.selectedSt = selected;
		ProgramState p = new ProgramState(1, selectedSt, new ExecStack<>(), new SymTable<>(), 
				new Output<>(), new FileTable<>(), new Heap<>());
		
		repo = new Repo("text1.txt");
		
		ctr = new Controller(repo);
		
		ctr.getRepo().addProgram(p);
		
		NoOfPrg.setText(new Integer(ctr.getRepo().getPrograms().size()).toString());
		NoOfPrg.setEditable(false);
		
		listPrgIDs.setItems(getPrgIDList());
		
		selectID();
		
		stack.setItems(getStack());
		out.setItems(getOut());
		initSymTable();
		initFileTable();
		initHeap();
		
		try {
			repo.clearPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void selectID()
	{
		listPrgIDs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
	    	  
            @Override
            public void changed(ObservableValue<? extends Integer> observable,  Integer oldValue, Integer newValue) {
            	
                
                System.out.println("Selected id: " + newValue);
                
                selectedID = newValue;
            }
        });
	}
	
	private void initSymTable()
	{
		
		varName.setCellValueFactory(new PropertyValueFactory<>("varName"));
		value.setCellValueFactory(new PropertyValueFactory<>("value"));
		
		
		symtable.setItems(getSym());
	
	}
	
	public ObservableList<SymTableTuple> getSym()
    {

        ObservableList<SymTableTuple> tuples = FXCollections
                .observableArrayList();

        ISymTable<String, Integer> sym;
        
        if(selectedID == null)
        	sym = getPrgBySt().getSymTable();
        else
        	sym = getPrgByID().getSymTable();
        
        Map<String, Integer> dict = sym.getDictionary();

         for(Entry<String, Integer> a: dict.entrySet())
         {
        	 SymTableTuple s = new SymTableTuple();
        	 s.setVarName(a.getKey());
        	 s.setValue(a.getValue());
        	 tuples.add(s);
        	 
         }
        

        return tuples;
    }
	
	private void initFileTable()
	{
		
		idC.setCellValueFactory(new PropertyValueFactory<FileTableTuple, Integer>("id"));
		filenameC.setCellValueFactory(new PropertyValueFactory<FileTableTuple, String>("filename"));
		
		
		filetable.setItems(getFileT());
	
	}
	
	public ObservableList<FileTableTuple> getFileT()
    {

        ObservableList<FileTableTuple> tuples = FXCollections
                .observableArrayList();

        IFileTable<Integer, Pair> f = getPrgBySt().getFileTable();
        
        Map<Integer, Pair> dict = f.getFileTable();

         for(Entry<Integer, Pair> a: dict.entrySet())
         {
        	 FileTableTuple s = new FileTableTuple();
        	 s.setId(a.getKey());
        	 s.setFilename(a.getValue().getFilename());
        	 tuples.add(s);
        	 
         }
        

        return tuples;
    }
	
	private void initHeap()
	{
		
		address.setCellValueFactory(new PropertyValueFactory<HeapTuple, Integer>("address"));
		content.setCellValueFactory(new PropertyValueFactory<HeapTuple, Integer>("content"));
		
		
		heap.setItems(getHeap());
	
	}
	
	public ObservableList<HeapTuple> getHeap()
    {

        ObservableList<HeapTuple> tuples = FXCollections
                .observableArrayList();

        IHeap<Integer> h = getPrgBySt().getHeap();
        
        Map<Integer, Integer> dict = h.getHeap();

         for(Entry<Integer, Integer> a: dict.entrySet())
         {
        	 HeapTuple s = new HeapTuple();
        	 s.setAddress(a.getKey());
        	 s.setContent(a.getValue());
        	 tuples.add(s);
        	 
         }
        

        return tuples;
    }
	
		
	
	@FXML
	public void handleRunButtonAction(ActionEvent event)
	{
		try {
			ctr.setExecutor(Executors.newFixedThreadPool(2));
			ctr.oneStepForAllPrg(ctr.getRepo().getPrograms());
			
			NoOfPrg.setText(new Integer(ctr.getRepo().getPrograms().size()).toString());
			listPrgIDs.setItems(getPrgIDList());
			selectID();
			stack.setItems(getStack());
			out.setItems(getOut());
			initSymTable();
			initFileTable();
			initHeap();
	
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ProgramState getPrgBySt()
	{
		for(ProgramState p: ctr.getRepo().getPrograms())
		{
			if(p.getStatement().equals(selectedSt))
				return p;
		}
		return null;
	}
	
	public ProgramState getPrgByID()
	{
		for(ProgramState p: ctr.getRepo().getPrograms())
		{
			if(p.getId() == selectedID)
				return p;
		}
		return null;
	}
	
	
	private ObservableList<IStatement> getStack()
	{
		ArrayList<IStatement> pr;
		
		if(selectedID == null)
			pr = (ArrayList<IStatement>)getPrgBySt().getStack().getStack();
		else
			pr = (ArrayList<IStatement>)getPrgByID().getStack().getStack();
		
		
		ObservableList<IStatement> prgs = FXCollections.observableArrayList(pr);
	     return prgs;
	}
	
	private ObservableList<Integer> getOut()
	{
		
		ArrayList<Integer> pr = (ArrayList<Integer>)getPrgBySt().getOutput().getOut();
		
		ObservableList<Integer> prgs = FXCollections.observableArrayList(pr);
	     return prgs;
	}
	
	private ObservableList<Integer> getPrgIDList()
	{
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(ProgramState p : ctr.getRepo().getPrograms())
		{
			a.add(p.getId());
		}
	
		
		ObservableList<Integer> prgs = FXCollections.observableArrayList(a);
	     return prgs;
	}
}
