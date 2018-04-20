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
	private volatile ProcessState ps = ProcessState.WAIT;
	private final SPMode spm;
	private final E userContent;
	private final Thread slideProcess;

	public SlidePane(SPMode position, E userContent) {
		super();

		spm = position;
		if (userContent == null) throw new NullPointerException();
		this.userContent = userContent;

		SlidePane.setAnchor(position, userContent, new Insets(0));
		initMode(spm);

		slideProcess = new Thread(this, "SlideProcess");
		slideProcess.start();

		this.setOnMouseEntered(event -> {
			ps = ProcessState.SLIDING_OUT;
			synchronized (slideProcess) {
				slideProcess.notify();
			}
			event.consume();
		});

		this.setOnMouseExited(event -> {
			ps = ProcessState.SLIDING_OFF;
			synchronized (slideProcess) {
				slideProcess.notify();
			}
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

		while (!(ps == ProcessState.STOP)) {
			// Получаем ссылку на Scene, если ещё не получена, чтобы отслеживать события окна
			if ((scene == null) && (this.getScene() != null) && (this.getScene().getWindow() != null)) {
				scene = this.getScene();
				// Если окно закрывается, то завершаем процесс
				scene.getWindow().setOnCloseRequest(event -> {
					ps = ProcessState.STOP;
					synchronized (slideProcess) {
						slideProcess.notify();
					}
				});
			}

			// Выдвигаем панель
			while (ps == ProcessState.SLIDING_OUT) {
				if ((spm == SPMode.TOP) | (spm == SPMode.BOTTOM)) {
					if (!(currentWidth + step >= USER_CONTENT_WIDTH)) {
						userContent.setPrefHeight(currentWidth += step);
						userContent.setOpacity(currentOpacity += opacityStep);
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						userContent.setPrefHeight(currentWidth = USER_CONTENT_WIDTH);
						userContent.setOpacity(currentOpacity = 1);
						ps = ProcessState.WAIT;
					}
				} else {
					if (!(currentWidth + step >= USER_CONTENT_WIDTH)) {
						userContent.setPrefWidth(currentWidth += step);
						userContent.setOpacity(currentOpacity += opacityStep);
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						userContent.setPrefWidth(currentWidth = USER_CONTENT_WIDTH);
						userContent.setOpacity(currentOpacity = 1);
						ps = ProcessState.WAIT;
					}
				}
			}

			// Задвигаем панель
			while (ps == ProcessState.SLIDING_OFF) {
				if ((spm == SPMode.TOP) | (spm == SPMode.BOTTOM)) {
					if (!(currentWidth - step <= 0)) {
						userContent.setPrefHeight(currentWidth -= step);
						userContent.setOpacity(currentOpacity -= opacityStep);
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						userContent.setPrefHeight(currentWidth = 0);
						userContent.setOpacity(currentOpacity = 0);
						ps = ProcessState.WAIT;
					}
				} else {
					if (!(currentWidth - step <= 0)) {
						userContent.setPrefWidth(currentWidth -= step);
						userContent.setOpacity(currentOpacity -= opacityStep);
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						userContent.setPrefWidth(currentWidth = 0);
						userContent.setOpacity(currentOpacity = 0);
						ps = ProcessState.WAIT;
					}
				}
			}

			// Ждем если ни чего не происходит
			if (ps == ProcessState.WAIT) {
				synchronized (slideProcess) {
					try {
						slideProcess.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private enum ProcessState {SLIDING_OUT, SLIDING_OFF, WAIT, STOP}

//	public void setSlidingOut() {
//		ps = ProcessState.SLIDING_OUT;
//	}
//
//	public void  setSlidingOff() {
//		ps = ProcessState.SLIDING_OFF;
//	}

	private void initMode(SPMode mode) {
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
		if ((spm == SPMode.TOP) || ( spm == SPMode.BOTTOM))
			this.setPrefHeight(this.ACTIVATION_WIDTH = activationWidth);
		if ((spm == SPMode.RIGHT) || ( spm == SPMode.LEFT))
			this.setPrefWidth(this.ACTIVATION_WIDTH = activationWidth);
	}

	public double getSlidingTime() {
		return TIME_OF_SLIDING;
	}

	public void setSlidingTime(double milliseconds) {
		TIME_OF_SLIDING = milliseconds;
	}

	public static <T extends Region> void setAnchor(SPMode position, T node, Insets insets) {
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

}