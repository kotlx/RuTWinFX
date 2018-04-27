package kotlx.ru.RuTWinFX;

		import javafx.geometry.Insets;
		import javafx.geometry.Rectangle2D;
		import javafx.scene.Group;
		import javafx.scene.Scene;
		import javafx.scene.input.MouseEvent;
		import javafx.scene.layout.AnchorPane;
		import javafx.scene.layout.Pane;
		import javafx.scene.layout.Region;
		import javafx.scene.paint.Color;
		import javafx.stage.Screen;
		import javafx.stage.Stage;
		import javafx.stage.StageStyle;

		import java.net.URL;

public class RuTWinFX {
	private static final RuTWinFX rutw = new RuTWinFX();
	private static Scene scene;

	private static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
	private static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

	private static double MIN_WIDTH = 50d;
	private static double MIN_HEIGHT = 50d;
	private static double PREF_WIDTH = 100d;
	private static double PREF_HEIGHT = 100d;
	private static final double STICK_WIDTH = 10d;
	private static final double BORDER_WIDTH = 8d;
	private static final double ANGLE_WIDTH = 16d;
	private static final double INDENT = 2;

	private static boolean maximized = false;
	private static Rectangle2D windowDimension;

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

	public static Scene init(final Stage stage) {
		final Group screenArea = new Group(rutw.buildFrame());
		stage.initStyle(StageStyle.TRANSPARENT);
		//*** на всякий случай:
		stage.setX(0.0);
		stage.setY(0.0);
		//***
		scene = new Scene(screenArea, SCREEN_WIDTH, SCREEN_HEIGHT, Color.TRANSPARENT);
		scene.getStylesheets().add("/kotlx/ru/RuTWinFX/res/bgStyle.css");
		return scene;
	}

	private AnchorPane buildFrame() {
		borderPaneS = new Pane();
		borderPaneS.setId("idBorderPaneS");
		borderPaneS.getStyleClass().add("BorderPane");
		borderPaneS.setPrefHeight(BORDER_WIDTH);
		AnchorPane.setBottomAnchor(borderPaneS, 0.0);
		AnchorPane.setLeftAnchor(borderPaneS, BORDER_WIDTH);
		AnchorPane.setRightAnchor(borderPaneS, BORDER_WIDTH);

		borderPaneW = new Pane();
		borderPaneW.setId("idBorderPaneW");
		borderPaneW.getStyleClass().add("BorderPane");
		borderPaneW.setPrefWidth(BORDER_WIDTH);
		AnchorPane.setLeftAnchor(borderPaneW, 0.0);
		AnchorPane.setBottomAnchor(borderPaneW, BORDER_WIDTH);
		AnchorPane.setTopAnchor(borderPaneW, BORDER_WIDTH);

		borderPaneN = new Pane();
		borderPaneN.setId("idBorderPaneN");
		borderPaneN.getStyleClass().add("BorderPane");
		borderPaneN.setPrefHeight(BORDER_WIDTH);
		AnchorPane.setTopAnchor(borderPaneN, 0.0);
		AnchorPane.setLeftAnchor(borderPaneN, BORDER_WIDTH );
		AnchorPane.setRightAnchor(borderPaneN, BORDER_WIDTH);

		borderPaneE = new Pane();
		borderPaneE.setId("idBorderPaneE");
		borderPaneE.getStyleClass().add("BorderPane");
		borderPaneE.setPrefWidth(BORDER_WIDTH);
		AnchorPane.setRightAnchor(borderPaneE, 0.0);
		AnchorPane.setTopAnchor(borderPaneE, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(borderPaneE, BORDER_WIDTH);

		anglePaneSW = new Pane();
		anglePaneSW.setId("idAnglePaneSW");
		anglePaneSW.getStyleClass().add("AnglePane");
		anglePaneSW.setPrefWidth(ANGLE_WIDTH);
		anglePaneSW.setPrefHeight(ANGLE_WIDTH);
		AnchorPane.setBottomAnchor(anglePaneSW, 0.0);
		AnchorPane.setLeftAnchor(anglePaneSW, 0.0);

		anglePaneNW = new Pane();
		anglePaneNW.setId("idAnglePaneNW");
		anglePaneNW.getStyleClass().add("AnglePane");
		anglePaneNW.setPrefWidth(ANGLE_WIDTH);
		anglePaneNW.setPrefHeight(ANGLE_WIDTH);
		AnchorPane.setTopAnchor(anglePaneNW, 0.0);
		AnchorPane.setLeftAnchor(anglePaneNW, 0.0);

		anglePaneNE = new Pane();
		anglePaneNE.setId("idAnglePaneNE");
		anglePaneNE.getStyleClass().add("AnglePane");
		anglePaneNE.setPrefWidth(ANGLE_WIDTH);
		anglePaneNE.setPrefHeight(ANGLE_WIDTH);
		AnchorPane.setTopAnchor(anglePaneNE, 0.0);
		AnchorPane.setRightAnchor(anglePaneNE, 0.0);

		anglePaneSE = new Pane();
		anglePaneSE.setId("idAnglePaneSE");
		anglePaneSE.getStyleClass().add("AnglePane");
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
				final double deltaX = event.getScreenX() - initX;
				final double newWidth = windowWidth - windowX - deltaX;

				// Проверяем левый край не меньше ли минимального размера и не меньше ли размеров экрана
				if ((newWidth >= MIN_WIDTH) & (event.getScreenX() > STICK_WIDTH)) {
					backgroundPane.setPrefWidth(newWidth);
					backgroundPane.setLayoutX(windowX + deltaX);
					// Если край меньше размеров экрана то устанавливаем его рвным 0 с учетом ширины бордюра
				} else if (event.getScreenX() <= STICK_WIDTH) {
					backgroundPane.setLayoutX(-BORDER_WIDTH + INDENT);
					backgroundPane.setPrefWidth(windowWidth + BORDER_WIDTH - INDENT);
				}
			}
			event.consume();
		});

		borderPaneN.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initY = event.getScreenY();
				windowY = backgroundPane.getLayoutY();
				windowHeight = windowY + backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				final double deltaY = event.getScreenY() - initY;
				final double newHeight = windowHeight - windowY - deltaY;
				if ( (newHeight >= MIN_HEIGHT) & (event.getScreenY() > STICK_WIDTH)) {
					backgroundPane.setLayoutY(windowY + deltaY);
					backgroundPane.setPrefHeight(newHeight);
				} else if (event.getScreenY()<= STICK_WIDTH) {
					backgroundPane.setLayoutY(-BORDER_WIDTH + INDENT);
					backgroundPane.setPrefHeight(windowHeight + BORDER_WIDTH - INDENT);
					//***dbg
//					System.out.println("Scene X = " + scene.getX() + "Scene Y = " + scene.getY() );
//					System.out.println("backgroundPane.getLayoutY() = " + backgroundPane.getLayoutY());
					//***
				}
			}
			event.consume();
		});

		borderPaneS.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initY = event.getSceneY();
				windowHeight = backgroundPane.getHeight();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				final double deltaY = event.getSceneY() - initY;
				final double newHeight = windowHeight + deltaY;
				// Если не меньше минимальной ширины по высоте и не боьлше нижнего края экрана с учетом панели задач ОС
				if (( newHeight >= MIN_HEIGHT) & (event.getScreenY() < SCREEN_HEIGHT - STICK_WIDTH - getTaskPaneHeight()))
					backgroundPane.setPrefHeight(newHeight);
				else if (event.getScreenY() >= SCREEN_HEIGHT - STICK_WIDTH - getTaskPaneHeight())
					backgroundPane.setPrefHeight(SCREEN_HEIGHT - backgroundPane.getLayoutY() - getTaskPaneHeight() + BORDER_WIDTH - INDENT);

			}
			event.consume();
		});

		borderPaneE.addEventFilter(MouseEvent.ANY, event -> {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				initX = event.getSceneX();
				windowWidth = backgroundPane.getWidth();
			}

			if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				final double deltaX = event.getSceneX() - initX;
				final double newWidth = windowWidth + deltaX;
				if ( (newWidth >= MIN_WIDTH) & (event.getScreenX() < SCREEN_WIDTH - STICK_WIDTH))
					backgroundPane.setPrefWidth(windowWidth + deltaX);
				else if (event.getScreenX() >= SCREEN_WIDTH - STICK_WIDTH) {
					backgroundPane.setPrefWidth(SCREEN_WIDTH - backgroundPane.getLayoutX() + BORDER_WIDTH - INDENT);
					//***dbg
//					System.out.println("SCREEN_WIDTH = " + SCREEN_WIDTH);
//					System.out.println("backgroundPane правый край = " + (backgroundPane.getWidth() + backgroundPane.getLayoutX()));
					//***
				}
			}
			event.consume();
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
				final double newWidth = windowWidth + deltaX;
				final double newHeight = windowHeight + deltaY;
				// Право
				if ( (newWidth >= MIN_WIDTH) & (event.getScreenX() < SCREEN_WIDTH - STICK_WIDTH))
					backgroundPane.setPrefWidth(windowWidth + deltaX);
				else if (event.getScreenX() >= SCREEN_WIDTH - STICK_WIDTH)
					backgroundPane.setPrefWidth(SCREEN_WIDTH - backgroundPane.getLayoutX() + BORDER_WIDTH - INDENT);
				// Низ
				if (( newHeight >= MIN_HEIGHT) & (event.getScreenY() < SCREEN_HEIGHT - STICK_WIDTH - getTaskPaneHeight()))
					backgroundPane.setPrefHeight(newHeight);
				else if (event.getScreenY() >= SCREEN_HEIGHT - STICK_WIDTH - getTaskPaneHeight())
					backgroundPane.setPrefHeight(SCREEN_HEIGHT - backgroundPane.getLayoutY() - getTaskPaneHeight() + BORDER_WIDTH - INDENT);
			}
			event.consume();
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
				final double deltaX = event.getScreenX() - initX;
				final double deltaY = event.getSceneY() - initY;
				final double newWidth = windowWidth - windowX - deltaX;
				final double newHeight = windowHeight + deltaY;
				// Лево
				if ((newWidth >= MIN_WIDTH) & (event.getScreenX() > STICK_WIDTH)) {
					backgroundPane.setLayoutX(windowX + deltaX);
					backgroundPane.setPrefWidth(newWidth);
				} else if (event.getScreenX() <= STICK_WIDTH) {
					backgroundPane.setLayoutX(-BORDER_WIDTH + INDENT);
					backgroundPane.setPrefWidth(windowWidth + BORDER_WIDTH - INDENT);
				}
				// Низ
				if (( newHeight >= MIN_HEIGHT) & (event.getScreenY() < SCREEN_HEIGHT - STICK_WIDTH - getTaskPaneHeight()))
					backgroundPane.setPrefHeight(newHeight);
				else if (event.getScreenY() >= SCREEN_HEIGHT - STICK_WIDTH - getTaskPaneHeight())
					backgroundPane.setPrefHeight(SCREEN_HEIGHT - backgroundPane.getLayoutY() - getTaskPaneHeight() + BORDER_WIDTH - INDENT);
			}
			event.consume();
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
				final double newWidth = windowWidth - windowX - deltaX;
				final double newHeight = windowHeight - windowY - deltaY;
				// Верх
				if ( (newHeight >= MIN_HEIGHT) & (event.getScreenY() > STICK_WIDTH)) {
					backgroundPane.setLayoutY(windowY + deltaY);
					backgroundPane.setPrefHeight(newHeight);
				} else if (event.getScreenY()<= STICK_WIDTH) {
					backgroundPane.setLayoutY(-BORDER_WIDTH + INDENT);
					backgroundPane.setPrefHeight(windowHeight + BORDER_WIDTH - INDENT);
				}
				// Лево
				if ((newWidth >= MIN_WIDTH) & (event.getScreenX() > STICK_WIDTH)) {
					backgroundPane.setLayoutX(windowX + deltaX);
					backgroundPane.setPrefWidth(newWidth);
				} else if (event.getScreenX() <= STICK_WIDTH) {
					backgroundPane.setLayoutX(-BORDER_WIDTH + INDENT);
					backgroundPane.setPrefWidth(windowWidth + BORDER_WIDTH - INDENT);
				}
			}
			event.consume();
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
				final double newWidth = windowWidth + deltaX;
				final double newHeight = windowHeight - windowY - deltaY;
				// Верх
				if ( (newHeight >= MIN_HEIGHT) & (event.getScreenY() > STICK_WIDTH)) {
					backgroundPane.setLayoutY(windowY + deltaY);
					backgroundPane.setPrefHeight(newHeight);
				} else if (event.getScreenY()<= STICK_WIDTH) {
					backgroundPane.setLayoutY(-BORDER_WIDTH + INDENT);
					backgroundPane.setPrefHeight(windowHeight + BORDER_WIDTH - INDENT);
				}
				// Право
				if ( (newWidth >= MIN_WIDTH) & (event.getScreenX() < SCREEN_WIDTH - STICK_WIDTH))
					backgroundPane.setPrefWidth(windowWidth + deltaX);
				else if (event.getScreenX() >= SCREEN_WIDTH - STICK_WIDTH)
					backgroundPane.setPrefWidth(SCREEN_WIDTH - backgroundPane.getLayoutX() + BORDER_WIDTH - INDENT);
			}
			event.consume();
		});
	}

	public static <T extends Region> void setRoot(T userContent) {
		if (userContent == null) throw new NullPointerException();

		AnchorPane.setTopAnchor(userContent, BORDER_WIDTH);
		AnchorPane.setRightAnchor(userContent, BORDER_WIDTH);
		AnchorPane.setBottomAnchor(userContent, BORDER_WIDTH);
		AnchorPane.setLeftAnchor(userContent, BORDER_WIDTH);

		backgroundPane.setPrefWidth(PREF_WIDTH = userContent.getPrefWidth() + 2 * BORDER_WIDTH);
		backgroundPane.setPrefHeight(PREF_HEIGHT = userContent.getPrefHeight() + 2 * BORDER_WIDTH);

		if (MIN_WIDTH < userContent.getMinWidth()) MIN_WIDTH = userContent.getMinWidth() + 2 * BORDER_WIDTH;
		else userContent.setMinWidth(MIN_WIDTH - 2 * BORDER_WIDTH);

		if (MIN_HEIGHT < userContent.getMinHeight()) MIN_HEIGHT = userContent.getMinHeight() + 2 * BORDER_WIDTH;
		else userContent.setMinHeight(MIN_HEIGHT - 2 * BORDER_WIDTH);

		backgroundPane.setMinWidth(MIN_WIDTH);
		backgroundPane.setMinHeight(MIN_HEIGHT);

		backgroundPane.getChildren().add(userContent);
	}

	public static <T extends Region> void setSlidePane(final SladePanePosition position, final T userContent) {
		final SlidePane<T> slidePane = new SlidePane<>(position, userContent);
		slidePane.setActivationWidth(18);
		final Insets insets = new Insets(BORDER_WIDTH);
		SlidePane.setAnchor(position, slidePane, insets);

		switch (position) {
			case TOP:
				if (MIN_WIDTH < slidePane.getMinWidth()) MIN_WIDTH = slidePane.getMinWidth() + 2 * BORDER_WIDTH;
				else slidePane.setMinWidth(MIN_WIDTH - 2 * BORDER_WIDTH);
				backgroundPane.setMinWidth(MIN_WIDTH);
				break;
			case RIGHT:
				if (MIN_HEIGHT < slidePane.getMinHeight()) MIN_HEIGHT = slidePane.getMinHeight() + 2 * BORDER_WIDTH;
				else slidePane.setMinHeight(MIN_HEIGHT - 2 * BORDER_WIDTH);
				backgroundPane.setMinHeight(MIN_HEIGHT);
				break;
			case LEFT:
				if (MIN_HEIGHT < slidePane.getMinHeight()) MIN_HEIGHT = slidePane.getMinHeight() + 2 * BORDER_WIDTH;
				else slidePane.setMinHeight(MIN_HEIGHT - 2 * BORDER_WIDTH);
				backgroundPane.setMinHeight(MIN_HEIGHT);
				break;
			case BOTTOM:
				if (MIN_WIDTH < slidePane.getMinWidth()) MIN_WIDTH = slidePane.getMinWidth() + 2 * BORDER_WIDTH;
				else slidePane.setMinWidth(MIN_WIDTH - 2 * BORDER_WIDTH);
				backgroundPane.setMinWidth(MIN_WIDTH);
				break;
		}

		backgroundPane.getChildren().add(slidePane);
	}

	public static void setStylesheet(URL path) {
		scene.getStylesheets().clear();
		scene.getStylesheets().add(path.toString());
	}

	public static AnchorPane getFrame() {
		return backgroundPane;
	}

	private static double getTaskPaneHeight() {
		return Screen.getPrimary().getBounds().getHeight() - Screen.getPrimary().getVisualBounds().getHeight();
	}

	public static void setFrameMaximized() {
		if (!maximized) {
			windowDimension = new Rectangle2D(backgroundPane.getLayoutX(), backgroundPane.getLayoutY(), backgroundPane.getWidth(), backgroundPane.getHeight());

			backgroundPane.setLayoutX(-BORDER_WIDTH + INDENT);
			backgroundPane.setLayoutY(-BORDER_WIDTH + INDENT);
			backgroundPane.setPrefWidth(SCREEN_WIDTH + 2 * BORDER_WIDTH - 2 * INDENT);
			backgroundPane.setPrefHeight(SCREEN_HEIGHT - getTaskPaneHeight() + 2 * BORDER_WIDTH - 2 * INDENT);

			maximized = true;
		}
		else {
			backgroundPane.setLayoutX(windowDimension.getMinX());
			backgroundPane.setLayoutY(windowDimension.getMinY());
			backgroundPane.setPrefWidth(windowDimension.getWidth());
			backgroundPane.setPrefHeight(windowDimension.getHeight());

			maximized = false;
		}
	}
}
