package miyou;

import miyou.createtiezi.CreateTieziActivity;
import miyou.extra.ShowToast;
import miyou.fragment.FragmentAllMiBo;
import miyou.fragment.FragmentChat;
import miyou.fragment.FragmentContact;
import miyou.fragment.FragmentFriendMiBo;
import miyou.fragment.FragmentHotMiBo;
import miyou.fragment.FragmentI;
import miyou.login.LoginActivity;
import miyou.user.User;
import statics.BroadcastString;
import statics.ChannelCodes;
import utilclass.NotificationIcon;

import com.luluandroid.miyou.R;

import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SpinnerAdapter;
import application.control.ManagerApplication;

public class MainActivity extends ActionBarActivity {
	private LocalBroadcastManager lbm;
	private LoginReceiver loginReceiver;
	private FragmentChangeReceiver mFragmentChangeReceiver;
	private Fragment mShowFragment;
	private RadioGroup dockbuttons;
	private ActionBar actionBarSpinner;
	private NotificationIcon mNotificationIcon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			mShowFragment = FragmentAllMiBo.getInstance(this);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, mShowFragment).commit();
		}
		initBroadcastReceiver();
		IsLogin();
		initView();
		initRadioGroup();
		initSpinner();
		ManagerApplication.getInstance().addActivity(this);
	}

	private void initView() {
		dockbuttons = (RadioGroup) this.findViewById(R.id.dockbuttons_group);
	}
	
	private void initSpinner(){
		actionBarSpinner = getSupportActionBar();
		actionBarSpinner.setNavigationMode(actionBarSpinner.NAVIGATION_MODE_LIST);
		actionBarSpinner.setDisplayShowTitleEnabled(false);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_array, android.R.layout.simple_spinner_dropdown_item);
		actionBarSpinner.setListNavigationCallbacks(mSpinnerAdapter, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				// TODO Auto-generated method stub\
				lbm = LocalBroadcastManager.getInstance(MainActivity.this);
				switch(itemPosition){
				case 0:
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_ALLMIBO));
					break;
				case 1:
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_HOTMIBO));
					break;
				case 2:
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_FRIENDMIBO));
					break;
					default:
						break;
				}
				return true;
			}
		});
		
	}

	public void initBroadcastReceiver() {
		lbm = LocalBroadcastManager.getInstance(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadcastString.ACTION_LOGIN_SUCCESS_RETURN_TO_MAIN);
		loginReceiver = new LoginReceiver();
		lbm.registerReceiver(loginReceiver, filter);
		mFragmentChangeReceiver = new FragmentChangeReceiver();
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(BroadcastString.ACTION_FRAGMENT_ALLMIBO);
		filter2.addAction(BroadcastString.ACTION_FRAGMENT_HOTMIBO);
		filter2.addAction(BroadcastString.ACTION_FRAGMENT_FRIENDMIBO);
		lbm.registerReceiver(mFragmentChangeReceiver, filter2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		lbm.unregisterReceiver(loginReceiver);
		lbm.unregisterReceiver(mFragmentChangeReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case R.id.actions_menu_create_tiezi:
			ShowToast.showShortToast(this, "发帖");
			Intent intent = new Intent(MainActivity.this,CreateTieziActivity.class);
			startActivityForResult(intent, ChannelCodes.CREATE_TIEZI);
			break;
		case R.id.actions_menu_message:
			ShowToast.showShortToast(this, "消息");
			break;
		case R.id.actions_menu_refresh:
			ShowToast.showShortToast(this, "刷新");
			break;
		case R.id.actions_menu_search:
			ShowToast.showShortToast(this, "搜贴");
			break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void IsLogin() {
		if (User.getUserIsntance().isLogin(this)) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	private void initRadioGroup() {
		/*mNotificationIcon = NotificationIcon.getInstance(MainActivity.this);
		mNotificationIcon.Marknewtips(findViewById(R.id.dockbarbuttons_mibo), "5", "TAG_marknewtips_mibo");
		mNotificationIcon.Marknewtips(findViewById(R.id.dockbarbuttons_chat), "3", "TAG_marknewtips_chat");*/
		dockbuttons.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				lbm = LocalBroadcastManager.getInstance(MainActivity.this);
				switch (checkedId) {
				case R.id.dockbarbuttons_mibo:
					/*mNotificationIcon.dismissBadgeViewWithTag("TAG_marknewtips_mibo");*/
					lbm.sendBroadcast(new Intent(BroadcastString.ACTION_FRAGMENT_ALLMIBO));
					
					break;
				case R.id.dockbarbuttons_chat:
					/*mNotificationIcon.dismissBadgeViewWithTag("TAG_marknewtips_chat");*/
					if (mShowFragment != FragmentChat
							.getInstance(MainActivity.this)) {
						mShowFragment = FragmentChat
								.getInstance(MainActivity.this);
					}
					
					break;
				case R.id.dockbarbuttons_contact:
					if (mShowFragment != FragmentContact
							.getInstance(MainActivity.this)) {
						mShowFragment = FragmentContact
								.getInstance(MainActivity.this);
					}
					break;
				case R.id.dockbarbuttons_i:
					if (mShowFragment != FragmentI
							.getInstance(MainActivity.this)) {
						mShowFragment = FragmentI
								.getInstance(MainActivity.this);
					}
					break;
				default:
					break;
				}
				if(mShowFragment!=null){
					switchFragment(mShowFragment);
				}else
				Log.e("fragment_error", "mShowFragment----->为空");
				
			}
		});
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case ChannelCodes.CREATE_TIEZI_SUCCESS:
			ShowToast.showShortToast(this, "发帖成功");
			break;
		case ChannelCodes.CREATE_TIEZI_FAIL:
			ShowToast.showShortToast(this, "发帖失败");
			break;
		default:
			break;
		}
	}

	public void switchFragment(Fragment fragment){
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
	}

	public class LoginReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(
					BroadcastString.ACTION_LOGIN_SUCCESS_RETURN_TO_MAIN)) {
				ShowToast.showShortToast(context, "跳转成功");
			}
		}

	}
	
	public class FragmentChangeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(BroadcastString.ACTION_FRAGMENT_ALLMIBO)){
				if (mShowFragment != FragmentAllMiBo
						.getInstance(MainActivity.this)) {
					mShowFragment = FragmentAllMiBo
							.getInstance(MainActivity.this);
				}
			}else if(intent.getAction().equals(BroadcastString.ACTION_FRAGMENT_HOTMIBO)){
				if (mShowFragment != FragmentHotMiBo
						.getInstance(MainActivity.this)) {
					mShowFragment = FragmentHotMiBo
							.getInstance(MainActivity.this);
				}
			}else if(intent.getAction().equals(BroadcastString.ACTION_FRAGMENT_FRIENDMIBO)){
				if (mShowFragment != FragmentFriendMiBo
						.getInstance(MainActivity.this)) {
					mShowFragment = FragmentFriendMiBo
							.getInstance(MainActivity.this);
				}
			}
			
			if(mShowFragment!=null){
				switchFragment(mShowFragment);
			}
		}
		
	}

}
