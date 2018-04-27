
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kotlx.ru.RuTWinFX.RuTWinFX;
import kotlx.ru.RuTWinFX.SladePanePosition;

import java.io.IOException;

public class DemoRwTWinXF extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Scene scene = RuTWinFX.init(stage);
		RuTWinFX.setStylesheet(getClass().getResource("/kotlx/ru/RuTWinFX/res/bgStyleLight.css"));

		AnchorPane root = FXMLLoader.load(getClass().getResource("/userDemoLayout/userRoot.fxml"));
		RuTWinFX.setRoot(root);

		AnchorPane decor = FXMLLoader.load(getClass().getResource("/userDemoLayout/windowDecor.fxml"));
		RuTWinFX.setSlidePane(SladePanePosition.TOP, decor);

		//***dbg
//		scene.setFill(new Color(0,0.9,0.9,0.3));
//		scene.setOnMouseMoved(event -> System.out.println("Scene.MouseMoved:(x = " + event.getScreenX() + ", y = " + event.getScreenY() +")"));
//		stage.addEventFilter(MouseEvent.MOUSE_MOVED, event -> System.out.println("Stage.MouseMoved:(x = " + event.getScreenX() + ", y = " + event.getScreenY() +")"));
		//***
		stage.setScene(scene);
		stage.show();
	}
}
