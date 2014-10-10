package miyou.receiver;

import miyou.extra.ShowToast;
import miyou.login.LoginActivity;
import miyou.user.User;
import statics.BroadcastString;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LoginReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().endsWith(BroadcastString.ACTION_LOGIN_SUCCESS))
		{
			ShowToast.showShortToast(context, "登录成功");
		}else if(intent.getAction().equals(BroadcastString.ACTION_LOGIN_FAILURE))
		{
			ShowToast.showShortToast(context, "登录失败");
		}
	}

}
