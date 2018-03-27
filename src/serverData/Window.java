package serverData;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Window {
	
	Stage stage;
	Pane root;
	Button butPost = new Button("Отправить");
	Button butGet = new Button("Получить");
	TextField fieldGet = new TextField();
	TextField fieldPost = new TextField();
	
	Scene scene = new Scene(root);
	
	public void start(Stage stage)
	{
		root.getChildren().addAll(butGet,butPost,fieldGet, fieldPost);
		stage.setScene(scene);
		stage.show();
	}
	
	public Window(){
		
		start(stage);
	}
//	root.getChildren.addAll(butStart,butClose,fieldGet);

}
