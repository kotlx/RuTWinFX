import RuTWinFX.kotlx.ru.RuTWinFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("SpellCheckingInspection")
public class DemoRwTWinXF extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Scene scene = RuTWinFX.initResizable(stage);

		AnchorPane root = FXMLLoader.load(getClass().getResource("/userDemoLayout/userLayout.fxml"));
		if (root != null) {
			RuTWinFX.setRoot(root);
		}

		stage.setScene(scene);
		stage.show();
	}
}