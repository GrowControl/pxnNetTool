package com.poixson.nettet.tool;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import com.poixson.app.xApp;
import com.poixson.app.steps.xAppStep;
import com.poixson.app.steps.xAppStep.StepType;
import com.poixson.nettet.tool.gui.tool.ToolWindow;
import com.poixson.utils.ThreadUtils;


public class ToolApp extends xApp {

	private final CopyOnWriteArraySet<ToolWindow> tools =
			new CopyOnWriteArraySet<ToolWindow>();



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
		new ToolWindow();
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



	public boolean register(final ToolWindow tool) {
		return this.tools
				.add(tool);
	}
	public boolean unregister(final ToolWindow tool) {
		return this.tools
				.remove(tool);
	}



}
