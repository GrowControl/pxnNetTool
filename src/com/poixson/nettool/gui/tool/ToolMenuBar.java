package com.poixson.nettet.tool.gui.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.poixson.nettet.EasyPipeline;
import com.poixson.nettet.EasyPipeline.PipelineType;
import com.poixson.nettet.tool.ToolApp;
import com.poixson.utils.Utils;


public class ToolMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	protected final ToolWindow window;

	// file menu
	protected final JMenu fileMenu;
	protected final FileMenuListener fileListener;
	protected final JMenuItem menuFileConnect;
	protected final JMenuItem menuFileClose;
	protected final JMenuItem menuFileExit;

	// transport menu
	protected final JMenu transMenu;
	protected final TransportMenuListener transListener;
	protected final JRadioButtonMenuItem menuTransTcpClient;
	protected final JRadioButtonMenuItem menuTransUdpClient;
	protected final JRadioButtonMenuItem menuTransUnixClient;
	protected final JRadioButtonMenuItem menuTransTcpServer;
	protected final JRadioButtonMenuItem menuTransUdpServer;
	protected final JRadioButtonMenuItem menuTransUnixServer;
	protected final JRadioButtonMenuItem menuTransSerial;

	// protocol menu
	protected final JMenu protocolMenu;
	protected final ProtocolMenuListener protocolListener;
	protected final JRadioButtonMenuItem menuProtocolRaw;
	protected final JRadioButtonMenuItem menuProtocolHttp;
	protected final JRadioButtonMenuItem menuProtocolHttps;
	protected final JRadioButtonMenuItem menuProtocolJson;
	protected final JRadioButtonMenuItem menuProtocolMqtt;

	// help menu
	protected final JMenu helpMenu;
	protected final HelpMenuListener helpListener;
	protected final JMenuItem menuHelpAbout;



	public ToolMenuBar(final ToolWindow window) {
		super();
		this.window = window;
		// file menu
		this.fileMenu = new JMenu("File");
		this.fileMenu.setMnemonic(KeyEvent.VK_F);
		this.fileListener = new FileMenuListener();
		{
			// connect
			this.menuFileConnect =
				addMenuItem( this.fileMenu, this.fileListener, "Connect..", KeyEvent.VK_C );
			this.menuFileConnect.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK)
			);
			// close
			this.menuFileClose =
				addMenuItem( this.fileMenu, this.fileListener, "Close",   KeyEvent.VK_C );
			this.menuFileClose.setVisible(false);
			this.menuFileClose.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK)
			);
			// ---
			this.fileMenu.addSeparator();
			// exit
			this.menuFileExit =
				addMenuItem( this.fileMenu, this.fileListener, "Exit", KeyEvent.VK_X );
			this.menuFileExit.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK)
			);
		}
		this.add(this.fileMenu);
		// transport menu
		this.transMenu = new JMenu("Transport");
		this.transMenu.setMnemonic(KeyEvent.VK_T);
		this.transListener = new TransportMenuListener();
		{
			final ButtonGroup group = new ButtonGroup();
			// tcp client
			this.menuTransTcpClient =
				addMenuRadio( this.transMenu, group, this.transListener, "TCP Client", KeyEvent.VK_T );
			// udp client
			this.menuTransUdpClient =
				addMenuRadio( this.transMenu, group, this.transListener, "UDP Client", KeyEvent.VK_D );
			// unix socket client
			this.menuTransUnixClient =
				addMenuRadio( this.transMenu, group, this.transListener, "Unix Socket Client", KeyEvent.VK_U );
			// ---
			this.transMenu.addSeparator();
			// tcp server
			this.menuTransTcpServer =
				addMenuRadio( this.transMenu, group, this.transListener, "TCP Server", KeyEvent.VK_C );
			// udp server
			this.menuTransUdpServer =
				addMenuRadio( this.transMenu, group, this.transListener, "UDP Server", KeyEvent.VK_P );
			// unix socket server
			this.menuTransUnixServer =
				addMenuRadio( this.transMenu, group, this.transListener, "Unix Socket Server", KeyEvent.VK_X );
			// ---
			this.transMenu.addSeparator();
			// serial
			this.menuTransSerial =
				addMenuRadio( this.transMenu, group, this.transListener, "Serial", KeyEvent.VK_S );
		}
		this.add(this.transMenu);
		// protocol menu
		this.protocolMenu = new JMenu("Protocol");
		this.protocolMenu.setMnemonic(KeyEvent.VK_P);
		this.protocolListener = new ProtocolMenuListener();
		{
			final ButtonGroup group = new ButtonGroup();
			// raw
			this.menuProtocolRaw =
				addMenuRadio( this.protocolMenu, group, this.protocolListener, "RAW", KeyEvent.VK_R );
			// http
			this.menuProtocolHttp =
				addMenuRadio( this.protocolMenu, group, this.protocolListener, "HTTP", KeyEvent.VK_H );
			// https
			this.menuProtocolHttps =
				addMenuRadio( this.protocolMenu, group, this.protocolListener, "HTTPS", KeyEvent.VK_S );
			// json
			this.menuProtocolJson =
				addMenuRadio( this.protocolMenu, group, this.protocolListener, "JSON", KeyEvent.VK_J );
			// mqtt
			this.menuProtocolMqtt =
				addMenuRadio( this.protocolMenu, group, this.protocolListener, "MQTT", KeyEvent.VK_Q );
		}
		this.add(this.protocolMenu);
		// help menu
		this.helpMenu = new JMenu("Help");
		this.helpMenu.setMnemonic(KeyEvent.VK_H);
		this.helpListener = new HelpMenuListener();
		{
			// about
			this.menuHelpAbout =
				addMenuItem( this.helpMenu, this.helpListener, "About..", KeyEvent.VK_A );
		}
		this.add(this.helpMenu);
	}



	protected static JMenuItem addMenuItem(
			final JMenu menu, final ActionListener listener,
			final String text, final int mnemonic) {
		final JMenuItem item = new JMenuItem(text);
		item.setMnemonic(mnemonic);
		item.addActionListener(listener);
		menu.add(item);
		return item;
	}
	protected static JCheckBoxMenuItem addMenuCheckbox(
			final JMenu menu, final ActionListener listener,
			final String text, final int mnemonic) {
			final JCheckBoxMenuItem item = new JCheckBoxMenuItem(text);
			item.setMnemonic(mnemonic);
			item.addActionListener(listener);
			menu.add(item);
			return item;
		}
	protected static JRadioButtonMenuItem addMenuRadio(
			final JMenu menu, final ButtonGroup group, final ActionListener listener,
			final String text, final int mnemonic) {
			final JRadioButtonMenuItem item = new JRadioButtonMenuItem(text);
			item.setMnemonic(mnemonic);
			item.addActionListener(listener);
			group.add(item);
			menu.add(item);
			return item;
		}



	// file menu listener
	protected class FileMenuListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent event) {
			final String text = event.getActionCommand();
			switch (text) {
			case "Connect..":
				
				break;
			case "Close":
				
				break;
			case "Exit":
				Utils.safeClose(ToolMenuBar.this.window);
				break;
			default:
				throw new RuntimeException("Unknown menu item: "+text);
			}
		}
	}



	// transport menu listener
	protected class TransportMenuListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent event) {
			final String text = event.getActionCommand();
			switch (text) {
			case "TCP Client":
				
				break;
			case "UDP Client":
				
				break;
			case "Unix Socket Client":
				
				break;
			case "TCP Server":
				
				break;
			case "UDP Server":
				
				break;
			case "Unix Socket Server":
				
				break;
			case "Serial":
				
				break;
			default:
				throw new RuntimeException("Unknown menu item: "+text);
			}
		}
	}



	// protocol menu listener
	protected class ProtocolMenuListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent event) {
			final String text = event.getActionCommand();
			switch (text) {
			case "RAW":
				ToolMenuBar.this.window
					.setPipeline(
						new EasyPipeline(PipelineType.RAW)
					);
				break;
			case "HTTP":
				ToolMenuBar.this.window
					.setPipeline(
						new EasyPipeline(PipelineType.HTTP)
					);
				break;
			case "HTTPS":
				ToolMenuBar.this.window
					.setPipeline(
						new EasyPipeline(PipelineType.HTTPS)
					);
				break;
			case "JSON":
				ToolMenuBar.this.window
					.setPipeline(
						new EasyPipeline(PipelineType.JSON)
					);
				break;
			case "MQTT":
				ToolMenuBar.this.window
					.setPipeline(
						new EasyPipeline(PipelineType.MQTT)
					);
				break;
			default:
				throw new RuntimeException("Unknown menu item: "+text);
			}
		}
	}



	// help menu listener
	protected class HelpMenuListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent event) {
			final String text = event.getActionCommand();
			switch (text) {
			case "About..":
				final ToolApp app = ToolApp.get();
				app.showAbout();
				break;
			default:
				throw new RuntimeException("Unknown menu item: "+text);
			}
		}
	}



}
