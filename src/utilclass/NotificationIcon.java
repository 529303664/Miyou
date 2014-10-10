package utilclass;

import com.readystatesoftware.viewbadger.BadgeView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

public class NotificationIcon {

	private Context context;
	private static NotificationIcon mNotificationIcon=null;
	private BadgeView badge;
	private NotificationIcon(Context context){
		this.context = context;
	}
	
	public static NotificationIcon getInstance(Context context){
		if(mNotificationIcon==null){
			mNotificationIcon = new NotificationIcon(context);
		}
		return mNotificationIcon;
	}
	
	public  void Marknewtips(View target,String number,String tag){
		if(null==findBadgeViewWithTag(tag)){
			badge = new BadgeView(context, target);
		}
		badge.setText(number);
		badge.setTag(tag);
		badge.show();
		/*badge.setClickable(false);
		badge.setActivated(false);*/
	}
	
	public BadgeView findBadgeViewWithTag(String tag){
		View fView = new View(context);
		badge = (BadgeView) fView.findViewWithTag(tag);
		if(badge!=null)
		return badge;
		else
			return null;
	}
	public boolean dismissBadgeViewWithTag(String tag){
		badge = findBadgeViewWithTag(tag);
		if(badge==null){
			Log.e("BadgeView_error", "BadgeView------>为空,无法删除");
			return false;
		}else
			badge.toggle();
		Log.e("BadgeView_error", "BadgeView------>删除完成");
			/*badge.setVisibility(View.GONE);*/
		return true;
	}
	
}
