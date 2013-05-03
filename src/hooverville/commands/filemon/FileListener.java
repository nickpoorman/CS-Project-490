package hooverville.commands.filemon;

import java.io.File;
import java.io.IOException;

public abstract class FileListener implements Runnable {

	private File f;
	private long lastModified;
	private File rootDirectory;
	private String packageName;

//	public FileListener(File f) {
//		this.f = f;
//		lastModified = f.lastModified();
//	}

	//root directory, package name, file name
	public FileListener(File f, String packageName, String fileName) {
		rootDirectory = f;
		String filePath = null;
		try {
			filePath = f.getCanonicalPath() + File.separator + packageName + File.separator + fileName;			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.f = new File(filePath);
		//this.f = f;
		lastModified = this.f.lastModified();
	}

	public void run() {
		if (f.exists()) {
			try {
				for (;;) {
					long newLastModified = f.lastModified();
					if (newLastModified != lastModified) {
						lastModified = newLastModified;
						fileChanged();
					}
					Thread.sleep(1000);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void fileChanged();

	public File getFile() {
		return f;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @param rootDirectory
	 *            the rootDirectory to set
	 */
	public void setRootDirectory(File rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * @return the rootDirectory
	 */
	public File getRootDirectory() {
		return rootDirectory;
	}

}
