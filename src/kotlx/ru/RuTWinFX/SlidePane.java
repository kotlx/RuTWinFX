package kotlx.ru.RuTWinFX;

/*
 * Выдвигающаяся панель, активируется при наведении курсора на определенную область окна.
 * Режимы SPMode TOP, LEFT, BOTTOM, и RIGHT закрепляют панель к соотвествующему краю окна.
 * userContent должен содержать JavaFX граф, который будет выведен на панель.
 * ACTIVATION_WIDTH - ширина области активации панели.
 * USER_CONTENT_WIDTH - заданная пользователем ширина выдвигающейся панели.
 * TIME_OF_SLIDING - время в миллисекундах в течении которого панель полностью выдвигается.
 */

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class SlidePane<E extends Region> extends AnchorPane implements Runnable {

	// Параметры по умолчаию
	private double ACTIVATION_WIDTH = 0;
	private double USER_CONTENT_WIDTH = 0;
	private double TIME_OF_SLIDING = 500;

	private Scene scene;
	private final SladePanePosition spm;
	private final E userContent;
	private final Thread slideThread;
	private final SlideThreadMonitor monitor;

	public SlidePane(SladePanePosition position, E userContent) {
		super();

		//***debug
//		this.setStyle("-fx-background-color: gold;");
		//***

		spm = position;
		if (userContent == null) throw new NullPointerException();
		this.userContent = userContent;

		SlidePane.setAnchor(position, userContent, new Insets(0));
		initMode(spm);

		slideThread = new Thread(this, "SlideThread");
		monitor = new SlideThreadMonitor(slideThread);
		slideThread.start();

		this.setOnMouseEntered(event -> {
//			//***
//			System.out.println("this.getPrefHeight() = " + this.getPrefHeight());
//			System.out.println("this.getHeight() = " + this.getHeight());
//			//***
			monitor.setSlidingOut();
			event.consume();
		});

		this.setOnMouseExited(event -> {
//			//***
//			System.out.println("this.getPrefHeight() = " + this.getPrefHeight());
//			System.out.println("this.getHeight() = " + this.getHeight());
//			//***
			monitor.setSlidingOff();
			event.consume();
		});

		this.getChildren().add(userContent);
	}

	@Override
	public void run() {
		double step = USER_CONTENT_WIDTH / (TIME_OF_SLIDING / 20);
		double opacityStep = 1 / (TIME_OF_SLIDING / 20);
		double currentWidth = 0;
		double currentOpacity = 0;

		while (!(monitor.getState() == SlideThreadState.STOP)) {
			// Получаем ссылку на Scene, если ещё не получена, чтобы отслеживать события окна
			if ((scene == null) && (this.getScene() != null) && (this.getScene().getWindow() != null)) {
				scene = this.getScene();
				System.out.println("Run: get scene");
				// Если окно закрывается, то завершаем процесс
				scene.getWindow().setOnCloseRequest(event -> {
					System.out.println("Run: stop");
					monitor.setThreadStop();
				});
			}

			// Выдвигаем панель
			while (monitor.getState() == SlideThreadState.SLIDING_OUT) {
				if ((spm == SladePanePosition.TOP) | (spm == SladePanePosition.BOTTOM)) {
					if (!(currentWidth + step >= USER_CONTENT_WIDTH)) {
						userContent.setPrefHeight(currentWidth += step);
						userContent.setOpacity(currentOpacity += opacityStep);
						monitor.sleep(20);
					} else {
						userContent.setPrefHeight(currentWidth = USER_CONTENT_WIDTH);
						userContent.setOpacity(currentOpacity = 1);
						monitor.setThreadWait();
					}
				} else {
					if (!(currentWidth + step >= USER_CONTENT_WIDTH)) {
						userContent.setPrefWidth(currentWidth += step);
						userContent.setOpacity(currentOpacity += opacityStep);
						monitor.sleep(20);
					} else {
						userContent.setPrefWidth(currentWidth = USER_CONTENT_WIDTH);
						userContent.setOpacity(currentOpacity = 1);
						monitor.setThreadWait();
					}
				}
			}

			// Задвигаем панель
			while (monitor.getState() == SlideThreadState.SLIDING_OFF) {
				if ((spm == SladePanePosition.TOP) | (spm == SladePanePosition.BOTTOM)) {
					if (!(currentWidth - step <= 0)) {
						userContent.setPrefHeight(currentWidth -= step);
						userContent.setOpacity(currentOpacity -= opacityStep);
						monitor.sleep(20);
					} else {
						userContent.setPrefHeight(currentWidth = 0);
						userContent.setOpacity(currentOpacity = 0);
						monitor.setThreadWait();
					}
				} else {
					if (!(currentWidth - step <= 0)) {
						userContent.setPrefWidth(currentWidth -= step);
						userContent.setOpacity(currentOpacity -= opacityStep);
						monitor.sleep(20);
					} else {
						userContent.setPrefWidth(currentWidth = 0);
						userContent.setOpacity(currentOpacity = 0);
						monitor.setThreadWait();
					}
				}
			}

			// Ждем если ни чего не происходит
			if (monitor.getState() == SlideThreadState.WAIT) {
				monitor.sleep();
			}
		}
		System.out.println("STOP");
	}

	private void initMode(SladePanePosition mode) {
		switch (mode) {
			case TOP:
				// Если пользователь явно не задал ACTIVATION_WIDTH то берем его из userContent
				if (ACTIVATION_WIDTH == 0) ACTIVATION_WIDTH = userContent.getPrefHeight();
				this.setPrefHeight(ACTIVATION_WIDTH);
				// Передаем размеры userContent в slidePane
				setContentWidth(userContent.getPrefHeight());
				this.setPrefWidth(userContent.getPrefWidth());
				this.setMinWidth(userContent.getMinWidth());
				// Началное значение  - панель закрыта
				userContent.setPrefHeight(0);
				break;
			case RIGHT:
				if (ACTIVATION_WIDTH == 0) ACTIVATION_WIDTH = userContent.getPrefWidth();
				this.setPrefWidth(ACTIVATION_WIDTH);
				setContentWidth(userContent.getPrefWidth());
				this.setPrefHeight(userContent.getPrefHeight());
				this.setMinHeight(userContent.getMinHeight());
				userContent.setPrefWidth(0);
				break;
			case BOTTOM:
				if (ACTIVATION_WIDTH == 0) ACTIVATION_WIDTH = userContent.getPrefHeight();
				this.setPrefHeight(ACTIVATION_WIDTH);
				setContentWidth(userContent.getPrefHeight());
				this.setPrefWidth(userContent.getPrefWidth());
				this.setMinWidth(userContent.getMinWidth());
				userContent.setPrefHeight(0);
				break;
			case LEFT:
				if (ACTIVATION_WIDTH == 0) ACTIVATION_WIDTH = userContent.getPrefWidth();
				this.setPrefWidth(ACTIVATION_WIDTH);
				setContentWidth(userContent.getPrefWidth());
				this.setPrefHeight(userContent.getPrefHeight());
				this.setMinHeight(userContent.getMinHeight());
				userContent.setPrefWidth(0);
				break;
		}
		userContent.setOpacity(0);
	}

	public double getContentWith() {
		return USER_CONTENT_WIDTH;
	}

	public void setContentWidth(double width) {
		if (width <= 0)
			System.err.println("Warning!: " + this + "  width value is too small. USER_CONTENT_WIDTH = " + width);
		USER_CONTENT_WIDTH = width;
	}

	public double getActivationWidth() {
		return ACTIVATION_WIDTH;
	}

	public void setActivationWidth(double activationWidth) {
		if ((spm == SladePanePosition.TOP) || ( spm == SladePanePosition.BOTTOM))
			this.setPrefHeight(this.ACTIVATION_WIDTH = activationWidth);
		if ((spm == SladePanePosition.RIGHT) || ( spm == SladePanePosition.LEFT))
			this.setPrefWidth(this.ACTIVATION_WIDTH = activationWidth);
	}

	public double getSlidingTime() {
		return TIME_OF_SLIDING;
	}

	public void setSlidingTime(double milliseconds) {
		TIME_OF_SLIDING = milliseconds;
	}

	public static <T extends Region> void setAnchor(SladePanePosition position, T node, Insets insets) {
		switch (position) {
			case TOP:
				SlidePane.setTop(node, insets);
				break;
			case RIGHT:
				SlidePane.setRight(node, insets);
				break;
			case BOTTOM:
				SlidePane.setBottom(node, insets);
				break;
			case LEFT:
				SlidePane.setLeft(node, insets);
				break;
		}
	}

	public static <T extends Region> void setTop(T node, Insets insets) {
		AnchorPane.setTopAnchor(node, insets.getTop());
		AnchorPane.setRightAnchor(node, insets.getRight());
		AnchorPane.setLeftAnchor(node, insets.getLeft());
	}

	public static <T extends Region> void setRight(T node, Insets insets) {
		AnchorPane.setRightAnchor(node, insets.getRight());
		AnchorPane.setBottomAnchor(node, insets.getBottom());
		AnchorPane.setTopAnchor(node, insets.getTop());
	}

	public static <T extends Region> void setBottom(T node, Insets insets) {
		AnchorPane.setBottomAnchor(node, insets.getBottom());
		AnchorPane.setLeftAnchor(node, insets.getLeft());
		AnchorPane.setRightAnchor(node, insets.getRight());
	}

	public static <T extends Region> void setLeft(T node, Insets insets) {
		AnchorPane.setLeftAnchor(node, insets.getLeft());
		AnchorPane.setTopAnchor(node, insets.getTop());
		AnchorPane.setBottomAnchor(node, insets.getBottom());
	}

	public void setStop() {
		monitor.setThreadStop();
	}

	public void setSlideOff() {
		monitor.setSlidingOff();
	}
}