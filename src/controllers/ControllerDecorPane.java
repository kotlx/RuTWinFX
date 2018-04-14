package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kotlx.ru.RuTWinFX.RuTWinFX;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDecorPane implements Initializable {
	@FXML private Pane minimizePane;

	@FXML private Pane maximizePane;

	@FXML private Pane closeBtnPane;

	@FXML private Pane decorationPane;

	AnchorPane frame = RuTWinFX.getFrame();
	private double xOffset = 0;
	private double yOffset = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Перемещение окна если нажать на декорационной панели
		decorationPane.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				xOffset = frame.getLayoutX() - event.getScreenX();
				yOffset = frame.getLayoutY() - event.getScreenY();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				frame.setLayoutX(event.getSceneX() + xOffset);
				frame.setLayoutY(event.getSceneY() + yOffset);
			}
			event.consume();
		});

		// Закрываем приложение
		closeBtnPane.setOnMouseClicked(event -> Platform.exit());
	}
}
