package com.poixson.nettool;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.xApp;
import com.poixson.app.steps.xAppStep;
import com.poixson.app.steps.xAppStep.StepType;
import com.poixson.nettet.tool.gui.about.AboutWindow;
import com.poixson.nettet.tool.gui.tool.ToolWindow;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public class ToolApp extends xApp {

	private final CopyOnWriteArraySet<ToolWindow> tools =
			new CopyOnWriteArraySet<ToolWindow>();

	private final AtomicReference<AboutWindow> aboutWindow =
			new AtomicReference<AboutWindow>(null);



	/**
	 * Get the server class instance.
	 * @return gcServer instance object.
	 */
	public static ToolApp get() {
		return (ToolApp) xApp.get();
	}



	public ToolApp() {
		super();
	}



	@SuppressWarnings("resource")
	@xAppStep(type=StepType.STARTUP, title="ShowWindow", priority=1000)
	public void __STARTUP_show() {
		final ToolWindow tool = new ToolWindow();
		tool.display();
	}



	@xAppStep(type=StepType.SHUTDOWN, title="CloseWindows", priority=1000)
	public void __SHUTDOWN_close() {
		this.closeAllWindows();
	}
	public void closeAllWindows() {
		for (int i=0; i<3; i++) {
			final Iterator<ToolWindow> it = this.tools.iterator();
			while (it.hasNext()) {
				final ToolWindow tool = it.next();
				tool.close();
				it.remove();
			}
			if (this.tools.isEmpty())
				break;
			ThreadUtils.Sleep(50L);
		}
	}



	@xAppStep(type=StepType.SHUTDOWN, title="exit", priority=2)
	public void __SHUTDOWN_exit() {
		final Thread stopThread =
			new Thread() {
				@Override
				public void run() {
					ThreadUtils.Sleep(250L);
					System.exit(0);
				}
			};
		stopThread.start();
	}



	public boolean register(final ToolWindow tool) {
		return this.tools
				.add(tool);
	}
	public boolean unregister(final ToolWindow tool) {
		final boolean result =
			this.tools
				.remove(tool);
		if (this.tools.isEmpty()) {
			this.stop();
		}
		return result;
	}



	// about window
	public void showAbout() {
		// existing window
		{
			final AboutWindow about = this.aboutWindow.get();
			if (about != null) {
				about.setVisible(true);
				about.requestFocus();
				return;
			}
		}
		// new window
		{
			AboutWindow about = new AboutWindow();
			if (!this.aboutWindow.compareAndSet(null, about)) {
				about = this.aboutWindow.get();
			}
			if (about == null) throw new NullPointerException("Failed to get about window");
			about.setVisible(true);
			about.requestFocus();
		}
	}
	public void closeAbout() {
		final AboutWindow about = this.aboutWindow.getAndSet(null);
		if (about == null)
			return;
		Utils.safeClose(about);
	}



}
