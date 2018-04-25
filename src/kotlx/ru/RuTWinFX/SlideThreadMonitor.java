package kotlx.ru.RuTWinFX;

/*
 * Управлятор потоком SlidePane
 */


public class SlideThreadMonitor {
	private Thread thread;
	private SlideThreadState state;

	SlideThreadMonitor(Thread thread) {
		this.thread = thread;
		setThreadWait();
	}

	public SlideThreadState getState() {
		return state;
	}

	public void setThreadWait() {
		synchronized (thread) {
			state = SlideThreadState.WAIT;
		}
	}

	public void setSlidingOut() {
		synchronized (thread) {
			state = SlideThreadState.SLIDING_OUT;
			thread.notify();
		}
	}

	public void  setSlidingOff() {
		synchronized (thread) {
			state = SlideThreadState.SLIDING_OFF;
			thread.notify();
		}
	}

	public void setThreadStop() {
		synchronized (thread) {
			state = SlideThreadState.STOP;
			thread.notify();
		}
	}

	public void wakeup() {
		synchronized (thread) {
			thread.notify();
		}
	}

	public void sleep() {
		synchronized (thread) {
			try {
				thread.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void sleep(long milliseconds) {
		synchronized (thread) {
			try {
				thread.wait(milliseconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
