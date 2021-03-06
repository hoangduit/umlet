package com.baselet.control.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.baselet.control.enums.Program;

public class Path {

	private static String tempDir;
	private static String homeProgramDir;

	public static String config() {
		return userHome() + File.separator + Program.getInstance().getConfigName();
	}

	private static String userHome() {
		String homeDir = userHomeBase();
		if (!homeDir.endsWith(File.separator)) {
			homeDir += File.separator;
		}
		File homeDirFile = new File(homeDir + Program.getInstance().getProgramName());
		if (!homeDirFile.exists()) {
			Utils.safeMkDir(homeDirFile, true);
		}
		return homeDirFile.getAbsolutePath();
	}

	private static String userHomeBase() {
		try {
			String xdgConfigHome = System.getenv("XDG_CONFIG_HOME"); // use env variable $XDG_CONFIG_HOME if it's set
			if (xdgConfigHome != null) {
				return xdgConfigHome;
			}
		} catch (Exception e) { /* if env variable cannot be read, ignore it */}

		return System.getProperty("user.home");
	}

	public static String customElements() {
		return homeProgram() + "custom_elements/";
	}

	public static String temp() {
		if (tempDir == null) {
			String tmp = System.getProperty("java.io.tmpdir");
			if (!tmp.endsWith(File.separator)) {
				tmp = tmp + File.separator;
			}
			tempDir = tmp;
		}
		return tempDir;
	}

	/**
	 * STANDALONE NOJAR: <programpath>
	 * STANDALONE JAR: <programpath>
	 * ECLIPSE NOJAR: <programpath>
	 * ECLIPSE JAR: <eclipsepath>/<configuration>/<dirToStoreCustomStuff>
	 */
	public static String homeProgram() {
		return homeProgramDir;
	}

	public static void setHomeProgram(String homeProgramDir) {
		Path.homeProgramDir = homeProgramDir;
	}

	/**
	 * STANDALONE NOJAR: <programpath>/bin/
	 * STANDALONE JAR: <programpath>/<progname>.jar
	 * ECLIPSE NOJAR: <programpath>
	 * ECLIPSE JAR: <eclipsepath>/<pluginname>.jar
	 */
	public static String executable() {
		String path = null;
		URL codeSourceUrl = Path.class.getProtectionDomain().getCodeSource().getLocation();
		try { // Convert URL to URI to avoid HTML problems with special characters like space,ä,ö,ü,...
			path = codeSourceUrl.toURI().getPath();
		} catch (URISyntaxException e) {/* path stays null */}

		if (path == null) { // URI2URL Conversion failed, because URI.getPath() returned null OR because of an URISyntaxException
			// In this case use the URL and replace special characters manually (for now only space)
			path = codeSourceUrl.getPath().replace("%20", " ");
		}

		return path;
	}

	public static Manifest manifest() throws IOException {
		Manifest manifest;
		if (Path.executable().endsWith(".jar")) {
			JarFile jarFile = null;
			try {
				jarFile = new JarFile(Path.executable());
				manifest = jarFile.getManifest();
			} finally {
				if (jarFile != null) {
					jarFile.close();
				}
			}
		}
		else {
			FileInputStream is = new FileInputStream(Path.homeProgram() + "META-INF" + File.separator + "MANIFEST.MF");
			manifest = new Manifest(is);
			is.close();
		}
		return manifest;
	}

}
