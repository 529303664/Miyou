package utilclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileManager {
	private String File_LOG_TAG = "MIYOU_FILE";
	private final File sdDir = Environment.getExternalStorageDirectory();//sd卡根路径
	private final File cacheDir = Environment.getDownloadCacheDirectory();//缓存目录
	private final File dataDir =Environment.getDataDirectory();//data文件夹目录
	private final File rootDir = Environment.getRootDirectory();//Android根目录
	private  File sdAppDir;//sd卡App根目录
	private  File sdAppCacheDir; // sd卡AppCache根目录
	private Context Applicationcontext;
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
	 * @return the context
	 */
	public Context getContext() {
		return Applicationcontext;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.Applicationcontext = context;
	}

	/**
	 * @return the sdAppDir
	 */
	public String getSdAppDirPath() {
		sdAppDir = Applicationcontext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return sdAppDir.getAbsolutePath();
	}

	/**
	 * @return the sdAppCacheDir
	 */
	public String getSdAppCacheDirPath() {
		sdAppCacheDir = Applicationcontext.getExternalCacheDir();
		return sdAppCacheDir.getAbsolutePath();
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
	
	/**
	 * 得到本app用户公共的照片目录
	 * @param albumName 目录名
	 * @return 返回指定的目录名对象
	 */
	public File getAlbumStorageDir(String albumName){
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),albumName);
		if(!file.mkdirs()){
			Log.e(File_LOG_TAG, "Directory is not created");
		}
		return file;
	}
	
	/**
	 * 得到本app用户私有的照片目录
	 * @param context 上下文
	 * @param albumName 指定的目录名
	 * @return 返回指定的目录名对象
	 */
	public File getAlbumStorageDir(Context context, String albumName){
		File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),albumName);
		if(!file.mkdirs()){
			Log.e(File_LOG_TAG, "Directory is not created");
		}
		return file;
	}
	
	/**
	 * 得到本app的sd卡存储缓存目录
	 * @param context
	 * @param albumName
	 * @return
	 */
	public File getAlbumStorageCacheDir(Context context, String albumName){
		File file  = new File(context.getExternalCacheDir(),albumName);
		if(!file.mkdirs()){
			Log.e(File_LOG_TAG, "Directory is not created");
		}
		return file;
	}
	
	/**
	 * 删除非内部存储的文件
	 * @param path 目录
	 * @param name 文件名
	 * @return
	 */
	public boolean deleteFile(String path,String name){
		File deleteFile = new File(path,name);
		return deleteFile.delete();
	}

	
	/**
	 * 检查sd卡是否可使用
	 * @return
	 */
	public  boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	
	public  void deleteDirAndFiles(String path) {
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}
	
}
