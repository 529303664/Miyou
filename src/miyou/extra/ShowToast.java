package miyou.extra;

import miyou.login.LoginActivity;
import miyou.user.User;
import android.content.Context;
import android.widget.Toast;

public class ShowToast {

	
	public static void showShortToast(Context context,String content)
	{
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
	public static void showLongToast(Context context,String content)
	{
		Toast.makeText(context, content, Toast.LENGTH_LONG).show();
	}
}
