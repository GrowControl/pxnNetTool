package com.poixson.nettet.tool;

import com.poixson.nettet.NettetLibraryLoader;
import com.poixson.serial.SerialLibraryLoader;
import com.poixson.utils.Keeper;


public class Main {

	// keep things in memory
	@SuppressWarnings("unused")
	private static final Keeper keeper = Keeper.get();

	private static ToolApp app = null;



	public static void main(final String[] argsArray) {
//TODO:
//		// process shell arguments
//		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);
		// load libraries
		{
			// load unix socket library
			NettetLibraryLoader.get()
				.loadUnixSocketLibrary();
			// load serial library
			final SerialLibraryLoader loader =
				SerialLibraryLoader.get();
			loader.loadSerialLibrary();
			// load d2xx open library
			loader.loadD2xxOpenLibrary();
			// load d2xx prop library
			loader.loadD2xxPropLibrary();
		}
		app = new ToolApp();
		app.start();
	}



}
