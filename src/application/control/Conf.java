package application.control;

import android.os.Build.VERSION;
import utilclass.FileManager;

public class Conf {

	public static final String BMOB_APP_ID = "0ba444c640ef9e526edb10377332c7c8";
	public static final String APP_SDCARD_PATH= FileManager.getInstance().getSdDir().getAbsolutePath()+"/MiYou";
	public static final String APP_SDCARD_CACHE_PATH= FileManager.getInstance().getSdDir().getAbsolutePath()+"/MiYou/Cache";
	
	//appsd卡album目录名字
	public static final String SD_Album_Dir_Name = "Miyou";
	
	//sdk版本
	public static final int APILevel = Integer.parseInt(VERSION.SDK);
	
	
	//图片处理的一些参数
	public final static int TAKE_PICTURE = 0x100;
	public final static int CHOOSE_PICTURE = 0x101;
	public final static int CROP = 0x102;
	public final static int CROP_PICTURE = 0x103;
	
	//handler 处理的一些参数
	public final static int HANDLER_CREATE_TIEZI_CB = 0x201;
	
}
