import kotlx.ru.RuTWinFX.RuTWinFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kotlx.ru.RuTWinFX.SPMode;
import kotlx.ru.RuTWinFX.SlidePane;

import java.io.IOException;

@SuppressWarnings("SpellCheckingInspection")
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
		RuTWinFX.setSlidePane(SPMode.TOP, decor);

		stage.setScene(scene);
		stage.show();

		//*** debug msg
		SlidePane sp = (SlidePane) decor.getParent();
		System.out.println("getPrefHeight() = " + sp.getPrefHeight() + "  getPrefWidth() = " + sp.getPrefWidth());
		System.out.println("getMinHeight() = " + sp.getMinHeight() + "  getMinWidth() = " + sp.getMinWidth());

		//** end debug msg
	}
}
