package utilclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

public class FileManager {
	private final File sdDir = Environment.getExternalStorageDirectory();//sd卡根路径
	private final File cacheDir = Environment.getDownloadCacheDirectory();//缓存目录
	private final File dataDir =Environment.getDataDirectory();//data文件夹目录
	private final File rootDir = Environment.getRootDirectory();//Android根目录
	
	private static FileManager mFileManager=null;
	private FileManager(){
	}
	
	public static FileManager getInstance(){
		if(mFileManager==null)
			mFileManager = new FileManager();
		return mFileManager;
	}

	/*public void saveFileInside(String path,String name){//内部文件存储
		File saveFile = new File(path,name);
		FileOutputStream mFileOutputStream  = null;
		try {
			mFileOutputStream = new FileOutputStream(saveFile);
			mFileOutputStream.flush();
			mFileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			saveFile.delete();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			saveFile.delete();
			e.printStackTrace();
		}
	}*/
	
	
	/*public File readFileInside(String path,String name){
		File dir = new File(path);
		if(dir.exists()){
			File files[] = dir.listFiles();
			for(File file : files){
				if(name.equals(file.getName()))return file;
			}
		}
		return null;
	}*/
	
	/*public void saveFileOutside(File saveFile,File saveDirectory,String savePath){
		if(saveDirectory==sdDir&&Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			saveFile
		}
	}*/
	
	/**
	 * @return the sdDir
	 */
	public File getSdDir() {
		return sdDir;
	}

	/**
	 * @return the cacheDir
	 */
	public File getCacheDir() {
		return cacheDir;
	}

	/**
	 * @return the dataDir
	 */
	public File getDataDir() {
		return dataDir;
	}

	/**
	 * @return the rootDir
	 */
	public File getRootDir() {
		return rootDir;
	}
	
}
