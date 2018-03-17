import cleanWin.kotlx.ru.CleanFXwin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CleanFXwinDemo extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Scene scene = CleanFXwin.initResizable(stage, false);

		AnchorPane root = FXMLLoader.load(getClass().getResource("/userDemoLayout/userRoot.fxml"));
		if (root != null) {
			CleanFXwin.setRoot(root);
		}

		//TODO Созадать оформление окна

		stage.setScene(scene);
		stage.show();
	}
}
