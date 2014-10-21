package application.control;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import utilclass.FileManager;
import cn.bmob.v3.Bmob;
import database.DatabaseHelper;
import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class ManagerApplication extends Application {

	private DatabaseHelper databaseHelper;
	private List<Activity>myActivityList = new LinkedList<Activity>();
	private static ManagerApplication instance;
	
	public synchronized static ManagerApplication getInstance() {
		if(null == instance){
			instance = new ManagerApplication();
		}
		return instance;
	}
	
	public void addActivity(Activity activity){
		myActivityList.add(activity);
	}
	
	public void killAllActivity()
	{
		try {
			for(Activity activity : myActivityList)
			{
				if(activity!=null)
					activity.finish();
			}
		}catch(Exception e){
			Log.e("killAllActivity", e.toString());
		}
	}
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Bmob.initialize(this, Conf.BMOB_APP_ID);
		/*this.databaseHelper = new DatabaseHelper(this.getApplicationContext(), "miyou.db", null, 1);
		*/
		//创建缓存目录，系统一运行就得创建缓存目录
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File cache = new File(Conf.APP_SDCARD_PATH, "cache");
			if(!cache.exists()){
				cache.mkdirs();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		System.gc();
	}

	
}
