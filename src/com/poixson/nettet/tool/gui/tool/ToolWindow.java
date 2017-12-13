package com.poixson.nettet.tool.gui;

import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.SwingUtilities;

import com.poixson.app.gui.xWindow;
import com.poixson.nettet.tool.ToolApp;
import com.poixson.utils.xLogger.xLog;


public class ToolWindow extends xWindow {
	private static final long serialVersionUID = 1L;



	public static ToolWindow getNew() {
		if (EventQueue.isDispatchThread()) {
			return new ToolWindow();
		}
		final FutureTask<ToolWindow> future =
			new FutureTask<ToolWindow> (new Callable<ToolWindow>() {
				@Override
				public ToolWindow call() throws Exception {
					return null;
				}
			}
		);
		SwingUtilities.invokeLater(future);
		try {
			return future.get();
		} catch (InterruptedException e) {
			xLog.getRoot()
				.trace(e);
		} catch (ExecutionException e) {
			xLog.getRoot()
				.trace(e);
		}
		return null;
	}
	public ToolWindow() {
		super();
		// window title
		final String title = null;
		this.setTitle(title);
		// layout manager
		final LayoutManager layout = null;
		this.setLayout(layout);
		ToolApp.get()
			.register(this);
	}



}
