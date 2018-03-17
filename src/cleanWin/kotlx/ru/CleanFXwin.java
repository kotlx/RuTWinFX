package cleanWin.kotlx.ru;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CleanFXwin {
	private static Stage stage;
	private static final CleanFXwin cw = new CleanFXwin();

	private static int MIN_HEIGHT = 100;
	private static int MIN_WIDTH = 100;
	private static int PREF_HEIGHT = 400;
	private static int PREF_WIDTH = 600;
	private static final int BORDER_WIDTH = 8;
	private static final int ANGLE_WIDTH = 15;

	private static double initX = 0;
	private static double initY = 0;
	private static double windowWidth = 0;
	private static double windowHeight = 0;
	private static double windowX = 0;
	private static double windowY = 0;

	private static AnchorPane backgroundPane;
	private static Pane borderPaneS;
	private static Pane borderPaneW;
	private static Pane borderPaneN;
	private static Pane borderPaneE;
	private static Pane anglePaneSW;
	private static Pane anglePaneNW;
	private static Pane anglePaneNE;
	private static Pane anglePaneSE;

	private CleanFXwin() {
	}

	public static Scene initResizable(Stage stg) {
		stage = stg;
		backgroundPane = new AnchorPane();
		cw.buildFrame(backgroundPane);
		cw.bindingBehavior();

		stage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(backgroundPane, PREF_WIDTH, PREF_HEIGHT, Color.TRANSPARENT);
		scene.getStylesheets().add("/cleanWin/kotlx/ru/res/bgStyle.css");
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

	public static <T extends Pane> void setRoot(T root) {
		stage.setWidth(root.getPrefWidth() + BORDER_WIDTH *2);
		stage.setHeight(root.getPrefHeight() + BORDER_WIDTH *2);
		stage.heightProperty().addListener((observable, oldValue, newValue) -> root.setPrefHeight(newValue.doubleValue() - BORDER_WIDTH *2));
		stage.widthProperty().addListener((observable, oldValue, newValue) -> root.setPrefWidth(newValue.doubleValue() - BORDER_WIDTH *2));
		root.setLayoutX(BORDER_WIDTH);
		root.setLayoutY(BORDER_WIDTH);

		backgroundPane.getChildren().add(root);
	}

	private void buildFrame(AnchorPane backgroundPane) {
		backgroundPane.setId("idBackgroundAnchorPane");

		borderPaneS = new Pane();
		borderPaneS.setId("idBorderPaneS");
		borderPaneS.getStyleClass().add("BorderPane");
		borderPaneS.setPrefHeight(BORDER_WIDTH);
		AnchorPane.setBottomAnchor(borderPaneS, 0.0);
		AnchorPane.setLeftAnchor(borderPaneS, 0.0);
		AnchorPane.setRightAnchor(borderPaneS, 0.0);

		borderPaneW = new Pane();
		borderPaneW.setId("idBorderPaneW");
		borderPaneW.getStyleClass().add("BorderPane");
		borderPaneW.setPrefWidth(BORDER_WIDTH);
		AnchorPane.setLeftAnchor(borderPaneW, 0.0);
		AnchorPane.setBottomAnchor(borderPaneW, 0.0);
		AnchorPane.setTopAnchor(borderPaneW, 0.0);

		borderPaneN = new Pane();
		borderPaneN.setId("idBorderPaneN");
		borderPaneN.getStyleClass().add("BorderPane");
		borderPaneN.setPrefHeight(BORDER_WIDTH);
		AnchorPane.setTopAnchor(borderPaneN, 0.0);
		AnchorPane.setLeftAnchor(borderPaneN, 0.0);
		AnchorPane.setRightAnchor(borderPaneN, 0.0);

		borderPaneE = new Pane();
		borderPaneE.setId("idBorderPaneE");
		borderPaneE.getStyleClass().add("BorderPane");
		borderPaneE.setPrefWidth(BORDER_WIDTH);
		AnchorPane.setRightAnchor(borderPaneE, 0.0);
		AnchorPane.setTopAnchor(borderPaneE, 0.0);
		AnchorPane.setBottomAnchor(borderPaneE, 0.0);

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

		backgroundPane.getChildren().addAll(borderPaneS, borderPaneW, borderPaneN, borderPaneE,
				anglePaneSW, anglePaneNW, anglePaneNE, anglePaneSE);
	}

	private void bindingBehavior() {
		borderPaneW.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getScreenX();
				windowX = stage.getX();
				windowWidth = windowX + stage.getWidth();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getScreenX() - initX;
				if ((windowWidth - windowX - deltaX) >= MIN_WIDTH) {
					stage.setWidth(windowWidth - windowX - deltaX);
					stage.setX(windowX + deltaX);
				}
			}
		});

		borderPaneN.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initY = event.getScreenY();
				windowY = stage.getY();
				windowHeight = windowY + stage.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaY = event.getScreenY() - initY;
				if ((windowHeight - windowY - deltaY) >= MIN_HEIGHT) {
					stage.setY(windowY + deltaY);
					stage.setHeight(windowHeight - windowY - deltaY);
				}
			}
		});

		borderPaneS.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initY = event.getSceneY();
				windowHeight = stage.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaY = event.getSceneY() - initY;
				if ((windowHeight + deltaY) >= MIN_HEIGHT)
					stage.setHeight(windowHeight + deltaY);
			}
		});

		borderPaneE.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getSceneX();
				windowWidth = stage.getWidth();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getSceneX() - initX;
				if ((windowWidth + deltaX) >= MIN_WIDTH)
					stage.setWidth(windowWidth + deltaX);
			}
		});

		anglePaneSE.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getSceneX();
				initY = event.getSceneY();
				windowWidth = stage.getWidth();
				windowHeight = stage.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getSceneX() - initX;
				double deltaY = event.getSceneY() - initY;
				if ((windowWidth + deltaX) >= MIN_WIDTH)
					stage.setWidth(windowWidth + deltaX);
				if((windowHeight + deltaY) >= MIN_HEIGHT)
					stage.setHeight(windowHeight + deltaY);
			}
		});

		anglePaneSW.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getScreenX();
				initY = event.getSceneY();
				windowX = stage.getX();
				windowWidth = windowX + stage.getWidth();
				windowHeight = stage.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getScreenX() - initX;
				double deltaY = event.getSceneY() - initY;
				if((windowWidth - windowX - deltaX) >= MIN_WIDTH) {
					stage.setX(windowX + deltaX);
					stage.setWidth(windowWidth - windowX - deltaX);
				}
				if ((windowHeight + deltaY) >= MIN_HEIGHT)
					stage.setHeight(windowHeight + deltaY);
			}
		});

		anglePaneNW.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getScreenX();
				initY = event.getScreenY();
				windowX = stage.getX();
				windowY = stage.getY();
				windowWidth = windowX + stage.getWidth();
				windowHeight = windowY + stage.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getScreenX() - initX;
				double deltaY = event.getScreenY() - initY;
				if ((windowWidth - windowX - deltaX) >= MIN_WIDTH) {
					stage.setX(windowX + deltaX);
					stage.setWidth(windowWidth - windowX - deltaX);
				}
				if ((windowHeight - windowY - deltaY) >= MIN_HEIGHT) {
					stage.setY(windowY + deltaY);
					stage.setHeight(windowHeight - windowY - deltaY);
				}
			}
		});

		anglePaneNE.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getSceneX();
				initY = event.getScreenY();
				windowY = stage.getY();
				windowWidth = stage.getWidth();
				windowHeight = windowY + stage.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				double deltaX = event.getSceneX() - initX;
				double deltaY = event.getScreenY() - initY;
				if ((windowHeight - windowY - deltaY) >= MIN_HEIGHT) {
					stage.setY(windowY + deltaY);
					stage.setHeight(windowHeight - windowY - deltaY);
				}
				if ((windowWidth + deltaX) >= MIN_WIDTH)
					stage.setWidth(windowWidth + deltaX);
			}
		});
	}

}
