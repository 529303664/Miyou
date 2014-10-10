package miyou.user;


import statics.BroadcastString;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordListener;
import cn.bmob.v3.listener.SaveListener;

public class User {
	private BmobUser currentUser;
	public String whatHappen;

	private static User user = null;
	
	private LocalBroadcastManager lbm;

	private User() {

	}

	public synchronized static User getUserIsntance() {
		if (user == null)
			user = new User();
		return user;
	}

	public BmobUser getCurrentUser(Context context) {
		currentUser = BmobUser.getCurrentUser(context);
		return currentUser;
	}

	public void toLogin(Context activity, String username, String password) {
		lbm = LocalBroadcastManager.getInstance(activity);
		BmobUser bulogin = new BmobUser();
		bulogin.setUsername(username);
		bulogin.setPassword(password);
		bulogin.login(activity, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				lbm.sendBroadcast(new Intent(BroadcastString.ACTION_LOGIN_SUCCESS));
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				whatHappen = arg1;
				Log.e("loginEror", arg1);
				lbm.sendBroadcast(new Intent(BroadcastString.ACTION_LOGIN_FAILURE));
			}
		});
	}

	public void toSignIn(Context activity, String username, String password) {
		lbm = LocalBroadcastManager.getInstance(activity);
		BmobUser bUser = new BmobUser();
		bUser.setUsername(username);
		bUser.setPassword(password);
		bUser.setEmail(username);
		bUser.signUp(activity, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				lbm.sendBroadcast(new Intent(BroadcastString.ACTION_SIGNIN_SUCCESS));
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				whatHappen = arg1;
				Log.e("signInError", arg1);
				lbm.sendBroadcast(new Intent(BroadcastString.ACTION_SIGNIN_FAILURE));
			}
		});
	}

	public void clearAuthorization(Context context) {
		BmobUser.logOut(context);
		currentUser = BmobUser.getCurrentUser(context);
	}

	public boolean isLogin(Context context) {
		BmobUser currentUser = BmobUser.getCurrentUser(context);
		if (currentUser == null) {
			return false;
		}
		return true;
	}

	public void resetUserPassword(Context context, String email) {
		lbm = LocalBroadcastManager.getInstance(context);
		BmobUser.resetPassword(context, email, new ResetPasswordListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				if(lbm.sendBroadcast(new Intent(BroadcastString.ACTION_EMAIL_RESET_SUCCESS)))
				Log.i("email_reset_broadcast", "已发送密码重置广播");
				Log.i("email_reset", "重置密码邮件已发");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				lbm.sendBroadcast(new Intent(BroadcastString.ACTION_EMAIL_RESET_FAILURE));
				whatHappen = arg1;
				Log.e("email_reset", arg1);
			}
		});
	}

}
