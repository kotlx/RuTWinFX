
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

		stage.setScene(scene);
		stage.show();
	}
}
