package RuTWinFX.kotlx.ru;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RuTWinFX {
	private static final RuTWinFX rutw = new RuTWinFX();

	private static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
	private static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

	private static double MIN_WIDTH = 100d;
	private static double MIN_HEIGHT = 100d;
	private static double PREF_WIDTH = 400d;
	private static double PREF_HEIGHT = 300d;
	private static final double BORDER_WIDTH = 8d;
	private static final double ANGLE_WIDTH = 16d;

	private static double initX = 0;
	private static double initY = 0;
	private static double windowX = 0;
	private static double windowY = 0;
	private static double windowWidth = 0;
	private static double windowHeight = 0;

	private static AnchorPane backgroundPane;
	private static Pane borderPaneS;
	private static Pane borderPaneW;
	private static Pane borderPaneN;
	private static Pane borderPaneE;
	private static Pane anglePaneSW;
	private static Pane anglePaneNW;
	private static Pane anglePaneNE;
	private static Pane anglePaneSE;

	private RuTWinFX() {
	}

	public static Scene initResizable(final Stage stage) {
		final Group screenArea = new Group(rutw.buildFrame());
		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(screenArea, SCREEN_WIDTH, SCREEN_HEIGHT, Color.TRANSPARENT);
		scene.getStylesheets().add("/RuTWinFX/kotlx/ru/res/bgStyle.css");
		return scene;
	}

	public static Scene initResizable(Stage stage, int prefWidth, int prefHeight) {
		PREF_HEIGHT = prefHeight;
		PREF_WIDTH = prefWidth;
		return initResizable(stage);
	}

	public static Scene initResizable(Stage stage, int prefWidth, int prefHeight, int minWidth, int minHeight) {
		PREF_WIDTH = prefWidth;
		PREF_HEIGHT = prefHeight;
		MIN_WIDTH = minWidth;
		MIN_HEIGHT = minHeight;
		return initResizable(stage);
	}

	private AnchorPane buildFrame() {
		borderPaneS = new Pane();
		borderPaneS.setId("idBorderPaneS");
		borderPaneS.getStyleClass().add("BorderPane");
		borderPaneS.setPrefHeight(BORDER_WIDTH);
		AnchorPane.setBottomAnchor(borderPaneS, 0.0);
		AnchorPane.setLeftAnchor(borderPaneS, ANGLE_WIDTH);
		AnchorPane.setRightAnchor(borderPaneS, ANGLE_WIDTH);

		borderPaneW = new Pane();
		borderPaneW.setId("idBorderPaneW");
		borderPaneW.getStyleClass().add("BorderPane");
		borderPaneW.setPrefWidth(BORDER_WIDTH);
		AnchorPane.setLeftAnchor(borderPaneW, 0.0);
		AnchorPane.setBottomAnchor(borderPaneW, ANGLE_WIDTH);
		AnchorPane.setTopAnchor(borderPaneW, ANGLE_WIDTH);

		borderPaneN = new Pane();
		borderPaneN.setId("idBorderPaneN");
		borderPaneN.getStyleClass().add("BorderPane");
		borderPaneN.setPrefHeight(BORDER_WIDTH);
		AnchorPane.setTopAnchor(borderPaneN, 0.0);
		AnchorPane.setLeftAnchor(borderPaneN, ANGLE_WIDTH);
		AnchorPane.setRightAnchor(borderPaneN, ANGLE_WIDTH);

		borderPaneE = new Pane();
		borderPaneE.setId("idBorderPaneE");
		borderPaneE.getStyleClass().add("BorderPane");
		borderPaneE.setPrefWidth(BORDER_WIDTH);
		AnchorPane.setRightAnchor(borderPaneE, 0.0);
		AnchorPane.setTopAnchor(borderPaneE, ANGLE_WIDTH);
		AnchorPane.setBottomAnchor(borderPaneE, ANGLE_WIDTH);

		anglePaneSW = new Pane();
		anglePaneSW.setId("idAnglePaneSW");
		anglePaneSW.getStyleClass().add("BorderPane");
		anglePaneSW.setPrefWidth(ANGLE_WIDTH);
		anglePaneSW.setPrefHeight(ANGLE_WIDTH);
		AnchorPane.setBottomAnchor(anglePaneSW, 0.0);
		AnchorPane.setLeftAnchor(anglePaneSW, 0.0);

		anglePaneNW = new Pane();
		anglePaneNW.setId("idAnglePaneNW");
		anglePaneNW.getStyleClass().add("BorderPane");
		anglePaneNW.setPrefWidth(ANGLE_WIDTH);
		anglePaneNW.setPrefHeight(ANGLE_WIDTH);
		AnchorPane.setTopAnchor(anglePaneNW, 0.0);
		AnchorPane.setLeftAnchor(anglePaneNW, 0.0);

		anglePaneNE = new Pane();
		anglePaneNE.setId("idAnglePaneNE");
		anglePaneNE.getStyleClass().add("BorderPane");
		anglePaneNE.setPrefWidth(ANGLE_WIDTH);
		anglePaneNE.setPrefHeight(ANGLE_WIDTH);
		AnchorPane.setTopAnchor(anglePaneNE, 0.0);
		AnchorPane.setRightAnchor(anglePaneNE, 0.0);

		anglePaneSE = new Pane();
		anglePaneSE.setId("idAnglePaneSE");
		anglePaneSE.getStyleClass().add("BorderPane");
		anglePaneSE.setPrefWidth(ANGLE_WIDTH);
		anglePaneSE.setPrefHeight(ANGLE_WIDTH);
		AnchorPane.setBottomAnchor(anglePaneSE, 0.0);
		AnchorPane.setRightAnchor(anglePaneSE, 0.0);

		backgroundPane = new AnchorPane(borderPaneS, borderPaneW, borderPaneN, borderPaneE,
				anglePaneSW, anglePaneNW, anglePaneNE, anglePaneSE);
		backgroundPane.setId("idBackgroundAnchorPane");
		backgroundPane.setPrefWidth(PREF_WIDTH);
		backgroundPane.setPrefHeight(PREF_HEIGHT);
		backgroundPane.setLayoutX(SCREEN_WIDTH / 2 - PREF_WIDTH / 2);
		backgroundPane.setLayoutY(SCREEN_HEIGHT / 3 - PREF_HEIGHT / 3);

		rutw.bindingBehavior();

		return backgroundPane;
	}

	private void bindingBehavior() {
		borderPaneW.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getScreenX();
				windowX = backgroundPane.getLayoutX();
				windowWidth = windowX + backgroundPane.getWidth();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getScreenX() - initX;
				if ((windowWidth - windowX - deltaX) >= MIN_WIDTH) {
					backgroundPane.setPrefWidth(windowWidth - windowX - deltaX);
					backgroundPane.setLayoutX(windowX + deltaX);
				}
			}
		});

		borderPaneN.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initY = event.getScreenY();
				windowY = backgroundPane.getLayoutY();
				windowHeight = windowY + backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaY = event.getScreenY() - initY;
				if ((windowHeight - windowY - deltaY) >= MIN_HEIGHT) {
					backgroundPane.setLayoutY(windowY + deltaY);
					backgroundPane.setPrefHeight(windowHeight - windowY - deltaY);
				}
			}
		});

		borderPaneS.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initY = event.getSceneY();
				windowHeight = backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaY = event.getSceneY() - initY;
				if ((windowHeight + deltaY) >= MIN_HEIGHT)
					backgroundPane.setPrefHeight(windowHeight + deltaY);
			}
		});

		borderPaneE.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getSceneX();
				windowWidth = backgroundPane.getWidth();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getSceneX() - initX;
				if ((windowWidth + deltaX) >= MIN_WIDTH)
					backgroundPane.setPrefWidth(windowWidth + deltaX);
			}
		});

		anglePaneSE.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getSceneX();
				initY = event.getSceneY();
				windowWidth = backgroundPane.getWidth();
				windowHeight = backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getSceneX() - initX;
				double deltaY = event.getSceneY() - initY;
				if ((windowWidth + deltaX) >= MIN_WIDTH)
					backgroundPane.setPrefWidth(windowWidth + deltaX);
				if ((windowHeight + deltaY) >= MIN_HEIGHT)
					backgroundPane.setPrefHeight(windowHeight + deltaY);
			}
		});

		anglePaneSW.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getScreenX();
				initY = event.getSceneY();
				windowX = backgroundPane.getLayoutX();
				windowWidth = windowX + backgroundPane.getWidth();
				windowHeight = backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getScreenX() - initX;
				double deltaY = event.getSceneY() - initY;
				if ((windowWidth - windowX - deltaX) >= MIN_WIDTH) {
					backgroundPane.setLayoutX(windowX + deltaX);
					backgroundPane.setPrefWidth(windowWidth - windowX - deltaX);
				}
				if ((windowHeight + deltaY) >= MIN_HEIGHT)
					backgroundPane.setPrefHeight(windowHeight + deltaY);
			}
		});

		anglePaneNW.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getScreenX();
				initY = event.getScreenY();
				windowX = backgroundPane.getLayoutX();
				windowY = backgroundPane.getLayoutY();
				windowWidth = windowX + backgroundPane.getWidth();
				windowHeight = windowY + backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getScreenX() - initX;
				double deltaY = event.getScreenY() - initY;
				if ((windowWidth - windowX - deltaX) >= MIN_WIDTH) {
					backgroundPane.setLayoutX(windowX + deltaX);
					backgroundPane.setPrefWidth(windowWidth - windowX - deltaX);
				}
				if ((windowHeight - windowY - deltaY) >= MIN_HEIGHT) {
					backgroundPane.setLayoutY(windowY + deltaY);
					backgroundPane.setPrefHeight(windowHeight - windowY - deltaY);
				}
			}
		});

		anglePaneNE.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getSceneX();
				initY = event.getScreenY();
				windowY = backgroundPane.getLayoutY();
				windowWidth = backgroundPane.getWidth();
				windowHeight = windowY + backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getSceneX() - initX;
				double deltaY = event.getScreenY() - initY;
				if ((windowHeight - windowY - deltaY) >= MIN_HEIGHT) {
					backgroundPane.setLayoutY(windowY + deltaY);
					backgroundPane.setPrefHeight(windowHeight - windowY - deltaY);
				}
				if ((windowWidth + deltaX) >= MIN_WIDTH)
					backgroundPane.setPrefWidth(windowWidth + deltaX);
			}
		});
	}

	public static <T extends Parent> void setRoot(T userContent) {
		AnchorPane.setTopAnchor(userContent, BORDER_WIDTH);
		AnchorPane.setRightAnchor(userContent, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(userContent, BORDER_WIDTH);
		AnchorPane.setLeftAnchor(userContent, BORDER_WIDTH);

		backgroundPane.getChildren().add(userContent);
	}
}
