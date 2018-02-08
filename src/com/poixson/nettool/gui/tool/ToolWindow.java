package com.poixson.nettet.tool.gui.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.poixson.app.gui.xWindow;
import com.poixson.nettet.tool.ToolApp;
import com.poixson.utils.xLogger.xLog;

import net.miginfocom.swing.MigLayout;


public class ToolWindow extends xWindow {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SIZE_X = 750;
	public static final int DEFAULT_SIZE_Y = 600;

	protected final ToolMenuBar menubar;
	protected final JButton sendButton;



	public static ToolWindow getNew() {
		{
			final ToolApp app = ToolApp.get();
			if (app.isStopping() || app.isStopped()) {
				throw new IllegalStateException("Cannot open a new window when app is stopping");
			}
		}
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
		ToolApp.get()
			.register(this);
		// build the window
		this.setSize(DEFAULT_SIZE_X, DEFAULT_SIZE_Y);
		this.setResizable(true);
		this.setTitle("<closed> "+ToolApp.get().getFullTitle());
		// layout manager
		final MigLayout mainLayout = new MigLayout("inset 3, wrap 1", "[fill, grow]");
		this.setLayout(mainLayout);
		// menu bar
		this.menubar = new ToolMenuBar(this);
		this.setJMenuBar(this.menubar);
		// received box
		{
			final JPanel receivedPanel = new JPanel();
			final BorderLayout layout = new BorderLayout();
			receivedPanel.setLayout(layout);
			final JTextArea receivedTextbox = new JTextArea();
			receivedTextbox.setEnabled(false);
			receivedTextbox.setBorder(new LineBorder(Color.GRAY, 1, false));
			receivedTextbox.setEditable(false);
			receivedPanel.add(receivedTextbox, BorderLayout.CENTER);
			this.add(receivedPanel, "h 50%, span, wrap");
		}
		// send box
		{
			final JPanel sendPanel = new JPanel();
			final BorderLayout layout = new BorderLayout();
			layout.setHgap(2);
			sendPanel.setLayout(layout);
			final JTextArea sendTextbox = new JTextArea();
			sendTextbox.setEnabled(false);
			sendTextbox.setBorder(new LineBorder(Color.GRAY, 1, false));
			sendPanel.add(sendTextbox, BorderLayout.CENTER);
			// send button
			this.sendButton = new JButton();
			final TextIcon textIcon =
				new TextIcon(
					this.sendButton,
					"Send",
					TextIcon.Layout.HORIZONTAL
				);
			final RotatedIcon rotatedIcon =
				new RotatedIcon(
					textIcon,
					RotatedIcon.Rotate.DOWN
				);
			this.sendButton.setIcon(rotatedIcon);
			sendPanel.add(this.sendButton, BorderLayout.EAST);
			this.add(sendPanel, "h 50%, span, wrap");
		}
		// status bar
		{
			final JPanel statusPanel = new JPanel();
			final MigLayout statusLayout = new MigLayout("", "[push][]");
			statusPanel.setLayout(statusLayout);
			statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			final JLabel statusLabel = new JLabel("<Closed>");
			statusPanel.add(statusLabel, "");
			this.add(statusPanel, "span");
		}
	}



	@Override
	public void close() {
		super.close();
		ToolApp.get()
			.unregister(this);
	}



}
