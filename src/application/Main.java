package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			
			Scene scene = new Scene(loader.load());
			
			SampleController controller = loader.getController();
		
			primaryStage.setScene(scene);
			
			primaryStage.setHeight(500);
			primaryStage.setWidth(500);
			
			controller.setStage(primaryStage);
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
